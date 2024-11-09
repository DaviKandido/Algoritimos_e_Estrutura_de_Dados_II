
import java.util.*;

class Rena{
    public String name;
    public int peso;
    public int idade;
    public Double altura;

    Rena(){
        this.name = "";
        this.peso = 0;
        this.idade = 0;
        this.altura = 0.0;
    }

    Rena(String name, int peso, int idade, Double altura){
        this.name = name;
        this.peso = peso;
        this.idade = idade;
        this.altura = altura;
    }

    public void print(){
        System.out.println(name);
    }

}



class Lista{
    private Rena rena[];
    public int size;

    Lista(int tamanho){
        rena = new Rena[tamanho];
        size = 0;
    }

    public void inserir(Rena r){

        this.rena[size] = r;
        size++;
    }


    public void ordenar(){

        for(int i = 1; i < size; i++){
            Rena tmp = rena[i];
            int j = i -1;

            while((j >= 0) && (rena[j].peso < tmp.peso 
                           || (rena[j].peso == tmp.peso && rena[j].idade > tmp.idade))
                           || (rena[j].peso == tmp.peso && rena[j].idade == tmp.idade && rena[j].altura > tmp.altura)){
                rena[j+1] = rena[j];
                j--;
            }

            rena[j+1] = tmp;
        }
    }

    public void prints(int n){
        for(int i = 0; i < n; i++){
            System.out.println((i+1) + " - " + rena[i].name);
        }
    }

    
}

public class Elfo {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);


            int cenario = sc.nextInt();
            int tamanho = sc.nextInt();
            int numPrints = sc.nextInt();

            Lista listaRenas = new Lista(tamanho);

            for(int i = 0; i < tamanho; i++){

                String nome = sc.next(); 
                int peso = sc.nextInt(); 
                int idade = sc.nextInt(); 
                Double altura = sc.nextDouble();
                
                Rena rena = new Rena(nome, peso, idade, altura);

                listaRenas.inserir(rena);
            }

            listaRenas.ordenar();

            System.out.println("CENARIO {"+cenario+"}");

            listaRenas.prints(numPrints);

            

        sc.close();

    }
    
}
