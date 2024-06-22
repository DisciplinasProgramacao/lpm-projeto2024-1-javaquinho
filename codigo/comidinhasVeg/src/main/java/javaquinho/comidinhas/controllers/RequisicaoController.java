package javaquinho.comidinhas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.excecoes.NaoExisteMenuException;
import javaquinho.comidinhas.excecoes.ProdutoNaoExisteNoMenuException;
import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Mesa;
import javaquinho.comidinhas.models.Pedido;
import javaquinho.comidinhas.models.Produto;
import javaquinho.comidinhas.models.Requisicao;
import javaquinho.comidinhas.repositories.RequisicaoRepository;
import javaquinho.comidinhas.repositories.ClienteRepository;
import javaquinho.comidinhas.repositories.MesaRepository;
import javaquinho.comidinhas.repositories.PedidoRepository;
import javaquinho.comidinhas.repositories.ProdutoRepository;

@RestController
@RequestMapping("/requisicoes")
public class RequisicaoController {

    @Autowired
    private RequisicaoRepository requisicaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<Requisicao> getAllRequisicoes() {
        return requisicaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Requisicao> getRequisicaoById(@PathVariable Long id) {
        Optional<Requisicao> requisicao = requisicaoRepository.findById(id);
        if (requisicao.isPresent()) {
            return ResponseEntity.ok(requisicao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    

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

    @PutMapping("/adicionarProduto")
    public ResponseEntity<Requisicao> adicionarProduto(@RequestParam Long requisicao, @RequestParam Long produto){
        Requisicao req = requisicaoRepository.findById(requisicao).orElse(null);
        Produto prod = produtoRepository.findById(produto).orElse(null);
        if (req != null && prod != null) {
            Pedido pedido = req.getPedido();

            try {
                pedido.addProduto(prod);
                return ResponseEntity.ok(requisicaoRepository.save(req));
            }
            catch (LimiteProdutosException e){
                return ResponseEntity.status(500).build();
            }
            catch (NaoExisteMenuException e){
                return ResponseEntity.status(500).build();
            }
            catch (ProdutoNaoExisteNoMenuException e){
                return ResponseEntity.status(500).build();
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{idCliente}/{quantPessoas}")
    public Requisicao createRequisicao(@PathVariable Integer idCliente, @PathVariable Integer quantPessoas) {
        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
        Requisicao req = new Requisicao(cliente, quantPessoas);
        return requisicaoRepository.save(req);   
    }

    /*patch que linka um pedido a requisição */
    @PutMapping("/{id}/{idPedido}")
    public ResponseEntity<Requisicao> linkarPedido(@PathVariable Long id, @RequestBody Long idPedido){
        Requisicao requisicao = requisicaoRepository.findById(id).orElse(null);
        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (requisicao != null) {
            requisicao.setPedido(pedido);
            return ResponseEntity.ok(requisicaoRepository.save(requisicao));
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PatchMapping("/alocar/{id}/{idMesa}")
    public ResponseEntity<Requisicao> alocarMesa(@PathVariable Long id, @PathVariable Integer idMesa) {
        Requisicao requisicao = requisicaoRepository.findById(id).orElse(null);
        Mesa mesa = mesaRepository.findById(idMesa).orElse(null);
        requisicao.alocarMesa(mesa);
        requisicaoRepository.save(requisicao);
        return ResponseEntity.ok(requisicao);
    }
}
