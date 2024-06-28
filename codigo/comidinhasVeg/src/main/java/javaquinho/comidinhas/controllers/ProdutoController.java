package javaquinho.comidinhas.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javaquinho.comidinhas.models.Produto;
import javaquinho.comidinhas.repositories.ProdutoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repository;

    /**
     * Retorna uma lista de todos os produtos.
     *
     * @return uma lista de todos os produtos
     */
    @GetMapping("")
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = repository.findAll();
        return ResponseEntity.ok().body(produtos);
    }

    /**
     * Retorna um produto específico pelo seu ID.
     *
     * @param id o ID do produto
     * @return o produto solicitado, ou 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
        Optional<Produto> produto = repository.findById(id);

        return produto.isPresent() ? ResponseEntity.ok(produto.get()) : ResponseEntity.notFound().build();
    }

    /**
     * Cria um novo produto.
     *
     * @param produto o produto a ser criado
     * @return a resposta com a URI do novo recurso criado
     */
    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto obj = repository.save(produto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Cria uma lista de novos produtos.
     *
     * @param produtos a lista de produtos a ser criada
     * @return a lista de produtos criados
     */
    @PostMapping("/all")
    public List<Produto> createProdutos(@RequestBody List<Produto> produto) {
        List<Produto> objs = repository.saveAll(produto);
        return objs;
    }
}