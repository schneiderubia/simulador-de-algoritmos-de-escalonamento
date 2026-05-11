import java.io.BufferedReader;
import java.io.FileReader;

public class Leitor {

    public static Dados ler(String caminho) throws Exception {
        Dados dados = new Dados();

        BufferedReader leitor = new BufferedReader(new FileReader(caminho));

        String linha;

        while ((linha = leitor.readLine()) != null) {
            linha = linha.trim();

            if (linha.length() == 0) {
                continue;
            }

            if (linha.startsWith("#")) {
                continue;
            }

            if (linha.contains("=")) {
                String[] partes = linha.split("=");

                String chave = partes[0].trim();
                String valor = partes[1].trim();

                if (chave.equalsIgnoreCase("QUANTUM")) {
                    dados.quantum = Integer.parseInt(valor);
                }
            } else {
                String[] partes = linha.split(";");

                if (partes.length < 6) {
                    leitor.close();
                    throw new Exception("Linha inválida: " + linha);
                }

                String nome = partes[0].trim();
                int chegada = Integer.parseInt(partes[1].trim());
                int cpu1 = Integer.parseInt(partes[2].trim());
                int io = Integer.parseInt(partes[3].trim());
                int cpu2 = Integer.parseInt(partes[4].trim());
                int prioridade = Integer.parseInt(partes[5].trim());

                Processo processo = new Processo(nome, chegada, cpu1, io, cpu2, prioridade);
                dados.processos.add(processo);
            }
        }

        leitor.close();

        return dados;
    }
}