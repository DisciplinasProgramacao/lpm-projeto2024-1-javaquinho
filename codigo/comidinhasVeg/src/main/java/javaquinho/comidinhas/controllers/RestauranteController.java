package javaquinho.comidinhas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;
import javaquinho.comidinhas.models.*;
import javaquinho.comidinhas.repositories.*;

import java.util.List;
import java.util.Set;

/**
 * Controlador REST para operações relacionadas ao restaurante, como criação de
 * clientes,
 * mesas, requisições, produtos e menus.
 */
@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    @Autowired
    private Restaurante restaurante;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RequisicaoRepository requisicaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Cria novos clientes no sistema.
     * 
     * @param clientes Lista de clientes a serem criados
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/clientes")
    public ResponseEntity<?> criarClientes(@RequestBody List<Cliente> clientes) {
        try {
            restaurante.criarClientes(clientes);
            return ResponseEntity.ok("Clientes criados com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar clientes: " + e.getMessage());
        }
    }

    /**
     * Cria novas mesas no restaurante.
     * 
     * @param mesas Lista de mesas a serem criadas
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/mesas")
    public ResponseEntity<?> criarMesas(@RequestBody List<Mesa> mesas) {
        try {
            restaurante.criarMesas(mesas);
            return ResponseEntity.ok("Mesas criadas com sucesso.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
     * Cria uma nova requisição para um cliente.
     * 
     * @param idCliente    ID do cliente
     * @param quantPessoas Quantidade de pessoas na requisição
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
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

    /**
     * Desaloca a mesa associada a uma requisição e finaliza o pedido.
     * 
     * @param requisicaoId ID da requisição
     * @return ResponseEntity com mensagem de sucesso, total do pedido ou erro
     */
    @PutMapping("/desalocar/{requisicaoId}")
    public ResponseEntity<String> desalocarMesaDeRequisicao(@PathVariable Long requisicaoId) {
        String resultado = restaurante.desalocarMesaDeRequisicao(requisicaoId);

        // Após desalocar a mesa e finalizar a requisição, obter o total do pedido
        Requisicao requisicao = requisicaoRepository.findById(requisicaoId).orElse(null);
        if (requisicao == null || requisicao.getPedido() == null) {
            return ResponseEntity.notFound().build();
        }

        double totalPedido = requisicao.getPedido().getSomarTotal();

        return ResponseEntity.ok(resultado + "\nTotal do Pedido: " + totalPedido);
    }

    /**
     * Inicia a lista de produtos no restaurante.
     * 
     * @param produtos Lista de produtos a serem iniciados
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/produtos")
    public ResponseEntity<String> criarProdutos(@RequestBody List<Produto> produtos) {
        try {
            String mensagem = restaurante.criarProdutos(produtos);
            return ResponseEntity.ok(mensagem);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao iniciar produtos: " + e.getMessage());
        }
    }

    /**
     * Cria um menu aberto com os produtos especificados.
     * 
     * @param produtos Conjunto de produtos do menu aberto
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/menu/aberto")
    public ResponseEntity<String> criarMenuAberto(@RequestBody Set<Produto> produtos) {
        try {
            String mensagem = restaurante.criarMenuAberto(produtos);
            return ResponseEntity.ok(mensagem);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar menu aberto: " + e.getMessage());
        }
    }

    /**
     * Cria um menu fechado com os produtos especificados.
     * 
     * @param produtos Conjunto de produtos do menu fechado
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/menu/fechado")
    public ResponseEntity<String> criarMenuFechado(@RequestBody Set<Produto> produtos) {
        try {
            String mensagem = restaurante.criarMenuFechado(produtos);
            return ResponseEntity.ok(mensagem);
        } catch (LimiteProdutosException e) {
            return ResponseEntity.status(400).body("Erro ao criar menu fechado: " + e.getMessage());
        }
    }

    /**
     * Atribui um menu a uma requisição, criando o pedido correspondente.
     * 
     * @param id     ID da requisição
     * @param idMenu ID do menu a ser atribuído
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/atribuir-menu/{id}/{idMenu}")
    public ResponseEntity<String> atribuirMenu(@PathVariable Long id, @PathVariable Long idMenu) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElse(null);
        Menu menu = menuRepository.findById(idMenu).orElse(null);

        if (requisicao == null || menu == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Pedido pedido;
            if (menu instanceof MenuAberto) {
                pedido = new PedidoAberto(requisicao.getQuantPessoas(), (MenuAberto) menu);
            } else if (menu instanceof MenuFechado) {
                pedido = new PedidoFechado(requisicao.getQuantPessoas(), (MenuFechado) menu);
            } else {
                throw new Exception("Tipo de menu inválido");
            }
            pedidoRepository.save(pedido);
            requisicao.setPedido(pedido);
            requisicaoRepository.save(requisicao);

        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }

        return ResponseEntity.ok("Menu atribuído com sucesso.");
    }

    /**
     * Adiciona um produto a um pedido associado a uma requisição.
     * 
     * @param requisicaoId ID da requisição
     * @param produtoId    ID do produto a ser adicionado
     * @return ResponseEntity com mensagem de sucesso ou erro
     */
    @PostMapping("/adicionar-produto/{requisicaoId}/{produtoId}")
    public ResponseEntity<String> adicionarProdutoARequisicao(
            @PathVariable Long requisicaoId,
            @PathVariable Long produtoId) {

        Requisicao requisicao = requisicaoRepository.findById(requisicaoId).orElse(null);
        if (requisicao == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Produto produto = produtoRepository.findById(produtoId).orElse(null);
            if (produto == null) {
                return ResponseEntity.notFound().build();
            }

            if (requisicao.getPedido() == null) {
                return ResponseEntity.badRequest().body("Não há pedido associado à requisição.");
            }

            requisicao.getPedido().addProduto(produto);
            pedidoRepository.save(requisicao.getPedido());

            return ResponseEntity.ok().body("Produto adicionado com sucesso!");

        } catch (LimiteProdutosException | NaoExisteMenuException | ProdutoNaoExisteNoMenuException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

}