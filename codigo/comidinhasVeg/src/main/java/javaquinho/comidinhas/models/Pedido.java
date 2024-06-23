package javaquinho.comidinhas.models;

import jakarta.persistence.Column;
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
import javaquinho.comidinhas.excecoes.MenuInvalidoException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@Entity
@Table(name = "pedido")
public abstract class Pedido<T extends Menu> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "pedido_produto",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    protected List<Produto> produtos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "menu_id", nullable = true)
    protected Menu menu;

    @Column(name = "qntPessoas", nullable = false)
    protected int qntPessoas;

    public Menu getMenu(){
        return menu;
    }

    public abstract void setMenu(T menu) throws MenuInvalidoException;

    public void setQntPessoas(int qnt){
        this.qntPessoas = qnt;
    }

    public int getQntPessoas(){
        return this.qntPessoas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public abstract void addProduto(Produto produto) throws LimiteProdutosException, NaoExisteMenuException, ProdutoNaoExisteNoMenuException;

    public void removeProduto(Produto produto) {
        
        this.produtos.remove(produto);
    }

    public abstract double getSomarTotal();
}
