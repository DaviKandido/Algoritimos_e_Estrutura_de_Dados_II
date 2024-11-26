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


 
 public class PokemonArvoreArvore {
    

    //Grava arquivo com parâmetros de Execução
    public static void GravarArquivoDeExecucao(String filename, int Comparacoes, int Movimentacoes, long FimTime ){

        try(RandomAccessFile arquivo = new RandomAccessFile(filename, "rw")){
            
            arquivo.writeChars("857859" + "\t" +  FimTime + "ms" + "\t" + Comparacoes);

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
        
        GerenciaPokemon gp = new GerenciaPokemon();

        gp.lerPokemon();

        ArvoreDeArvore arvorePokemons = new ArvoreDeArvore();


        long start = System.currentTimeMillis(); //Variáveis de analise de Execução

                
        // Ler até encontrar "FIM"
        while (parar) {
            String entrada = sc.nextLine();
            
            if (isFim(entrada)) {
                parar = false;
               } else {
                   
                   int id = Integer.parseInt(entrada);
                   try{
                        Pokemon poke = gp.procuraPokemon(id);
                        arvorePokemons.inserir(poke);
                   }
                   catch(Exception e){
                       System.out.println("Pokemon nao encontrado " + e.getMessage());
                   }
               }
           }

        parar = true;
        
        while (parar) {
            String entrada = sc.nextLine();
            
            if (isFim(entrada)) {
                parar = false;
               } else {
                boolean resp = arvorePokemons.pesquisar(entrada);
                System.out.println(resp ? "SIM" : "NAO");
               }
           }
           
       long FimTime = System.currentTimeMillis() - start;


    //    arvorePokemons.caminharPre();

        sc.close();

    

        GravarArquivoDeExecucao("857859_arvoreArvore.txt", VariaveisGlobais.comparacoes, VariaveisGlobais.movimentacoes, FimTime); //Arquivo de analise de Execução


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

 // ------------------------------------  No 1 (Primeira Arvore) -------------------------------------------- //

class No {

    public int mod;
    public No esq, dir;
    public No2 outro;

    public No(int mod){
        this.mod = mod;
        this.esq = this.dir = null;
        this.outro = null;
    }

    public No(int mod, No esq, No dir){
        this.mod = mod;
        this.esq = esq;
        this.dir = dir;
    }


    public int getMod(){
        return this.mod;
    }
}
// ------------------------------------ END - No 1 (Primeira Arvore)-------------------------------------------- //


 // ------------------------------------  No 2 (Primeira Arvore) -------------------------------------------- //

 class No2 {

    public Pokemon pokemon;
    public No2 esq, dir;

    public No2(Pokemon pokemon){
        this.pokemon = pokemon;
        this.esq = this.dir = null;
    }

    public No2(Pokemon Pokemon, No2 esq, No2 dir){
        this.pokemon = pokemon;
        this.esq = esq;
        this.dir = dir;
    }

}
// ------------------------------------ END - No 2 (Primeira Arvore)-------------------------------------------- //




 // ------------------------------------  Class Arvore  -------------------------------------------- //

class ArvoreDeArvore{
    private No raiz;

    public ArvoreDeArvore(){
        raiz = null;
        try{
        inserir(7); 
        inserir(3);
        inserir(11);
        inserir(1);
        inserir(5);
        inserir(9);
        inserir(13);
        inserir(0);
        inserir(2);
        inserir(4);
        inserir(6);
        inserir(8);
        inserir(10);
        inserir(12);
        inserir(14); 
        }catch(Exception e){
            System.err.println("Erro ao configurar Arvore de Arvore: " + e.getMessage());
        }
        // Inserindo todos seus nós e respeitando a ordem 7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12 e 14.
    }

    public void inserir(int mod) throws Exception{
        raiz = inserir(mod, raiz);
    }

    private No inserir(int mod, No i) throws Exception{
        if( i == null){
            i = new No(mod);
        }else if(mod < i.mod){
            VariaveisGlobais.comparacoes++;
            i.esq = inserir(mod, i.esq);
        }else if(mod > i.mod){
            VariaveisGlobais.comparacoes++;
            i.dir = inserir(mod, i.dir);
        }else {
            throw new Exception("Erro ao inserir! elemento inexistente");
        }

        return i;
    }


    public void inserir(Pokemon pokemon) throws Exception{
        inserir(pokemon, raiz);
    }

    private No inserir(Pokemon pokemon, No i) throws Exception{
        if( i == null){
            throw new Exception("Erro ao inserir: mod invalido");
        }else if(pokemon.getCaptureRate() % 15 < i.mod){
            VariaveisGlobais.comparacoes++;
            i.esq = inserir(pokemon, i.esq);

        }else if(pokemon.getCaptureRate() % 15 > i.mod){
            VariaveisGlobais.comparacoes++;
            i.dir = inserir(pokemon, i.dir);
            
        }else {
            i.outro = inserir(pokemon, i.outro);
        }
        
        return i;
    }


    private No2 inserir(Pokemon pokemon, No2 i) throws Exception{
        if( i == null){
            i = new No2(pokemon);
        }else if(pokemon.getName().compareTo(i.pokemon.getName()) < 0){
            VariaveisGlobais.comparacoes++;
            i.esq = inserir(pokemon, i.esq);
        }else if(pokemon.getName().compareTo(i.pokemon.getName()) > 0){
            VariaveisGlobais.comparacoes++;
            i.dir = inserir(pokemon, i.dir);
        }else {
            throw new Exception("Erro ao inserir! Elemento inexistente");
        }

        return i;
    }
    


    public boolean pesquisar(String name){
        System.out.println("=> " + name);
        System.out.print("raiz ");
        return PesquisarcaminharPre(name, raiz);
    }

    private boolean PesquisarcaminharPre(String name, No i){
        boolean resp;
        if(i == null){
            resp = false;
        }else{
			resp = pesquisar(name, i.outro);
            
            if(resp == false){
                System.out.print("ESQ ");
                resp = PesquisarcaminharPre(name, i.esq);
            }

            if(resp == false){
                System.out.print("DIR ");
                resp = PesquisarcaminharPre(name, i.dir);
            }

        }

        return resp;
    }

    private boolean pesquisar(String name, No2 i){
        boolean resp;
        if( i == null){
            resp = false;
        } else if(name.equals(i.pokemon.getName())){
            VariaveisGlobais.comparacoes++;
            resp = true;
        } else if(name.compareTo(i.pokemon.getName()) < 0){
            VariaveisGlobais.comparacoes++;
            System.out.print("esq ");
            resp = pesquisar(name, i.esq);
        } else{
            System.out.print("dir ");
            resp = pesquisar(name, i.dir);
        }

        return resp;
    }







}


  // ------------------------------------  END - Class Arvore  -------------------------------------------- //



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





