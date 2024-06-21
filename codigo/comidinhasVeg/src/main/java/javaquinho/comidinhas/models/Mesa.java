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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int idMesa;

    @Column(name = "capacidade", nullable = false)
    private int capacidade;

    @Column(name = "ocupada", nullable = false)
    private boolean ocupada;

    public Mesa(int capacidade) {
        this.capacidade = capacidade;
        this.ocupada = false;
    }

    public void ocupar() {
        this.ocupada = true;
    }

    public void desocupar() {
        this.ocupada = false;
    }

    public boolean estahLiberada(int quantPessoas) {
        return (quantPessoas <= capacidade && !ocupada);
    }

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
