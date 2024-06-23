package javaquinho.comidinhas.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A entidade Requisicao representa um pedido de um cliente em um restaurante,
 * incluindo detalhes como mesa, quantidade de pessoas, horários de entrada e saída,
 * estado de atendimento e encerramento, e o pedido associado.
 */
@Entity
@Table(name = Requisicao.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Requisicao {

    // Nome da tabela no banco de dados
    public static final String TABLE_NAME = "requisicao";

    // Identificador único da requisição
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    // Cliente associado à requisição
    @OneToOne
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente;

    // Mesa associada à requisição
    @OneToOne
    @JoinColumn(name = "mesa", nullable = true)
    private Mesa mesa;

    // Quantidade de pessoas na requisição
    @Column(name = "quantPessoas", nullable = false)
    private int quantPessoas;

    // Horário de entrada na mesa
    @Column(name = "entrada", nullable = true)
    private LocalDateTime entrada;

    // Horário de saída da mesa
    @Column(name = "saida", nullable = true)
    private LocalDateTime saida;

    // Indica se a requisição foi atendida
    @Column(name = "atendida", nullable = false)
    private Boolean atendida;

    // Indica se a requisição foi encerrada
    @Column(name = "encerrada", nullable = false)
    private Boolean encerrada;

    // Pedido associado à requisição
    @OneToOne
    @JoinColumn(name = "pedido", nullable = true)
    private Pedido pedido;

    // Restaurante associado à requisição
    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    @JsonBackReference
    private Restaurante restaurante;

    /**
     * Construtor que inicializa uma requisição com um cliente, quantidade de pessoas e restaurante.
     * 
     * @param cliente Cliente da requisição.
     * @param quantPessoas Quantidade de pessoas na requisição.
     * @param r Restaurante associado.
     * @throws IllegalArgumentException se o cliente for nulo ou a quantidade de pessoas for menor que 1.
     */
    public Requisicao(Cliente cliente, int quantPessoas, Restaurante r) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        if (quantPessoas < 1) {
            throw new IllegalArgumentException("A quantidade de pessoas deve ser pelo menos 1");
        }
        this.quantPessoas = quantPessoas;
        this.cliente = cliente;
        this.entrada = null;
        this.saida = null;
        this.mesa = null;
        this.atendida = false;
        this.encerrada = false;
        this.restaurante = r;
        this.pedido = new Pedido(); 
        this.pedido.setCliente(cliente); 
    }

    /**
     * Método para encerrar a requisição, liberando a mesa associada.
     * 
     * @param mesa Mesa a ser desocupada.
     * @throws IllegalStateException se a mesa for nula.
     */
    public void encerrar(Mesa mesa) {
        if (mesa == null) {
            throw new IllegalStateException("Não é possível encerrar uma requisição sem uma mesa alocada.");
        }
        saida = LocalDateTime.now();
        mesa.desocupar();
        encerrada = true;
    }

    /**
     * Método para alocar uma mesa à requisição e marcar o horário de entrada.
     * 
     * @param mesa Mesa a ser alocada.
     */
    public void alocarMesa(Mesa mesa) {
        this.mesa = mesa;
        this.entrada = LocalDateTime.now();
        this.atendida = true;
        this.mesa.ocupar();
    }

    /**
     * Verifica se a requisição está encerrada.
     * 
     * @return true se a requisição estiver encerrada, false caso contrário.
     */
    public boolean estahEncerrada() {
        return encerrada;
    }

    /**
     * Verifica se a requisição é de uma determinada mesa.
     * 
     * @param idMesa ID da mesa.
     * @return true se a requisição for da mesa especificada, false caso contrário.
     */
    public boolean ehDaMesa(Long idMesa) {
        return idMesa == mesa.getIdMesa();
    }

    /**
     * Retorna a quantidade de pessoas na requisição.
     * 
     * @return Quantidade de pessoas.
     */
    public int quantPessoas() {
        return quantPessoas;
    }

    /**
     * Exibe o total da conta do pedido associado.
     * 
     * @return Valor total do pedido.
     */
    public double exibirConta() {
        return pedido.getSomarTotal();
    }

    /**
     * Exibe o valor da conta dividido pelo número de pessoas.
     * 
     * @return Valor por pessoa.
     */
    public double exibirValorPorPessoa() {
        return pedido.getSomarTotal() / quantPessoas;
    }

    /**
     * Fecha o pedido associado à requisição.
     */
    public void fecharPedido() {
        if (pedido != null) {
            pedido = null;
        }
    }

    /**
     * Adiciona um produto ao pedido associado à requisição.
     * 
     * @param produto Produto a ser adicionado.
     * @throws IllegalStateException se a requisição estiver encerrada.
     * @throws RuntimeException se ocorrer um erro ao adicionar o produto.
     */
    public void adicionarProduto(Produto produto) {
        if (this.encerrada) {
            throw new IllegalStateException("Não é possível adicionar produtos a uma requisição finalizada.");
        }
        if (this.pedido == null) {
            this.pedido = new Pedido();
        }
        try {
            this.pedido.addProduto(produto);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar produto ao pedido: " + e.getMessage(), e);
        }
    }

    /**
     * Remove um produto do pedido associado à requisição.
     * 
     * @param produto Produto a ser removido.
     * @throws IllegalStateException se a requisição estiver encerrada.
     */
    public void removerProduto(Produto produto) {
        if (this.encerrada) {
            throw new IllegalStateException("Não é possível remover produtos de uma requisição finalizada.");
        }
        if (this.pedido != null) {
            this.pedido.removeProduto(produto);
        }
    }

    /**
     * Retorna uma representação em string da requisição, incluindo informações do cliente,
     * mesa, horários de entrada e saída, e produtos no pedido.
     * 
     * @return String representando a requisição.
     */
    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder stringReq = new StringBuilder(cliente.toString());
        if (mesa != null) {
            stringReq.append("\n").append(mesa.toString()).append("\n");
            stringReq.append("Entrada em ").append(formato.format(entrada)).append("\n");
            if (saida != null)
                stringReq.append("Saída em ").append(formato.format(saida)).append("\n");
        } else {
            stringReq.append(" Em espera.");
        }
        stringReq.append("\nProdutos:\n");
        if (pedido != null) {
            for (Produto produto : pedido.getProdutos()) {
                stringReq.append(produto.getNome()).append(" - R$").append(produto.getPreco()).append("\n");
            }
            stringReq.append("Total: ").append(exibirConta()).append("\n");
            stringReq.append("Valor por pessoa: ").append(exibirValorPorPessoa()).append("\n");
        }
        return stringReq.toString();
    }
}
