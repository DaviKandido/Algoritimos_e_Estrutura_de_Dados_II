
import java.util.*;

public class Alienigena{

    public static void VerificaAlien(String alfabeto, String msg, int K_alf, int N_car){

        boolean resp = true;

        for(int i = 0; i < N_car && resp; i++){
            resp = false;
            for(int j = 0; j < K_alf; j++){
                if(alfabeto.charAt(j) == msg.charAt(i)){
                    resp = true;
                }
            }
        }

        if(resp){
            System.out.println("S");
        }else{
            System.out.println("N");
        }
    }


    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        int K_alf = sc.nextInt();
        int N_car = sc.nextInt();
        sc.nextLine();    
    
        do{
        
        String alfabeto = sc.nextLine();
        String msg = sc.nextLine();
        
        VerificaAlien(alfabeto, msg, K_alf, N_car);
        
        K_alf = sc.nextInt();
        N_car = sc.nextInt();
        sc.nextLine();

        }while(K_alf <= 68 && K_alf >= 1 && N_car >= 1 && N_car <= 1000);

        sc.close();
    }
}