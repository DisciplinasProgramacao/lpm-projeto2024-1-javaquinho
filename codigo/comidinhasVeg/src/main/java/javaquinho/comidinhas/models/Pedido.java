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
import jakarta.persistence.Table;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.MenuInvalidoException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata representando um Pedido.
 *
 * @param <T> tipo de Menu associado ao Pedido
 */
@Entity
@Table(name = "pedido")
public abstract class Pedido<T extends Menu> {

    /**
     * ID do pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Lista de produtos associados ao pedido.
     */
    @ManyToMany
    @JoinTable(name = "pedido_produto", joinColumns = @JoinColumn(name = "pedido_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    protected List<Produto> produtos = new ArrayList<>();

    /**
     * Menu associado ao pedido.
     */
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = true)
    protected Menu menu;

    /**
     * Quantidade de pessoas associada ao pedido.
     */
    @Column(name = "qntPessoas", nullable = false)
    protected int qntPessoas;

    /**
     * Construtor padrão.
     */
    public Pedido() {
    }

    /**
     * Construtor que inicializa a quantidade de pessoas.
     *
     * @param qntPessoas quantidade de pessoas
     */
    public Pedido(int qntPessoas) {
        setQntPessoas(qntPessoas);
    }

    /**
     * Construtor que inicializa a quantidade de pessoas e o menu.
     *
     * @param qntPessoas quantidade de pessoas
     * @param menu       menu associado ao pedido
     * @throws MenuInvalidoException se o menu for inválido
     */
    public Pedido(int qntPessoas, T menu) throws MenuInvalidoException {
        setQntPessoas(qntPessoas);
        setMenu(menu);
    }

    /**
     * Retorna o menu associado ao pedido.
     *
     * @return menu
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Define o menu associado ao pedido.
     *
     * @param menu menu a ser associado
     * @throws MenuInvalidoException se o menu for inválido
     */
    public abstract void setMenu(T menu) throws MenuInvalidoException;

    /**
     * Define a quantidade de pessoas.
     *
     * @param qnt quantidade de pessoas
     */
    public void setQntPessoas(int qnt) {
        this.qntPessoas = qnt;
    }

    /**
     * Retorna a quantidade de pessoas.
     *
     * @return quantidade de pessoas
     */
    public int getQntPessoas() {
        return this.qntPessoas;
    }

    /**
     * Retorna o ID do pedido.
     *
     * @return ID do pedido
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna a lista de produtos do pedido.
     *
     * @return lista de produtos
     */
    public List<Produto> getProdutos() {
        return produtos;
    }

    /**
     * Adiciona um produto ao pedido.
     *
     * @param produto produto a ser adicionado
     * @throws LimiteProdutosException        se o limite de produtos for excedido
     * @throws NaoExisteMenuException         se não houver menu associado ao pedido
     * @throws ProdutoNaoExisteNoMenuException se o produto não existir no menu
     */
    public abstract void addProduto(Produto produto) throws LimiteProdutosException, NaoExisteMenuException, ProdutoNaoExisteNoMenuException;

    /**
     * Remove um produto do pedido.
     *
     * @param produto produto a ser removido
     */
    public void removeProduto(Produto produto) {
        this.produtos.remove(produto);
    }

    /**
     * Retorna o valor total do pedido.
     *
     * @return valor total do pedido
     */
    public abstract double getSomarTotal();
}