import java.util.ArrayList;

public class FCFS extends SimuladorBase implements Escalonador {

    public String executar(ArrayList<Processo> processosOriginais, int quantum) {
        ArrayList<Processo> processos = copiarProcessos(processosOriginais);
        ArrayList<Processo> filaProntos = new ArrayList<Processo>();
        ArrayList<Processo> bloqueados = new ArrayList<Processo>();

        StringBuilder saida = new StringBuilder();

        int tempo = 0;
        Processo executando = null;

        saida.append("Algoritmo escolhido: FCFS\n");
        saida.append("--------------------------------------\n");

        while (todosFinalizados(processos) == false) {
            verificarChegadas(processos, filaProntos, tempo, saida);

            if (executando == null) {
                if (filaProntos.size() > 0) {
                    executando = filaProntos.remove(0);

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
            }

            tempo = tempo + 1;
        }

        saida.append("--------------------------------------\n");
        saida.append("Simulação finalizada em t=").append(tempo).append(" ms.\n");

        return saida.toString();
    }
}