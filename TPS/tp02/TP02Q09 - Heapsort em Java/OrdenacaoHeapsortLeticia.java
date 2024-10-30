import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.text.ParseException; 


//classe principal
public class OrdenacaoHeapsortLeticia
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
   
           String entrada = new String();
   
           List<Pokemon> pokemons = LerArquivo.lerTodoArquivo("/tmp/pokemon.csv");
           //List<Pokemon> pokemons = LerArquivo.lerTodoArquivo("pokemon.csv");
           List<Pokemon> selecionados = new ArrayList<>();
   
           
   while (!(entrada = sc.nextLine()).equals("FIM")) 
   {
       try 
       {
           // Converter a entrada para um número inteiro
           int pokemonID = Integer.parseInt(entrada);
   
           // Procurar o Pokémon na lista e adicionar aos selecionados
           Pokemon encontrado = ProcurarPokemon.procurar(pokemons, pokemonID);
   
           if (encontrado != null) 
           {
               selecionados.add(encontrado);
           } 
          
       } 
       catch (NumberFormatException e) 
       {
           e.printStackTrace();
       }
   }
   
    
           Ordenacao ordenacao = new Ordenacao();
   
           long startTime = System.nanoTime(); // Início da contagem do tempo
           // insercao os Pokémon
           ordenacao.heapsort(selecionados);
           long endTime = System.nanoTime(); // Fim da contagem do tempo
   
           long tempoExecucao = endTime - startTime;
   
           // Contadores de comparações e movimentações
           int numComparacoes = ordenacao.getNumComparacoes();
           int numMovimentacoes = ordenacao.getNumMovimentacoes();
   
           // Mostrar os Pokémon selecionados
           for (int i = 1; i < selecionados.size(); i++) {
               Pokemon.printar(selecionados.get(i));
           }
   
           // Criar o arquivo de log
           String matricula = "840757"; 
           String logFileName = "840757_selecao.txt";
   
           try (FileWriter writer = new FileWriter(logFileName)) {
               writer.write(matricula + "\t" + numComparacoes + "\t" + numMovimentacoes + "\t" + tempoExecucao);
              
           } catch (IOException e) {
               System.out.println("Erro ao escrever o arquivo de log: " + e.getMessage());
           }
   
           sc.close();
       }
    }


//classe para insercao os pokemons
class Ordenacao
{
    private int numComparacoes = 0;
    private int numMovimentacoes = 0;



    void construir(List<Pokemon> pokemons, int tam)
    {

        for (int i = tam; i > 1 && pokemons.get(i).getHeight() > pokemons.get(i/2).getHeight() || (pokemons.get(i).getHeight() == pokemons.get(i/2).getHeight() && 
        pokemons.get(i).getName().compareTo(pokemons.get(i/2).getName()) > 0); i /= 2){
            numComparacoes++;
            Pokemon temp = pokemons.get(i);
            pokemons.set(i, pokemons.get(i/2));
            pokemons.set(i/2, temp);
            numMovimentacoes+=3;
        }
        }

        
    int getMaiorFilho(List<Pokemon> pokemons, int i, int tam){
        int filho;
        numComparacoes++;
        if (2*i == tam || pokemons.get(2*i).getHeight() > pokemons.get(2*i+ 1).getHeight() || (pokemons.get(2*i).getHeight() == pokemons.get(2*i+ 1).getHeight() && 
        pokemons.get(2*i).getName().compareTo(pokemons.get(2*i+ 1).getName()) > 0)) {
        filho = 2*i;
        } else {
        filho = 2*i + 1;
        }
        return filho;
        }
       
    boolean hasFilho(int i, int tam) {
        return (i <= (tam/2));
    }
    
