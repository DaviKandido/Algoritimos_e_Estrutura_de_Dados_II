import java.util.*;

public class PapaiNoel {

    public static void informaMensagem(String idioma[][], int n, String pessoa[][], int p){

        // for(int i = 0; i < p; i++){
        //     for(int j = 0; j < n; j++){
        //         if(pessoa[i][1].equals(idioma[j][0])){
        //             System.out.println(pessoa[i][0]);
        //             System.out.println(idioma[j][1]);
        //             System.out.println();
        //         }
        //     } 
        // }

        for(int i = 0; i < p; i++){
            for(int j = 0; j < n; j++){ 
                if(pessoa[i][1].intern() == idioma[j][0].intern()){ // Usar intern() para garantir a mesma instância
                    System.out.println(pessoa[i][0]);  
                    System.out.println(idioma[j][1]);  
                    System.out.println();
                }
            } 
        }

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();

        String idioma[][] = new String[n][2];
       
        for(int i = 0; i < n; i++){
            idioma[i][0] = sc.nextLine();
            idioma[i][1] = sc.nextLine();
        }
       
        int p = sc.nextInt();
        sc.nextLine();

        String pessoa[][] = new String[p][2];

        for(int i = 0; i<p; i++){
            pessoa[i][0] = sc.nextLine();
            pessoa[i][1] = sc.nextLine();
        }


        informaMensagem(idioma, n, pessoa, p);

        sc.close();
    }
    
}
