package javaquinho.comidinhas.repositories;

import javaquinho.comidinhas.models.Menu;
import javaquinho.comidinhas.models.Pedido;
import javaquinho.comidinhas.models.PedidoAberto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId")
    List<Pedido> findByClienteId(@Param("clienteId") Long clienteId);
}