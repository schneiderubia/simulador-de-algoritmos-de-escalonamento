import java.util.ArrayList;

public interface Escalonador {

    String executar(ArrayList<Processo> processos, int quantum);
}