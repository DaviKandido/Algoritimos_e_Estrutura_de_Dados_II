#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>

#define MAX 100 
#define TAMANHO 801

#define FILE_PATH "/tmp/pokemon.csv"
//#define FILE_PATH "pokemon.csv"

// Estrutura de data
typedef struct {
    int dia;
    int mes;
    int ano;
} Date;

// Estrutura do Pokemon
typedef struct {
    int id;
    int generation;
    char name[MAX];
    char description[MAX];
    char type[2][MAX];
    char abilities[20][MAX];
    double weight;
    double height;
    int captureRate;
    bool isLegendary;  
    Date captureDate;
} Pokemon;


bool isFim(char* entrada){
    return (strlen(entrada) == 3 && entrada[0] == 'F' && entrada[1] && entrada[2]);
}

//Função para procurar um Pokémon com o di ID
Pokemon* procurar(Pokemon* pokemons, int id) {
    
    Pokemon *pokemon;
    
    for (int i = 0; i < TAMANHO; i++) {
        if (pokemons[i].id == id) {
            pokemon = &pokemons[i];
        }
    }
    
    return pokemon; 
}


Pokemon *procuraBinaria_id(Pokemon** pokemons, int id, int *conparacoes) {
    
    bool resp = false;
    int dir = 801-1, esq = 0, meio;
    while(esq <= dir){
        meio = (esq+dir) / 2;
        if(id == pokemons[meio]->id){
            resp = true;
            esq = 801;
            (*conparacoes)++;
        }else if(id > pokemons[meio]->id){
            esq = meio + 1;
            (*conparacoes)++;
        } else{
            dir = meio -1;
        }
    } 

    if(resp) return pokemons[meio];
}


Pokemon *procuraBinaria_name(Pokemon** pokemons, char* name, int *comparacoes) {
    
    int dir = 801 - 1, esq = 0, meio;

    while (esq <= dir) {
        meio = (esq + dir) / 2;

        int cmp = strcmp(name, pokemons[meio]->name);
        (*comparacoes)++;

        if (cmp == 0) {
            return pokemons[meio];
        } else if (cmp > 0) {

            esq = meio + 1;
        } else {

            dir = meio - 1;
        }
    }

    return NULL;
}

void removerOcorrencias(char *str, char caractere) {
    int i, j = 0;
    size_t comprimento = strlen(str);
    
    for (i = 0; i < comprimento; i++) { // Se o caractere atual não for o que deve ser removido
        
        if (str[i] != caractere) {
            str[j++] = str[i];
        }
    }
    str[j] = '\0'; // Adiciona o caractere nulo no final da nova string
}


// Função para extrair um atributo entre aspas ou até o próximo delimitador
char *extrairAtributo(char **str, char delimitador) {
    char *inicio = *str;
    char *fim = *str;

    if (*inicio == '"') {
        inicio++;
        fim = strchr(inicio, '"');
    } else {
        fim = strchr(inicio, delimitador);
    }

    if (fim != NULL) {
        *fim = '\0';
        *str = fim + 1;
    } else {
        *str = inicio + strlen(inicio);
    }

    return inicio;
}

