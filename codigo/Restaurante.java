import java.util.ArrayList;

public class Restaurante {
    private ArrayList<Mesa> mesas;
    private ArrayList<Requisicao> filaAtendimento;
    private ArrayList<Requisicao> historicoAtendimento;
    private ArrayList<Cliente> listaClientes;

    Restaurante() {
        setfilaAtendimento(new ArrayList<Requisicao>());
        setlistaClientes(new ArrayList<Cliente>());
        setHistoricoAtendimento(new ArrayList<Requisicao>());
        iniciaMesas();
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

    private void iniciaMesas() {
        int[] capacidades = { 4, 6, 8 };
        int[] quant = { 4, 4, 2 };

        Mesa mesa = null;

        for (int i = 0; i < quant.length; i++) {
            int quantidade = quant[i];
            for (int j = 0; j < quantidade; j++) {
                mesa = new Mesa(capacidades[i]);
                mesas.add(mesa);
            }
        }
    }

    /**
     * Método para alocar mesa com a primeira requisição disponível
     */
    public void alocarMesa() {
        Requisicao requisicao;
        boolean alocado = false;
        int indice = 0;

        while (!alocado) {
            requisicao = filaAtendimento.get(indice);
            for (Mesa mesa : mesas) {
                if (mesa.getCapacidade() <= requisicao.getQuantidadePessoas()
                        && !mesa.estaOcupada()) {
                    mesa.ocupar(requisicao);
                    int indiceReq = indexListaRequisicao(requisicao);
                    removerRequisicaoFila(indiceReq);
                    alocado = true;
                }
            }

            indice++;
        }
    }

    /**
     * Método para fechar a conta da mesa indicada
     * 
     * @param mesa mesa que terá a conta fechada
     */
    public void fecharConta(Mesa mesa) {
        for (Mesa mesaFila : mesas) {
            if (mesa.equals(mesaFila)) {
                mesa.desocupar();
                return;
            }
        }
    }

    /**
     * Cria nova requisição a partir do cpf e quantidade de clientes informados
     * 
     * @param cpf cpf do cliente dono da requisição
     * @param qntPessoas quantidade de pessoas para a mesa
     */
    private void criarRequisicao(String cpf, int qntPessoas) {
        int indice = getIndiceListaClientes(cpf);
        Cliente cliente = getCliente(indice);
        Requisicao requisicao = new Requisicao(cliente, qntPessoas);
        adicionarRequisicaoFila(requisicao);
    }

    /**
     * Retorna o index da requisição que estamos procurando na fila de atendimento
     * 
     * @param requisicao requisição que queremos pesquisar o index
     */
    private int indexListaRequisicao(Requisicao requisicao) {
        int index = -1;
        for (Requisicao requisicaoFila : filaAtendimento) {
            if (requisicaoFila.equals(requisicao)) {
                return index;
            }

            index++;
        }

        return index;
    }

    /**
     * Método para adicionar requisição na lista de requisições
     * 
     * @param requisicao requisição que será adicionada a lista
     */
    public void adicionarRequisicaoFila(Requisicao requisicao) {
        filaAtendimento.add(requisicao);
    }

    /**
     * Método para remover requisição na lista de requisições
     * 
     * @param requisicao requisição que será removida da lista
     */
    public void removerRequisicaoFila(int index) {
        filaAtendimento.remove(index);
    }

    /**
     * Método para adicionar requisição no histórico de requisições
     * 
     * @param requisicao requisição que será adicionada ao histórico
     */
    public void adicionarRequisicaoAoHistorico(Requisicao requisicao) {
        historicoAtendimento.add(requisicao);
    }

    /**
     * Retorna se o cadastro do cliente existe ou não no restaurante
     * 
     * @param cpf string com o cpf do cliente que estamos procurando
     */
    private boolean clienteExiste(String cpf) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getCPF() == cpf) {
                return true;
            }
        }

        return false;
    }

    /**
     * Cadastra cliente no sistema
     * 
     * @param nome       nome do cliente que será cadastrado
     * @param telContato telefone do cliente que será cadastrado
     * @param cpf        cpf do cliente que será cadastrado
     */
    public void newCliente(String nome, String telContato, String cpf) {
        Cliente cliente = new Cliente(nome, telContato, cpf);
        listaClientes.add(cliente);
        return;
    }

    /**
     * Retorna a posição do cliente na lista de clientes do restaurante
     * 
     * @param cpf cpf do cliente que estamos procurando
     */
    public int getIndiceListaClientes(String cpf) {
        int index = -1;
        for (Cliente cliente : listaClientes) {
            if (cliente.getCPF() == cpf) {
                return index;
            }

            index++;
        }

        return index;
    }

    /**
     * Retorna os dados do cliente na lista de clientes do restaurante, na posição
     * solicitada
     * 
     * @param posicao posicao do cliente que estamos procurando
     */
    private Cliente getCliente(int posicao) {
        return listaClientes.get(posicao);
    }
}