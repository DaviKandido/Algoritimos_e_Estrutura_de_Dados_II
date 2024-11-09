
import java.util.*;

public class MenorDaPilha {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int numOp = sc.nextInt();

        Lista presenteList = new Lista(numOp);

        for(int i = 0; i < numOp; i++){
            String entrada = sc.next();

            if(entrada.equals("PUSH")){
                presenteList.push(sc.nextInt());
            }else if(entrada.equals("POP")){
                presenteList.pop();
            }else if(entrada.equals("MIN")){
                System.out.println(presenteList.min().grauDiversao);
            }
        }


        sc.close();
        
    }
}


class Presente{
    int grauDiversao;

    Presente(){
        grauDiversao = 0;
    }

    Presente(int p){
        grauDiversao = p;
    }
}


class Lista{
    Presente array[];
    int size;

    Lista(int tamanho){
        array = new Presente[tamanho];
        size = 0;
    }

    public void push(int num){

        Presente p = new Presente(num);

        if(size >= array.length){
            System.out.println("EMPTY");
        }else{
            array[size] = p;
            size++;
        }
    }

    public void pop(){
        if(size == 0){
            System.out.println("EMPTY");
        }else{
            array[size] = null;
            size--;
            
        }
    }

    public Presente min(){
        Presente min = array[0];
        if(size == 0){
            System.out.println("EMPTY");
        }else{
            for(int i = 0; i < size; i++){
                if(min.grauDiversao > array[i].grauDiversao){
                    min = array[i];
                }
            }
            
        }

        return min;
    }
}