// Função para ler o arquivo CSV e carregar os Pokémons
Pokemon* lerTodoArquivo(char fileName[]) {

    Pokemon* pokemons = (Pokemon*)malloc(TAMANHO * sizeof(Pokemon)); //Inicializa Pokemons

    if (!pokemons) {
        printf("Erro de alocação de memória.\n");
    }

    FILE* arquivo = fopen(fileName, "r");
    if (!arquivo) {
        printf("Erro ao abrir o arquivo.\n");
        free(pokemons);
    }

    char linha[500];
    fgets(linha, sizeof(linha), arquivo); // Pular o cabeçalho


    int index = 0;
    while (fgets(linha, sizeof(linha), arquivo) != NULL && index < TAMANHO ) { //TAMANHO no lugar de 1
        Pokemon *temp = &pokemons[index];
        char *token = linha;

        temp->id = atoi(extrairAtributo(&token, ','));
        temp->generation = atoi(extrairAtributo(&token, ','));
        strcpy(temp->name, extrairAtributo(&token, ','));
        strcpy(temp->description, extrairAtributo(&token, ','));
        strcpy(temp->type[0], extrairAtributo(&token, ','));
        strcpy(temp->type[1], extrairAtributo(&token, ','));

        // Processar habilidades (considerando que estão entre colchetes e aspas simples)
        char *habilidadesStr = extrairAtributo(&token, ','); 

        removerOcorrencias(habilidadesStr, '['); // remove [
        removerOcorrencias(habilidadesStr, ']'); // remove ]
        removerOcorrencias(habilidadesStr, '\''); // remove '

        //printf("%s\n", habilidadesStr);
        
        char *habilidadesToken = habilidadesStr;
        int habilidadeIndex = 0;

        while (*habilidadesToken != '\0' && habilidadeIndex < 10) {
            // Remover espaços em branco no início
            while (isspace(*habilidadesToken)) {
                habilidadesToken++;
            }

            // Verificar se a habilidade está entre aspas simples
            char *inicioHabilidade = habilidadesToken;
            char *fimHabilidade;
            if (*inicioHabilidade == '\'') {
                inicioHabilidade++; // Avança para depois da aspa simples
                fimHabilidade = strchr(inicioHabilidade, '\'');
            } else {
                fimHabilidade = strchr(inicioHabilidade, ','); 
            }

            if (fimHabilidade != NULL) {
                *fimHabilidade = '\0'; 
                strcpy(temp->abilities[habilidadeIndex], inicioHabilidade);
                habilidadeIndex++;
                habilidadesToken = fimHabilidade + 1;
            } else {
                // Última habilidade
                strcpy(temp->abilities[habilidadeIndex], inicioHabilidade);
                break;
            }
        }

        char* lixo = extrairAtributo(&token, ','); //Livra da string virgulas sobressalente em habilidades


        temp->weight = atof(extrairAtributo(&token, ','));
        temp->height = atof(extrairAtributo(&token, ','));


        
        temp->captureRate = atoi(extrairAtributo(&token, ','));


        int int_IsLegendary = atoi(extrairAtributo(&token, ','));

        if(int_IsLegendary == 0) temp->isLegendary = false; //verifica se é legendary
        else temp->isLegendary = true;


        sscanf(extrairAtributo(&token, '/'), "%d", &temp->captureDate.dia);

        sscanf(extrairAtributo(&token, '/'), "%d", &temp->captureDate.mes);

        sscanf(extrairAtributo(&token, '/'), "%d", &temp->captureDate.ano);

        index++;
    }

    fclose(arquivo);
    return pokemons;
}

// Função para imprimir informações de um Pokémon
void printarPokemon(Pokemon *pokemon) {
    printf("[#%d -> %s: %s - ", pokemon->id, pokemon->name, pokemon->description);

    // Imprime os tipos
    printf("['%s'", pokemon->type[0]);
    if (strlen(pokemon->type[1]) > 0) {
        printf(", '%s'", pokemon->type[1]);
    }
    printf("] - ");

    // Imprime as habilidades
    printf("[");
    for (int i = 0; strlen(pokemon->abilities[i]) > 0; i++) {
        printf("'%s'", pokemon->abilities[i]);
        if (strlen(pokemon->abilities[i + 1]) > 0) {
            printf(", ");
        }
    }
    printf("] - ");

    printf("%.1lfkg - %.1lfm - %d%% - %s - %d gen] - %02d/%02d/%d\n", 
           pokemon->weight, 
           pokemon->height, 
           pokemon->captureRate, 
           pokemon->isLegendary ? "true" : "false", 
           pokemon->generation,
           pokemon->captureDate.dia, 
           pokemon->captureDate.mes, 
           pokemon->captureDate.ano);
}
void GravarArquivoDeExecucao(const char *Filename, int Comparacoes, int movimentacoes, long timeTotal) {

    FILE *arquivo = fopen(Filename, "w+");

    if (arquivo == NULL) {
        printf("ERRO ao gerar o arquivo\n");
    } else {

        fprintf(arquivo, "857859\t%d\t%d\t%ldms", Comparacoes, movimentacoes, timeTotal);
        
        fclose(arquivo);
    }
}

