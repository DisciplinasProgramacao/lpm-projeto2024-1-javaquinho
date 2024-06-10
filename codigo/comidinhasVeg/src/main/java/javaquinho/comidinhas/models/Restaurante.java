package javaquinho.comidinhas.models;
import java.util.ArrayList;

public class Restaurante {
    private ArrayList<Mesa> mesas;
    public ArrayList<Requisicao> filaAtendimento;
    private ArrayList<Requisicao> historicoAtendimento;
    private ArrayList<Cliente> listaClientes;
    Menu menu;

    public Restaurante() {
        setFilaAtendimento(new ArrayList<Requisicao>());
        setListaClientes(new ArrayList<Cliente>());
        setHistoricoAtendimento(new ArrayList<Requisicao>());
        iniciaMesas();
        menu = new Menu();
    }

    private void setFilaAtendimento(ArrayList<Requisicao> filaAtendimento) {
        this.filaAtendimento = filaAtendimento;
    }

    private void setHistoricoAtendimento(ArrayList<Requisicao> historicoAtendimento) {
        this.historicoAtendimento = historicoAtendimento;
    }

    private void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    private Mesa getMesa(int idMesa) {
        Mesa mesaRetorno = null;

        for (Mesa mesa : mesas) {
            if (mesa.getIdMesa() == idMesa) {
                mesaRetorno = mesa;
            }
        }
        return mesaRetorno;
    }

    private void iniciaMesas() {
        int[] capacidades = { 4, 6, 8 };
        int[] quant = { 4, 4, 2 };

        mesas = new ArrayList<Mesa>();
        Mesa mesa;

        for (int i = 0; i < quant.length; i++) {
            int quantidade = quant[i];
            for (int j = 0; j < quantidade; j++) {
                mesa = new Mesa(null, capacidades[i], false);
                mesas.add(mesa);
            }
        }
    }

    //public String getMenu() {
    //    return menu.retornaMenu();
    //}

    public Requisicao processarFila() {
        Requisicao requisicao = null;
        boolean alocado = false;
        int indice = 0;

        while (!alocado && indice < filaAtendimento.size()) {
            requisicao = filaAtendimento.get(indice);
            for (Mesa mesa : mesas) {
                if (mesa.estahLiberada(requisicao.quantPessoas())) {
                    requisicao.alocarMesa(mesa);
                    int indiceReq = indexListaRequisicao(requisicao);
                    removerRequisicaoFila(indiceReq);
                    alocado = true;
                    break;
                }
            }
            indice++;
        }
        if (!alocado) {
            requisicao = null;
        }

        return requisicao;
    }

    public void fecharConta(Mesa mesa) {
        for (Mesa mesaFila : mesas) {
            if (mesa.equals(mesaFila)) {
                mesa.desocupar();
                return;
            }
        }
    }

    public void criarRequisicao(String cpf, int qntPessoas) {
        int indice = getIndiceListaClientes(cpf);
        Cliente cliente = getCliente(indice);
        Requisicao requisicao = new Requisicao(cliente, qntPessoas);
        adicionarRequisicaoFila(requisicao);
    }

    private int indexListaRequisicao(Requisicao requisicao) {
        for (int index = 0; index < filaAtendimento.size(); index++) {
            if (filaAtendimento.get(index).equals(requisicao)) {
                return index;
            }
        }
        return -1;
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
        for (Cliente cliente : listaClientes) {
            if (cliente.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public void newCliente(String nome, String telContato, String cpf) {
        Cliente cliente = new Cliente(null, nome, telContato, cpf);
        listaClientes.add(cliente);
    }

    public Cliente localizarCliente(int idCliente) {
        for (Cliente clienteL : listaClientes) {
            if (clienteL.getId() == idCliente) {
                return clienteL;
            }
        }
        return null;
    }

    public int getIndiceListaClientes(String cpf) {
        for (int index = 0; index < listaClientes.size(); index++) {
            if (listaClientes.get(index).getCpf().equals(cpf)) {
                return index;
            }
        }
        return -1;
    }

    public void addCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    private Cliente getCliente(int posicao) {
        return listaClientes.get(posicao);
    }

    public void mostrarFilaAtendimento() {
        for (Requisicao requisicao : filaAtendimento) {
            System.out.println(requisicao);
        }
    }

    public Requisicao encerrarAtendimento(int idMesa) {
        Requisicao requisicao = null;
        for (Requisicao requisicaoFila : filaAtendimento) {
            if (requisicaoFila.ehDaMesa(idMesa)) {
                requisicao = requisicaoFila;
                break;
            }
        }
        if (requisicao != null) {
            requisicao.encerrar();
        }
        return requisicao;
    }

    public void statusMesas() {
        for (Mesa mesa : mesas) {
            System.out.println(mesa);
        }
    }

    public void filaDeEspera() {
        for (Requisicao requisicao : filaAtendimento) {
            System.out.println(requisicao);
        }
    }
}