import java.util.*;

class palindromo {

    public static boolean isFim(String entrada){

        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }



    public static String verificaPolindromo(String polindromo){

        String resposta = "SIM";
        int esq = 0;
        int dir = polindromo.length() - 1;

        while(esq < dir){
            if(polindromo.charAt(esq) != polindromo.charAt(dir)){
                resposta = "NAO";
            }
            esq++;
            dir--;
        }


        return resposta;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String entrada[] = new String[1000];

        int i = 0;

        do{
            entrada[i] = sc.nextLine();

        }while(isFim(entrada[i++]) == false);

        for(int j = 0; j < i-1; j++){
            System.out.println(verificaPolindromo(entrada[j]));
        }
    }
}