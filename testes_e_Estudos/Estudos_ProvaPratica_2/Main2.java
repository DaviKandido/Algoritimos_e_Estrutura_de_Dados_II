
import java.util.*;

public class Main2{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while(sc.hasNextInt()){
            int numOp = sc.nextInt();

            int soma = 0;
            int tempo = 0;
            int ciclos = 0;

            for(int i = 0; i < numOp; i++){

                if(ciclos > tempo) soma += ciclos;
                else soma += ciclos+tempo;

                tempo = sc.nextInt();
                ciclos = sc.nextInt();

            }

            System.out.println(soma);
        }


        sc.close();
        
    }
}