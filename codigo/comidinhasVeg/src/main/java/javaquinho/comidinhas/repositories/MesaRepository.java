package javaquinho.comidinhas.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javaquinho.comidinhas.models.Mesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    //filtrar quais mesas estão disponíveis para o funcionamento da alocação
    List<Mesa> findByCapacidadeAndOcupada(int capacidade, boolean ocupada);
}
