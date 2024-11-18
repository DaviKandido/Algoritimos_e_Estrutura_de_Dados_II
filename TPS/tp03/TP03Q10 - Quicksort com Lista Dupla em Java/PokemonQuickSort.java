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



 public class PokemonQuickSort {

        //Grava arquivo com paramentros de Execução
        public static void GravarArquivoDeExecucao(String filename, int Comparacoes, int Movimentacoes, long FimTime ){

            try(RandomAccessFile arquivo = new RandomAccessFile(filename, "rw")){
                
                arquivo.writeChars("857859\t"+ Comparacoes + "\t" + Movimentacoes + "\t" + FimTime +"ms");
    
                arquivo.close();
    
            }catch(IOException err){
                System.err.println("Erro no arquivo criado" + err.getMessage());
            }
        }


    public static boolean isFim(String entrada){
        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }


    public static void main(String[] args) {
         
        Scanner sc = new Scanner(System.in);
        boolean parar = true;
        int i = 0;
        int k = 0;

        
        GerenciaPokemon gp = new GerenciaPokemon();

        gp.lerPokemon();

        ListaDupla listaPokemons = new ListaDupla();

                
        // Ler até encontrar "FIM"
        while (parar) {
            String entrada = sc.nextLine();
            
            if (isFim(entrada)) {
                parar = false;
               } else {
                   int id = Integer.parseInt(entrada);
                   try{
                        Pokemon poke = gp.procuraPokemon(id);
                        listaPokemons.inserirFim(poke);
                        i++;
                   }
                   catch(Exception e){
                       System.out.println("Pokemon nao encontrado " + e.getMessage());
                   }
               }
           }


       long start = System.currentTimeMillis(); //Variáveis de analise de Execução

       listaPokemons.QuickSort(); //Chamada do quicksort

       long FimTime = System.currentTimeMillis() - start;


    //    System.out.println("--------------------------END Pivo---------------------");
       listaPokemons.mostrar();

        sc.close();

    

        GravarArquivoDeExecucao("857859_quicksort3.txt", GlobalVars.getComparacoes(), GlobalVars.getMovimentacoes(), FimTime); //Arquivo de analise de Execução


       }
 }


 // ------------------------------------  Variáveis Globais -------------------------------------------- //

class GlobalVars {
    public static int comparacoes;
    public static int movimentacoes;

