package javaquinho.comidinhas;
import java.util.Scanner;

import javaquinho.comidinhas.models.Cliente;
import javaquinho.comidinhas.models.Requisicao;
import javaquinho.comidinhas.models.Restaurante;

public class AppJC {
    static Scanner teclado;
    static Restaurante restaurante;

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println(" LPM VEGAN - v0.1 ");
        System.out.println("=====================");
    }

    static int MenuPrincipal() {
        int opcao;
        cabecalho();    
        System.out.println("1 - Verificar Mesas");
        System.out.println("2 - Verificar Fila");
        System.out.println("3 - Solicitar Mesa");
        System.out.println("4 - Encerrar Mesa");
        System.out.println("5 - Processar Fila");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        opcao = Integer.parseInt(teclado.nextLine());
        return opcao;
    }

    static void mostrarFila() {
        cabecalho();
        restaurante.filaDeEspera();
        pausa();
    }

    static void exibirMesas() {
        cabecalho();
        restaurante.statusMesas();
        pausa();
    }
    
    static void solicitarMesa() {
        int idCli;
        cabecalho();
        System.out.print("Digite o id do cliente: ");
        idCli = Integer.parseInt(teclado.nextLine());
        Cliente localizado = restaurante.localizarCliente(idCli);
        if (localizado == null) {
            System.out.println("Cliente não localizado. Vamos cadastrá-lo:");
            localizado = cadastrarNovoCliente();
            restaurante.addCliente(localizado);
        }
        criarRequisicao(localizado);
    }

    static Cliente cadastrarNovoCliente() {
        String nome, telContato, CPF;
        System.out.print("Nome do cliente: ");
        nome = teclado.nextLine();
        System.out.print("Telefone do cliente: ");
        telContato = teclado.nextLine();
        System.out.print("CPF do cliente: ");
        CPF = teclado.nextLine();
        Cliente novo = new Cliente(null, nome, telContato, CPF);
        System.out.println("Cliente cadastrado: " + novo);
        pausa();
        return novo;
    }

    static void criarRequisicao(Cliente cliente) {
        int quantasPessoas;
        cabecalho();
        System.out.print("Para quantas pessoas será a mesa? ");
        quantasPessoas = Integer.parseInt(teclado.nextLine());
        Requisicao novaRequisicao = new Requisicao(cliente, quantasPessoas);
        restaurante.adicionarRequisicaoFila(novaRequisicao);
        novaRequisicao = processarFila();
        if (novaRequisicao != null) {
            System.out.println(novaRequisicao);
        } else {
            System.out.println("Não há mesas disponíveis no momento. Requisição em espera");
        }
        pausa();
    }

    static Requisicao processarFila() {
       return restaurante.processarFila();
    }

    static void encerrarMesa() {
        int idMesa;
        cabecalho();
        System.out.print("Qual o número da mesa para encerrar atendimento? ");
        idMesa = Integer.parseInt(teclado.nextLine());
        Requisicao finalizada = restaurante.encerrarAtendimento(idMesa);
        if (finalizada != null) {
            System.out.println(finalizada);
        } else {
            System.out.println("Mesa " + idMesa + " não está em atendimento");
        }
        pausa();
    }

    public static void main(String[] args) throws Exception {
        teclado = new Scanner(System.in);
        int opcao;
        restaurante = new Restaurante();
        do {
            opcao = MenuPrincipal();
            switch (opcao) {
                case 1 -> exibirMesas();
                case 2 -> mostrarFila();
                case 3 -> solicitarMesa();
                case 4 -> encerrarMesa();
                case 5 -> {
                    Requisicao atendida = processarFila();
                    if (atendida != null) {
                        System.out.println(atendida);
                    } else {
                        System.out.println("Fila vazia ou mesas não disponíveis. Favor verificar a situação.");
                    }
                    pausa();
                }
            }
        } while (opcao != 0);
        teclado.close();
    }
}