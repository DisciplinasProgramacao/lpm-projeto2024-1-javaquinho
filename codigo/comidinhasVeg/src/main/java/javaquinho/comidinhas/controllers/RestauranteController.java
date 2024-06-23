package javaquinho.comidinhas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Mesa;
import javaquinho.comidinhas.models.Menu;
import javaquinho.comidinhas.models.Restaurante;
import javaquinho.comidinhas.repositories.ClienteRepository;
import javaquinho.comidinhas.models.Requisicao;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    @Autowired
    private Restaurante restaurante;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/clientes")
    public ResponseEntity<?> criarClientes(@RequestBody List<Cliente> clientes) {
        try {
            List<Cliente> novosClientes = restaurante.criarClientes(clientes);
            return ResponseEntity.ok("Clientes criados com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar clientes: " + e.getMessage());
        }
    }

    @PostMapping("/mesas")
    public ResponseEntity<?> criarMesas(@RequestBody List<Mesa> mesas) {
        try {
            List<Mesa> novasMesas = restaurante.criarMesas(mesas);
            return ResponseEntity.ok("Mesas criadas com sucesso.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/{idCliente}/{quantPessoas}")
    public ResponseEntity<Requisicao> criarRequisicao(
            @PathVariable Integer idCliente,
            @PathVariable Integer quantPessoas) {

        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        Requisicao requisicao = new Requisicao(cliente, quantPessoas);

        try {
            Requisicao novaRequisicao = restaurante.criarRequisicao(requisicao);
            return ResponseEntity.ok(novaRequisicao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/alocar/{requisicaoId}")
    public ResponseEntity<String> alocarMesaParaRequisicao(@PathVariable Long requisicaoId) {
        String resultado = restaurante.alocarMesaParaRequisicao(requisicaoId);
        return ResponseEntity.ok(resultado);        
    }

    @PutMapping("/desalocar/{requisicaoId}")
    public ResponseEntity<String> desalocarMesaDeRequisicao(@PathVariable Long requisicaoId) {
        String resultado = restaurante.desalocarMesaDeRequisicao(requisicaoId);
        return ResponseEntity.ok(resultado);
    }
}
