package javaquinho.comidinhas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@Entity
@Table(name = "pedido")
public abstract class Pedido<T extends Menu> {
    @OneToOne
    @JoinColumn(name = "requisicao", nullable = true)
    private Requisicao requisicao;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "pedido_produto",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    protected List<Produto> produtos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "menu_id", nullable = true)
    private Menu menu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Requisicao getRequisicao(){
        return requisicao;
    }

    public void setRequisicao(Requisicao req){
        this.requisicao = req;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public abstract void addProduto(Produto produto) throws LimiteProdutosException, NaoExisteMenuException, ProdutoNaoExisteNoMenuException;

    public void removeProduto(Produto produto) {
        
        this.produtos.remove(produto);
    }

    public Menu getMenu(){
        return menu;
    }

    public void setMenu(T menu){
        this.menu = menu;
    }

    public abstract double getSomarTotal();
}
