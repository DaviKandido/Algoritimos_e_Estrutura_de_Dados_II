public class Tp3Q1 {
    public static void main(String[] args) {
        GerenciadorPokemons gp = new GerenciadorPokemons();
        gp.lerPokemonsDeArquivo();
        Pokemon[] pokemons = gp.getPokemons();

        //FAÇA O SEU EXERCICIO
        Lista lista = new Lista();
    }
}

class Lista {
    Pokemon[] array;
    int n;
    //IMPLEMENTAR
}

class Pokemon {
    //atributos, getters, setters, clone, etc
}

class GerenciadorPokemons {
    private Pokemon[] pokemons = new Pokemon[801];

    public void lerPokemonsDeArquivo() {
        //Ler o arquivo
        //Para cada linha do arquivo
            //criar o pokemon e guardar no array
    }

    public Pokemon[] getPokemons() {
        return pokemons;
    }
}