/**
 * @path TP03Q01 - Classe em Java/Pokemon.java 
 * @description Java class of all Pokemon
 * @author Davi Cândido de Almeida - https://github.com/DaviKandido
 * @version 2.0
 * @update 2024-10-24
 */


 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Date;
 import java.util.Locale;
 import java.util.Scanner;
 import java.io.File;
 import java.io.RandomAccessFile;



 public class MatrizDinamica {


        public static int comparacoes;
        public static int movimentacoes;

        //Grava arquivo com paramentros de Execução
        public static void GravarArquivoDeExecucao(String filename, int Comparacoes, int Movimentacoes, long FimTime ){

            try(RandomAccessFile arquivo = new RandomAccessFile(filename, "rw")){
                
                arquivo.writeChars("857859\t"+ Comparacoes + "\t" + Movimentacoes + "\t" + FimTime +"ms");
    
                arquivo.close();
    
            }catch(IOException err){
                System.err.println("Erro no arquivo criado" + err.getMessage());
            }
        }



    public static void main(String[] args) {
         
        Scanner sc = new Scanner(System.in);
        int numOp = 0;

        long start = System.currentTimeMillis(); //Variáveis de analise de Execução

        numOp = sc.nextInt();

        for(int i = 0; i < numOp; i++){

            int numLinhas = sc.nextInt();
            int numColunas = sc.nextInt();

            Matriz matriz1 = new Matriz(numLinhas, numColunas);

            try{
                matriz1.inserirRead(sc);
            }catch(Exception e){
                System.out.println("Erro ao ler o arquivo");
            }

            numLinhas = sc.nextInt();
            numColunas = sc.nextInt();

            Matriz matriz2 = new Matriz(numLinhas, numColunas);

            try{
                matriz2.inserirRead(sc);
            }catch(Exception e){
                System.out.println("Erro ao ler o arquivo");
            }

            matriz1.mostrarDiagonalPrincipal();
            matriz1.mostrarDiagonalSecundaria();

            try{
                Matriz matrizSoma = matriz1.soma(matriz2);
                Matriz matrizMult = matriz1.multiplicacao(matriz2);
                
                matrizSoma.mostrar();
                matrizMult.mostrar();
            }catch(Exception e){
                System.out.println("Erro mostrar soma ou multiplicação: " + e.getMessage());
            }
        
        }

        sc.close();

 
        long FimTime = System.currentTimeMillis() - start;
    

        GravarArquivoDeExecucao("857859_MatrizDinamica.txt", VariaveisGlobais.getComparacoes(), VariaveisGlobais.getMovimentacoes(), FimTime); //Arquivo de analise de Execução


       }
 }


// ------------------------------------  Variáveis Globais -------------------------------------------- //

class VariaveisGlobais {
    public static int comparacoes;
    public static int movimentacoes;

    public VariaveisGlobais(){
    comparacoes = 0;
    movimentacoes = 0;
    }
    public static int getComparacoes() {
    return comparacoes;
    }
    public static int getMovimentacoes() {
    return movimentacoes;
    }
}

// ------------------------------------  END - Variáveis Globais -------------------------------------------- //

 // ------------------------------------  Célula Flexível - Matriz -------------------------------------------- //

class Celula {
    public int elemento;
    public Celula inf, sup, esq, dir;

    public Celula(){
        this(0);
    }

    public Celula(int elemento){
        this(elemento, null, null, null, null);
    }

    public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir){
        this.elemento = elemento;
        this.inf = inf;
        this.sup = sup;
        this.esq = esq;
        this.dir = dir;
    }
}

// ------------------------------------ END - Célula Flexível  Matriz -------------------------------------------- //


 // ------------------------------------  Class Matriz -------------------------------------------- //
class Matriz{

    private Celula inicio;
    private int linha, coluna;

    public Matriz(){
        this(3,3);
    }