    void reconstruir(List<Pokemon> pokemons, int tam){
        int i = 1;
        while (hasFilho(i, tam) == true){
        int filho = getMaiorFilho(pokemons, i, tam);
        if (pokemons.get(i).getHeight() < pokemons.get(filho).getHeight() || (pokemons.get(i).getHeight() == pokemons.get(filho).getHeight() && 
        pokemons.get(i).getName().compareTo(pokemons.get(filho).getName()) < 0) )
        {
            numComparacoes++;
            Pokemon temp = pokemons.get(i);
            pokemons.set(i, pokemons.get(filho));
            pokemons.set(filho, temp);
            numMovimentacoes+=3;
        i = filho;
        } else {
        i = tam;
        }
        }
       } 
       
    
    public void heapsort(List<Pokemon> pokemons)  
    {
        int n = pokemons.size() - 1;
        //Contrução do heap
        for (int tam = 2; tam <= n; tam++){
        construir(pokemons, tam);
        }
        //Ordenacao propriamente dita
        int tam = n;
        while (tam > 1){
      
            Pokemon temp = pokemons.get(1);
            pokemons.set(1, pokemons.get(tam));
            pokemons.set(tam--, temp);
            numMovimentacoes+=3;
        reconstruir(pokemons, tam);
        }
        }
       
    
    
    
    
     public int getNumComparacoes() {
        return numComparacoes;
    }

    public int getNumMovimentacoes() {
        return numMovimentacoes;
    }
}
//classe do Pokemon 
class Pokemon 
{
    //definir dados
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
    
    //iniciar construtor
    public Pokemon () 
    {
     id = 0;
     generation = 0;
     name = " ";
     description = " ";
      types = new ArrayList<>();
      abilities = new ArrayList<>();
      weight = 0;
      height = 0;
     captureRate = 0;
      isLegendary = false;  
      captureDate = new Date();

    }

    //construtor para inserir dados
    public Pokemon (int id, int generation, String name, String description, ArrayList<String> types, 
    ArrayList<String> abilities, double weight, double height, int captureRate, 
    boolean isLegendary, Date captureDate)
    {
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

    public Pokemon (String[] partes)
    {
        

        for(int i = 0; i < partes.length; i++)
        {
            if(partes[i].isEmpty())
            {
                partes[i] = "0";
            }
        }
        this.id = Integer.parseInt(partes[0]);

        this.generation = Integer.parseInt(partes[1]);
        
        this.name = partes[2];

        this.description = partes[3];

        this.types = new ArrayList<>();
        String tmp = "'";
        tmp += partes[4];
        tmp += "'";
        this.types.add(tmp);
        if(partes[5] != "0")
        {
            tmp = "'";
            tmp += partes[5];
            tmp += "'";
            this.types.add(tmp);
        }

         //arrumar formato da string
         String temp = " ";
         for(int i = 0; i < partes[6].length(); i++)
         {
            char c = partes[6].charAt(i);
            if(c != '\"' && c != '[' && c != ']')
            {
                temp += c;
            }
          
         }

         String[] tmp1 = temp.split(",");
         this.abilities = new ArrayList<>();

         for (int i = 0; i < tmp1.length; i++) 
         {
            abilities.add(tmp1[i].trim());
        }

        this.weight = Double.parseDouble(partes[7]);
        this.height = Double.parseDouble(partes[8]);

        this.captureRate = Integer.parseInt(partes[9]);

        if (partes[10].equals("1")) 
        {

            this.isLegendary = true;
        }
        else
        {
            this.isLegendary = false;
        }

        SimpleDateFormat formatodaData = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Verifica se partes[11] não é nulo ou vazio
            if (partes[11] != null && !partes[11].isEmpty()) {
                this.captureDate = formatodaData.parse(partes[11]);
            } else {
                this.captureDate = null; 
            }
        } catch (ParseException e) {
            // Lida com a exceção, como imprimir uma mensagem de erro ou definir uma data padrão
            System.err.println("Erro ao analisar a data: " + e.getMessage());
            this.captureDate = null; // ou outra lógica que você queira
        }

    }
      //Métodos Get e Set
      public int getId() { return id; }
      public void setId(int id) { this.id = id; }
  
      public int getGeneration() { return generation; }
      public void setGeneration(int generation) { this.generation = generation; }
  
      public String getName() { return name; }
      public void setName(String name) { this.name = name; }
  
      public String getDescription() { return description; }
      public void setDescription(String description) { this.description = description; }
  
      public ArrayList<String> getTypes() { return types; }
      public void setTypes(ArrayList<String> types) { this.types = types; }
  
      public ArrayList<String> getAbilities() { return abilities; }
      public void setAbilities(ArrayList<String> abilities) { this.abilities = abilities; }
  
