package javaquinho.comidinhas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javaquinho.comidinhas.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {


}
