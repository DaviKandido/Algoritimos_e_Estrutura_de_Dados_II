import java.io.BufferedReader;
import java.net.URL;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;



public class LeitorHTML {


    public static boolean isFim(String entrada){

        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }

    //Transforma o conteudo do html em uma string
    public static String analisadorHTML(String siteURL){

        String conteudoHtml = new String();

        try {
            URL url = new URL(siteURL);
            //BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), 
            StandardCharsets.UTF_8)); // Definindo UTF-8

            String linhaDoHTML;
            while ((linhaDoHTML = br.readLine()) != null) {
                conteudoHtml += linhaDoHTML + "\n";
            }
            br.close();
        } catch (IOException err) {
            System.out.println("Erro ao abri o site " + err.getMessage());
        }

        // O conteúdo do HTML é armazenado na variável conteudoHtml
        return conteudoHtml;
    }


    public static void ConadorDeOcorrencias(String siteUrl, String siteName) {
        
        String conteudoHtml = analisadorHTML(siteUrl);

// Inicialize as variáveis de contagem
int num_a = 0, num_e = 0, num_i = 0, num_o = 0, num_u = 0;
int num_á = 0, num_é = 0, num_í = 0, num_ó = 0, num_ú = 0;
int num_à = 0, num_è = 0, num_ì = 0, num_ò = 0, num_ù = 0;
int num_ã = 0, num_õ = 0, num_â = 0, num_ê = 0, num_î = 0;
int num_ô = 0, num_û = 0, num_consoante = 0, num_br = 0, num_table = 0;

// Itera sobre o conteúdo HTML
for(int i = 0; i < conteudoHtml.length(); i++){
    char caractere = conteudoHtml.charAt(i);

    // Contagem de vogais
    if (caractere == 'a') {
        num_a++;
    } else if (caractere == 'e') {
        num_e++;
    } else if (caractere == 'i') {
        num_i++;
    } else if (caractere == 'o') {
        num_o++;
    } else if (caractere == 'u') {
        num_u++;
    } else if (caractere == 'á') {
        num_á++;
    } else if (caractere == 'é') {
        num_é++;
    } else if (caractere == 'í') {
        num_í++;
    } else if (caractere == 'ó') {
        num_ó++;
    } else if (caractere == 'ú') {
        num_ú++;
    } else if (caractere == 'à') {
        num_à++;
    } else if (caractere == 'è') {
        num_è++;
    } else if (caractere == 'ì') {
        num_ì++;
    } else if (caractere == 'ò') {
        num_ò++;
    } else if (caractere == 'ù') {
        num_ù++;
    } else if (caractere == 'ã') {
        num_ã++;
    } else if (caractere == 'õ') {
        num_õ++;
    } else if (caractere == 'â') {
        num_â++;
    } else if (caractere == 'ê') {
        num_ê++;
    } else if (caractere == 'î') {
        num_î++;
    } else if (caractere == 'ô') {
        num_ô++;
    } else if (caractere == 'û') {
        num_û++;
    }
    
    // Contagem de consoantes (considerei o alfabeto sem acentos)
    else if ( (caractere >= 'b' && caractere <= 'z') && (caractere != 'a' && caractere != 'e' && caractere != 'i' && caractere != 'o' && caractere != 'u') ) {
        num_consoante++;
    }
    
        // Contagem de tags <br> e <table>
        if (i + 3 < conteudoHtml.length() && //Impede que acesse index inexistente
            conteudoHtml.charAt(i) == '<' &&
            conteudoHtml.charAt(i + 1) == 'b' &&
            conteudoHtml.charAt(i + 2) == 'r' &&
            conteudoHtml.charAt(i + 3) == '>') {

            num_br++;
            i += 3; // Avance de índice para pular a tag
        } else if (i + 6 < conteudoHtml.length() &&
                conteudoHtml.charAt(i) == '<' &&
                conteudoHtml.charAt(i + 1) == 't' &&
                conteudoHtml.charAt(i + 2) == 'a' &&
                conteudoHtml.charAt(i + 3) == 'b' &&
                conteudoHtml.charAt(i + 4) == 'l' &&
                conteudoHtml.charAt(i + 5) == 'e' &&
                conteudoHtml.charAt(i + 6) == '>') {

            num_table++;
            i += 6; // Avance de índice para pular a tag
        }
    }
    System.out.printf(
        "a(%d) e(%d) i(%d) o(%d) u(%d) á(%d) é(%d) í(%d) ó(%d) ú(%d) " +
        "à(%d) è(%d) ì(%d) ò(%d) ù(%d) ã(%d) õ(%d) â(%d) ê(%d) î(%d) " +
        "ô(%d) û(%d) consoante(%d) <br>(%d) <table>(%d) %s\n",
        num_a, num_e, num_i, num_o, num_u, // vogais sem acento
        num_á, num_é, num_í, num_ó, num_ú, // vogais com acento agudo
        num_à, num_è, num_ì, num_ò, num_ù, // vogais com acento grave
        num_ã, num_õ,                      // vogais com til
        num_â, num_ê, num_î, num_ô, num_û, // vogais com acento circunflexo
        num_consoante, num_br, num_table,  // consoantes, <br> e <table>
        siteName                           // nome da página
    );


    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String siteName = "";
        String siteUrl = "";

        siteName = sc.nextLine();
        siteUrl = sc.nextLine();

        boolean parar = true;

        while(parar){
            
            ConadorDeOcorrencias(siteUrl, siteName);

            siteName = sc.nextLine();

            if(isFim(siteName) == true){
                parar = false;
            }
            
            if(parar){
                siteUrl = sc.nextLine();
            }

        }

    }
}
