#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>
#include <math.h>


#define MAX 100 
#define TAMANHO 801

#define FILE_PATH "/tmp/pokemon.csv"
//#define FILE_PATH "pokemon.csv"

int Comparacoes = 0;
int Movimentacoes = 0;

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
    return (strlen(entrada) == 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M');
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
void GravarArquivoDeExecucao(const char *Filename, long timeTotal) {

    FILE *arquivo = fopen(Filename, "w+");

    if (arquivo == NULL) {
        printf("ERRO ao gerar o arquivo\n");
    } else {

        fprintf(arquivo, "857859\t%d\t%d\t%ldms", Comparacoes, Movimentacoes, timeTotal);
        
        fclose(arquivo);
    }
}


// ------------------------------------  Célula Flexível -------------------------------------------- //

typedef struct Celula {
    Pokemon *pokemon;
    struct Celula *prox;
}Celula;

Celula *new_Celula(Pokemon *pokemon) {
    Celula *temp = (Celula*)malloc(sizeof(Celula));
    temp->pokemon = pokemon;
    temp->prox = NULL;
    return temp;
}

// ------------------------------------ END - Célula Flexível -------------------------------------------- //


// ------------------------------------  Fila Circular Flexível -------------------------------------------- //

    /**
    *  Fila Circular Flexível
    * @author Davi Cândido de almeida
    * @version 2 10/2024
    */

    #define MAXTAM    5


    typedef struct Fila{
        struct Celula *primeiro, *ultimo;
        int size;
    } Fila;

    Fila new_Fila(){
        Fila temp;
        temp.primeiro = temp.ultimo = new_Celula(NULL);
        temp.size = 0;
        return temp;
    }

    int size_lista(Fila *fila){
        return fila->size;
    }

    Pokemon *remover(Fila *fila){
        
        if(fila->primeiro == fila->ultimo){
            printf("\nA fila esta vazia!\n");
            return 0;
        }

        Celula *temp = fila->primeiro;
        fila->primeiro = fila->primeiro->prox;
        fila->size--;
        free(temp);
        return fila->primeiro->pokemon;
    }
    
    void inserir(Fila *fila, Pokemon *pokemon){

        if (fila->size > MAXTAM -1) {
            remover(fila);
        }

        fila->ultimo->prox = new_Celula(pokemon);
        fila->ultimo = fila->ultimo->prox;
        fila->size++;

        //Calculo de media após inserção
        Celula *i = fila->primeiro->prox;
        int soma = 0;
        int k = 0;

        while (i != NULL) {
            soma += i->pokemon->captureRate;
            k++;
            i = i->prox;
        }
        
        // Calcular e imprimir a média arredondada se houver elementos
        if(k > 0) {
            int mediaArredondada = (int)round((double)soma / k);
            printf("Média: %d\n", mediaArredondada);
        }
    }




    bool pesquisar_lista(Fila *lista, Pokemon *pokemon){
        Celula *i;
        for (i = lista->primeiro->prox; i != NULL; i = i->prox)
            if(i->pokemon == pokemon)
                return true;
        return false;
    }


    void print_Fila(Fila *f){
        Celula *i;
        int count = 0;
        for (i = f->primeiro->prox; i != NULL; i = i->prox)
        {
            
        printf("[%d] ", count++);
        printf("[#%d -> %s: %s - ", i->pokemon->id, i->pokemon->name, i->pokemon->description);

        // Imprime os tipos
        printf("['%s'", i->pokemon->type[0]);
        if (strlen(i->pokemon->type[1]) > 0) {
            printf(", '%s'", i->pokemon->type[1]);
        }
        printf("] - ");

        // Imprime as habilidades
        printf("[");
        for (int j = 0; strlen(i->pokemon->abilities[j]) > 0; j++) {
            printf("'%s'", i->pokemon->abilities[j]);
            if (strlen(i->pokemon->abilities[j + 1]) > 0) {
                printf(", ");
            }
        }
        printf("] - ");

        printf("%.1lfkg - %.1lfm - %d%% - %s - %d gen] - %02d/%02d/%d\n", 
            i->pokemon->weight, 
            i->pokemon->height, 
            i->pokemon->captureRate, 
            i->pokemon->isLegendary ? "true" : "false", 
            i->pokemon->generation,
            i->pokemon->captureDate.dia, 
            i->pokemon->captureDate.mes, 
            i->pokemon->captureDate.ano);
        }
    }

    void delete_lista(Fila *fila){
        while(fila->size > 0)
            remover(fila);

        free(fila->primeiro);

    }

// ------------------------------------ END - Fila Circular Flexível -------------------------------------------- //

int main(void) {

    clock_t startClock, endClock;
    double timeTotal;


    Pokemon* pokemons = lerTodoArquivo(FILE_PATH);

    Fila filaPokemons = new_Fila();

    char entrada[30];
    int id;
    while (scanf("%s", entrada) && !isFim(entrada)) {
        sscanf(entrada, "%d", &id);

        inserir( &filaPokemons, procurar(pokemons, id));

    }


    startClock = clock();

    int numOp = 0;

    Pokemon *pokeExcluido;
    int k = 0;

    scanf("%d", &numOp);

        for(int j = 0; j < numOp; j++){

            char* op = (char*) malloc(sizeof(char) * 3);
            scanf("%s", op);

            Comparacoes++;
            
            if(strcmp(op, "I") == 0){ 

                int num;
                scanf("%d", &num);
                Movimentacoes++;
                inserir(&filaPokemons, procurar(pokemons, num));

            }else Comparacoes++; if(strcmp(op, "R") == 0){

                Movimentacoes++;
                pokeExcluido = remover(&filaPokemons);

                printf("(R) %s\n", pokeExcluido->name);

            }

            free(op);
        }

    printf("\n");

    print_Fila(&filaPokemons);


    endClock = clock();



    // Finaliza a contagem do tempo
    timeTotal = ((double)(endClock - startClock));

    GravarArquivoDeExecucao("857859_FilaCircular.txt", timeTotal);

    free(pokemons);

    return 0;
}