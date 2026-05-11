public class Processo {

    String nome;
    int chegada;
    int cpu1;
    int io;
    int cpu2;
    int prioridade;

    int restanteCpu1;
    int restanteIO;
    int restanteCpu2;

    int faseCpu;
    int ordemFila;

    boolean entrouNoSistema;
    boolean finalizado;

    public Processo(String nome, int chegada, int cpu1, int io, int cpu2, int prioridade) {
        this.nome = nome;
        this.chegada = chegada;
        this.cpu1 = cpu1;
        this.io = io;
        this.cpu2 = cpu2;
        this.prioridade = prioridade;

        this.restanteCpu1 = cpu1;
        this.restanteIO = io;
        this.restanteCpu2 = cpu2;

        this.faseCpu = 1;
        this.ordemFila = 0;

        this.entrouNoSistema = false;
        this.finalizado = false;
    }

    public Processo copiar() {
        Processo copia = new Processo(nome, chegada, cpu1, io, cpu2, prioridade);
        return copia;
    }

    public void executarUmMilissegundo() {
        if (faseCpu == 1) {
            restanteCpu1 = restanteCpu1 - 1;
        } else {
            restanteCpu2 = restanteCpu2 - 1;
        }
    }

    public boolean terminouCpuAtual() {
        if (faseCpu == 1 && restanteCpu1 == 0) {
            return true;
        }

        if (faseCpu == 2 && restanteCpu2 == 0) {
            return true;
        }

        return false;
    }
}