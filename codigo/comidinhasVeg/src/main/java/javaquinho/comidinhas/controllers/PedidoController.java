package javaquinho.comidinhas.controllers;

import java.util.List;
import java.util.Optional;

import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.MenuInvalidoException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;
import javaquinho.comidinhas.models.Menu;
import javaquinho.comidinhas.models.Pedido;
import javaquinho.comidinhas.models.PedidoAberto;
import javaquinho.comidinhas.models.PedidoFechado;
import javaquinho.comidinhas.models.Produto;
import javaquinho.comidinhas.repositories.MenuRepository;
import javaquinho.comidinhas.repositories.PedidoRepository;
import javaquinho.comidinhas.repositories.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@Validated
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MenuRepository menuRepository;

    /**
     * Cria um novo pedido aberto.
     *
     * @param qntPessoas quantidade de pessoas no pedido
     * @return mensagem de sucesso ou erro
     */
    @PostMapping("/aberto")
    public ResponseEntity<String> criarPedidoAberto(@RequestParam int qntPessoas) {
        PedidoAberto obj = new PedidoAberto(qntPessoas);
        pedidoRepository.save(obj);
        return ResponseEntity.ok().body("Pedido aberto criado com sucesso!");
    }

    /**
     * Cria um novo pedido fechado.
     *
     * @param qntPessoas quantidade de pessoas no pedido
     * @return mensagem de sucesso ou erro
     */
    @PostMapping("/fechado")
    public ResponseEntity<String> criarPedidoFechado(@RequestParam int qntPessoas) {
        PedidoFechado obj = new PedidoFechado(qntPessoas);
        pedidoRepository.save(obj);
        return ResponseEntity.ok().body("Pedido fechado criado com sucesso!");
    }

    /**
     * Retorna todos os pedidos.
     *
     * @return lista de pedidos
     */
    @GetMapping("")
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return ResponseEntity.ok().body(pedidos);
    }

    /**
     * Retorna um pedido específico com base no ID.
     *
     * @param pedidoId ID do pedido
     * @return pedido ou status de não encontrado
     */
    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long pedidoId) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(pedidoId);
        return optionalPedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Adiciona um menu a um pedido.
     *
     * @param id     ID do pedido
     * @param idMenu ID do menu
     * @return mensagem de sucesso ou erro
     */
    @PostMapping("/adicionar-menu")
    public ResponseEntity<String> adicionarMenu(@RequestParam Long id, @RequestParam Long idMenu) {
        Menu menu = menuRepository.findById(idMenu).orElse(null);
        Pedido pedido = pedidoRepository.findById(id).orElse(null);

        if (menu == null || pedido == null) {
            return ResponseEntity.notFound().build();
        } else {
            try {
                pedido.setMenu(menu);
                pedidoRepository.save(pedido);
                return ResponseEntity.ok().body("Menu adicionado ao pedido!");

            } catch (MenuInvalidoException e) {
                return ResponseEntity.status(403).body(e.getMessage());

            } catch (Exception e) {
                return ResponseEntity.status(403).body(e.getMessage());
            }
        }
    }

    /**
     * Retorna o valor total do pedido.
     *
     * @param pedidoId ID do pedido
     * @return valor total do pedido
     */
    @GetMapping("/total/{pedidoId}")
    public ResponseEntity<Double> getTotalPedido(@PathVariable Long pedidoId) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(pedidoId);
        if (optionalPedido.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pedido pedido = optionalPedido.get();
        double total = pedido.getSomarTotal();
        return ResponseEntity.ok().body(total);
    }

    /**
     * Adiciona um produto a um pedido.
     *
     * @param id        ID do pedido
     * @param idProduto ID do produto
     * @return mensagem de sucesso ou erro
     */
    @PutMapping("/adicionar-produto")
    public ResponseEntity<String> adicionarProdutoAoPedido(
            @RequestParam Long id,
            @RequestParam Long idProduto) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        if (optionalPedido.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pedido pedido = optionalPedido.get();
        try {
            Produto produto = produtoRepository.findById(idProduto).orElse(null);
            pedido.addProduto(produto);
            pedidoRepository.save(pedido);
            return ResponseEntity.ok().body("Produto adicionado com sucesso!");

        } catch (LimiteProdutosException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (NaoExisteMenuException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (ProdutoNaoExisteNoMenuException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}