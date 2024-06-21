package javaquinho.comidinhas.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	// private static int ultimoID;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long idMesa;

	@Column(name = "capacidade", nullable = false)
	private int capacidade;

	@Column(name = "ocupada", nullable = false)
	private boolean ocupada = false;

	
	// static{
	// 	ultimoID = 0;
	// }

	// public Mesa(int capacidade) {
	// 	this.capacidade = 2;
	// 	if(capacidade>2)
	// 		this.capacidade = capacidade;
	// 	id = ++ultimoID;
	// 	ocupada = false;
	// }

	public void ocupar() {
		ocupada = true;
	}

	public void desocupar() {
		ocupada = false;
	}

	public boolean estahLiberada(int quantPessoas) {
		return (quantPessoas <= capacidade && !ocupada);
	}

	public Long getIdMesa(){
		return idMesa;
	}
	
	public String toString(){
		String descricao = String.format("Mesa %02d (%d pessoas), ",idMesa, capacidade);
		if(ocupada)
			descricao += "ocupada.";
		else 
			descricao += "liberada.";
		
		return descricao;
	}

}
