package javaquinho.comidinhas.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurante")
@Getter
@Setter
@Component
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long idRestaurante;

    @Column(name = "nome", nullable = false)
    private String name;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Mesa> mesas = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Requisicao> listaRequisicao = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cliente> listaClientes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Menu menu;

    public Restaurante(String nome) {
        this.name = nome;
        this.listaRequisicao = new ArrayList<>();
        this.listaClientes = new ArrayList<>();
        iniciaMesas();
        this.menu = new Menu();
    }

    // Inicia as mesas do restaurante
    private void iniciaMesas() {
        int[] capacidades = { 4, 6, 8 };
        int[] quant = { 4, 4, 2 };

        for (int i = 0; i < quant.length; i++) {
            int quantidade = quant[i];
            for (int j = 0; j < quantidade; j++) {
                Mesa mesa = new Mesa(null, capacidades[i], false, this);
                mesas.add(mesa);
            }
        }
    }


    // Encontra a mesa pelo Id
    private Mesa getMesa(Long idMesa) {
        int pos =mesas.indexOf(idMesa);
        return mesas.get(pos);

        // return mesas.stream()
        //         .filter(mesa -> mesa.getIdMesa().equals(idMesa))
        //         .findFirst()
        //         .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
    }

    // Função que processa a fila de atendimento, alocando as requisições nas mesas
    // disponíveis
    public void processarFilaRequisicao() {
        Requisicao req =
        listaRequisicao.stream()
                .filter(r -> !r.getAtendida())
                .findFirst().orElse(null);
        if(req!=null){
            Mesa mesa = mesas.stream().filter(m -> m.estahLiberada(req.getQuantPessoas()))
                        .findFirst().orElse(null);
                        
            if(mesa!=null)
                req.alocarMesa(mesa);

        }
                // .forEach(r -> {
                //     mesas.stream()
                //             .filter(m -> m.estahLiberada(r.getQuantPessoas()))
                //             .findFirst()
                //             .ifPresent(m -> {
                //                 r.alocarMesa(m);
                //                 m.ocupar();
                //             });
                // });
    }

    // Cria uma requisição a partir de um CPF e quantidade de pessoas
    public void criarRequisicao(String cpf, int qntPessoas) {
        Cliente cliente = listaClientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        Requisicao requisicao = new Requisicao(cliente, qntPessoas, this);
        listaRequisicao.add(requisicao);
        processarFilaRequisicao();
    }

    // Verifica se o cliente já existe
    public Boolean clienteExiste(String cpf) {
        return listaClientes.stream().anyMatch(cliente -> cliente.getCpf().equals(cpf));
    }

    // Cadastrar novo cliente ao restaurante
    public void newCliente(String nome, String telContato, String cpf) {  //receber o cliente pronto do main
        if (!clienteExiste(cpf)) {
            Cliente cliente = new Cliente(null, nome, telContato, cpf, this);// tirar o restaurante
            listaClientes.add(cliente);
        } else
            throw new RuntimeException("Cliente já cadastrado");
    }

    // Localizar cliente pelo CPF
    public Cliente localizarCliente(String cpf) {
        return listaClientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    // Encerra uma requisição através do número da mesa que ela está alocada
    public void encerrarAtendimento(Long idMesa) {
        Requisicao requisicao = listaRequisicao.stream()    //esta correto existir, porem provavelmente eh melhor outra estrutura que nao seja uma lista para buscar.
                .filter(r -> r.ehDaMesa(idMesa))
                .findFirst()
                .orElse(null);
        if (requisicao != null) {
            requisicao.encerrar(getMesa(idMesa));
            processarFilaRequisicao();
        }
    }

    // Lista de requisições já atendidas
    public void historicoAtendimento() {
        listaRequisicao.stream()
                .filter(r -> r.getEncerrada())
                .toList();
                //criar uma string a partir da lista e retornar
    }

    // Lista de requisições não atendidas
    public void filaDeEspera() {
        listaRequisicao.stream()
                .filter(r -> !r.getAtendida());
    }

    // Localização de requisição pelo cpf
    public Requisicao localizarRequisicaoEmAtendimento(String cpf){
        Cliente c = localizarCliente(cpf);
        return listaRequisicao.stream()
        .filter(r -> r.getAtendida())
        .filter(r -> !r.getEncerrada())
        .filter(r -> r.getCliente().equals(c))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Nenhuma requisisição encontrada"));
    }

    // Adicionar produto à requisicao
    public void addProdutoPedido(Long idMesa, String nomeProduto){
       //metodo de localizar requisicao
       //produto eh localizado no cardapio
       r.adicionarProduto(prod);
       
        listaRequisicao.stream()
        .filter(r -> r.ehDaMesa(idMesa))
        .findFirst()
        .ifPresent(r -> {
            Produto prod = menu.getProdutos().stream()
            .filter(p -> p.getNome().equals(nomeProduto))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            r.adicionarProduto(prod);
            });
    }

}