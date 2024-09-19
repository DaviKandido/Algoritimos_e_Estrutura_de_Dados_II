import java.util.*;

public class Medalhas{

    public static void ordenaMedalhas(String[] pais, int medalhas[][], int n) {


        for(int i = 0; i < n-1; i++){
            for( int j = i+1; j < n; j++){

                if(medalhas[i][0] < medalhas[j][0]){
                    String tempPais = pais[i];
                    int tempMedalhas[] = medalhas[i];

                    pais[i] = pais[j];
                    medalhas[i] = medalhas[j];

                    pais[j] = tempPais;
                    medalhas[j] = tempMedalhas;

                }else if(medalhas[i][0] == medalhas[j][0])
                      if( medalhas[i][1] < medalhas[j][1]){
                        String tempPais = pais[i];
                        int tempMedalhas[] = medalhas[i];

                        pais[i] = pais[j];
                        medalhas[i] = medalhas[j];

                        pais[j] = tempPais;
                        medalhas[j] = tempMedalhas;

                }else if(medalhas[i][0] == medalhas[j][0] && medalhas[i][1] == medalhas[j][1])
                      if(medalhas[i][2] < medalhas[j][2]){
                        String tempPais = pais[i];
                        int tempMedalhas[] = medalhas[i];

                        pais[i] = pais[j];
                        medalhas[i] = medalhas[j];

                        pais[j] = tempPais;
                        medalhas[j] = tempMedalhas;

            }else if(medalhas[i][0] == medalhas[j][0] && medalhas[i][1] == medalhas[j][1] && medalhas[i][2] < medalhas[j][2])
            if(pais[i].charAt(0) > pais[j].charAt(0)){
                    String tempPais = pais[i];
                    int tempMedalhas[] = medalhas[i];

                    pais[i] = pais[j];
                    medalhas[i] = medalhas[j];

                    pais[j] = tempPais;
                    medalhas[j] = tempMedalhas;
            }
        }
    }


    System.out.println("\n");
    for(int i = 0; i < n; i++){

        System.out.println(pais[i] + " " + medalhas[i][0] +" "+ medalhas[i][1] + " " + medalhas[i][2]);

    }


    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();

        if(n >= 0 && n <=500){

            String pais[] = new String[n];
            int medalhas[][] = new int[n][3];

            for(int i = 0; i < n; i++){
                pais[i] = sc.next();
                for(int j = 0; j < 3; j++){
                    medalhas[i][j] = sc.nextInt();
                }
            }

            ordenaMedalhas(pais, medalhas, n);
        }
    }
}