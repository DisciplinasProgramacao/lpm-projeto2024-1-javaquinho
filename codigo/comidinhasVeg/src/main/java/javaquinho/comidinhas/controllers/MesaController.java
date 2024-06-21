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

    @GetMapping
    public List<Mesa> getAllMesas() {
        return mesaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> getMesaById(@PathVariable int id) {
        return mesaRepository.findById(id)
            .map(mesa -> ResponseEntity.ok(mesa))
            .orElse(ResponseEntity.notFound().build());
    }

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

    @PostMapping
    public Mesa createMesa(@RequestBody Mesa mesa){
        return mesaRepository.save(mesa);
    }

    @PostMapping("/lista")
    public ResponseEntity<?> createMesas(@RequestBody List<Mesa> mesas) {
        try {
            List<Mesa> novasMesas = mesaRepository.saveAll(mesas);
            return ResponseEntity.ok("Mesas criadas com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao criar mesas: " + e.getMessage());
        }
    }

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
