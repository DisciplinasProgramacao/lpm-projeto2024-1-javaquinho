package javaquinho.comidinhas.models;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe abstrata representado um Menu
 */
@Entity
@Table(name = Menu.TABLE_NAME)
public abstract class Menu {

    public static final String TABLE_NAME = "menu";

    /**
     * ID do menu
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * Coleção de produtos que o menu possui
     */
    @ManyToMany
    @JoinTable(name = "menu_produto", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    protected Set<Produto> produtos;

    /**
     * Retorna o id do menu
     *
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Retorna a coleção de produtos do menu
     *
     * @return a coleção dos produtos que o menu possui
     */
    public Set<Produto> getProdutos() {
        return this.produtos;
    }

    /**
     * Define a coleção de produtos para o menu.
     *
     * @param produtos a nova coleção de produtos
     * @throws LimiteProdutosException se o limite de produtos for excedido
     */
    public abstract void setProdutos(Set<Produto> produtos) throws LimiteProdutosException;

    /**
     * Verifica se um produto específico existe no menu.
     *
     * @param produto o produto a ser verificado
     * @return true se o produto existir no menu, false caso contrário
     */
    public boolean produtoExiste(Produto produto) {
        for (Produto p : this.getProdutos()) {
            if (p.equals(produto)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adiciona um produto ao menu.
     *
     * @param produto o produto a ser adicionado
     * @throws LimiteProdutosException se o limite de produtos for excedido
     */
    public abstract void adicionarProduto(Produto produto) throws LimiteProdutosException;

    /**
     * Retorna o nome simples da classe (sem o pacote).
     *
     * @return o nome simples da classe
     */
    public String getClassName() {
        return this.getClass().getSimpleName();
    }
}