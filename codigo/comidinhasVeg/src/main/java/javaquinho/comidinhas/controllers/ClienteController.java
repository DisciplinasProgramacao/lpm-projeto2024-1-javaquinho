package javaquinho.comidinhas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.repositories.ClienteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Retorna todos os clientes.
     *
     * @return lista de clientes
     */
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Retorna um cliente específico com base no ID.
     *
     * @param id ID do cliente
     * @return cliente ou status de não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable int id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cria um novo cliente.
     *
     * @param cliente objeto cliente a ser criado
     * @return cliente criado
     */
    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param id ID do cliente a ser atualizado
     * @param clienteDetails detalhes do cliente a serem atualizados
     * @return cliente atualizado ou status de não encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable int id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            Cliente existingCliente = cliente.get();
            existingCliente.setNome(clienteDetails.getNome());
            existingCliente.setTelefone(clienteDetails.getTelefone());
            existingCliente.setCpf(clienteDetails.getCpf());
            final Cliente updatedCliente = clienteRepository.save(existingCliente);
            return ResponseEntity.ok(updatedCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta um cliente existente.
     *
     * @param id ID do cliente a ser deletado
     * @return status de sucesso ou não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable int id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
