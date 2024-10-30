
import java.util.*;

public class sequenciaEspelho{

    public static void SequenciaEspelho(int num1, int num2){


        
        for(int i = num1; num2 >= i; i++){
            System.out.print(i);
        }

        for(int i = num2; num1 <= i; i--){

            String numAlt = String.valueOf(i);

            if(numAlt.length() > 1){
                for(int j = numAlt.length() -1; j >= 0; j--){
                    System.out.print(numAlt.charAt(j));
                }
            }else{
                System.out.print(i);
            }
        }

        System.out.println();

    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while(sc.hasNext()){

            int num1 = sc.nextInt();
            int num2 = sc.nextInt();
    
            SequenciaEspelho(num1, num2);
        }
    }
}