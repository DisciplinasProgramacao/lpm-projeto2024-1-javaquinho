package javaquinho.comidinhas.models;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.repositories.ClienteRepository;
import javaquinho.comidinhas.repositories.MesaRepository;
import javaquinho.comidinhas.repositories.ProdutoRepository;
import javaquinho.comidinhas.repositories.MenuRepository;
import javaquinho.comidinhas.repositories.RequisicaoRepository;

/**
 * Serviço que gerencia operações relacionadas ao restaurante. Criação de
 * clientes, mesas, produtos, requisições e menus.
 */
@Service
public class Restaurante {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RequisicaoRepository requisicaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Queue<Requisicao> filaEspera = new LinkedList<>();

    /**
     * Cria novos clientes no sistema através de uma lista de objetos.
     * 
     * @param clientes Lista de clientes a serem criados
     * @return Lista de clientes criados
     */
    public List<Cliente> criarClientes(List<Cliente> clientes) {
        return clienteRepository.saveAll(clientes);
    }

    /**
     * Cria novas mesas no restaurante, contabilizando as que já existem + as que
     * estão sendo criadas.
     * 
     * @param mesas Lista de mesas a serem criadas
     * @return Lista de mesas criadas
     * @throws IllegalStateException Se o número máximo de mesas (10) for excedido
     */
    public List<Mesa> criarMesas(List<Mesa> mesas) {
        long count = mesaRepository.count();
        if (count + mesas.size() > 10) {
            throw new IllegalStateException("Número máximo de mesas atingido.");
        }
        return mesaRepository.saveAll(mesas);
    }

    /**
     * Cria novos produtos no restaurante.
     * 
     * @param produtos Lista de produtos a serem criados
     * @return Mensagem indicando a criação dos produtos
     */
    public String criarProdutos(List<Produto> produtos) {
        produtoRepository.saveAll(produtos);
        return "Os produtos do restaurante foram iniciados";
    }

    /**
     * Tenta alocar uma mesa para uma requisição com base na capacidade necessária.
     * 
     * @param requisicaoId ID da requisição
     * @return Mensagem indicando o resultado da alocação da mesa
     */
    public String alocarMesaParaRequisicao(Long requisicaoId) {
        Requisicao requisicao = requisicaoRepository.findById(requisicaoId).orElse(null);
        if (requisicao == null) {
            return "Requisição não encontrada.";
        }

        int quantPessoas = requisicao.getQuantPessoas();
        List<Mesa> mesasDisponiveis = mesaRepository.findByCapacidadeAndOcupada(quantPessoas, false);

        if (mesasDisponiveis.isEmpty()) {
            filaEspera.add(requisicao);
            return "Não há mesas disponíveis. A requisição foi adicionada à fila de espera.";
        }

        Mesa mesa = null;
        for (Mesa m : mesasDisponiveis) {
            if (m.getCapacidade() >= quantPessoas) {
                mesa = m;
                break;
            }
        }

        if (mesa == null) {
            filaEspera.add(requisicao);
            return "Não há mesas disponíveis com capacidade suficiente. A requisição foi adicionada à fila de espera.";
        }

        requisicao.alocarMesa(mesa);
        requisicaoRepository.save(requisicao);
        return "Mesa alocada para a requisição com sucesso!";
    }

    /**
     * Cria uma requisição para um cliente, tentando alocá-lo numa mesa
     * automaticamente.
     * Se não houver mesas disponíveis, adiciona a requisição à fila de espera.
     * 
     * @param requisicao Requisição a ser criada
     * @return Mensagem indicando o resultado da criação da requisição
     * @throws IllegalArgumentException Se o cliente for nulo ou a quantidade de
     *                                  pessoas for menor que 1
     */
    public String criarRequisicao(Requisicao requisicao) {
        if (requisicao.getCliente() == null || requisicao.getQuantPessoas() < 1) {
            throw new IllegalArgumentException(
                    "Cliente não pode ser nulo e a quantidade de pessoas deve ser pelo menos 1.");
        }

        requisicao = requisicaoRepository.save(requisicao);

        String resultadoAlocacao = alocarMesaParaRequisicao(requisicao.getId());

        if (resultadoAlocacao.startsWith("Mesa alocada")) {
            requisicao.setEntrada(LocalDateTime.now());
            requisicaoRepository.save(requisicao);
        }

        if (!resultadoAlocacao.startsWith("Mesa alocada")) {
            filaEspera.add(requisicao);
        }

        return resultadoAlocacao;
    }

    /**
     * Finaliza uma requisição, desalocando a mesa e, se houver, realocando a
     * próxima da fila de espera.
     * Calcula o total do pedido ao finalizar.
     * 
     * @param requisicaoId ID da requisição a ser finalizada
     * @return Mensagem indicando o resultado da finalização da requisição e o total
     *         do pedido
     */
    public String desalocarMesaDeRequisicao(Long requisicaoId) {
        Requisicao requisicao = requisicaoRepository.findById(requisicaoId).orElse(null);
        if (requisicao == null) {
            return "Requisição não encontrada.";
        }

        if (requisicao.getMesa() == null) {
            return "Nenhuma mesa está alocada a esta requisição.";
        }

        try {
            requisicao.encerrar();
            requisicaoRepository.save(requisicao);

            if (!filaEspera.isEmpty()) {
                Requisicao proximaRequisicao = filaEspera.poll();
                alocarMesaParaRequisicao(proximaRequisicao.getId());
            }

            double totalPedido = requisicao.getPedido().getSomarTotal();
            return "Mesa desalocada. Requisição finalizada com sucesso!\nTotal do Pedido: " + totalPedido;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    /**
     * Cria um menu aberto com os produtos especificados.
     * 
     * @param produtos Conjunto de produtos do menu aberto
     * @return Mensagem indicando o resultado da criação do menu aberto
     */
    public String criarMenuAberto(Set<Produto> produtos) {
        MenuAberto menuAberto = new MenuAberto(produtos);
        menuRepository.save(menuAberto);
        return "Menu aberto criado com sucesso!";
    }

    /**
     * Cria um menu fechado com os produtos especificados.
     * 
     * @param produtos Conjunto de produtos do menu fechado
     * @return Mensagem indicando o resultado da criação do menu fechado
     * @throws LimiteProdutosException Se o número de produtos no menu fechado
     *                                 exceder o limite permitido
     */
    public String criarMenuFechado(Set<Produto> produtos) throws LimiteProdutosException {
        MenuFechado menuFechado = new MenuFechado(produtos);
        menuRepository.save(menuFechado);
        return "Menu fechado criado com sucesso!";
    }
}