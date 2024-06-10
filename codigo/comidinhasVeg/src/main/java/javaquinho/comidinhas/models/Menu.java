package javaquinho.comidinhas.models;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

//import org.hibernate.annotations.Fetch;
//import org.hibernate.annotations.FetchMode;
//import org.hibernate.mapping.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
   // @Fetch(FetchMode.JOIN)
    private Set<Produto> produtos;

    // @JoinColumn(name = "produto_id", nullable = false, updatable = false)
    // private Produto produto;

    // private ArrayList<Produto> produtos;

    public Menu() {
        // this.produtos.add(new Produto("banana", 30.0));
        // this.produtos.add(new Produto("chocolate", 20.0));

        // repository.save(this);
        // this.produtos = new ArrayList<Produto>();
        // String[] itensComida = { "Moqueca de Pamito", "Falafel Assado", "Salada
        // Primavera com Macarrão Konjac",
        // "Escondidinho de Inhame", "Strogonoff de Cogumelos", "Caçarola de legumes" };
        // String[] itensBebida = { "Água", "Copo de Suco", "Refrigerante orgânico",
        // "Cerveja vegana",
        // "Taça de vinho vegano" };
        // Integer[] valorComida = { 32, 20, 25, 18, 35, 22 };
        // Integer[] valorBebida = { 3, 7, 7, 9, 18 };

        // for (int i = 0; i < itensComida.length; i++) {
        // adicionarProduto(itensComida[i], valorComida[i]);
        // System.out.println(produtos.get(i));
        // }
        // for (int i = 0; i < itensBebida.length; i++) {
        // adicionarProduto(itensBebida[i], valorBebida[i]);
        // }
    }

    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    // public String retornaMenu() {
    // String relat = "";
    // for (int i = 0; i < produtos.size(); i++) {
    // relat+=(produtos.get(i) + "\n");
    // }
    // return relat;
    // }

    // public Produto getProduto(int indice) {
    // return produtos.get(indice);
    // }

   public int tamanhoMenu() {
  return produtos.size();
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