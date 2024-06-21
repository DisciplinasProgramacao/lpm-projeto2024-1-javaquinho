package javaquinho.comidinhas.controllers;

import java.util.List;
import java.util.Optional;
import java.net.URI;

import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;
import javaquinho.comidinhas.models.PedidoFechado;
import javaquinho.comidinhas.models.Produto;
import javaquinho.comidinhas.repositories.PedidoFechadoRepository;
import javaquinho.comidinhas.repositories.RequisicaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/pedidos-fechados")
@Validated
public class PedidoFechadoController {

    @Autowired
    private PedidoFechadoRepository repository;

    @Autowired
    RequisicaoRepository repositoryRequisicao;

    // Criar novo pedido
    @PostMapping
    public ResponseEntity<PedidoFechado> criarPedido(@RequestBody PedidoFechado pedido) {
        PedidoFechado novoPedido = repository.save(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(novoPedido.getId()).toUri();
        return ResponseEntity.created(uri).body(novoPedido);
    }

    // Puxar todos os pedidos
    @GetMapping
    public ResponseEntity<List<PedidoFechado>> getAllPedidos() {
        List<PedidoFechado> pedidos = repository.findAll();
        return ResponseEntity.ok().body(pedidos);
    }

    // Puxar determinado pedido
    @GetMapping("/{pedidoId}")
    public ResponseEntity<PedidoFechado> getPedidoById(@PathVariable Long pedidoId) {
        Optional<PedidoFechado> optionalPedido = repository.findById(pedidoId);
        return optionalPedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Puxar todos os pedidos do cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoFechado>> getPedidosByClienteId(@PathVariable Long clienteId) {
        List<PedidoFechado> pedidos = repository.findByClienteId(clienteId);
        return pedidos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(pedidos);
    }

    // Retornar o valor total do pedido
    @GetMapping("/total/{pedidoId}")
    public ResponseEntity<Double> getTotalPedido(@PathVariable Long pedidoId) {
        Optional<PedidoFechado> optionalPedido = repository.findById(pedidoId);
        if (optionalPedido.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PedidoFechado pedido = optionalPedido.get();
        double total = pedido.getSomarTotal();
        return ResponseEntity.ok().body(total);
    }

    // Atualizar pedido
    @PutMapping("/{pedidoId}/adicionar-produto")
    public ResponseEntity<PedidoFechado> adicionarProdutoAoPedido(
            @PathVariable Long pedidoId,
            @RequestBody Produto produto) {
        PedidoFechado pedido = repository.findById(pedidoId).orElse(null);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            pedido.addProduto(produto);
            repository.save(pedido);
            return ResponseEntity.ok().body(pedido);

        } catch (LimiteProdutosException e) {
            return ResponseEntity.status(500).build();
        } catch (NaoExisteMenuException e){
            return ResponseEntity.status(500).build();
        } catch(ProdutoNaoExisteNoMenuException e ){
            return ResponseEntity.status(500).build();
        }

    }
}
