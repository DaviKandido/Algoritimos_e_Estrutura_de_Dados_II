import java.util.*;

public class Ciframento {

    public static boolean isFim(String entrada){

        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }


    public static String Ciframento(String entrada){

        int quant = entrada.length();

        String saida = "";

        for(int n = 0; n < quant; n++){
            char letra = (char)(entrada.charAt(n) + 3);

            if(letra < (char) 127 && letra > (char) 32){
                saida += letra;
            }else{
                saida += entrada.charAt(n);
            }

        }


        return saida;
    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String entrada[] = new String[1000];

        int i = 0;

        do{
            
            entrada[i] = sc.nextLine();

        }while(isFim(entrada[i++]) == false);

        for(int j = 0; j < i -1; j++){
            System.out.println(Ciframento(entrada[j]));
        }

        sc.close();
    }
}
