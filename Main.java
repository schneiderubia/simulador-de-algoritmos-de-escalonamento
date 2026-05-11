import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String caminhoArquivo = "entrada.txt";

        System.out.println("===== SIMULADOR DE ESCALONAMENTO =====");
        System.out.println("1 - FCFS");
        System.out.println("2 - Prioridade");
        System.out.println("3 - Round Robin");
        System.out.print("Escolha o algoritmo: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        Dados dados = Leitor.ler(caminhoArquivo);

        Escalonador escalonador = null;

        if (opcao == 1) {
            escalonador = new FCFS();
        }

        if (opcao == 2) {
            escalonador = new Prioridade();
        }

        if (opcao == 3) {
            escalonador = new RoundRobin();
        }

        if (escalonador == null) {
            System.out.println("Opção inválida.");
            return;
        }

        String resultado = escalonador.executar(dados.processos, dados.quantum);

        System.out.println();
        System.out.println("===== RESULTADO =====");
        System.out.println(resultado);

        scanner.close();
    }
}