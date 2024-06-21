package javaquinho.comidinhas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Mesa;
import javaquinho.comidinhas.models.Menu;
import javaquinho.comidinhas.models.Restaurante;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    @Autowired
    private Restaurante restaurante;

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = restaurante.criarCliente(cliente);
        return ResponseEntity.ok(novoCliente);
    }

    @PostMapping("/mesas")
    public ResponseEntity<Mesa> criarMesa(@RequestBody Mesa mesa) {
        Mesa novaMesa = restaurante.criarMesa(mesa);
        return ResponseEntity.ok(novaMesa);
    }

    //menu inicia sem produtos. 
    @PostMapping("/menus")
    public ResponseEntity<Menu> criarMenu(@RequestBody Menu menu) {
        Menu novoMenu = restaurante.criarMenu(menu);
        return ResponseEntity.ok(novoMenu);
    }

    @PutMapping("/requisicoes/alocar/{requisicaoId}")
    public ResponseEntity<String> alocarMesaParaRequisicao(@PathVariable Long requisicaoId) {
        String resultado = restaurante.alocarMesaParaRequisicao(requisicaoId);
        return ResponseEntity.ok(resultado);        
    }

    @PutMapping("/requisicoes/desalocar/{requisicaoId}")
    public ResponseEntity<String> desalocarMesaDeRequisicao(@PathVariable Long requisicaoId) {
        String resultado = restaurante.desalocarMesaDeRequisicao(requisicaoId);
        return ResponseEntity.ok(resultado);
    }
}
