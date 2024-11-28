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


 
 public class PokemonArvoreAlvinegra {
    

    //Grava arquivo com parâmetros de Execução
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
        
        GerenciaPokemon gp = new GerenciaPokemon();

        gp.lerPokemon();

        ArvoreAlvinegra arvorePokemons = new ArvoreAlvinegra();


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

 // ------------------------------------  No -------------------------------------------- //

class NoAN {

    public boolean cor;
    public Pokemon pokemon;
    public NoAN esq, dir;

    public NoAN(Pokemon pokemon){
        this(pokemon, false, null, null);
    }

    public NoAN(Pokemon pokemon, boolean cor){
        this(pokemon, cor, null, null);
    }

    public NoAN(Pokemon pokemon, boolean cor, NoAN esq, NoAN dir){
        this.cor = cor;
        this.pokemon = pokemon;
        this.esq = esq;
        this.dir = dir;
    }
    
}
// ------------------------------------ END - No -------------------------------------------- //


 // ------------------------------------  Class Arvore  -------------------------------------------- //

class ArvoreAlvinegra{
    private NoAN raiz;

    public ArvoreAlvinegra(){
        raiz = null;
    }

    public boolean pesquisar(String name){
        System.out.println(name);
        System.out.print("raiz ");
        return pesquisar(name, raiz);
    }

    private boolean pesquisar(String name, NoAN i){
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


    public void caminharPre() {
		caminharPre(raiz);
	}

    private void caminharPre(NoAN i){
        if (i != null) {
			System.out.println(i.pokemon.getName());
			caminharPre(i.esq);
			caminharPre(i.dir);
            
		}
    }


    public void inserir(Pokemon pokemon) throws Exception{
        if( raiz == null){
            raiz = new NoAN(pokemon);
        }else if(raiz.esq == null && raiz.dir == null){
            if(pokemon.getName().compareTo(raiz.pokemon.getName()) < 0){
                VariaveisGlobais.comparacoes++;
                raiz.esq = new NoAN(pokemon);
            }else{
                VariaveisGlobais.comparacoes+=2;
                raiz.dir = new NoAN(pokemon);
            }
        }else if(raiz.esq == null){
            if(pokemon.getName().compareTo(raiz.pokemon.getName()) < 0){
                VariaveisGlobais.comparacoes++;
                raiz.esq = new NoAN(pokemon);
            }else if(pokemon.getName().compareTo(raiz.dir.pokemon.getName()) < 0){
                VariaveisGlobais.comparacoes++;
                raiz.esq = new NoAN(raiz.pokemon);
                raiz.pokemon = pokemon;
                VariaveisGlobais.movimentacoes++;
            }else{
                VariaveisGlobais.comparacoes+=2;
                raiz.esq = new NoAN(raiz.pokemon);
                raiz.pokemon = raiz.dir.pokemon;
                raiz.dir.pokemon = pokemon;
                VariaveisGlobais.movimentacoes+=2;
            }
            raiz.esq.cor = raiz.dir.cor = false;
        }else if(raiz.dir == null){
            if(pokemon.getName().compareTo(raiz.pokemon.getName()) > 0){
                VariaveisGlobais.comparacoes++;
                raiz.dir = new NoAN(pokemon);
            }else if(pokemon.getName().compareTo(raiz.esq.pokemon.getName()) > 0){
                VariaveisGlobais.comparacoes++;
                raiz.dir = new NoAN(raiz.pokemon);
                raiz.pokemon = pokemon;
                VariaveisGlobais.movimentacoes++;
            }else{
                VariaveisGlobais.comparacoes+=2;
                raiz.dir = new NoAN(raiz.pokemon);
                raiz.pokemon = raiz.esq.pokemon;
                raiz.esq.pokemon = pokemon;
                VariaveisGlobais.movimentacoes+=2;
            }
            raiz.esq.cor = raiz.dir.cor = false;
    
        // Senao, a arvore tem tres ou mais elementos
        }else{
            inserir(pokemon, null, null, null, raiz);
        }
        raiz.cor = false;
        }



        private void inserir(Pokemon pokemon, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
            if (i == null) {
               if (pokemon.getName().compareTo(pai.pokemon.getName()) < 0) {
                  i = pai.esq = new NoAN(pokemon, true);
               } else {
                  i = pai.dir = new NoAN(pokemon, true);
               }
               if (pai.cor == true) {
                  balancear(bisavo, avo, pai, i);
               }
            } else {
               // Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
               if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
                  i.cor = true;
                  i.esq.cor = i.dir.cor = false;
                  if (i == raiz) {
                     i.cor = false;
                  } else if (pai.cor == true) {
                     balancear(bisavo, avo, pai, i);
                  }
               }
               if (pokemon.getName().compareTo(i.pokemon.getName()) < 0) {
                  inserir(pokemon, avo, pai, i, i.esq);
               } else if (pokemon.getName().compareTo(i.pokemon.getName()) > 0) {
                  inserir(pokemon, avo, pai, i, i.dir);
               } else {
                  throw new Exception("Erro inserir (Pokemon repetido)!");
               }
            }
         }




        private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
            // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
            if (pai.cor == true) {
               // 4 tipos de reequilibrios e acoplamento
               if (pai.pokemon.getName().compareTo(avo.pokemon.getName()) > 0) { // rotacao a esquerda ou direita-esquerda
                  if (i.pokemon.getName().compareTo(pai.pokemon.getName()) > 0) {
                     avo = rotacaoEsq(avo);
                  } else {
                     avo = rotacaoDirEsq(avo);
                  }
               } else { // rotacao a direita ou esquerda-direita
                  if (i.pokemon.getName().compareTo(pai.pokemon.getName()) < 0) {
                     avo = rotacaoDir(avo);
                  } else {
                     avo = rotacaoEsqDir(avo);
                  }
               }
               if (bisavo == null) {
                  raiz = avo;
               } else if (avo.pokemon.getName().compareTo(bisavo.pokemon.getName()) < 0) {
                  bisavo.esq = avo;
               } else {
                  bisavo.dir = avo;
               }
               // reestabelecer as cores apos a rotacao
               avo.cor = false;
               avo.esq.cor = avo.dir.cor = true;

            } // if(pai.cor == true)
         }


         private NoAN rotacaoDir(NoAN no) {
            NoAN noEsq = no.esq;
            NoAN noEsqDir = noEsq.dir;
      
            noEsq.dir = no;
            no.esq = noEsqDir;

            VariaveisGlobais.movimentacoes+=4;
            return noEsq;
         }
      
         private NoAN rotacaoEsq(NoAN no) {
            NoAN noDir = no.dir;
            NoAN noDirEsq = noDir.esq;
      
            noDir.esq = no;
            no.dir = noDirEsq;

            VariaveisGlobais.movimentacoes+=4;
            return noDir;
         }
      
         private NoAN rotacaoDirEsq(NoAN no) {
            no.dir = rotacaoDir(no.dir);
            return rotacaoEsq(no);
         }
      
         private NoAN rotacaoEsqDir(NoAN no) {
            no.esq = rotacaoEsq(no.esq);
            return rotacaoDir(no);
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