    public GlobalVars(){
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

 // ------------------------------------  Célula Flexível -------------------------------------------- //

class CelulaDupla {
    public Pokemon pokemon;
    public CelulaDupla prox, ant;

    public CelulaDupla(){
        this(null);
    }


    public CelulaDupla(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.prox = null;
    }
}
// ------------------------------------ END - Célula Flexível -------------------------------------------- //



 class ListaDupla {
   
    private CelulaDupla primeiro, ultimo;
    private int size;
 
    public int getTamanho() {
        return size;
    }

    public ListaDupla(){
       primeiro = new CelulaDupla();
       ultimo = primeiro;    
    }

    public void inserirInicio(Pokemon pokemon){
        CelulaDupla tmp = new CelulaDupla(pokemon);

        tmp.ant = primeiro;
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if(primeiro == ultimo){
            ultimo = tmp;
        }else{
            tmp.prox.ant = tmp;
        }
        tmp = null;

        size++;
    }

    public void inserirFim(Pokemon pokemon){
        ultimo.prox = new CelulaDupla(pokemon);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
        size++;
    }

    public void inserir(Pokemon pokemon, int pos) throws Exception {

        int tamanho = size;
  
        if(pos < 0 || pos > tamanho){
              throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
        } else if (pos == 0){
           inserirInicio(pokemon);
        } else if (pos == tamanho){
           inserirFim(pokemon);
        } else {
             // Caminhar ate a posicao anterior a insercao
           CelulaDupla i = primeiro;
           for(int j = 0; j < pos; j++, i = i.prox);
          
           CelulaDupla tmp = new CelulaDupla(pokemon);
           tmp.ant = i;
           tmp.prox = i.prox;
           tmp.ant.prox = tmp.prox.ant = tmp;
           tmp = i = null;

           size++;
        }
     }


     public Pokemon removerInicio() throws Exception{
        if (primeiro == ultimo){
            throw new Exception("Erro ao remover (vazia)!");
        }

        CelulaDupla tmp = primeiro;
        primeiro = primeiro.prox;
        Pokemon resp = primeiro.pokemon;
        tmp.prox = primeiro.ant = null;
        tmp = null;
        size--;

        return resp;
     }

    public Pokemon removerFim() throws Exception{
        if (primeiro == ultimo){
            throw new Exception("Erro ao remover (vazia)!");
        }

        Pokemon resp = ultimo.pokemon;
        ultimo = ultimo.ant;
        ultimo.prox.ant = null;
        ultimo.prox = null;
        size--;

        return resp;
     }

   

     public Pokemon remover(int pos) throws Exception {
        Pokemon resp;
        int tamanho = size;
  
          if (primeiro == ultimo){
              throw new Exception("Erro ao remover (vazia)!");
  
        } else if(pos < 0 || pos >= tamanho){
              throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
        } else if (pos == 0){
           resp = removerInicio();
        } else if (pos == tamanho - 1){
           resp = removerFim();
        } else {
             // Caminhar ate a posicao anterior a insercao
           CelulaDupla i = primeiro.prox;
           for(int j = 0; j < pos; j++, i = i.prox);
          
           i.ant.prox = i.prox;
           i.prox.ant = i.ant;
           resp = i.pokemon;
           i.prox = i.ant = null;
           i = null;
        }
  
          return resp;
      }

	public void mostrar() {
		mostrar(primeiro.prox, 0);
	}


    /**
     * Mostra os elementos da pilha.
     */
    private void mostrar(CelulaDupla i, int count){
        if(i != null){
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = outputDateFormat.format(i.pokemon.getCaptureDate());

            System.out.println("[#"
                                + i.pokemon.getId() + " -> "
                                + i.pokemon.getName() + ": "
                                + i.pokemon.getDescription() + " - "
                                + i.pokemon.getTypes() + " - "
                                + i.pokemon.getAbilities() + " - "
                                + i.pokemon.getWeight() + "kg - "
                                + i.pokemon.getHeight() + "m - "
                                + i.pokemon.getCaptureRate() + "% - "
                                + i.pokemon.getIsLegendary() + " - "
                                + i.pokemon.getGeneration() + " gen] - " // Corrigido: Usar getGeneration() aqui
                                + formattedDate 
                            );
            mostrar(i.prox, ++count);
       }
    }
 
 
    /**
     * Procura um elemento e retorna se ele existe.
     */
    public boolean pesquisar(Pokemon x) {
       boolean retorno = false;
       for (CelulaDupla i = primeiro.prox; i != null && retorno == false; i = i.prox) {
          retorno = (i.pokemon == x);
       }
       return retorno;
    }


    private CelulaDupla obterPivo(int pos, CelulaDupla ci) {
        CelulaDupla atual = ci; 
        for (int i = 0; i < pos && atual != null; i++) {
            atual = atual.prox;
        }
        return atual; 
    }

    void swap(CelulaDupla ci, CelulaDupla cj) {
        Pokemon tmp = ci.pokemon;
        ci.pokemon = cj.pokemon;
        cj.pokemon = tmp;
    }
    


    public void QuickSort(){
        QuickSort(0, getTamanho() - 1, primeiro.prox, ultimo);
    }


    private void QuickSort(int esq, int dir, CelulaDupla cEsq, CelulaDupla cDir) {
        
        CelulaDupla ci = cEsq, cj = cDir;
        int i = esq, j = dir;

        CelulaDupla pivo = obterPivo((dir+esq)/2, ci);
        // CelulaDupla pivo = ci;

        // pivo.pokemon.printPokemon();

        while(i <= j){
            // Comparação com o pivô
            while(ci.pokemon.getGeneration() < pivo.pokemon.getGeneration() || (ci.pokemon.getGeneration() == pivo.pokemon.getGeneration() && ci.pokemon.getName().compareTo(pivo.pokemon.getName()) < 0)){
                GlobalVars.comparacoes++;
                i++;
                ci = ci.prox;
            }
            while ( (cj.pokemon.getGeneration() > pivo.pokemon.getGeneration() || (cj.pokemon.getGeneration() == pivo.pokemon.getGeneration() && cj.pokemon.getName().compareTo(pivo.pokemon.getName()) > 0))) {
                GlobalVars.comparacoes++;
                j--;
                cj = cj.ant;
            }
            if (i <= j) {
                GlobalVars.movimentacoes += 3;
                swap(ci, cj);
                ci = ci.prox;
                i++;
                cj = cj.ant;
                j--;
            }
         }
    
        // //Chamadas recursivas
        if (esq < j) {
            QuickSort(esq, j, cEsq, cj);
        }
        if (i < dir) {
            QuickSort(i, dir, ci, cDir);
        }
     }
 }


 class GerenciaPokemon {

    // Global variables
    //public static final String FILE_PATH = "pokemon.csv";
    public static final String FILE_PATH = "/tmp/pokemon.csv";

    private static Pokemon pokemon[] = new Pokemon[801];
 
         //---------------------------------- Função cria pokemon usando split -------------------------------------------//
 
         public Pokemon criarPokemon(String linha) {
            
             Pokemon pokemon = new Pokemon();
             SimpleDateFormat ddf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
     
             // Divide a linha em partes usando split
             String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
     
             // Atribui os valores aos atributos do Pokemon
             pokemon.setId(Integer.parseInt(campos[0])); 
             pokemon.setGeneration(Integer.parseInt(campos[1])); 
             pokemon.setName(campos[2]); 
             pokemon.setDescription(campos[3]); 
     
             
             ArrayList<String> types = new ArrayList<>();
             types.add("'" + campos[4] + "'"); // type1
             if (!campos[5].isEmpty()) {
                 types.add("'" + campos[5] + "'"); // type2, se presente
             }
             pokemon.setTypes(types); 
     
             // Remove as aspas ao redor das habilidades e ajusta o formato
             pokemon.getAbilities().addAll(
                         Arrays.asList(
                             campos[6]
                                 .replace("\"", "")  // Remove as aspas
                                 .replace("[", "")    // Remove o colchete de abertura
                                 .replace("]", "")    // Remove o colchete de fechamento
                                 .split(", ")         // Divide a string em partes através da ","
                                 ));
                                     
             pokemon.setWeight(campos[7].isEmpty() ? 0.0 : Double.parseDouble(campos[7])); 
             pokemon.setHeight(campos[8].isEmpty() ? 0.0 : Double.parseDouble(campos[8]));         
             pokemon.setCaptureRate(Integer.parseInt(campos[9])); 
             pokemon.setIsLegendary(campos[10].equals("1")); 
     
             // Converte a data para o formato correto
             try {
                 pokemon.setCaptureDate(ddf.parse(campos[11]));
             } catch (ParseException e) {
                 e.printStackTrace();
             }
     
             return pokemon;
         }
         
 
     //---------------------------------- Ler Pokemon -------------------------------------------//
 
     public Pokemon[] lerPokemon() {
 
         int i = 0;
 
         try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
           
 
             String line = br.readLine(); //Descarta a primeira leitura
 
             line = br.readLine();
 
             while (line != null) {
 
                 pokemon[i++] =  criarPokemon(line);
                 
                 line = br.readLine();
             }
         } catch (IOException e) {
             System.out.println("Erro ao ler o arquivo: " + e.getMessage());
         }
 
         return pokemon;
     }



     public Pokemon procuraPokemon(int id) {
 
        int i = 0;

        Pokemon pokemonRetorno = new Pokemon();

        for(i = 0; i < 801; i++){
            if(pokemon[i].getId() == id){
                 pokemonRetorno = pokemon[i];
            }
        }
        // Retorna null se o Pokémon com o ID especificado não for encontrado
        return pokemonRetorno;
    }


    public Pokemon procuraPokemon(String name) {
 
        int i = 0;

        Pokemon pokemonRetorno = new Pokemon();

        for(i = 0; i < 801; i++){
            if(pokemon[i].getName().intern() == name.intern()){
                pokemonRetorno = pokemon[i];
            }
        }

        // Retorna null se o Pokémon com o nome especificado não for encontrado
        return pokemonRetorno;
    }



    public int swap(int i, int j){
        Pokemon tmp = pokemon[i];
        pokemon[i] = pokemon[j];
        pokemon[j] = tmp; 
        
        return 3;
    }


    public int[] ordenacaoSelecao(int n){

        int dados[] = new int[2];

        for(int i = 0; i < n-1; i++){
            int menor = i;
            for(int j = (i+1); j < n; j++){
                dados[0]++;
                if(pokemon[menor].getName().compareTo(pokemon[j].getName()) > 0){
                    menor = j;
                }
            }
            dados[1] += swap(i, menor);
        }


        return dados;
    }

    public Pokemon[] getPokemons() {
        return pokemon;
    }

 }
 
 
class Pokemon {

