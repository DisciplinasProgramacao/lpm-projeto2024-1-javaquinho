package javaquinho.comidinhas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javaquinho.comidinhas.models.Mesa;
import javaquinho.comidinhas.repositories.MesaRepository;

@RestController
@RequestMapping("/mesas")
public class MesaController {
    @Autowired
    private MesaRepository mesaRepository;

    /**
     * Retorna todas as mesas do restaurante.
     *
     * @return uma lista de objetos Mesa
     */
    @GetMapping
    public List<Mesa> getAllMesas() {
        return mesaRepository.findAll();
    }

    /**
     * Retorna uma mesa específica com base no ID fornecido.
     *
     * @param id o ID da mesa a ser retornada
     * @return um objeto ResponseEntity contendo a mesa solicitada ou um status 404 se não encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<Mesa> getMesaById(@PathVariable int id) {
        return mesaRepository.findById(id)
            .map(mesa -> ResponseEntity.ok(mesa))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza o status de uma mesa para "ocupada" com base no ID fornecido.
     *
     * @param id o ID da mesa a ser atualizada
     * @param mesa um objeto Mesa contendo os dados para a atualização
     * @return um objeto ResponseEntity contendo a mesa atualizada ou um status 404 se não encontrada
     */
    @PatchMapping("/ocupar/{id}")
    public ResponseEntity<Mesa> updateOcuparMesa(@PathVariable int id, @RequestBody Mesa mesa) {
        return mesaRepository.findById(id)
            .map(mesaData -> {
                mesaData.ocupar();
                Mesa updatedMesa = mesaRepository.save(mesaData);
                return ResponseEntity.ok(updatedMesa);
            })
            .orElse(ResponseEntity.notFound().build());
    } 

    /**
     * Atualiza o status de uma mesa para "desocupada" com base no ID fornecido.
     *
     * @param id o ID da mesa a ser atualizada
     * @param mesa um objeto Mesa contendo os dados para a atualização
     * @return um objeto ResponseEntity contendo a mesa atualizada ou um status 404 se não encontrada
     */
    @PatchMapping("/desocupar/{id}")
    public ResponseEntity<Mesa> updateDesocuparMesa(@PathVariable int id, @RequestBody Mesa mesa) {
        return mesaRepository.findById(id)
            .map(mesaData -> {
                mesaData.desocupar();
                Mesa updatedMesa = mesaRepository.save(mesaData);
                return ResponseEntity.ok(updatedMesa);
            })
            .orElse(ResponseEntity.notFound().build());
    } 

    /**
     * Cria uma nova mesa.
     *
     * @param mesa um objeto Mesa contendo os dados da nova mesa
     * @return o objeto Mesa criado
     */
    @PostMapping
    public Mesa createMesa(@RequestBody Mesa mesa){
        return mesaRepository.save(mesa);
    }


    /**
     * Cria uma lista de novas mesas.
     *
     * @param mesas uma lista de objetos Mesa a serem criados
     * @return um objeto ResponseEntity indicando sucesso ou falha na operação
     */
    @PostMapping("/lista")
    public ResponseEntity<?> createMesas(@RequestBody List<Mesa> mesas) {
        try {
            List<Mesa> novasMesas = mesaRepository.saveAll(mesas);
            return ResponseEntity.ok("Mesas criadas com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar mesas: " + e.getMessage());
        }
    }

    /**
     * Deleta uma mesa com base no ID fornecido.
     *
     * @param id o ID da mesa a ser deletada
     * @return um objeto ResponseEntity indicando sucesso ou um status 404 se não encontrada
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Mesa> deleteMesa(@PathVariable int id) {
        return mesaRepository.findById(id)
            .map(mesa -> {
                mesaRepository.delete(mesa);
                return ResponseEntity.ok(mesa);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}