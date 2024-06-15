package javaquinho.comidinhas.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "restaurante")
@Getter
@Setter
@NoArgsConstructor
@Component
public class Restaurante {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mesa> mesas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Requisicao> filaAtendimento = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Requisicao> historicoAtendimento = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cliente> listaClientes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Menu menu;

    public Restaurante() {
        this.filaAtendimento = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        this.historicoAtendimento = new ArrayList<>();
        iniciaMesas();
        this.menu = new Menu();
    }

    // Não sei se tem que manter isso aq
    private void iniciaMesas() {
        int[] capacidades = {4, 6, 8};
        int[] quant = {4, 4, 2};

        for (int i = 0; i < quant.length; i++) {
            int quantidade = quant[i];
            for (int j = 0; j < quantidade; j++) {
                Mesa mesa = new Mesa(capacidades[i]);
                mesas.add(mesa);
            }
        }
    }


    // Mantém assim, ou só chama o toString de Mesa?
    private Mesa getMesa(Long idMesa) {
        return mesas.stream()
                .filter(mesa -> mesa.getIdMesa().equals(idMesa))
                .findFirst()
                .orElse(null);
    }

    public Requisicao processarFila() {
        Requisicao requisicao = null;
        boolean alocado = false;
        int indice = 0;

        while (!alocado && indice < filaAtendimento.size()) {
            requisicao = filaAtendimento.get(indice);
            for (Mesa mesa : mesas) {
                if (mesa.estahLiberada(requisicao.getQuantPessoas())) {
                    requisicao.alocarMesa(mesa);
                    filaAtendimento.remove(indice);
                    alocado = true;
                    break;
                }
            }
            if (!alocado) indice++;
        }

        return alocado ? requisicao : null;
    }

    public void fecharConta(Mesa mesa) {
        requisicao.exibirConta();
        requisicao.encerrar();
    }

    public void criarRequisicao(String cpf, int qntPessoas) {
        Cliente cliente = listaClientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        Requisicao requisicao = new Requisicao(cliente, qntPessoas);
        filaAtendimento.add(requisicao);
    }

    public void adicionarRequisicaoFila(Requisicao requisicao) {
        filaAtendimento.add(requisicao);
    }

    public void removerRequisicaoFila(int index) {
        filaAtendimento.remove(index);
    }

    public void adicionarRequisicaoAoHistorico(Requisicao requisicao) {
        historicoAtendimento.add(requisicao);
    }

    public boolean clienteExiste(String cpf) {
        return listaClientes.stream().anyMatch(cliente -> cliente.getCpf().equals(cpf));
    }

    public void newCliente(String nome, String telContato, String cpf) {
        Cliente cliente = new Cliente(nome, telContato, cpf);
        listaClientes.add(cliente);
    }

    public Cliente localizarCliente(Long idCliente) {
        return listaClientes.stream()
                .filter(cliente -> cliente.getId().equals(idCliente))
                .findFirst()
                .orElse(null);
    }

    public void mostrarFilaAtendimento() {
        filaAtendimento.forEach(System.out::println);
    }

    public Requisicao encerrarAtendimento(Long idMesa) {
        Requisicao requisicao = filaAtendimento.stream()
                .filter(req -> req.ehDaMesa(idMesa))
                .findFirst()
                .orElse(null);

        if (requisicao != null) {
            requisicao.encerrar();
            filaAtendimento.remove(requisicao);
            adicionarRequisicaoAoHistorico(requisicao);
        }

        return requisicao;
    }

    public void statusMesas() {
        mesas.forEach(System.out::println);
    }

    public void filaDeEspera() {
        filaAtendimento.forEach(System.out::println);
    }
}
