package javaquinho.comidinhas.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javaquinho.comidinhas.excecoes.LimiteProdutosException;
import javaquinho.comidinhas.models.Menu;
import javaquinho.comidinhas.models.MenuAberto;
import javaquinho.comidinhas.models.MenuFechado;
import javaquinho.comidinhas.models.Produto;
import javaquinho.comidinhas.repositories.MenuRepository;
import javaquinho.comidinhas.repositories.ProdutoRepository;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuRepository repository;

    @Autowired
    private ProdutoRepository repositorioProduto;

    @GetMapping("")
    public List<Menu> getAllByRestauranteId() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenu(@PathVariable Long id) {
        Optional<Menu> menu = repository.findById(id);
        return menu.isPresent() ? ResponseEntity.ok(menu.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/aberto")
    public ResponseEntity<String> criarMenuAberto(@RequestBody HashSet<Produto> produtos) {
        MenuAberto obj = new MenuAberto(produtos);
        repository.save(obj);
        return ResponseEntity.ok().body("Menu aberto criado com sucesso!");
    }

    @PostMapping("/fechado")
    public ResponseEntity<String> criarMenuFechado(@RequestBody HashSet<Produto> produtos) {
        try {
            MenuFechado obj = new MenuFechado(produtos);
            repository.save(obj);
            return ResponseEntity.ok().body("Menu fechado criado com sucesso!");
        } catch (LimiteProdutosException e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @PutMapping("/adicionarProduto")
    public ResponseEntity<String> adicionarProduto(@RequestParam Long menuId, @RequestBody Produto produto) {
        Optional<Menu> optionalMenu = repository.findById(menuId);
        if (optionalMenu.isPresent()) {
            Menu menu = optionalMenu.get();
            try {
                menu.adicionarProduto(produto);
                repository.save(menu);

                return ResponseEntity.ok("Produto adicionado com sucesso!");
            } catch (LimiteProdutosException e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/adicionarProduto/all")
    public ResponseEntity<String> adicionarProduto(@RequestParam Long menuId, @RequestBody List<Produto> produtos) {
        Menu menu = repository.findById(menuId).orElse(null);
        if (menu != null) {
            for (Produto p : produtos) {
                try {
                    menu.adicionarProduto(p);
                } catch (LimiteProdutosException e) {
                    return ResponseEntity.status(400).body(e.getMessage());
                }
            }

            repository.save(menu);
            return ResponseEntity.ok().body("Produtos adicionados com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
