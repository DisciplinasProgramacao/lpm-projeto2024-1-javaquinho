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
 * Representa uma requisição feita por um cliente em um restaurante.
 */
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

    @Column(name = "entrada", nullable = true)
    private LocalDateTime entrada;

    @Column(name = "saida", nullable = true)
    private LocalDateTime saida;

    @Column(name = "atendida", nullable = false)
    private Boolean atendida;

    @Column(name = "encerrada", nullable = false)
    private Boolean encerrada;

    @OneToOne
    @JoinColumn(name = "pedido", nullable = true)
    private Pedido pedido;

    	@ManyToOne
	@JoinColumn(name = "restaurante_id")
	@JsonBackReference
	private Restaurante restaurante;


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
    }
  
    public void encerrar(Mesa mesa) {
        saida = LocalDateTime.now();
        mesa.desocupar();
        encerrada = true;
    }

    public void alocarMesa(Mesa mesa) {
            this.mesa = mesa;
            this.entrada = LocalDateTime.now();
            this.atendida = true;
    }

    public boolean estahEncerrada() {
        return encerrada;
    }

    public boolean ehDaMesa(Long idMesa) {
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
            stringReq.append(pedido.toString()).append("\n");
            stringReq.append("Total: ").append(exibirConta()).append("\n");
            stringReq.append("Valor por pessoa: ").append(exibirValorPorPessoa()).append("\n");
        }
        return stringReq.toString();
    }
}
