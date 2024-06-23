package javaquinho.comidinhas.models;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javaquinho.comidinhas.repositories.ClienteRepository;
import javaquinho.comidinhas.repositories.MesaRepository;
import javaquinho.comidinhas.repositories.MenuRepository;
import javaquinho.comidinhas.repositories.RequisicaoRepository;

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

    private Queue<Requisicao> filaEspera = new LinkedList<>();

    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> criarClientes(List<Cliente> clientes) {
        return clienteRepository.saveAll(clientes);
    }

    public Mesa criarMesa(Mesa mesa) {
        long count = mesaRepository.count();
        if (count >= 10) {
            throw new IllegalStateException("Número máximo de mesas atingido.");
        }
        return mesaRepository.save(mesa);
    }

    public List<Mesa> criarMesas(List<Mesa> mesas) {
        long count = mesaRepository.count();
        if (count + mesas.size() > 10) {
            throw new IllegalStateException("Número máximo de mesas atingido.");
        }
        return mesaRepository.saveAll(mesas);
    }

    public Menu criarMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public String criarRequisicao(Requisicao requisicao) {
        if (requisicao.getCliente() == null || requisicao.getQuantPessoas() < 1) {
            throw new IllegalArgumentException("Cliente não pode ser nulo e a quantidade de pessoas deve ser pelo menos 1.");
        }

        requisicao = requisicaoRepository.save(requisicao);
        return alocarMesaParaRequisicao(requisicao.getId());
    }

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
        return "Mesa alocada com sucesso.";
    }

    public String desalocarMesaDeRequisicao(Long requisicaoId) {
        Requisicao requisicao = requisicaoRepository.findById(requisicaoId).orElse(null);
        if (requisicao == null) {
            return "Requisição não encontrada.";
        }

        if (requisicao.getMesa() == null) {
            return "Nenhuma mesa está alocada a esta requisição.";
        }

        requisicao.setSaida(LocalDateTime.now());
        requisicao.getMesa().desocupar();
        requisicao.setMesa(null);
        requisicao.setEncerrada(true);
        requisicaoRepository.save(requisicao);

        if (!filaEspera.isEmpty()) {
            Requisicao proximaRequisicao = filaEspera.poll();
            alocarMesaParaRequisicao(proximaRequisicao.getId());
        }

        return "Mesa desalocada com sucesso.";
    }
}
