package javaquinho.comidinhas.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que representa uma requisição em um sistema de restaurante.
 * Contém informações sobre o cliente, mesa, quantidade de pessoas, horários de entrada e saída, 
 * se está encerrada e o pedido associado.
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

    // Horário de entrada
    @Column(name = "entrada", columnDefinition = "DATETIME")
    private LocalDateTime entrada;

    // Horário de saída
    @Column(name = "saida", columnDefinition = "DATETIME")
    private LocalDateTime saida;

    // Indica se a requisição está encerrada
    @Column(name = "encerrada", nullable = false)
    private boolean encerrada;

    // Pedido associado à requisição
    @OneToOne
    @JoinColumn(name = "pedido", nullable = true)
    private Pedido pedido;

    /**
     * Construtor da classe que inicializa a requisição com um cliente e quantidade de pessoas.
     * 
     * @param cliente Cliente associado à requisição.
     * @param quantPessoas Quantidade de pessoas na requisição.
     * @throws IllegalArgumentException se o cliente for nulo ou a quantidade de pessoas for menor que 1.
     */
    public Requisicao(Cliente cliente, int quantPessoas) {
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
        this.encerrada = false;
    }

    /**
     * Construtor da classe que inicializa a requisição com um cliente e quantidade de pessoas.
     * 
     * @param cliente Cliente associado à requisição.
     * @param quantPessoas Quantidade de pessoas na requisição.
     */
    public Requisicao(Cliente cliente, Integer quantPessoas) {
        this.cliente = cliente;
        this.quantPessoas = quantPessoas;
    }

    /**
     * Método para encerrar a requisição, liberando a mesa associada.
     * Define o horário de saída como o momento atual, desocupa a mesa e marca a requisição como encerrada.
     * 
     * @throws IllegalStateException se a mesa for nula.
     */
    public void encerrar() {
        if (this.mesa == null) {
            throw new IllegalStateException("Nenhuma mesa está alocada a esta requisição.");
        }
        this.saida = LocalDateTime.now();
        this.mesa.desocupar();
        this.encerrada = true;
        this.mesa = null;
    }

    /**
     * Método para alocar uma mesa à requisição, se a mesa estiver liberada para a quantidade de pessoas.
     * Define o horário de entrada como o momento atual e ocupa a mesa.
     * 
     * @param mesa Mesa a ser alocada.
     */
    public void alocarMesa(Mesa mesa) {
        if (mesa.estahLiberada(quantPessoas)) {
            this.mesa = mesa;
            entrada = LocalDateTime.now();
            this.mesa.ocupar();
        }
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
     * Verifica se a requisição está associada a uma mesa específica.
     * 
     * @param idMesa Identificador da mesa.
     * @return true se a requisição estiver associada à mesa, false caso contrário.
     */
    public boolean ehDaMesa(int idMesa) {
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
     * Exibe o total da conta do pedido associado à requisição.
     * 
     * @return Valor total da conta.
     */
    public double exibirConta() {
        return pedido.getSomarTotal();
    }

    /**
     * Exibe o valor da conta dividido pelo número de pessoas na requisição.
     * 
     * @return Valor por pessoa.
     */
    public double exibirValorPorPessoa() {
        return pedido.getSomarTotal() / quantPessoas;
    }

    /**
     * Fecha o pedido associado à requisição, definindo-o como nulo.
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
     */
    public void adicionarProduto(Produto produto) {
        if (this.encerrada) {
            throw new IllegalStateException("Não é possível adicionar produtos a uma requisição finalizada.");
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
     * Retorna uma representação textual da requisição.
     * Inclui informações sobre o cliente, mesa, horários, produtos e valores.
     * 
     * @return Representação textual da requisição.
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
            stringReq.append("Total: ").append(exibirConta()).append("\n");
            stringReq.append("Valor por pessoa: ").append(exibirValorPorPessoa()).append("\n");
        }
        return stringReq.toString();
    }
}
