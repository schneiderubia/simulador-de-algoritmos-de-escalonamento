import java.util.ArrayList;

public class Prioridade extends SimuladorBase implements Escalonador {

    public String executar(ArrayList<Processo> processosOriginais, int quantum) {
        ArrayList<Processo> processos = copiarProcessos(processosOriginais);
        ArrayList<Processo> filaProntos = new ArrayList<Processo>();
        ArrayList<Processo> bloqueados = new ArrayList<Processo>();

        StringBuilder saida = new StringBuilder();

        int tempo = 0;
        Processo executando = null;

        saida.append("Algoritmo escolhido: Prioridade\n");
        saida.append("Menor número significa maior prioridade.\n");
        saida.append("--------------------------------------\n");

        while (todosFinalizados(processos) == false) {
            verificarChegadas(processos, filaProntos, tempo, saida);

            if (executando == null) {
                if (filaProntos.size() > 0) {
                    executando = removerMaiorPrioridade(filaProntos);

                    saida.append("[t=").append(tempo).append("] ");
                    saida.append(executando.nome).append(" começou a executar com prioridade ");
                    saida.append(executando.prioridade).append(".");
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

    private Processo removerMaiorPrioridade(ArrayList<Processo> filaProntos) {
        int indiceMelhor = 0;

        for (int i = 1; i < filaProntos.size(); i++) {
            Processo atual = filaProntos.get(i);
            Processo melhor = filaProntos.get(indiceMelhor);

            if (atual.prioridade < melhor.prioridade) {
                indiceMelhor = i;
            } else {
                if (atual.prioridade == melhor.prioridade) {
                    if (atual.ordemFila < melhor.ordemFila) {
                        indiceMelhor = i;
                    }
                }
            }
        }

        return filaProntos.remove(indiceMelhor);
    }
}