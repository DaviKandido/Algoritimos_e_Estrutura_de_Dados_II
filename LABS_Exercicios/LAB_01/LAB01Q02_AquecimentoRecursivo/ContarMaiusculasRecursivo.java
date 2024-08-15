package LAB01Q02_AquecimentoRecursivo;

import java.util.*;

public class ContarMaiusculasRecursivo {
    
    //Verifica se o caracter enviado é maiusculo
    public static boolean isMaiuscula (char c){

        return (c >= 'A' && c<= 'Z');
    }



    //Verifica se a string analisada é a "FIM"
    public static boolean isFim(String entrada){
        
        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }

    //Contabiliza a quantidade de Maiusculas na String de forma recursiva
    public static int contarLetrasMaiusculas(String entrada, int index) {
        
        int quantMaius = 0;

        if (index < entrada.length()){
            if(isMaiuscula(entrada.charAt(index))){
                quantMaius = 1 + contarLetrasMaiusculas(entrada, index + 1);
            } else{
                quantMaius = contarLetrasMaiusculas(entrada, index + 1);
            }

        }


        return quantMaius;
    }


    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] entrada = new String[50];

        int i = 0;

        do { 
            entrada[i] = sc.nextLine();

        } while (isFim(entrada[i++]) == false);

        

        for (int j = 0; j < i-1; j++){
            System.out.println(contarLetrasMaiusculas(entrada[j], 0));
        }


    }
}
