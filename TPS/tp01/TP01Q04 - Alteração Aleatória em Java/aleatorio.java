import java.util.*;

public class aleatorio {

    public static boolean isFim(String entrada){
        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }

    public static String alteracaoAleatoria(String entrada, Random gerador){


        String saida = "";

        char char1 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
        char char2 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));

        for(int i = 0; i < entrada.length(); i++){

            if(entrada.charAt(i) == char1){
                saida += char2;
            }else{
                saida += entrada.charAt(i);
            }
        }

        return saida;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada[] = new String[1000];

        int i = 0;

        entrada[i] = sc.nextLine();

        Random gerador = new Random();
        gerador.setSeed(4);

        do{

            System.out.println(alteracaoAleatoria(entrada[i], gerador));
            i++;
            entrada[i] = sc.nextLine();
        } while (!isFim(entrada[i])); 


        sc.close();
    }
}