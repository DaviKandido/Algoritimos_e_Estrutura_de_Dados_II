/**
 * @path TP02Q01 - Classe em Java/Pokemon.java 
 * @description Java class of all Pokemon
 * @author Davi Cândido de Almeida - https://github.com/DaviKandido
 * @version 2.0
 * @update 2024-09-24
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
 
 public class PokemonHeap{
 
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
 
     public Pokemon PokemonClone() { return new Pokemon( this.id, this.generation, this.name, this.description, this.types, this.abilities, this.weight, this.height, this.captureRate, this.isLegendary, this.captureDate); }
 
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
 
 
     //---------------------------------- Verifica se é o FIM -------------------------------------------//
 
     public static boolean isFim(String entrada){
         return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
     }
 
         //---------------------------------- Função cria Pokemonusando split -------------------------------------------//
 
         public static Pokemon criarPokemon(String linha) {
             Pokemon pokemon= new Pokemon();
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
         
 
     //---------------------------------- Ler Pokemon-------------------------------------------//
 
     public static Pokemon[] lerPokemon(Pokemon pokemon[]) {
 
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



     public static Pokemon ProcuraPokemon(Pokemon pokemon[], int id) {
 
        int i = 0;

        for(i = 0; i < 801; i++){
            if(pokemon[i].getId() == id){
                return pokemon[i];
            }
        }
        // Retorna null se o Pokémon com o ID especificado não for encontrado
        return null;
    }


    public static Pokemon ProcuraPokemon(Pokemon pokemon[], String name) {
 
        int i = 0;

        for(i = 0; i < 801; i++){
            if(pokemon[i].getName().intern() == name.intern()){
                return pokemon[i];
            }
        }

        // Retorna null se o Pokémon com o nome especificado não for encontrado
        return null;
    }
    
    //Grava arquivo com paramentros de Execução
    public static void GravarArquivoDeExecucao(String filename, int Comparacoes, int movimentacoes, long FimTime ){

        try(RandomAccessFile arquivo = new RandomAccessFile(filename, "rw")){
            
            arquivo.writeChars("857859\t"+ Comparacoes + "\t" + movimentacoes + "\t" + FimTime +"ms");

            arquivo.close();

        }catch(IOException err){
            System.err.println("Erro no arquivo criado" + err.getMessage());
        }
    }


    //--------------------Metodos heapSort------------------------------//
    public static void swap(Pokemon pokemon[], int i, int j) {
        Pokemon tmp = pokemon[i];
        pokemon[i] = pokemon[j];
        pokemon[j] = tmp; 
    }
    
    public static void construir(Pokemon pokemon[], int tam) {
        for (int i = tam; i > 1 && (pokemon[i] != null) && pokemon[i].getHeight() > pokemon[i/2].getHeight(); i /= 2) {
            swap(pokemon, i, i / 2);
        }
    }
    
    public static int getMaiorFilho(Pokemon pokemon[], int i, int tam) {
        int filho;
        if (2 * i == tam) {
            filho = 2 * i;
        } else if (pokemon[2 * i].getHeight() > pokemon[2 * i + 1].getHeight()) {
            filho = 2 * i;
        } else if (pokemon[2 * i].getHeight() < pokemon[2 * i + 1].getHeight()) {
            filho = 2 * i + 1;
        } else {
            if (pokemon[2 * i].getName().compareTo(pokemon[2 * i + 1].getName()) > 0) {
                filho = 2 * i;
            } else {
                filho = 2 * i + 1;
            }
        }
        return filho;
    }
    
    public static boolean hasFilho(int i, int tam) {
        return (i <= (tam / 2));
    }
    
    public static void reconstruir(Pokemon pokemon[], int tam) {
        int i = 1;
        while (hasFilho(i, tam) == true) {
            int filho = getMaiorFilho(pokemon, i, tam);
            if ((pokemon[i] != null) && pokemon[i].getHeight() < pokemon[filho].getHeight()) {
                swap(pokemon, i, filho);
                i = filho;
            } else if ( (pokemon[i] != null) && pokemon[i].getHeight() == pokemon[filho].getHeight()) {
                if (pokemon[i].getName().compareTo(pokemon[filho].getName()) < 0) {
                    swap(pokemon, i, filho);
                    i = filho;
                } else {
                    i = tam; // Sai do loop
                }
            } else {
                i = tam; // Sai do loop
            }
        }
    }
    
    public static int[] heapSort(Pokemon pokemon[], int n) {
        int dados[] = new int[2];
        dados[0]++;
        dados[1]++;
        // Construção do heap
        for (int tam = 2; tam <= n; tam++) {
            construir(pokemon, tam);
        }
    
        // Ordenação propriamente dita
        int tam = n;
        while (tam > 1) {
            swap(pokemon, 1, tam--);
            reconstruir(pokemon, tam);
        }
    
        return dados;
    }

        //--------------------END - Metodos heapSort------------------------------//

 
     public static void main(String[] args) {

         
         Scanner sc = new Scanner(System.in);
         boolean parar = true;
         int i = 1;
         
         Pokemon pokemon[] = new Pokemon[801];
         pokemon = lerPokemon(pokemon);
         
         Pokemon pokemonsBuscados[] = new Pokemon[200];
         
         // Ler até encontrar "FIM"
         while (parar) {
             String entrada = sc.nextLine();
             
             if (isFim(entrada)) {
                 parar = false;
                } else {
                    
                    int id = Integer.parseInt(entrada);
                    
                    Pokemon pokemonEncontrado = ProcuraPokemon(pokemon, id);
                    if (pokemonEncontrado != null) {
                        pokemonsBuscados[i++] = pokemonEncontrado;
                    }
                    
                }
            }

            
        long start = System.currentTimeMillis(); //Variáveis de analise de Execução

        int dados[] = heapSort(pokemonsBuscados, i);

        long FimTime = System.currentTimeMillis() - start;

        for(int j = 1; (pokemonsBuscados[j] != null) && j <= i; j++){
            pokemonsBuscados[j].printPokemon();
        }
            
         sc.close();

     

         GravarArquivoDeExecucao("857859_heapsort.txt", dados[0], dados[1], FimTime); //Arquivo de analise de Execução


        }
 }