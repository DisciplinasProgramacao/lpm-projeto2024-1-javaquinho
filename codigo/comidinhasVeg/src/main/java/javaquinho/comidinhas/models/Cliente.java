package javaquinho.comidinhas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que representa um Cliente.
 */
@Entity
@Table(name = Cliente.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Cliente {

    /**
     * Nome da tabela no banco de dados.
     */
    public static final String TABLE_NAME = "cliente";

    /**
     * Identificador único do cliente.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nome do cliente.
     */
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    /**
     * Telefone do cliente.
     */
    @Column(name = "telefone", length = 11, nullable = false, unique = true)
    private String telefone;

    /**
     * CPF do cliente.
     */
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;
}

