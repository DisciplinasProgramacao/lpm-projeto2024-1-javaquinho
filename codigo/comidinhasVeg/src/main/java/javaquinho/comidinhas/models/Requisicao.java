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

@Entity
@Table(name = Requisicao.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "entrada", columnDefinition = "DATETIME")
    private LocalDateTime entrada;

    @Column(name = "saida", columnDefinition = "DATETIME")
    private LocalDateTime saida;

    @Column(name = "encerrada", nullable = false)
    private boolean encerrada;

    @OneToOne
    @JoinColumn(name = "pedido", nullable = true)
    private Pedido pedido;

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
        // this.pedido = new Pedido(); 
    }

    public Requisicao(Cliente cliente, Integer quantPessoas) {
        this.cliente = cliente;
        this.quantPessoas = quantPessoas;
    }
  
    public Mesa encerrar() {
        if (mesa == null) {
            throw new IllegalStateException("Não é possível encerrar uma requisição sem uma mesa alocada.");
        }
        saida = LocalDateTime.now();
        mesa.desocupar();
        encerrada = true;
        return mesa;
    }

    public void alocarMesa(Mesa mesa) {
        if (mesa.estahLiberada(quantPessoas)) {
            this.mesa = mesa;
            entrada = LocalDateTime.now();
            this.mesa.ocupar();
        }
    }

    public boolean estahEncerrada() {
        return encerrada;
    }

    public boolean ehDaMesa(int idMesa) {
        return idMesa == mesa.getIdMesa();
    }

    public int quantPessoas() {
        return quantPessoas;
    }

    public double exibirConta() {
        return pedido.getSomarTotal();
    }

    public double exibirValorPorPessoa() {
        return pedido.getSomarTotal() / quantPessoas;
    }

    public void fecharPedido() {
        if (pedido != null) {
            pedido = null;
        }
    }

    public void adicionarProduto(Produto produto) {
        if (this.encerrada) {
            throw new IllegalStateException("Não é possível adicionar produtos a uma requisição finalizada.");
        }
        if (this.pedido == null) {
            // this.pedido = new Pedido();
        }
        try {
            this.pedido.addProduto(produto);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar produto ao pedido: " + e.getMessage(), e);
        }
    }

    public void removerProduto(Produto produto) {
        if (this.encerrada) {
            throw new IllegalStateException("Não é possível remover produtos de uma requisição finalizada.");
        }
        if (this.pedido != null) {
            this.pedido.removeProduto(produto);
        }
    }

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
            // for (Produto produto : pedido.getProdutos()) {
            //     stringReq.append(produto.getNome()).append(" - R$").append(produto.getPreco()).append("\n");
            // }
            stringReq.append("Total: ").append(exibirConta()).append("\n");
            stringReq.append("Valor por pessoa: ").append(exibirValorPorPessoa()).append("\n");
        }
        return stringReq.toString();
    }
}
