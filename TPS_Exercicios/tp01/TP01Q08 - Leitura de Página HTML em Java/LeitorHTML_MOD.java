import java.io.BufferedReader;
import java.net.URL;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;



public class LeitorHTML_MOD {


    public static boolean isFim(String entrada){

        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }

    //Transforma o conteudo do html em uma string
    public static String analisadorHTML(String siteURL){

        String conteudoHtml = new String();

        try {
            URL url = new URL(siteURL);
            //BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)); // Definindo UTF-8

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


    public static void ContadorDeOcorrencias(String siteUrl, String siteName) {
        
        String conteudoHtml = analisadorHTML(siteUrl);

        // Inicialize as variáveis de contagem
        int num_a = 0, num_e = 0, num_i = 0, num_o = 0, num_u = 0;                             // vogais sem acento
        int num_a_Agu = 0, num_e_Agu = 0, num_i_Agu = 0, num_o_Agu = 0, num_u_Agu = 0;        // vogais com acento agudo
        int num_a_Gra = 0, num_e_Gra = 0, num_i__Gra = 0, num_o_Gra = 0, num_u_Gra = 0;      // vogais com acento grave
        int num_a_til = 0, num_o_til = 0;                                                   // vogais com til
        int num_a_Cir = 0, num_e_Cir = 0, num_i_Cir = 0, num_o_Cir = 0, num_u_Cir = 0;     // vogais com acento circunflexo
        int num_consoante = 0, num_br = 0, num_table = 0;                                 // consoantes, <br> e <table>

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
            } else if (caractere == '\u00e1') { // 'á'
                num_a_Agu++;
            } else if (caractere == '\u00e9') { // 'é'
                num_e_Agu++;
            } else if (caractere == '\u00ed') { // 'í'
                num_i_Agu++;
            } else if (caractere == '\u00f3') { // 'ó'
                num_o_Agu++;
            } else if (caractere == '\u00fa') { // 'ú'
                num_u_Agu++;
            } else if (caractere == '\u00e0') { // 'à'
                num_a_Gra++;
            } else if (caractere == '\u00e8') { // 'è'
                num_e_Gra++;
            } else if (caractere == '\u00ec') { // 'ì'
                num_i__Gra++;
            } else if (caractere == '\u00f2') { // 'ò'
                num_o_Gra++;
            } else if (caractere == '\u00f9') { // 'ù'
                num_u_Gra++;
            } else if (caractere == '\u00e3') { // 'ã'
                num_a_til++;
            } else if (caractere == '\u00f5') { // 'õ'
                num_o_til++;
            } else if (caractere == '\u00e2') { // 'â'
                num_a_Cir++;
            } else if (caractere == '\u00ea') { // 'ê'
                num_e_Cir++;
            } else if (caractere == '\u00ee') { // 'î'
                num_i_Cir++;
            } else if (caractere == '\u00f4') { // 'ô'
                num_o_Cir++;
            } else if (caractere == '\u00fb') { // 'û'
                num_u_Cir++;
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
        "a(%d) e(%d) i(%d) o(%d) u(%d) \u00e1(%d) \u00e9(%d) \u00ed(%d) \u00f3(%d) \u00fa(%d) " +
        "\u00e0(%d) \u00e8(%d) \u00ec(%d) \u00f2(%d) \u00f9(%d) \u00e3(%d) \u00f5(%d) \u00e2(%d) \u00ea(%d) \u00ee(%d) " +
        "\u00f4(%d) \u00fb(%d) consoante(%d) <br>(%d) <table>(%d) %s\n",
        num_a, num_e, num_i, num_o, num_u,                      // vogais sem acento
        num_a_Agu, num_e_Agu, num_i_Agu, num_o_Agu, num_u_Agu,  // vogais com acento agudo
        num_a_Gra, num_e_Gra, num_i__Gra, num_o_Gra, num_u_Gra, // vogais com acento grave
        num_a_til, num_o_til,                                   // vogais com til
        num_a_Cir, num_e_Cir, num_i_Cir, num_o_Cir, num_u_Cir,  // vogais com acento circunflexo
        num_consoante, num_br, num_table,                       // consoantes, <br> e <table>
        siteName                                                // nome da página
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
            
            ContadorDeOcorrencias(siteUrl, siteName);

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
