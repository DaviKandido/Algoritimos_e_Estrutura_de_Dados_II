/**
 * @path TP02Q01 - Classe em Java/Pokemon.java 
 * @description Java class of all Pokemon
 * @author Davi Cândido de Almeida - https://github.com/DaviKandido
 * @version 2.0
 * @update 2024-09-24
 */

// ---------------------------------------------------------------------------------------------------- //

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

public class Pokemon {

    // static SimpleDateFormat ddf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    // Global variables
    public static final String FILE_PATH = "./pokemon.csv";
    public static ArrayList<Character> allCharacters = new ArrayList<Character>();

    // Attributes
    private int id;
    private int generation;
    private String name;
    private String description;
    private String types;
    private String abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private String captureDate;


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
        this.captureDate = "";
    }

    public Pokemon(int id, int generation, String name, String description, String types, String abilities, double weight, double height, int captureRate, boolean isLegendary, String captureDate) {
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
    public String getTypes(){ return this.types; }
    public String getAbilities(){ return this.abilities; }
    public double getWeight(){ return this.weight; }
    public double getHeight(){ return this.height; }
    public int getCaptureRate(){ return this.captureRate; }
    public boolean getIsLegendary(){ return this.isLegendary; }
    public String getCaptureDate(){ return this.captureDate; }

    //---------------------------------- Sets -------------------------------------------//

    public void setId(int id){ this.id = id; }
    public void setGeneration(int generation){ this.generation = generation; }
    public void setName(String name){ this.name = name; }
    public void setDescription(String description){ this.description = description; }
    public void setTypes(String types){ this.types = types; }
    public void setAbilities(String abilities){ this.abilities = abilities; }
    public void setWeight(double weight){ this.weight = weight; }
    public void setHeight(double height){ this.height = height; }
    public void setCaptureRate(int captureRate){ this.captureRate = captureRate; }
    public void setIsLegendary(boolean isLegendary){ this.isLegendary = isLegendary; }
    public void setCaptureDate(String captureDate){ this.captureDate = captureDate; }

    //---------------------------------- Clone -------------------------------------------//

    public Pokemon Clone() { return new Pokemon( this.id, this.generation, this.name, this.description, this.types, this.abilities, this.weight, this.height, this.captureRate, this.isLegendary, this.captureDate); }

    //---------------------------------- Imprimir -------------------------------------------//

    public void printPokemon(){
        // Formatar a data para o padrão desejado
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = outputDateFormat.format(this.captureDate);

        System.out.println("[#"
                            + getName() + " -> "
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


    public Pokemon criarPokemon (){

        Pokemon pokemon = new Pokemon();

        //Desenvolver método de separação
        return pokemon;

    }

    //---------------------------------- Ler Pokemon -------------------------------------------//

    public static Pokemon lerPokemon(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
          
            String line = br.readLine();
            while (line != null) {


                line = br.readLine();

                return criarPokemon(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Retorna null se o Pokémon com o ID especificado não for encontrado
        return null;
    }



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean parar = true;
        int i = 0;

        Pokemon pokemon[] = new Pokemon[1000];

        // Ler até encontrar "FIM"
        while (parar) {

            String entrada = sc.nextLine();

            if (isFim(entrada)) {
                parar = false;
            } else {
                pokemon[i] = lerPokemon(0);
                i++;
            }
        }

        sc.close();
    }
}