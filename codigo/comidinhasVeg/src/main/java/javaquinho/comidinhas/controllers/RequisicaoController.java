package javaquinho.comidinhas.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javaquinho.comidinhas.models.Requisicao;
import javaquinho.comidinhas.repositories.RequisicaoRepository;

@RestController
@RequestMapping("/requisicoes")
public class RequisicaoController {
    
    @Autowired
    private RequisicaoRepository requisicaoRepository;

    @GetMapping
    public List<Requisicao> getAllRequisicoes() {
        return requisicaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Requisicao> getRequisicaoById(@PathVariable int id) {
        Optional<Requisicao> requisicao = requisicaoRepository.findById(id);
        if (requisicao.isPresent()) {
            return ResponseEntity.ok().body(requisicao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    @PostMapping
    public Requisicao createRequisicao(@RequestBody Requisicao requisicao){
        requisicao.setEntrada(LocalDateTime.now());
        return requisicaoRepository.save(requisicao);
    }

    // @PutMapping("/{id}/encerrar")
    // public ResponseEntity<Requisicao> encerrarRequisicao(@PathVariable int id){
    // Requisicao requisicao = requisicaoRepository.findById(id).orElse(null);
    //     if (requisicao != null) {
    //         requisicao.encerrar();
    //         return ResponseEntity.ok(requisicaoRepository.save(requisicao));
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @PutMapping("/adicionarProduto")
    // public ResponseEntity<Requisicao> adicionarProduto(@RequestParam int requisicao, @RequestParam Long produto){
    //     Requisicao req = requisicaoRepository.findById(requisicao).orElse(null);
    //     Produto prod = produtoRepository.findById(produto).orElse(null);
    //     if (req != null && prod != null) {
    //         Pedido pedido = req.getPedido();
    //         pedido.addProduto(prod);
    //         return ResponseEntity.ok(requisicaoRepository.save(req));
    //     }
    //     else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
}
