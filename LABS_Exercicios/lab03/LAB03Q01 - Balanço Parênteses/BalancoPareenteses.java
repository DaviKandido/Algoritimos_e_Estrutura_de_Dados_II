

import java.util.*;

class BalancoPareenteses {

    public static boolean isFim(String e){

        return e.length() == 3 && e.charAt(0) == 'F' && e.charAt(1) == 'I' && e.charAt(2) == 'M';
    }

    public static void balancoPareenteses(String entrada){

        int P_abre = 0;
        int P_fecha = 0;

        boolean erro = true;

        for(int i = 0; i < entrada.length() && erro; i++){
            if(entrada.charAt(i) == '('){
                P_abre++;
            }else if(entrada.charAt(i) == ')'){
                P_fecha++;
            }
            
            if(P_fecha > P_abre){
                erro = false;
            }
        }


        if(P_abre == P_fecha && erro){
            System.out.println("correto");
        }else{
            System.out.println("incorreto");
        }

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String entrada = sc.nextLine();



        do{
            balancoPareenteses(entrada);
            entrada = sc.nextLine();

        }while(isFim(entrada) == false);

        sc.close();
    }
}