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

@Entity
@Table(name = Menu.TABLE_NAME)
public abstract class Menu {

    public static final String TABLE_NAME = "menu";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

     @ManyToMany
    @JoinTable(
        name = "menu_produto",
        joinColumns = @JoinColumn(name = "menu_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    protected Set<Produto> produtos;

    public Long getId(){
        return this.id;
    }

    public Set<Produto> getProdutos(){
        return this.produtos;
    }

    public abstract void setProdutos(Set<Produto> produtos) throws LimiteProdutosException;

    public boolean produtoExiste(Produto produto){
        for (Produto p : this.getProdutos()){
            if (p.equals(produto)) {
                return true;
            }
        }

        return false;
    }

    public abstract void adicionarProduto(Produto produto) throws LimiteProdutosException;

    public String getClassName(){
        return this.getClass().getSimpleName();
    }
}