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

@Entity
@Table(name = Mesa.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Mesa {
    private static final String TABLE_NAME = "mesa";

    /**
     * Identificador único da mesa.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int idMesa;

    /**
     * Capacidade máxima de pessoas que a mesa pode acomodar.
     */
    @Column(name = "capacidade", nullable = false)
    private int capacidade;

    /**
     * Indica se a mesa está ocupada.
     */
    @Column(name = "ocupada", nullable = false)
    private boolean ocupada;

    /**
     * Construtor para criar uma mesa com uma capacidade específica.
     * A mesa é inicialmente marcada como desocupada.
     *
     * @param capacidade a capacidade da mesa
     */
    public Mesa(int capacidade) {
        this.capacidade = capacidade;
        this.ocupada = false;
    }

    /**
     * Marca a mesa como ocupada.
     */
    public void ocupar() {
        this.ocupada = true;
    }

    /**
     * Marca a mesa como desocupada.
     */
    public void desocupar() {
        this.ocupada = false;
    }

    /**
     * Verifica se a mesa está liberada para acomodar uma quantidade específica de pessoas.
     *
     * @param quantPessoas a quantidade de pessoas a ser acomodada
     * @return true se a mesa estiver liberada, false caso contrário
     */
    public boolean estahLiberada(int quantPessoas) {
        return (quantPessoas <= capacidade && !ocupada);
    }

    /**
     * Retorna uma descrição da mesa.
     *
     * @return uma string descrevendo a mesa
     */
    @Override
    public String toString() {
        String descricao = String.format("Mesa %02d (%d pessoas), ", idMesa, capacidade);
        if (ocupada) {
            descricao += "ocupada.";
        } else {
            descricao += "liberada.";
        }
        return descricao;
    }
}