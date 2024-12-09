
import java.util.*;


public class HalldosAssassinos{


    public static void printFinal(Assasino assasinos[], int n){

        System.out.println("HALL OF MURDERERS");

        for(int i = 0; i < n-1; i ++){
            for(int j = i+1; j < n; j++){
                if(assasinos[i].nome.compareTo(assasinos[j].nome) > 0){
                    Assasino tmp = assasinos[i];
                    assasinos[i] = assasinos[j];
                    assasinos[j] = tmp;
                }
            }
        }


        for(int i = 0; i < n; i++){
            if(!assasinos[i].morto){
                assasinos[i].print();
            }
        }

    }



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Assasino assasinos[] = new Assasino[50];
        int n = 0; int m = 0;
        String mortos[] = new String[50];

        while(sc.hasNext()){

            String nome = sc.next();
            mortos[m++] = sc.next();

            int achou = -1;

            for(int i = 0; i < n; i++){
                if(assasinos[i].nome.compareTo(nome) == 0){
                    achou = i;
                }
            }

            if(achou == -1){
                assasinos[n++] = new Assasino(nome);
            }else{
                assasinos[achou].assasinados++;
            }

            

            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    if(assasinos[i].nome.compareTo(mortos[j]) == 0){
                        assasinos[i].morto = true;
                    }
                }
            }

        }

        printFinal(assasinos, n);
        
    }
}



class Assasino{

    public String nome;
    public int assasinados;
    public boolean morto;

    Assasino(String nome){
        this.nome = nome;
        this.assasinados = 1;
        this.morto = false;
    }

    public void print(){
        System.out.println(this.nome + " " + this.assasinados);
    }
}