     // static SimpleDateFormat ddf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
 
     // Global variables
     //public static final String FILE_PATH = "pokemon.csv";
     public static final String FILE_PATH = "/tmp/pokemon.csv";
 
     //public static ArrayList<Pokemon> allCharacters = new ArrayList<Pokemon>();
 
     // Attributes
     private int id;
     private int generation;
     private String name;
     private String description;
     private ArrayList<String>  types;
     private ArrayList<String>  abilities;
     private double weight;
     private double height;
     private int captureRate;
     private boolean isLegendary;
     private Date captureDate;
 
     //---------------------------------- Construtores -------------------------------------------//
 
     public Pokemon() {
         this.id = 0;
         this.generation = 0;
         this.name = "";
         this.description = "";
         this.types = new ArrayList<String>();
         this.abilities = new ArrayList<String>();
         this.weight = 0.0;
         this.height = 0.0;
         this.captureRate = 0;
         this.isLegendary = false;
         this.captureDate = new Date();
     }
 
     public Pokemon(int id, int generation, String name, String description, ArrayList<String>  types, ArrayList<String>  abilities, double weight, double height, int captureRate, boolean isLegendary, Date captureDate) {
         this.id = id;
         this.generation = generation;
         this.name = name;
         this.description = description;
         this.types = types;
         this.abilities = abilities;
         this.weight = weight;
         this.height = height;
         this.captureRate = captureRate;
         this.isLegendary = isLegendary;
         this.captureDate = captureDate;
     }
 
