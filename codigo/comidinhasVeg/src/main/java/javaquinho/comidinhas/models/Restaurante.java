package javaquinho.comidinhas.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa um restaurante.
 */
@Entity
@Table(name = "restaurante")
@Getter
@Setter
@Component
public class Restaurante {

    /**
     * Identificador único do restaurante.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long idRestaurante;

    /**
     * Mapa das mesas do restaurante e suas respectivas requisições.
     */
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Map<Mesa, Requisicao> mesas = new HashMap<>();

    /**
     * Lista de requisições em fila para serem atendidas.
     */
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Requisicao> listaRequisicao = new ArrayList<>();

    /**
     * Histórico de requisições já atendidas.
     */
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Requisicao> historicoRequisicao = new ArrayList<>();

    /**
     * Mapa dos clientes cadastrados no restaurante.
     */
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Map<String, Cliente> clientes = new HashMap<>();

    /**
     * Menu atual do restaurante.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Menu menuAberto;

    /**
     * Construtor padrão que inicializa as mesas e o menu do restaurante.
     */
    public Restaurante() {
        iniciaMesas();
        this.menuAberto = new Menu();
        iniciaMenu();
    }

    /**
     * Inicializa as mesas do restaurante.
     */
    private void iniciaMesas() {
        int[] capacidades = { 4, 6, 8 };
        int[] quant = { 4, 4, 2 };

        for (int i = 0; i < quant.length; i++) {
            int quantidade = quant[i];
            for (int j = 0; j < quantidade; j++) {
                Mesa mesa = new Mesa(null, capacidades[i], false);
                mesas.put(mesa, null);
            }
        }
    }

    /**
     * Inicializa o menu do restaurante com produtos predefinidos.
     */
    private void iniciaMenu() {
        String[] nomes = {
            "Moqueca de Pamito", "Falafel Assado", "Salada Primavera com Macarrão Konjac",
            "Escondidinho de Inhame", "Strogonoff de Cogumelos", "Caçarola de legumes", "Água", "Copo de Suco",
            "Refrigerante orgânico", "Cerveja vegana", "Taça de vinho vegano"
        };
        Double[] precos = { 32.0, 20.0, 25.0, 18.0, 35.0, 22.0, 3.0, 7.0, 7.0, 9.0, 18.0 };

        for (int i = 0; i < nomes.length; i++) {
            Produto p = new Produto(null, nomes[i], precos[i], menuAberto);
            menuAberto.adicionarProduto(p);
        }
    }

    /**
     * Localiza uma mesa pelo seu identificador.
     * 
     * @param idMesa O identificador da mesa.
     * @return A mesa correspondente ao identificador.
     * @throws RuntimeException Se a mesa não for encontrada.
     */
    private Mesa localizarMesa(Long idMesa) {
        return mesas.keySet().stream()
            .filter(m -> m.getIdMesa().equals(idMesa))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Id de mesa não encontrado."));
    }

    /**
     * Verifica se um cliente já está cadastrado no restaurante.
     * 
     * @param cpf O CPF do cliente.
     * @return True se o cliente já estiver cadastrado, false caso contrário.
     */
    private Boolean clienteExiste(String cpf) {
        return clientes.containsKey(cpf);
    }

    /**
     * Processa a fila de requisições, alocando as requisições nas mesas disponíveis.
     */
    public void processarFilaRequisicao() {
        listaRequisicao.stream()
            .findFirst()
            .ifPresent(req -> {
                Mesa mesa = mesas.keySet().stream()
                    .filter(m -> m.estahLiberada(req.getQuantPessoas()))
                    .findFirst()
                    .orElse(null);

                if (mesa != null) {
                    req.alocarMesa(mesa);
                    mesa.ocupar();
                    mesas.put(mesa, req);
                    listaRequisicao.remove(req);
                }
            });
    }

    /**
     * Cria uma nova requisição a partir do CPF do cliente e da quantidade de pessoas.
     * 
     * @param cpf O CPF do cliente.
     * @param qntPessoas A quantidade de pessoas na requisição.
     * @throws RuntimeException Se o cliente não estiver cadastrado.
     */
    public void criarRequisicao(String cpf, int qntPessoas) {
        if (!clienteExiste(cpf)) throw new RuntimeException("Cliente não cadastrado.");
        Requisicao requisicao = new Requisicao(clientes.get(cpf), qntPessoas);
        listaRequisicao.add(requisicao);
        processarFilaRequisicao();
    }

    /**
     * Cadastra um novo cliente no restaurante.
     * 
     * @param cpf O CPF do cliente.
     * @param nome O nome do cliente.
     * @param telefone O telefone do cliente.
     */
    public void novoCliente(String cpf, String nome, String telefone) {
        Cliente c = new Cliente(null, cpf, nome, telefone);
        clientes.put(cpf, c);
    }

    /**
     * Encerra o atendimento de uma requisição através do número da mesa que ela está alocada.
     * 
     * @param idMesa O identificador da mesa.
     */
    public void encerrarAtendimento(Long idMesa) {
        Mesa mesa = localizarMesa(idMesa);
        Requisicao req = mesas.get(mesa);
        req.encerrar(mesa);
        mesas.put(mesa, null);
        historicoRequisicao.add(req);
    }

    /**
     * Retorna uma lista de requisições já atendidas.
     * 
     * @return Uma string com a lista de requisições atendidas.
     */
    public String historicoAtendimento() {
        return historicoRequisicao.stream()
            .map(Requisicao::toString)
            .collect(Collectors.joining("\n"));
    }

    /**
     * Retorna uma lista de requisições não atendidas.
     * 
     * @return Uma string com a lista de requisições não atendidas.
     */
    public String filaDeAtendimento() {
        return listaRequisicao.stream()
            .map(Requisicao::toString)
            .collect(Collectors.joining("\n"));
    }

    /**
     * Adiciona um produto ao pedido de uma mesa específica.
     * 
     * @param idMesa O identificador da mesa.
     * @param nomeProduto O nome do produto a ser adicionado.
     */
    public void addProdutoPedido(Long idMesa, String nomeProduto) {
        Mesa mesa = localizarMesa(idMesa);
        Requisicao r = mesas.get(mesa);
        r.adicionarProduto(menuAberto.localizarProduto(nomeProduto));
    }

}
