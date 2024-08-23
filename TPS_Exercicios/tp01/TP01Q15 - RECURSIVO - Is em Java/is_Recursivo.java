
import java.util.*;

public class is_Recursivo {

    public static boolean isFim(String entrada){

        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }

    public static boolean IsVoga(char entrada){

        boolean resp = true;


        if(!((entrada == 'a' || entrada == 'e' || entrada == 'i' || entrada == 'o'|| entrada == 'u' || entrada == 'A' || entrada == 'E' || entrada == 'I' || entrada == 'O'|| entrada == 'U'))){
                resp = false;
        }


        return resp;
    }

    public static boolean IsVogais(String entrada, int i, boolean resp){

        int n = entrada.length();
        if(resp == true && i < n){
            if(!IsVoga(entrada.charAt(i))){
                resp = false;
            }
            resp = IsVogais(entrada, i+1, resp);
        }

        return resp;
    }


    public static boolean IsConsoantes(String entrada, int i, boolean resp){

        int n = entrada.length();
        if(resp == true && i < n){
            if(!((entrada.charAt(i) >= 'a' && entrada.charAt(i) <= 'z') || (entrada.charAt(i) >= 'A' && entrada.charAt(i) <= 'Z')) || (IsVoga(entrada.charAt(i)))) {
                resp = false;
            }
            resp = IsConsoantes(entrada, i+1, resp);
        }

        return resp;
    }

    public static boolean IsNumInt(String entrada, int i, boolean resp){


        int n = entrada.length();
        if(resp == true && i < n){
            if( !(entrada.charAt(i) >= '0' && entrada.charAt(i) <= '9')){
                resp = false;

                if(entrada.charAt(i) == '.' || entrada.charAt(i) == ','){
                    resp = false;
                }
            }
            resp = IsNumInt(entrada, i+1, resp);
        }

        return resp;
    }


    public static boolean IsNumReal(String entrada, int i, boolean resp, int decimal){

        int n = entrada.length();

        if(resp == true && i < n){
            if( !((entrada.charAt(i) >= '0' && entrada.charAt(i) <= '9' )|| entrada.charAt(i) == '.' || entrada.charAt(i) == ',')){
                resp = false;
            }

            if(entrada.charAt(i) == '.' || entrada.charAt(i) == ','){
                decimal = decimal + 1;
            }

            resp = IsNumReal(entrada, i+1, resp, decimal);
        }
        
        
        if (i == n - 1 && (decimal == 0 || decimal > 1)) {
            resp = false;
        }
        

        return resp;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        
        String entrada[] = new String[1000];

        int i = 0;

        do{
            
            entrada[i] = sc.nextLine();

        }while(isFim(entrada[i++]) == false);

        for(int j = 0; j < i -1; j++){

            if(IsVogais(entrada[j], 0, true))
                System.out.printf("SIM ");
            else
                System.out.printf("NAO ");
            
            if (IsConsoantes(entrada[j], 0, true))
                System.out.printf("SIM ");
            else 
                System.out.printf("NAO ");
                
            if (IsNumInt(entrada[j], 0, true))
                System.out.printf("SIM ");
            else
                System.out.printf("NAO ");
                            
            if (IsNumReal(entrada[j], 0, true, 0))
                System.out.printf("SIM ");
            else
                System.out.printf("NAO");
            
                System.out.println();
        }

        sc.close();
    }
}


/*
 * 
oi
sim
dfg
123 
12,3
FIM
 */