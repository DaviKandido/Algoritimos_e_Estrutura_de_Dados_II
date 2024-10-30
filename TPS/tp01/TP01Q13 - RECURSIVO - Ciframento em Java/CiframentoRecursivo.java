import java.util.*;

public class CiframentoRecursivo {


    //Verifica se não é a palavra fim
    public static boolean isFim(String entrada){

        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }

    //Aplica um ciframento de cesar com tamanho 3
    public static String Ciframento(String entrada, String saida2, int n) {
        
        if (n >= entrada.length()) {
            return saida2;
        }
        // Obtém o caractere atual e aplica o deslocamento
        char letra = (char) (entrada.charAt(n) + 3);
    
        // Verifica se o caractere cifrado está dentro dos limites válidos
        if (letra < (char) 127 && letra > (char) 32) {
            saida2 += letra;
        } else {
            saida2 += entrada.charAt(n);
        }
    

        return Ciframento(entrada, saida2, n + 1);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String entrada[] = new String[1000];

        int i = 0;

        do{
            
            entrada[i] = sc.nextLine();

        }while(isFim(entrada[i++]) == false);

        for(int j = 0; j < i -1; j++){
            System.out.println(Ciframento(entrada[j], "", 0));
        }

    }
}
