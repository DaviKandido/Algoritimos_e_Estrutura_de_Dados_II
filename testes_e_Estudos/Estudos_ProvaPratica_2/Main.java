
import java.util.*;


class List{

    private String pessoa[];
    public int size;

    List(){
        pessoa = new String[800];
        size = 0;
    }

    public void inserir(String name){

        boolean possue = false;
        for(int i = 0; i < size;i++){
            if(name.equals(pessoa[i]))
                possue = true;
        }

        if (!possue) pessoa[size++] = name;
    }

    public void print(String name){
        for(int i = 0; i < size;i++){
            System.out.println(pessoa[i]);
        }
    }

    public void ordenar(){
        for(int i = 1; i<size; i++){
            String tmp = pessoa[i];
            int j = i-1;

            while((j>=0) && (pessoa[j].compareTo(tmp) > 0)){
                pessoa[j+1] = pessoa[j];
                j--;
            }

            pessoa[j+1] = tmp;
        }
    }

}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        List listaPessoas = new List();

        String nome = sc.next();

        while(!nome.equals("FIM")){
            String cond = sc.next();
            
            
            listaPessoas.inserir(nome);
        
            nome = sc.next();
        }

        listaPessoas.ordenar();

        listaPessoas.print(nome);
        

    }

}