void swap(Pokemon **pokemon, int i, int j) {
    Pokemon *tmp = pokemon[i];
    pokemon[i] = pokemon[j];
    pokemon[j] = tmp;
}

void OrdenarPokemonsQuickSort(Pokemon **pokemon, int esq, int dir, int *Comparacoes, int *Movimentacoes) {
    if (esq >= dir) return; // Caso base para evitar chamadas desnecessárias

    int i = esq, j = dir;
    int pivo = pokemon[(esq + dir) / 2]->generation;

    while (i <= j) {
        // Comparação com o pivô
        while (pokemon[i] != NULL && (pokemon[i]->generation < pivo || pokemon[i]->generation == pivo && strcmp(pokemon[i]->name, pokemon[(esq + dir) / 2]->name) < 0)) {
            (*Comparacoes)++;
            i++;
        }
        while (pokemon[j] != NULL && (pokemon[j]->generation > pivo || pokemon[j]->generation == pivo && strcmp(pokemon[j]->name, pokemon[(esq + dir) / 2]->name) > 0)) {
            (*Comparacoes)++;
            j--;
        }


        // Troca apenas se os índices forem válidos
        if (i <= j) {
            (*Movimentacoes) += 3;
            swap(pokemon, i, j);
            i++;
            j--;
        }
    }

    // Chamadas recursivas
    if (esq < j)
        OrdenarPokemonsQuickSort(pokemon, esq, j, Comparacoes, Movimentacoes);
    if (i < dir)
        OrdenarPokemonsQuickSort(pokemon, i, dir, Comparacoes, Movimentacoes);
}

int main(void) {
    clock_t start, end;
    double timeTotal;
    int Comparacoes = 0;
    int movimentacoes = 0;

    Pokemon* pokemons = lerTodoArquivo(FILE_PATH);
    
    if (!pokemons) {
        printf("Pokemons nao inicializados\n");
        return 1;
    }

    Pokemon *pokemonBuscados[200];
    char entrada[30];
    int id;
    int i = 0;

    while (scanf("%s", entrada) && !isFim(entrada)) {
        sscanf(entrada, "%d", &id);
        Pokemon *pokemonEncontrado = procurar(pokemons, id);

        if (pokemonEncontrado != NULL) {
            pokemonBuscados[i++] = pokemonEncontrado; 
        }
    }

    start = clock();

    int m = i;
    int j = 0;
    i = 0;

    for(i = 0; i < m; i++) {

        for(j = i + 1; j < m; j++) {

            Comparacoes++;

            if(strcmp(character_getHouse(&mainCharacters[i]), character_getHouse(&mainCharacters[j])) > 0) {

                Character temp = mainCharacters[i];
                mainCharacters[i] = mainCharacters[j];
                mainCharacters[j] = temp;
            }
            else if(strcmp(character_getHouse(&mainCharacters[i]), character_getHouse(&mainCharacters[j])) == 0) {

                Comparacoes++;

                if(strcmp(character_getName(&mainCharacters[i]), character_getName(&mainCharacters[j])) > 0) {

                    Character temp = mainCharacters[i];
                    mainCharacters[i] = mainCharacters[j];
                    mainCharacters[j] = temp;
                }
            }
        }
    }

    end = clock();

    for (int j = 0; j < i; j++) {
        printarPokemon(pokemonBuscados[j]);
    }

    // Finaliza a contagem do tempo
    timeTotal = ((double)(end - start));

    GravarArquivoDeExecucao("857859_shellsort.txt", Comparacoes, movimentacoes, timeTotal);
    
    free(pokemons);
    return 0;
}