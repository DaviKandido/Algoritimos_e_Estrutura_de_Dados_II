
import java.util.Locale;
import java.util.Scanner;




public class Estiagem{
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in).useLocale(Locale.US);

        boolean parar = true;
        int cont = 0;
        
        while(parar){
            int n = sc.nextInt();

            if(n != 0){
                Cidade cidade = new Cidade(n);
                for(int i = 0; i < n; i++){
                    cidade.newImovel(i, sc.nextInt(), sc.nextInt());
                }
    
                cidade.ordenaImoveis();
                cidade.print(cont++);
            }else{
                parar = false;
            }
        }


    }
}


class Cidade{
    public int habitantes;
    public int consumoTotal;
    public double consumoMedio;
    public Imovel imoveis[];


    public Cidade(int n){
        this(n,0,0,0);
    }

    public Cidade(int n, int consumoMedio, int consumoTotal, int habitantes) {
        this.consumoMedio = consumoMedio;
        this.consumoTotal = consumoTotal;
        this.habitantes = habitantes;
        this.imoveis = new Imovel[n];
    }

    public void newImovel(int i, int moradores, int consumo){
        imoveis[i] = new Imovel(moradores, consumo);
        defineCidade(moradores,consumo);
    }

    public void defineCidade(int moradores, int consumo){
        this.habitantes += moradores;
        this.consumoTotal += consumo;
        this.consumoMedio = (double) consumoTotal/habitantes;
    }

    public void print(int i){
        System.out.println("Cidade# "+(i+1)+":");
        for(int j = 0; j < imoveis.length; j++){
            System.out.print(imoveis[j].moradores+"-"+imoveis[j].consumoMedio + " ");
        }
        System.out.println();
        System.out.printf("Consumo medio: %.2f m3.\n\n", this.consumoMedio);
    }

    public void ordenaImoveis(){
        for(int i = 0; i < imoveis.length -1; i++){
            for(int j = i+1; j < imoveis.length; j++){
                if(imoveis[i].consumoMedio > imoveis[j].consumoMedio){
                    Imovel tmp = imoveis[i];
                    imoveis[i] = imoveis[j];
                    imoveis[j] = tmp;
                }
            }
        }

    }
}

class Imovel{
    public int moradores;
    public int consumo;
    public int consumoMedio;

    Imovel(){
        this(0,0);
    }

    Imovel(int moradores, int consumo){
        this.moradores = moradores;
        this.consumo = consumo;
        this.consumoMedio = this.consumo/ this.moradores;
    }
}