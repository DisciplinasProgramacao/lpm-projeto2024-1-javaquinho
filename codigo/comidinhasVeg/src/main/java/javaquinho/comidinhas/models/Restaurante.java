package javaquinho.comidinhas.models;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javaquinho.comidinhas.repositories.ClienteRepository;
import javaquinho.comidinhas.repositories.MesaRepository;
import javaquinho.comidinhas.repositories.MenuRepository;
import javaquinho.comidinhas.repositories.RequisicaoRepository;

@Service
public class Restaurante {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RequisicaoRepository requisicaoRepository;

    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Mesa criarMesa(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    public Menu criarMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public String alocarMesaParaRequisicao(Long requisicaoId) {
        Requisicao requisicao = requisicaoRepository.findById(requisicaoId).orElse(null);
        if (requisicao == null) {
            return "Requisição não encontrada.";
        }

        int quantPessoas = requisicao.getQuantPessoas();
        List<Mesa> mesasDisponiveis = mesaRepository.findByCapacidadeAndOcupada(quantPessoas, false);

        if (mesasDisponiveis.isEmpty()) {
            return "Não há mesas disponíveis para a quantidade de pessoas.";
        }

        Mesa mesa = mesasDisponiveis.get(0);
        requisicao.alocarMesa(mesa);
        requisicaoRepository.save(requisicao);
        return "Mesa alocada com sucesso.";
    }

    public String desalocarMesaDeRequisicao(Long requisicaoId) {
        Requisicao requisicao = requisicaoRepository.findById(requisicaoId).orElse(null);
        if (requisicao == null) {
            return "Requisição não encontrada.";
        }

        if (requisicao.getMesa() == null) {
            return "Nenhuma mesa está alocada a esta requisição.";
        }

        requisicao.setSaida(LocalDateTime.now());
        requisicao.getMesa().desocupar();
        requisicao.setMesa(null);
        requisicaoRepository.save(requisicao);
        return "Mesa desalocada com sucesso.";
    }
}
