import java.util.ArrayList;

public abstract class SimuladorBase {

    int contadorOrdemFila = 0;

    public ArrayList<Processo> copiarProcessos(ArrayList<Processo> processosOriginais) {
        ArrayList<Processo> copia = new ArrayList<Processo>();

        for (int i = 0; i < processosOriginais.size(); i++) {
            copia.add(processosOriginais.get(i).copiar());
        }

        return copia;
    }

    public boolean todosFinalizados(ArrayList<Processo> processos) {
        for (int i = 0; i < processos.size(); i++) {
            if (processos.get(i).finalizado == false) {
                return false;
            }
        }

        return true;
    }

    public void verificarChegadas(
            ArrayList<Processo> processos,
            ArrayList<Processo> filaProntos,
            int tempo,
            StringBuilder saida
    ) {
        for (int i = 0; i < processos.size(); i++) {
            Processo processo = processos.get(i);

            if (processo.entrouNoSistema == false && processo.chegada <= tempo) {
                processo.entrouNoSistema = true;
                colocarNaFila(processo, filaProntos);

                saida.append("[t=").append(tempo).append("] ");
                saida.append(processo.nome).append(" chegou e entrou na fila de prontos.");
                saida.append("\n");
            }
        }
    }

    public void colocarNaFila(Processo processo, ArrayList<Processo> filaProntos) {
        processo.ordemFila = contadorOrdemFila;
        contadorOrdemFila = contadorOrdemFila + 1;

        filaProntos.add(processo);
    }

    public void atualizarIO(
            ArrayList<Processo> bloqueados,
            ArrayList<Processo> filaProntos,
            int tempo,
            StringBuilder saida
    ) {
        int fimTempo = tempo + 1;

        for (int i = bloqueados.size() - 1; i >= 0; i--) {
            Processo processo = bloqueados.get(i);

            processo.restanteIO = processo.restanteIO - 1;

            saida.append("[t=").append(tempo).append(" -> t=").append(fimTempo).append("] ");
            saida.append(processo.nome).append(" aguardando E/S.");
            saida.append("\n");

            if (processo.restanteIO == 0) {
                processo.faseCpu = 2;

                bloqueados.remove(i);
                colocarNaFila(processo, filaProntos);

                saida.append("[t=").append(fimTempo).append("] ");
                saida.append(processo.nome).append(" terminou E/S e voltou para a fila de prontos.");
                saida.append("\n");
            }
        }
    }

    public Processo tratarFimCpu(
            Processo processo,
            ArrayList<Processo> bloqueados,
            int tempo,
            StringBuilder saida
    ) {
        if (processo.faseCpu == 1) {
            if (processo.cpu2 > 0) {
                if (processo.io > 0) {
                    bloqueados.add(processo);

                    saida.append("[t=").append(tempo).append("] ");
                    saida.append(processo.nome).append(" terminou CPU 1 e foi para E/S.");
                    saida.append("\n");

                    return null;
                } else {
                    processo.faseCpu = 2;

                    saida.append("[t=").append(tempo).append("] ");
                    saida.append(processo.nome).append(" terminou CPU 1 e continuará na CPU 2.");
                    saida.append("\n");

                    return processo;
                }
            } else {
                processo.finalizado = true;

                saida.append("[t=").append(tempo).append("] ");
                saida.append(processo.nome).append(" finalizado.");
                saida.append("\n");

                return null;
            }
        }

        if (processo.faseCpu == 2) {
            processo.finalizado = true;

            saida.append("[t=").append(tempo).append("] ");
            saida.append(processo.nome).append(" finalizado.");
            saida.append("\n");

            return null;
        }

        return processo;
    }
}