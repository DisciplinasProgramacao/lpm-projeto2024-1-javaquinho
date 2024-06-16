package javaquinho.comidinhas.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Restaurante;
import javaquinho.comidinhas.repositories.ClienteRepository;
import javaquinho.comidinhas.repositories.RestauranteRepository;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Restaurante> getAllRestaurantes() {
        return restauranteRepository.findAll();
    }

    @PostMapping
    public Restaurante createRestaurante(@RequestBody Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @PostMapping("/{restauranteId}/clientes")
    public ResponseEntity<Cliente> insert(@PathVariable Long restauranteId, @RequestBody Cliente cliente){
        Restaurante r = restauranteRepository.findById(restauranteId).orElseThrow(() -> new RuntimeException("Restaurante inexistente"));
        cliente.setRestaurante(r);
        cliente = clienteRepository.save(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @GetMapping("/{restauranteId}/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new RuntimeException("Restaurante inexistente"));
    
        List<Cliente> clientes = restaurante.getListaClientes();
        return ResponseEntity.ok(clientes);
    }
}