// package javaquinho.comidinhas.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import javaquinho.comidinhas.models.Cliente;
// import javaquinho.comidinhas.repositories.ClienteRepository;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/clientes")
// public class ClienteController {

//     @Autowired
//     private ClienteRepository clienteRepository;

//     @GetMapping
//     public List<Cliente> getAllClientes() {
//         return clienteRepository.findAll();
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Cliente> getClienteById(@PathVariable int id) {
//         Optional<Cliente> cliente = clienteRepository.findById(id);
//         if (cliente.isPresent()) {
//             return ResponseEntity.ok(cliente.get());
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     public Cliente createCliente(@RequestBody Cliente cliente) {
//         return clienteRepository.save(cliente);
//     }
// }
