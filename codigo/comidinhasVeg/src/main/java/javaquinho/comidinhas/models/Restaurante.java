package javaquinho.comidinhas.models;

import java.util.ArrayList;

public class Restaurante {
    private ArrayList<Mesa> mesas;
    public ArrayList<Requisicao> filaAtendimento;
    private ArrayList<Requisicao> historicoAtendimento;
    private ArrayList<Cliente> listaClientes;
    Menu menu;

    public Restaurante() {
        setfilaAtendimento(new ArrayList<Requisicao>());
        setlistaClientes(new ArrayList<Cliente>());
        setHistoricoAtendimento(new ArrayList<Requisicao>());
        iniciaMesas();
        menu = new Menu();
    }

    private void setfilaAtendimento(ArrayList<Requisicao> filaAtendimento) {
        this.filaAtendimento = filaAtendimento;
    }

    private void setHistoricoAtendimento(ArrayList<Requisicao> historicoAtendimento) {
        this.historicoAtendimento = historicoAtendimento;
    }

    private void setlistaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Mesa getMesa(int idMesa) {
        for (Mesa mesa : mesas) {
            if (mesa.getIdMesa() == idMesa) {
                return mesa;
            }
        }
        return null;
    }

    private void iniciaMesas() {
        int[] capacidades = { 4, 6, 8 };
        int[] quant = { 4, 4, 2 };

        mesas = new ArrayList<Mesa>();
        for (int i = 0; i < quant.length; i++) {
            int quantidade = quant[i];
            for (int j = 0; j < quantidade; j++) {
                mesas.add(new Mesa(capacidades[i]));
            }
        }
    }

    /*public String getMenu() {
        return menu.retornaMenu();
    }*/

    public Requisicao processarFila() {
        for (Requisicao requisicao : filaAtendimento) {
            for (Mesa mesa : mesas) {
                if (mesa.estahLiberada(requisicao.quantPessoas())) {
                    requisicao.alocarMesa(mesa);
                    removerRequisicaoFila(filaAtendimento.indexOf(requisicao));
                    return requisicao;
                }
            }
        }
        return null;
    }

    public void fecharConta(Mesa mesa) {
        mesa.desocupar();
        adicionarRequisicaoAoHistorico(encerrarAtendimento(mesa.getIdMesa()));
    }

    public void criarRequisicao(String cpf, int qntPessoas) {
        int indice = getIndiceListaClientes(cpf);
        Cliente cliente = getCliente(indice);
        Requisicao requisicao = new Requisicao(cliente, qntPessoas);
        adicionarRequisicaoFila(requisicao);
    }

    private int indexListaRequisicao(Requisicao requisicao) {
        int index = 0;
        for (Requisicao requisicaoFila : filaAtendimento) {
            if (requisicaoFila.equals(requisicao)) {
                return index;
            }
            index++;
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
            if (cliente.getCPF().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public void newCliente(String nome, String telContato, String cpf) {
        Cliente cliente = new Cliente(nome, telContato, cpf);
        listaClientes.add(cliente);
    }

    public Cliente localizarCliente(int idCliente) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getId() == idCliente) {
                return cliente;
            }
        }
        return null;
    }

    public int getIndiceListaClientes(String cpf) {
        int index = 0;
        for (Cliente cliente : listaClientes) {
            if (cliente.getCPF().equals(cpf)) {
                return index;
            }
            index++;
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
                requisicao.encerrar();
                removerRequisicaoFila(filaAtendimento.indexOf(requisicao));
                break;
            }
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
