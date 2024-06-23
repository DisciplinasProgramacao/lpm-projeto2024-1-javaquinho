package javaquinho.comidinhas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;
import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Menu;
import javaquinho.comidinhas.models.MenuAberto;
import javaquinho.comidinhas.models.MenuFechado;
import javaquinho.comidinhas.models.Mesa;
import javaquinho.comidinhas.models.Pedido;
import javaquinho.comidinhas.models.PedidoAberto;
import javaquinho.comidinhas.models.PedidoFechado;
import javaquinho.comidinhas.models.Produto;
import javaquinho.comidinhas.models.Requisicao;
import javaquinho.comidinhas.repositories.RequisicaoRepository;
import javaquinho.comidinhas.repositories.ClienteRepository;
import javaquinho.comidinhas.repositories.MenuRepository;
import javaquinho.comidinhas.repositories.MesaRepository;
import javaquinho.comidinhas.repositories.PedidoRepository;
import javaquinho.comidinhas.repositories.ProdutoRepository;

@RestController
@RequestMapping("/requisicoes")
public class RequisicaoController {

    @Autowired
    private RequisicaoRepository requisicaoRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Retorna todas as requisições.
     *
     * @return uma lista de objetos Requisicao
     */
    @GetMapping
    public List<Requisicao> getAllRequisicoes() {
        return requisicaoRepository.findAll();
    }

    /**
     * Retorna uma requisição específica com base no ID fornecido.
     *
     * @param id o ID da requisição a ser retornada
     * @return um objeto ResponseEntity contendo a requisição solicitada ou um
     *         status 404 se não encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<Requisicao> getRequisicaoById(@PathVariable Long id) {
        Optional<Requisicao> requisicao = requisicaoRepository.findById(id);
        if (requisicao.isPresent()) {
            return ResponseEntity.ok(requisicao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Encerra uma requisição com base no ID fornecido.
     *
     * @param id o ID da requisição a ser encerrada
     * @return um objeto ResponseEntity contendo a requisição encerrada ou um status
     *         404 se não encontrada
     */
    @PutMapping("/{id}/encerrar")
    public ResponseEntity<Requisicao> encerrarRequisicao(@PathVariable Long id) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElse(null);
        if (requisicao != null) {
            requisicao.encerrar();
            return ResponseEntity.ok(requisicaoRepository.save(requisicao));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint de teste para vincular um menu a uma requisição e criar um pedido.
     *
     * @param id     o ID da requisição
     * @param idMenu o ID do menu
     * @return uma mensagem de sucesso ou falha
     */
    @PostMapping("/atribuir-menu/{id}/{idMenu}")
    public ResponseEntity<String> teste(@PathVariable Long id, @PathVariable Long idMenu) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElse(null);
        Menu menu = menuRepository.findById(idMenu).orElse(null);

        try {
            Pedido pedido;
            System.out.println(menu.getClassName());
            if (menu.getClassName().equals("MenuAberto")) {
                pedido = new PedidoAberto(requisicao.getQuantPessoas(), (MenuAberto) menu);
            } else if (menu.getClassName().equals("MenuFechado")) {
                pedido = new PedidoFechado(requisicao.getQuantPessoas(), (MenuFechado) menu);
            } else {
                throw new Exception("");
            }
            pedidoRepository.save(pedido);
            requisicao.setPedido(pedido);
            requisicaoRepository.save(requisicao);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body("yay");
    }

    /**
     * Cria uma nova requisição.
     *
     * @param idCliente    o ID do cliente
     * @param quantPessoas a quantidade de pessoas na requisição
     * @return a nova requisição criada
     */
    @PostMapping("/{idCliente}/{quantPessoas}")
    public Requisicao createRequisicao(@PathVariable Integer idCliente, @PathVariable Integer quantPessoas) {
        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
        Requisicao req = new Requisicao(cliente, quantPessoas);
        return requisicaoRepository.save(req);
    }

    /**
     * Adiciona um pedido à requisição com base no tipo de pedido fornecido.
     *
     * @param id         o ID da requisição
     * @param tipoPedido o tipo de pedido ("aberto" ou "fechado")
     * @return uma mensagem de sucesso ou falha
     */
    @PutMapping("/adicionar-pedido-aberto/{id}/{tipoPedido}")
    public ResponseEntity<String> adicionarPedidoAberto(@PathVariable Long id, @PathVariable String tipoPedido) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElse(null);
        try {
            if (requisicao != null) {
                System.out.println(tipoPedido);
                Pedido pedido;
                if (tipoPedido.toLowerCase().equals("aberto")) {
                    pedido = new PedidoAberto(requisicao.getQuantPessoas());
                } else if (tipoPedido.toLowerCase().equals("fechado")) {
                    pedido = new PedidoFechado(requisicao.getQuantPessoas());
                } else {
                    throw new Exception("parâmetro de tipo de pedido incorreto!");
                }
                requisicao.setPedido(pedido);
                pedidoRepository.save(pedido);
                requisicaoRepository.save(requisicao);
                return ResponseEntity.ok().body("Pedido criado com sucesso!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Aloca uma mesa para a requisição passando o ID da requisição e o ID da mesa.
     *
     * @param id     o ID da requisição
     * @param idMesa o ID da mesa a ser alocada
     * @return um objeto ResponseEntity contendo a requisição atualizada ou um
     *         status 404 se não encontrada
     */
    @PatchMapping("/alocar/{id}/{idMesa}")
    public ResponseEntity<Requisicao> alocarMesa(@PathVariable Long id, @PathVariable Integer idMesa) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElse(null);
        Mesa mesa = mesaRepository.findById(idMesa).orElse(null);
        requisicao.alocarMesa(mesa);
        requisicaoRepository.save(requisicao);
        return ResponseEntity.ok(requisicao);
    }

    /**
     * Adiciona um produto a um pedido existente em uma requisição.
     *
     * @param id        o ID da requisição
     * @param idProduto o ID do produto a ser adicionado
     * @return um objeto ResponseEntity contendo a requisição atualizada ou um
     *         status 404/500 conforme apropriado
     */
    @PutMapping("/adicionarProduto/{id}/{idProduto}")
    public ResponseEntity<Requisicao> adicionarProduto(@PathVariable Long id, @PathVariable Long idProduto) {
        Requisicao req = requisicaoRepository.findById(id).orElse(null);
        Produto prod = produtoRepository.findById(idProduto).orElse(null);
        if (req != null && prod != null) {
            Pedido pedido = req.getPedido();
            try {
                pedido.addProduto(prod);
                return ResponseEntity.ok(requisicaoRepository.save(req));
            } catch (LimiteProdutosException | NaoExisteMenuException | ProdutoNaoExisteNoMenuException e) {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}