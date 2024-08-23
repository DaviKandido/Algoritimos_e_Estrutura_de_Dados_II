import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalisadorPaginaWeb {

    public static void main(String[] args) {
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in))) {
            String linha;

            while (!(linha = leitor.readLine()).equals("FIM")) {
                String[] entrada = linha.split(" ", 2);
                String endereco = entrada[0];
                String nomePagina = entrada[1];
                
                String conteudoHTML = baixarHTML(endereco);
                if (conteudoHTML == null) {
                    System.out.println("Não foi possível acessar a página: " + nomePagina);
                    continue;
                }

                int[] contagens = contarOcorrencias(conteudoHTML);
                System.out.printf("a(%d) e(%d) i(%d) o(%d) u(%d) á(%d) é(%d) í(%d) ó(%d) ú(%d) à(%d) è(%d) ì(%d) ò(%d) ù(%d) ã(%d) õ(%d) â(%d) ê(%d) î(%d) ô(%d) û(%d) consoante(%d) <br>(%d) <table>(%d) %s(%d)\n",
                    contagens[0], contagens[1], contagens[2], contagens[3], contagens[4], // vogais sem acento
                    contagens[5], contagens[6], contagens[7], contagens[8], contagens[9], // vogais acentuadas
                    contagens[10], contagens[11], contagens[12], contagens[13], contagens[14], // vogais com acento grave
                    contagens[15], contagens[16], contagens[17], contagens[18], contagens[19], // vogais com til e circunflexo
                    contagens[20], contagens[21], // consoantes
                    contagens[22], contagens[23], // padrões <br> e <table>
                    nomePagina, nomePagina.length() // nome da página e seu tamanho
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String baixarHTML(String urlString) {
        try {
            URL url = new URL(urlString);
            BufferedReader leitor = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder conteudo = new StringBuilder();
            String linhaEntrada;
            while ((linhaEntrada = leitor.readLine()) != null)
                conteudo.append(linhaEntrada);
            leitor.close();
            return conteudo.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static int[] contarOcorrencias(String conteudoHTML) {
        int[] contagens = new int[26]; // Array para armazenar contagens
        
        // Definir padrões de caracteres e strings a serem contados
        String vogais = "aeiouáéíóúàèìòùãõâêîôû";
        String[] padroesVogais = vogais.split("");
        String consoantes = "bcdfghjklmnpqrstvwxyz";
        Pattern padraoConsoantes = Pattern.compile("[" + consoantes + "]", Pattern.CASE_INSENSITIVE);
        Pattern padraoBr = Pattern.compile("<br>", Pattern.CASE_INSENSITIVE);
        Pattern padraoTable = Pattern.compile("<table>", Pattern.CASE_INSENSITIVE);

        // Contar vogais (sem e com acento)
        for (int i = 0; i < padroesVogais.length; i++) {
            contagens[i] = contarPadroes(conteudoHTML, Pattern.compile(padroesVogais[i], Pattern.CASE_INSENSITIVE));
        }

        // Contar consoantes
        contagens[20] = contarPadroes(conteudoHTML, padraoConsoantes);

        // Contar padrões <br> e <table>
        contagens[22] = contarPadroes(conteudoHTML, padraoBr);
        contagens[23] = contarPadroes(conteudoHTML, padraoTable);

        return contagens;
    }

    private static int contarPadroes(String conteudoHTML, Pattern padrao) {
        Matcher matcher = padrao.matcher(conteudoHTML);
        int contagem = 0;
        while (matcher.find()) {
            contagem++;
        }
        return contagem;
    }
}
