package OrdenarPackage;

import java.util.*;



public class OrdenadorPackage{


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        while(sc.hasNext()){

            if(sc.nextInt() == 1){

                Lista listaPackage = new Lista();

                while(!sc.hasNextInt()){
                    listaPackage.inserir(sc.next(), sc.nextInt());

                }

                listaPackage.ordenar();
                listaPackage.mostrar();
                System.out.println();

            }
    
        }
        
    }


}




class Package{
    public String nome;
    public int numero;

    Package(){
        this.nome = "";
        this.numero = -1; 
    }

    Package(String nome, int numero){
        this.nome = nome;
        this.numero = numero; 
    }

    public void mostrar(){
        System.out.println(nome + " " + 0 + 0 + numero);
    }
}


class Lista{

    public Package packages[];
    public int n;

    Lista(){
        packages = new Package[50];
        n = 0;
    }

    public void ordenar(){
        for(int i = 0; i < n-1; i++){
            for(int j = i + 1; j < n; j++){
                if(packages[i].numero > packages[j].numero){
                    Package tmp = packages[i];
                    packages[i] = packages[j];
                    packages[j] = tmp;
                }
            }
        }
    }

    public void inserir(String nome, int numero){
        packages[n++] = new Package(nome, numero);
    }

    public void mostrar(){
        for(int i = 0; i < n; i++){
            packages[i].mostrar();
        }
    }


}