
import java.util.*;

public class sequenciaEspelho_Com_Naisses{

    public static String SequenciaEspelho(int num1, int num2){


        String numInt = "";
        for(int i = num1; num2 >= i; i++){
            numInt += i;
        }
        
        for(int i = numInt.length() -1; i >= 0; i--){
            numInt += numInt.charAt(i);
        }

        return numInt;
    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while(sc.hasNext()){

            int num1 = sc.nextInt();
            int num2 = sc.nextInt();
    
            String result = SequenciaEspelho(num1, num2);

            System.out.println(result);
        }
    }
}