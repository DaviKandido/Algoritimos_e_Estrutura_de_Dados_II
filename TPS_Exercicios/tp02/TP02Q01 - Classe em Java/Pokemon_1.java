/**
 * @path TP02Q01 - Classe em Java/Pokemon.java 
 * @description Java class of all Pokemon
 * @author Davi Cândido de Almeida - https://github.com/DaviKandido
 * @version 2.0
 * @update 2024-09-24
 */

// ---------------------------------------------------------------------------------------------------- //

// Imports
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


public class Pokemon_1 {

    static SimpleDateFormat ddf = new SimpleDateFormat("dd-MM-yyyy");

    // Global variables
    public static final String FILE_PATH = "pokemon.csv";
    public static ArrayList<Character> allCharacters = new ArrayList<Character>();

    // Attributes
    private int id;
    private int generation;
    private String name;
    private String description;
    private ArrayList<String> types;
    private ArrayList<String> abilities;
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
        this.types = null;
        this.abilities = null;
        this.weight = 0.0;
        this.height = 0.0;
        this.captureRate = 0;
        this.isLegendary = false;
        this.captureDate = new Date();
    }

    public Pokemon(int id, int generation, String name, String description, ArrayList<String> types, ArrayList<String> abilities, double weight, double height, int captureRate, boolean isLegendary, Date captureDate) {
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

    private static Pokemon criarPokemon(String[] valores) {
      int id = Integer.parseInt(valores[0]);
      int generation = Integer.parseInt(valores[1]);
      String name = valores[2];
      String description = valores[3];

      // Criar ArrayLists para types e abilities
      ArrayList<String> types = new ArrayList<>(Arrays.asList(valores[4].split(",")));
      if (valores[5].isEmpty()) {
          types.add(""); // Adiciona uma string vazia se type2 estiver vazio
      } else {
          types.add(valores[5]);
      }

      // Remover os caracteres '[' e ']' e as aspas simples da string de abilities
      String abilitiesString = valores[6].substring(1, valores[6].length() - 1).replace("'", "");
      ArrayList<String> abilities = new ArrayList<>(Arrays.asList(abilitiesString.split(", ")));

      // Tratar valores vazios para weight e height
      double weight = valores[7].isEmpty() ? 0.0 : Double.parseDouble(valores[7]);
      double height = valores[8].isEmpty() ? 0.0 : Double.parseDouble(valores[8]);

      int captureRate = Integer.parseInt(valores[9]);
      boolean isLegendary = Integer.parseInt(valores[10]) == 1;

      // Converter a data de captura de String para Date
      Date captureDate;
      try {
          captureDate = ddf.parse(valores[11]);
      } catch (ParseException e) {
          System.out.println("Erro ao converter a data de captura: " + e.getMessage());
          captureDate = new Date(); // Define a data atual como padrão em caso de erro
      }

      return new Pokemon(id, generation, name, description, types, abilities, weight, height, captureRate, isLegendary, captureDate);
  }


    //---------------------------------- Gets -------------------------------------------//

    public int getId(){ return this.id; }
    public int getGeneration(){ return this.generation; }
    public String getName(){ return this.name; }
    public String getDescription(){ return this.description; }
    public ArrayList<String> getTypes(){ return this.types; }
    public ArrayList<String> getAbilities(){ return this.abilities; }
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
    public void setTypes(ArrayList<String> types){ this.types = types; }
    public void setAbilities(ArrayList<String> abilities){ this.abilities = abilities; }
    public void setWeight(double weight){ this.weight = weight; }
    public void setHeight(double height){ this.height = height; }
    public void setCaptureRate(int captureRate){ this.captureRate = captureRate; }
    public void setIsLegendary(boolean isLegendary){ this.isLegendary = isLegendary; }
    public void setCaptureDate(Date captureDate){ this.captureDate = captureDate; }

  //---------------------------------- Sets -------------------------------------------//

    public Pokemon Clone() { return new Pokemon( this.id, this.generation, this.name, this.description, this.types, this.abilities, this.weight, this.height, this.captureRate, this.isLegendary, this.captureDate); }

    //---------------------------------- Imprimir -------------------------------------------//

    public void printPokemon(){
      System.out.println("[#"
                          +getName()+ " -> "
                          +getDescription()+ " - "
                          +getTypes()+ " - "
                          +getAbilities()+ " - "
                          +getWeight()+ "kg - "
                          +getHeight()+ "m - "
                          +getCaptureRate()+ "% - "
                          +getIsLegendary()+ " - "
                          +getCaptureRate()+ " gen] - "
                          +getCaptureDate()
                          );

      //[#137 -> Porygon: Virtual Pokémon - ['normal'] - ['Trace', 'Download', 'Analytic'] - 36.5kg - 0.8m - 45% - false - 1 gen] - 06/06/1996
    }


        //---------------------------------- Verifica se é o FIM -------------------------------------------//

    public static boolean isFim(String entrada){

        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
     }

     public static Pokemon lerPokemon(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            // Ignorar a primeira linha (cabeçalho)
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                
                // Separar os valores da linha pelo delimitador ','
                String[] valores = line.split(",");

                // Verificar se o ID do Pokémon na linha atual corresponde ao ID buscado
                if (Integer.parseInt(valores[0]) == id) {
                    // Criar um novo objeto Pokemon com os valores da linha
                    return criarPokemon(valores);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Retorna null se o Pokémon com o ID especificado não for encontrado
        return null;
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        boolean parar = true;
    
        while (parar) {
            String entrada = scanner.nextLine();
    
            // Verificar se a entrada é "FIM" ANTES da conversão
            if (isFim(entrada)) {
                parar = false;
            } else {
                // Converter a entrada para inteiro APENAS se não for "FIM"
                int id = Integer.parseInt(entrada); 
                Pokemon pokemon = lerPokemon(id);
    
                if (pokemon != null) {
                    pokemon.printPokemon();
                }
            }
        }
    
        scanner.close();
    }
}
