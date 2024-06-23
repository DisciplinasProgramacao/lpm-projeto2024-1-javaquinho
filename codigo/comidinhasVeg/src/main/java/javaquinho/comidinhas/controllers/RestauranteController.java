package javaquinho.comidinhas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Mesa;
import javaquinho.comidinhas.models.Produto;
import javaquinho.comidinhas.models.Restaurante;
import javaquinho.comidinhas.repositories.ClienteRepository;
import javaquinho.comidinhas.models.Requisicao;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    @Autowired
    private Restaurante restaurante;

    @Autowired
    private ClienteRepository clienteRepository;

    //criação de clientes
    @PostMapping("/clientes")
    public ResponseEntity<?> criarClientes(@RequestBody List<Cliente> clientes) {
        try {
            restaurante.criarClientes(clientes);
            return ResponseEntity.ok("Clientes criados com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar clientes: " + e.getMessage());
        }
    }

    //cria as mesas
    @PostMapping("/mesas")
    public ResponseEntity<?> criarMesas(@RequestBody List<Mesa> mesas) {
        try {
            restaurante.criarMesas(mesas);
            return ResponseEntity.ok("Mesas criadas com sucesso.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //aloca uma mesa e finaliza a requisição (entrada)
    @PostMapping("/{idCliente}/{quantPessoas}")
    public ResponseEntity<String> criarRequisicao(
            @PathVariable Integer idCliente,
            @PathVariable Integer quantPessoas) {

        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        Requisicao requisicao = new Requisicao(cliente, quantPessoas);

        try {
            String resultadoAlocacao = restaurante.criarRequisicao(requisicao);
            return ResponseEntity.ok(resultadoAlocacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro ao criar requisição: " + e.getMessage());
        }
    }

    //desaloca a mesa e finaliza a requisição (saída)
    @PutMapping("/desalocar/{requisicaoId}")
    public ResponseEntity<String> desalocarMesaDeRequisicao(@PathVariable Long requisicaoId) {
        String resultado = restaurante.desalocarMesaDeRequisicao(requisicaoId);
        return ResponseEntity.ok(resultado);
    }

    //cria todos os produtos
    @PostMapping("/produtos")
    public ResponseEntity<String> criarProdutos(@RequestBody List<Produto> produtos) {
        try {
            String mensagem = restaurante.criarProdutos(produtos);
            return ResponseEntity.ok(mensagem);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao iniciar produtos: " + e.getMessage());
        }
    }

    //cria um menu aberto
        @PostMapping("/menu/aberto")
    public ResponseEntity<String> criarMenuAberto(@RequestBody Set<Produto> produtos) {
        try {
            String mensagem = restaurante.criarMenuAberto(produtos);
            return ResponseEntity.ok(mensagem);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar menu aberto: " + e.getMessage());
        }
    }

    //cria um menu fechado
    @PostMapping("/menu/fechado")
    public ResponseEntity<String> criarMenuFechado(@RequestBody Set<Produto> produtos) {
        try {
            String mensagem = restaurante.criarMenuFechado(produtos);
            return ResponseEntity.ok(mensagem);
        } catch (LimiteProdutosException e) {
            return ResponseEntity.status(400).body("Erro ao criar menu fechado: " + e.getMessage());
        }
    }
    
}