     //---------------------------------- Gets -------------------------------------------//
 
     public int getId(){ return this.id; }
     public int getGeneration(){ return this.generation; }
     public String getName(){ return this.name; }
     public String getDescription(){ return this.description; }
     public ArrayList<String>  getTypes(){ return this.types; }
     public ArrayList<String>  getAbilities(){ return this.abilities; }
     public double getWeight(){ return this.weight; }
     public double getHeight(){ return this.height; }
     public int getCaptureRate(){ return this.captureRate; }
     public boolean getIsLegendary(){ return this.isLegendary; }
     public Date getCaptureDate(){ return this.captureDate; }
 
     //---------------------------------- Sets -------------------------------------------//
 
     public void setId(int id){ this.id = id; }
     public void setGeneration(int generation){ this.generation = generation; }
     public void setName(String name){ this.name = name; }
     public void setDescription(String description){ this.description = description; }
     public void setTypes(ArrayList<String>  types){ this.types = types; }
     public void setAbilities(ArrayList<String>  abilities){ this.abilities = abilities; }
     public void setWeight(double weight){ this.weight = weight; }
     public void setHeight(double height){ this.height = height; }
     public void setCaptureRate(int captureRate){ this.captureRate = captureRate; }
     public void setIsLegendary(boolean isLegendary){ this.isLegendary = isLegendary; }
     public void setCaptureDate(Date captureDate){ this.captureDate = captureDate; }
 
     //---------------------------------- Clone -------------------------------------------//

     public Pokemon Clone() { return new Pokemon( this.id, this.generation, this.name, this.description, this.types, this.abilities, this.weight, this.height, this.captureRate, this.isLegendary, this.captureDate); }
 
     //---------------------------------- Imprimir -------------------------------------------//
 
     public void printPokemon(){
         // Formatar a data para o padrão desejado
         SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
         String formattedDate = outputDateFormat.format(this.captureDate);
 
         System.out.println("[#"
                             + getId() + " -> "
                             + getName() + ": "
                             + getDescription() + " - "
                             + getTypes() + " - "
                             + getAbilities() + " - "
                             + getWeight() + "kg - "
                             + getHeight() + "m - "
                             + getCaptureRate() + "% - "
                             + getIsLegendary() + " - "
                             + getGeneration() + " gen] - " // Corrigido: Usar getGeneration() aqui
                             + formattedDate 
                           );
 
         //[#137 -> Porygon: Virtual Pokémon - ['normal'] - ['Trace', 'Download', 'Analytic'] - 36.5kg - 0.8m - 45% - false - 1 gen] - 06/06/1996
     }
 }





