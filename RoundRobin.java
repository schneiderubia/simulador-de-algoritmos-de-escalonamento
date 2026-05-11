import java.util.ArrayList;

public class RoundRobin extends SimuladorBase implements Escalonador {

    public String executar(ArrayList<Processo> processosOriginais, int quantum) {
        ArrayList<Processo> processos = copiarProcessos(processosOriginais);
        ArrayList<Processo> filaProntos = new ArrayList<Processo>();
        ArrayList<Processo> bloqueados = new ArrayList<Processo>();

        StringBuilder saida = new StringBuilder();

        int tempo = 0;
        int tempoQuantumAtual = 0;

        Processo executando = null;

        saida.append("Algoritmo escolhido: Round Robin\n");
        saida.append("Quantum: ").append(quantum).append(" ms\n");
        saida.append("--------------------------------------\n");

        if (quantum <= 0) {
            saida.append("Erro: quantum precisa ser maior que zero.\n");
            return saida.toString();
        }

        while (todosFinalizados(processos) == false) {
            verificarChegadas(processos, filaProntos, tempo, saida);

            if (executando == null) {
                if (filaProntos.size() > 0) {
                    executando = filaProntos.remove(0);
                    tempoQuantumAtual = 0;

                    saida.append("[t=").append(tempo).append("] ");
                    saida.append(executando.nome).append(" começou a executar.");
                    saida.append("\n");
                }
            }

            boolean terminouCpu = false;

            if (executando != null) {
                int fimTempo = tempo + 1;

                saida.append("[t=").append(tempo).append(" -> t=").append(fimTempo).append("] ");
                saida.append("Executando ").append(executando.nome).append(".");
                saida.append("\n");

                executando.executarUmMilissegundo();
                tempoQuantumAtual = tempoQuantumAtual + 1;

                if (executando.terminouCpuAtual()) {
                    terminouCpu = true;
                }
            } else {
                int fimTempo = tempo + 1;

                saida.append("[t=").append(tempo).append(" -> t=").append(fimTempo).append("] ");
                saida.append("CPU ociosa.");
                saida.append("\n");
            }

            atualizarIO(bloqueados, filaProntos, tempo, saida);

            if (executando != null && terminouCpu == true) {
                executando = tratarFimCpu(executando, bloqueados, tempo + 1, saida);

                if (executando == null) {
                    tempoQuantumAtual = 0;
                }
            }

            if (executando != null) {
                if (tempoQuantumAtual == quantum) {
                    saida.append("[t=").append(tempo + 1).append("] ");
                    saida.append(executando.nome).append(" foi preemptado e voltou para a fila de prontos.");
                    saida.append("\n");

                    colocarNaFila(executando, filaProntos);

                    executando = null;
                    tempoQuantumAtual = 0;
                }
            }

            tempo = tempo + 1;
        }

        saida.append("--------------------------------------\n");
        saida.append("Simulação finalizada em t=").append(tempo).append(" ms.\n");

        return saida.toString();
    }
}