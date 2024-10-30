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



 public class PokemonAlocacaoSequencial {


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

        Lista listaPokemons = new Lista(801);

        Pokemon pokesExcluidos[] = new Pokemon[20];
                
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

        int numOp = sc.nextInt();

        long start = System.currentTimeMillis(); //Variáveis de analise de Execução

        for(int j = 0; j < numOp; j++){
            String op = sc.next();
            comparacoes++;
            if(op.equals("II")){
                int num = sc.nextInt();
                try{
                    movimentacoes++;
                    listaPokemons.inserirInicio(gp.procuraPokemon(num));
                }catch(Exception e){
                    System.out.println("Pokemon nao encontrado " + e.getMessage());
                }
            } else  comparacoes++; if(op.equals("IF")){ 
                int num = sc.nextInt();
                try{
                    movimentacoes++;
                    listaPokemons.inserirFim(gp.procuraPokemon(num));
                }catch(Exception e){
                    System.out.println("Pokemon nao encontrado " + e.getMessage());
                }
            }else  comparacoes++; if(op.equals("RI")){
                try{
                    movimentacoes++;
                    pokesExcluidos[k++] = listaPokemons.removerInicio();
                }catch(Exception e){
                    System.out.println("Lista vazia " + e.getMessage());
                }
            }else  comparacoes++; if(op.equals("RF")){
                try{
                    movimentacoes++;
                    pokesExcluidos[k++] = listaPokemons.removerFim();
                }catch(Exception e){
                    System.out.println("Lista vazia " + e.getMessage());
                }
            }else  comparacoes++; if(op.equals("I*")){
                int pos = sc.nextInt();
                int num = sc.nextInt();
                try{
                    movimentacoes++;
                    listaPokemons.inserir(gp.procuraPokemon(num), pos);
                }catch(Exception e){
                    System.out.println("Lista vazia " + e.getMessage());
                }
            }else  comparacoes++; if(op.equals("R*")){
                int pos = sc.nextInt();
                try{
                    movimentacoes++;
                    pokesExcluidos[k++] = listaPokemons.remover(pos);
                }catch(Exception e){
                    System.out.println("Lista vazia " + e.getMessage());
                }
            }
        }

           
       long FimTime = System.currentTimeMillis() - start;

       for(int j = 0; j < k; j++){
        System.out.println("(R) " + pokesExcluidos[j].getName());
       }

       listaPokemons.mostrar();

        sc.close();

    

        GravarArquivoDeExecucao("857859_PilhaFlexivel.txt", comparacoes, movimentacoes, FimTime); //Arquivo de analise de Execução


       }
 }



 class Lista {
    private Pokemon[] array;
    private int n;
 
 
    /**
     * Construtor da classe.
     */
    public Lista () {
       this(6);
    }
 
 
    /**
     * Construtor da classe.
     * @param tamanho Tamanho da lista.
     */
    public Lista (int tamanho){
       array = new Pokemon[tamanho];
       n = 0;
    }
 
 
    /**
     * Insere um elemento na primeira posicao da lista e move os demais
     * elementos para o fim da lista.
     * @param x int elemento a ser inserido.
     * @throws Exception Se a lista estiver cheia.
     */
    public void inserirInicio(Pokemon x) throws Exception {
 
       //validar insercao
       if(n >= array.length){
          throw new Exception("Erro ao inserir!");
       } 
 
       //levar elementos para o fim do array
       for(int i = n; i > 0; i--){
          array[i] = array[i-1];
       }
 
       array[0] = x;
       n++;
    }
 
 
    /**
     * Insere um elemento na ultima posicao da lista.
     * @param x int elemento a ser inserido.
     * @throws Exception Se a lista estiver cheia.
     */
    public void inserirFim(Pokemon x) throws Exception {
 
       //validar insercao
       if(n >= array.length){
          throw new Exception("Erro ao inserir!");
       }
 
       array[n] = x;
       n++;
    }
 
 
    /**
     * Insere um elemento em uma posicao especifica e move os demais
     * elementos para o fim da lista.
     * @param x int elemento a ser inserido.
     * @param pos Posicao de insercao.
     * @throws Exception Se a lista estiver cheia ou a posicao invalida.
     */
    public void inserir(Pokemon x, int pos) throws Exception {
 
       //validar insercao
       if(n >= array.length || pos < 0 || pos > n){
          throw new Exception("Erro ao inserir!");
       }
 
       //levar elementos para o fim do array
       for(int i = n; i > pos; i--){
          array[i] = array[i-1];
       }
 
       array[pos] = x;
       n++;
    }
 
 
    /**
     * Remove um elemento da primeira posicao da lista e movimenta 
     * os demais elementos para o inicio da mesma.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia.
     */
    public Pokemon removerInicio() throws Exception {
 
       //validar remocao
       if (n == 0) {
          throw new Exception("Erro ao remover!");
       }
 
       Pokemon resp = array[0];
       n--;
 
       for(int i = 0; i < n; i++){
          array[i] = array[i+1];
       }
 
       return resp;
    }
 
 
    /**
     * Remove um elemento da ultima posicao da lista.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia.
     */
    public Pokemon removerFim() throws Exception {
 
       //validar remocao
       if (n == 0) {
          throw new Exception("Erro ao remover!");
       }
 
       return array[--n];
    }
 
 
    /**
     * Remove um elemento de uma posicao especifica da lista e 
     * movimenta os demais elementos para o inicio da mesma.
     * @param pos Posicao de remocao.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista estiver vazia ou a posicao for invalida.
     */
    public Pokemon remover(int pos) throws Exception {
 
       //validar remocao
       if (n == 0 || pos < 0 || pos >= n) {
          throw new Exception("Erro ao remover!");
       }
 
       Pokemon resp = array[pos];
       n--;
 
       for(int i = pos; i < n; i++){
          array[i] = array[i+1];
       }
 
       return resp;
    }
 
 
    /**
     * Mostra os elementos da lista separados por espacos.
     */
    public void mostrar (){
       for(int i = 0; i < n; i++){
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = outputDateFormat.format(array[i].getCaptureDate());

        System.out.println("[" + i + "] [#"
                            + array[i].getId() + " -> "
                            + array[i].getName() + ": "
                            + array[i].getDescription() + " - "
                            + array[i].getTypes() + " - "
                            + array[i].getAbilities() + " - "
                            + array[i].getWeight() + "kg - "
                            + array[i].getHeight() + "m - "
                            + array[i].getCaptureRate() + "% - "
                            + array[i].getIsLegendary() + " - "
                            + array[i].getGeneration() + " gen] - " // Corrigido: Usar getGeneration() aqui
                            + formattedDate 
                          );
       }

    }
 
 
    /**
     * Procura um elemento e retorna se ele existe.
     * @param x int elemento a ser pesquisado.
     * @return <code>true</code> se o array existir,
     * <code>false</code> em caso contrario.
     */
    public boolean pesquisar(Pokemon x) {
       boolean retorno = false;
       for (int i = 0; i < n && retorno == false; i++) {
          retorno = (array[i] == x);
       }
       return retorno;
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





