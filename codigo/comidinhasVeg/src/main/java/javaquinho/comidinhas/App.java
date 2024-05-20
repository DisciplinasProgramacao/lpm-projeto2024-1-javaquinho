/*package javaquinho.comidinhas;

import java.util.Scanner;

import javaquinho.comidinhas.models.Restaurante;

public class App {
    public static void main(String[] args) {

        // Inicialização do Scanner para leitura de entrada do usuário
        Scanner scanner = new Scanner(System.in);
        // Inicialização da instância da classe Restaurante
        Restaurante restaurante = new Restaurante();
        // Variável para armazenar a opção escolhida pelo usuário
        int opcao;

        // Loop principal do programa
        do {
            // Exibe o menu de opções para o usuário
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Criar nova requisição");
            System.out.println("3. Mostrar fila de atendimento");
            System.out.println("4. Mostrar menu");
            System.out.println("5. Preencher pedido");
            System.out.println("6. Fechar Conta");
            System.out.println("7. Sair");

            // Obtém a opção escolhida pelo usuário
            opcao = scanner.nextInt();

            // Realiza a ação correspondente à opção escolhida
            switch (opcao) {

                case 1:   // Cadastrar cliente ou criar requisição
                    String nome,  telContato,  CPF;
                    System.out.println("Digite o CPF: ");
                    CPF = scanner.next();
                    scanner.nextLine();
                    if(restaurante.clienteExiste(CPF)){
                        System.out.println("Cliente já possui cadastro.");
                    } else{
                        System.out.println("Digite o nome: ");
                        nome = scanner.nextLine();
                        System.out.println("Digite o telefone de contato: ");
                        telContato = scanner.nextLine();
                        restaurante.newCliente(nome, telContato, CPF);
                    }

                    // Verifica se o usuário deseja criar uma requisição de mesa
                    System.out.println("Deseja requerir uma mesa? Digite 1 para sim ou 0 para não.");
                    int continuar = scanner.nextInt();

                    // Se o usuário optar por não criar uma requisição, volta ao menu principal
                    if(continuar == 0){
                        break;
                    }

                case 2:  
                    String cpf; 
                    int quantidadePessoas;

                    // Solicita o CPF do cliente
                    System.out.println("Digite o CPF: ");
                    cpf = scanner.next();
                    scanner.nextLine();
                    // Solicita a quantidade de pessoas para a requisição
                    System.out.println("Digite a quantidade de pessoas: ");
                    quantidadePessoas = scanner.nextInt();
                    // Cria a requisição de mesa para o cliente informado
                    restaurante.criarRequisicao(cpf, quantidadePessoas); 
                    break;

                case 3: // Mostra a fila de atendimento
                    restaurante.mostrarFilaAtendimento();  
                    break;

                case 4:// Mostra o menu do restaurante
                    System.out.println(restaurante.getMenu());
                    break;
                
                case 5:  // Preenche o pedido de uma Requisição/Mesa
                    // Implementação do preenchimento do pedido
                    break;

                case 6: // Fecha a conta de uma mesa
                    // Implementação do fechamento de conta
                    break;

                case 7: // Sai do programa
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        } while (opcao != 7); // Continua o loop até que o usuário escolha a opção de sair

        scanner.close(); // Fecha o Scanner para liberar recursos
    }
}*/