    public Matriz(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;

        int lin = 0;
        this.inicio = new Celula();
        Celula i = this.inicio;

        while(lin < this.linha){
            Celula ancora = i;

            //função para alocar linhas
            for(int col = 1; col < this.coluna; i = i.dir, col++){
                Celula tmp = new Celula();
                i.dir = tmp;
                tmp.esq = i;
            }

            // retorna o i para o inicio da linha
            i = ancora;

            // conecta o sup com o inf
            if(lin > 0 && lin <= this.linha){
                int col = 1;

                 // j e i caminhamentos para os elementos paralelos
                for(Celula j = i.sup; col <= this.coluna; j = j.dir, i = i.dir, col++){
                    j.inf = i;
                    i.sup = j;
                }

                // retorna o i para o inicio da linha
                i = ancora;
            }

            // Cria a primeira celula da proxima linha
            if(lin < this.linha - 1){
                i.inf = new Celula();
                i.inf.sup = i;
                i = i.inf;
            }

            lin++;
        }
    }

    public Matriz soma (Matriz m) throws Exception {
        if (this.linha != m.linha || this.coluna != m.coluna) {
            throw new Exception("As matrizes devem ter as mesmas dimensões para serem somadas.");
        }
    
        Matriz resp = new Matriz(this.linha, this.coluna);
        
        for (Celula ai = this.inicio, bi = m.inicio, ci = resp.inicio; ai != null; ai = ai.inf, bi = bi.inf, ci = ci.inf) {
            for (Celula aj = ai, bj = bi, cj = ci; aj != null; aj = aj.dir, bj = bj.dir, cj = cj.dir) {
                cj.elemento = aj.elemento + bj.elemento;
            }
        }
    
        return resp;
    }
    

    public Matriz multiplicacao(Matriz m) throws Exception {
        // Verifica se as dimensões são compatíveis para a multiplicação
        if (this.coluna != m.linha) {
            throw new Exception("Dimensões incompatíveis para a multiplicação.");
        }
    
        Matriz resp = new Matriz(this.linha, m.coluna);
    
        //Calcular cada elemento da matriz resultado da multiplicação
        for (Celula ai = this.inicio, ci = resp.inicio; ai != null; ai = ai.inf, ci = ci.inf) { 
            Celula mColInicio = m.inicio; 
            for (Celula cj = ci; cj != null; cj = cj.dir) { 
                Celula aij = ai; 
                Celula bj = mColInicio; 
                int mult = 0;
                while (aij != null && bj != null) { 
                    mult += aij.elemento * bj.elemento;
                    aij = aij.dir; 
                    bj = bj.inf;   
                }
                cj.elemento = mult; 
                mColInicio = mColInicio.dir; 
            }
        }
    
        return resp;
    }

    public boolean isQuadrada(){
        return (this.linha == this.coluna);
     }


    public void mostrarDiagonalPrincipal(){
        if(isQuadrada() == true){

            Celula i = this.inicio;

            while(i != null){
                System.out.print(i.elemento + " ");

                i = i.dir;
                if(i != null) i = i.inf;
            }
            System.out.println();
        }
    }

    public void mostrarDiagonalSecundaria(){
        if(isQuadrada() == true){

            Celula i = this.inicio;

            for(;i.dir != null; i = i.dir);

            while(i != null){
                System.out.print(i.elemento + " ");

                i = i.esq;
                if(i != null) i = i.inf;
            }
            System.out.println();
        }
    }


    void inserir(int x, int linhaPos, int colunaPos){
        if(linhaPos >= this.linha || colunaPos >= this.coluna || linhaPos < 0 || colunaPos < 0){
            try{
                throw new Exception("Posicao invalida");
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Celula tmp = inicio;
            for(int i = 0; i < colunaPos; i++){
                tmp = tmp.dir;
            }
            for(int i = 0; i < linhaPos; i++){
                tmp = tmp.inf;
            }
            tmp.elemento = x;
        }
    }


    public void inserirRead(Scanner sc) throws Exception{
        if(inicio == null)
            throw new Exception("Erro ao inserir!");

        for(Celula i = this.inicio; i != null; i = i.inf){
            for(Celula j = i; j != null; j = j.dir){
                j.elemento = sc.nextInt();
            }
        }
    }

    public void mostrar(){

        for(Celula i = this.inicio; i != null; i = i.inf){
            for(Celula j = i; j != null; j = j.dir){
                System.out.print(j.elemento + " ");
            }
            System.out.println();
        }
    }

}


 // ------------------------------------  END - Class Matriz -------------------------------------------- //
