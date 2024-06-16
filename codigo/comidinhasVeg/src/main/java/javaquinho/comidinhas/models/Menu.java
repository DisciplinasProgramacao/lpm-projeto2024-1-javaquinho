package javaquinho.comidinhas.models;

import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Menu.TABLE_NAME)
public class Menu {
    // @Autowired
    // private MenuRepository repository;

    public static final String TABLE_NAME = "menu";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @JsonIgnoreProperties("menu")
    @OneToMany(mappedBy = "menu")
    @Fetch(FetchMode.JOIN)
    private Set<Produto> produtos;

    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }
}

/* 
Corpo para criar menu
Set<Produto> produtos = new LinkedHashSet()<Produto>;
produtos.add(new Produto("Moqueca de Pamito", 32.0))
produtos.add(new Produto("Falafel Assado", 20.0))
produtos.add(new Produto("Salada Primavera com Macarrão Konjac", 25.0))
produtos.add(new Produto("Escondidinho de Inhame", 18.0))
produtos.add(new Produto("Strogonoff de Cogumelos", 35.0))
produtos.add(new Produto("Caçarola de legumes", 22.0))
produtos.add(new Produto("Água", 3.0))
produtos.add(new Produto("Copo de Suco", 7.0))
produtos.add(new Produto("Refrigerante orgânico", 7.0))
produtos.add(new Produto("Cerveja vegana", 9.0))
produtos.add(new Produto("Taça de vinho vegano", 18.0))
this.produtos = produtos;
*/