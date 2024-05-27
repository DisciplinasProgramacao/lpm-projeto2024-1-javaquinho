package javaquinho.comidinhas.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa uma requisição feita por um cliente em um restaurante.
 */
@Entity
@Table(name = Requisicao.TABLE_NAME)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Requisicao {

    public static final String TABLE_NAME = "requisicao";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "mesa", nullable = true)
    private Mesa mesa;

    @Column(name = "quantPessoas", nullable = false)
    private int quantPessoas;

    @Column(name = "entrada", nullable = true)
    private LocalDateTime entrada;

    @Column(name = "saida", nullable = true)
    private LocalDateTime saida;

    @Column(name = "encerrada", nullable = false)
    private boolean encerrada;






    @OneToMany
    @JoinColumn(name = "produto_id")
    private List<Produto> produtos;

    public Requisicao() {
        this.produtos = new ArrayList<>();
    }

    /**
     * Adiciona um produto à lista de produtos da requisição.
     *
     * @param produto O produto a ser adicionado.
     */
    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }




    

    // @OneToOne
    // @JoinColumn(name = "produtos", nullable = true)
    // private List<Produto> produtos;

    /**
     * Construtor da classe Requisicao.
     * 
     * @param cliente O cliente que fez a requisição.
     * @param quantPessoas O número de pessoas para a requisição.
     */
    // public Requisicao(Cliente cliente, int quantPessoas) {
    //     this.quantPessoas = 1;
    //     if (quantPessoas > 1)
    //         this.quantPessoas = quantPessoas;
    //     this.cliente = cliente;
    //     entrada = saida = null;
    //     mesa = null;
    //     encerrada = false;
    //     produtos = new ArrayList<>();
    // }

    // /**
    //  * Encerra a requisição e desocupa a mesa associada.
    //  * 
    //  * @return A mesa que foi desocupada.
    //  */
    // public Mesa encerrar() {
    //     saida = LocalDateTime.now();
    //     mesa.desocupar();
    //     encerrada = true;
    //     return mesa;
    // }

    // /**
    //  * Aloca uma mesa à requisição se estiver disponível.
    //  * 
    //  * @param mesa A mesa a ser alocada.
    //  */
    // public void alocarMesa(Mesa mesa) {
    //     if (mesa.estahLiberada(quantPessoas)) {
    //         this.mesa = mesa;
    //         entrada = LocalDateTime.now();
    //         this.mesa.ocupar();
    //     }
    // }

    // /**
    //  * Verifica se a requisição está encerrada.
    //  * 
    //  * @return true se a requisição estiver encerrada, caso contrário, false.
    //  */
    // public boolean estahEncerrada() {
    //     return encerrada;
    // }

    // /**
    //  * Verifica se a requisição é da mesa com o ID fornecido.
    //  * 
    //  * @param idMesa O ID da mesa a ser verificado.
    //  * @return true se a requisição for da mesa com o ID fornecido, caso contrário, false.
    //  */
    // public boolean ehDaMesa(int idMesa) {
    //     return idMesa == mesa.getIdMesa();
    // }

    // /**
    //  * Retorna a quantidade de pessoas associadas à requisição.
    //  * 
    //  * @return A quantidade de pessoas.
    //  */
    // public int quantPessoas() {
    //     return quantPessoas;
    // }

    // /**
    //  * Adiciona um produto à lista de produtos da requisição.
    //  * 
    //  * @param produto O produto a ser adicionado.
    //  */
    // public void adicionarProduto(Produto produto) {
    //     produtos.add(produto);
    // }

    // /**
    //  * Calcula o valor total dos produtos na requisição.
    //  * 
    //  * @return O valor total dos produtos.
    //  */
    // public double calcularValorTotal() {
    //     double total = 0;
    //     for (Produto produto : produtos) {
    //         total += produto.getPreco();
    //     }
    //     return total;
    // }

    // /**
    //  * Calcula o valor por cliente na requisição.
    //  * 
    //  * @return O valor por cliente na requisição.
    //  */
    // public double calcularValorPorCliente() {
    //     return calcularValorTotal() / quantPessoas;
    // }

    // /**
    //  * Retorna uma representação em formato de string da requisição.
    //  * 
    //  * @return Uma string representando os detalhes da requisição.
    //  */
    // public String toString() {
    //     DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    //     StringBuilder stringReq = new StringBuilder(cliente.toString());
    //     if (mesa != null) {
    //         stringReq.append("\n").append(mesa.toString()).append("\n");
    //         stringReq.append("Entrada em ").append(formato.format(entrada)).append("\n");
    //         if (saida != null)
    //             stringReq.append("Saída em ").append(formato.format(saida)).append("\n");
    //     } else {
    //         stringReq.append(" Em espera.");
    //     }
    //     stringReq.append("\nProdutos:\n");
    //     for (Produto produto : produtos) {
    //         stringReq.append(produto.toString()).append("\n");
    //     }
    //     stringReq.append("Valor total: R$").append(calcularValorTotal()).append("\n");
    //     stringReq.append("Valor por cliente: R$").append(calcularValorPorCliente()).append("\n");
    //     return stringReq.toString();
    // }
}
