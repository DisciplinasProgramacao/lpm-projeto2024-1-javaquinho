package javaquinho.comidinhas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = Produto.TABLE_NAME)
public class Produto {

    public static final String TABLE_NAME = "produto";

    /**
     * ID do produto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * Nome do produto
     */
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * Preço do produto
     */
    @Column(name = "preco", nullable = false)
    private double preco;
}