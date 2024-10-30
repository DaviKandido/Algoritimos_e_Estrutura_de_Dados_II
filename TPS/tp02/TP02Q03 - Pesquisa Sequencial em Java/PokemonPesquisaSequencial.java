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
 
 public class PokemonPesquisaSequencial {
 
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
 
 
     public PokemonPesquisaSequencial() {
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
 
     public PokemonPesquisaSequencial(int id, int generation, String name, String description, ArrayList<String>  types, ArrayList<String>  abilities, double weight, double height, int captureRate, boolean isLegendary, Date captureDate) {
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
 
     public PokemonPesquisaSequencial Clone() { return new PokemonPesquisaSequencial( this.id, this.generation, this.name, this.description, this.types, this.abilities, this.weight, this.height, this.captureRate, this.isLegendary, this.captureDate); }
 
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
 
         //---------------------------------- Função cria pokemon usando split -------------------------------------------//
 
         public static PokemonPesquisaSequencial criarPokemon(String linha) {
             PokemonPesquisaSequencial pokemon = new PokemonPesquisaSequencial();
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
 
     public static PokemonPesquisaSequencial[] lerPokemon(PokemonPesquisaSequencial pokemon[]) {
 
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



     public static PokemonPesquisaSequencial ProcuraPokemon_id(PokemonPesquisaSequencial pokemon[], int id) {
 
        int i = 0;

        for(i = 0; i < 801; i++){
            if(pokemon[i].getId() == id){
                return pokemon[i];
            }
        }
        // Retorna null se o Pokémon com o ID especificado não for encontrado
        return null;
    }


    public static PokemonPesquisaSequencial ProcuraPokemon_name(PokemonPesquisaSequencial pokemon[], String name) {
 
        int i = 0;

        for(i = 0; i < 801; i++){
            if(name.equals(pokemon[i].getName())){
                return pokemon[i];
            }
        }
        // Retorna null se o Pokémon com o ID especificado não for encontrado
        return null;
    }

    
    //Grava arquivo com paramentros de Execução
    public static void GravarArquivoDeExecucao(String filename,long FimTime, int Comparacoes ){

        try(RandomAccessFile arquivo = new RandomAccessFile(filename, "rw")){
            
            arquivo.writeChars("857859\t" + FimTime +"ms\t" + Comparacoes);

            arquivo.close();

        }catch(IOException err){
            System.err.println("Erro no arquivo criado" + err.getMessage());
        }
    }
 
     public static void main(String[] args) {
 
        int Comparacoes = 0;
 
         Scanner sc = new Scanner(System.in);

     
         PokemonPesquisaSequencial pokemon[] = new PokemonPesquisaSequencial[801];
         pokemon = lerPokemon(pokemon);
         
         int id;

         PokemonPesquisaSequencial pokemonsBuscadosPorId[] = new PokemonPesquisaSequencial[200];

         

         boolean parar = true;
         int i = 0;
         // Ler até encontrar "FIM"
         while (parar) {
             String entrada = sc.nextLine();
     
             if (isFim(entrada)) {
                 parar = false;
             } else {
                
                id = Integer.parseInt(entrada);

                pokemonsBuscadosPorId[i++] = ProcuraPokemon_id(pokemon, id); 


            }
        }

        PokemonPesquisaSequencial pokemonsBuscadosPorName[] = new PokemonPesquisaSequencial[200];
         



        parar = true;
        int k = 0;
        // Ler até encontrar "FIM"
        while (parar) {
            String entrada = sc.nextLine();
    
            if (isFim(entrada)) {
                parar = false;
            } else {

                        
               pokemonsBuscadosPorName[k++] = ProcuraPokemon_name(pokemon, entrada); 
               
           }
       }


        long start = System.currentTimeMillis(); //Variáveis de analise de Execução


         for(int j = 0; j < i-1; j++){    
            boolean encontrado = false;
            for(int numIds = 0; numIds < k-1; numIds++){
                Comparacoes ++;
                if(pokemonsBuscadosPorName[j].getName().intern() == pokemonsBuscadosPorId[numIds].getName().intern()){
                    encontrado = true;
                }
            }
            if(encontrado) System.out.println("SIM");
            else           System.out.println("NAO");
         }
         
         sc.close();

         long FimTime = System.currentTimeMillis() - start;
     

         GravarArquivoDeExecucao("857859_Sequencial.txt", FimTime, Comparacoes); //Arquivo de analise de Execução


        }
 }