      public double getWeight() { return weight; }
      public void setWeight(double weight) { this.weight = weight; }
  
      public double getHeight() { return height; }
      public void setHeight(double height) { this.height = height; }
  
      public int getCaptureRate() { return captureRate; }
      public void setCaptureRate(int captureRate) { this.captureRate = captureRate; }
  
      public boolean getIsLegendary() { return isLegendary; }
      public void setIsLegendary(boolean isLegendary) { this.isLegendary = isLegendary; }
  
      public Date getCaptureDate() { return captureDate; }
      public void setCaptureDate(Date captureDate) { this.captureDate = captureDate; }

       //metodo clone
       // Construtor de cópia
    public Pokemon(Pokemon original) 
    {
        this.id = original.id;
        this.generation = original.generation;
        this.name = original.name;
        this.description = original.description;
        
        
        this.types = new ArrayList<>(original.types);
        this.abilities = new ArrayList<>(original.abilities);
        
        this.weight = original.weight;
        this.height = original.height;
        this.captureRate = original.captureRate;
        this.isLegendary = original.isLegendary;
        
        
        this.captureDate = (Date) original.captureDate.clone();
    }
     // Cria e retorna um novo objeto Pokemon baseado no objeto atual (this)
      public Pokemon clonar() {
       
        return new Pokemon(this); 
    }

    public static void printar(Pokemon pokemon)
    {
        //definir formato da saida da data
        SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");

        String data = " ";

        //verificar se é válida
        if(pokemon.captureDate != null)
        {
            data = formatoSaida.format(pokemon.captureDate);
        }
        else
        {
            data = "ERRO na data";
        }

        //printar dados
        System.out.println( "[#" + pokemon.id + " -> " + pokemon.name + ": " + pokemon.description + " - " + pokemon.types + " - " + pokemon.abilities + 
        " - " + pokemon.weight + "kg - " + pokemon.height + "m - " + pokemon.captureRate + "% - " + pokemon.isLegendary + " - " + pokemon.generation + 
        " gen] - " + data);
    }

}

class ProcurarPokemon 
{
    // Função estática que busca um Pokémon pelo ID
    public static Pokemon procurar(List<Pokemon> pokemons, int id)
    {
        Pokemon foundPokemon = null;

            for (int i = 0; i < pokemons.size(); i++) 
            { 
                //pegar o pokemon da lista na posicao i
                Pokemon pokemon = pokemons.get(i); 
                if (pokemon.getId() == id) 
                { // Verifica se o ID do Pokémon é igual ao ID procurado
                    foundPokemon =  pokemon; // Retorna o Pokémon encontrado
                }
            }
            return foundPokemon; // Retorna null se o Pokémon não for encontrado
    }
}
//classe para ler o arquivo do Pokemon

class LerArquivo 
{
    // Criar uma lista de objetos com todos os pokemons
    public static List<Pokemon> lerTodoArquivo(final String fileName) 
    {
        // Criar a lista de pokemons
        List<Pokemon> pokemons = new ArrayList<>();

        try 
        {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Pular o cabeçalho
            br.readLine();

            // Ler linha por linha
            String Line;
            while ((Line = br.readLine()) != null) 
            {
                // Trocar vírgula por espaço
                Line = trocarVirgula(Line);

                // Criar o objeto Pokemon com os dados da linha
                Pokemon pokemon = new Pokemon(Line.split(";"));
                pokemons.add(pokemon);
            }

            br.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            pokemons = null;
        }

        return pokemons;
    }

    // Trocar a vírgula para outro caractere, pois existem vírgulas em outros lugares e não somente para dividir
    private static String trocarVirgula(String line) 
    {
        // Substituir para char[] para ser possível a manipulação
        char[] array = line.toCharArray();

        // Verificar se está com aspas
        boolean aspas = false;

        // Percorrer todo o arranjo
        for (int i = 0; i < array.length; i++) 
        {
            // Se as aspas forem falsas e for uma vírgula, trocar por espaço
            if (!aspas && array[i] == ',') 
            {
                array[i] = ';';
            } 
            // Abrir e fechar aspas
            else if (array[i] == '"') 
            {
                aspas = !aspas;
            }
        }

        // Converter novamente para String
        return new String(array);
    }
}