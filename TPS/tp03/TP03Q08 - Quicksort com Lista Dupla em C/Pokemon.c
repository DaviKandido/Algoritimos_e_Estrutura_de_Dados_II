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


// ------------------------------------  Célula Dupla Flexível -------------------------------------------- //

typedef struct CelulaDupla {
    Pokemon *pokemon;
    struct CelulaDupla *prox, *ant;
}CelulaDupla;

CelulaDupla *new_Celula_Dupla(Pokemon *pokemon) {
    CelulaDupla *temp = (CelulaDupla*)malloc(sizeof(CelulaDupla));
    temp->pokemon = pokemon;
    temp->ant = NULL;
    temp->prox = NULL;
    return temp;
}

// ------------------------------------ END - Célula Dupla Flexível -------------------------------------------- //


// ------------------------------------ Lista Dupla Flexível -------------------------------------------- //

    /**
    * Lista Flexivel
    * @author Davi Cândido de almeida
    * @version 2 10/2024
    */


    typedef struct ListaDupla{
        struct CelulaDupla *primeiro, *ultimo;
        int size;
    }ListaDupla;

    ListaDupla* new_Lista_Dupla(){
        ListaDupla *temp = (ListaDupla*) malloc(sizeof(ListaDupla));
        temp->primeiro = temp->ultimo = new_Celula_Dupla(NULL);
        temp->size = 0;
        return temp;
    }


    int size_lista(ListaDupla *lista){
        return lista->size;
    }

    void inserirInicio(ListaDupla *lista, Pokemon *pokemon){
        CelulaDupla *temp = new_Celula_Dupla(pokemon);
        temp->prox = lista->primeiro;

        lista->primeiro->pokemon = pokemon;
        lista->primeiro->ant = temp; 
        lista->primeiro = temp;
        lista->size++;
    }

    void inserirFim(ListaDupla *lista, Pokemon *pokemon){
        lista->ultimo->prox = new_Celula_Dupla(pokemon);
        lista->ultimo->prox->ant = lista->ultimo;
        lista->ultimo = lista->ultimo->prox;
        lista->size++;
    }

    void inserir(ListaDupla *lista, Pokemon *pokemon, int pos){
        if(pos < 0 || pos > lista->size)
            printf("Erro ao tentar inserir na posicao (%d/ tamanho = %d) invalida!", pos, lista->size);
        else if (pos == 0)
            inserirInicio(lista, pokemon);
        else if (pos == lista->size)
            inserirFim(lista, pokemon);
        else{
        
        CelulaDupla *ant = lista->primeiro;
        for(int i = 0; i < pos; i++){
            ant = ant->prox;
        }

        CelulaDupla *temp = new_Celula_Dupla(pokemon);
        temp->prox = ant->prox;
        temp->prox->ant = temp;
        temp->ant = ant;
        ant->prox = temp;
        lista->size++;
        }

    }

    Pokemon* removerFim(ListaDupla *lista){

        if(lista->primeiro == lista->ultimo){
            printf("\nA lista esta vazia!\n");
            return 0;
        }

        CelulaDupla *temp = lista->ultimo;
        Pokemon *pokemon = temp->pokemon;

        lista->ultimo = lista->ultimo->ant;
        lista->ultimo->prox = NULL;
        lista->size--;

        free(temp);

        return pokemon;
    }

    Pokemon *remover(ListaDupla *lista, int pos){

        if(lista->primeiro == lista->ultimo){
            printf("\nA lista esta vazia!\n");
            return 0;
        }else if(pos < 0 || pos > lista->size-1)
            printf("Erro ao tentar remover item da posicao (%d/ tamanho = %d) invalida!", pos, lista->size);
        else if(pos == lista->size-1)
            removerFim(lista);
        else{

            CelulaDupla *ant = lista->primeiro;
            for(int i = 0; i < pos; i++)
                ant = ant->prox;

            CelulaDupla *temp = ant->prox;
            Pokemon *pokemon = temp->pokemon;
            ant->prox = temp->prox;
            free(temp);

            if(pos == lista->size-1)    
                lista->ultimo = ant;
            lista->size--;

            return pokemon;
        }

    }


    Pokemon* removerInicio(ListaDupla *lista){
        return remover(lista, 0);
    }


    bool pesquisar_lista(ListaDupla *lista, Pokemon *pokemon){
        CelulaDupla *i;
        for (i = lista->primeiro->prox; i != NULL; i = i->prox)
            if(i->pokemon == pokemon)
                return true;
        return false;
    }


    void print_lista(ListaDupla *l){
        CelulaDupla *i;
        int count = 0;
        for (i = l->primeiro->prox; i != NULL; i = i->prox)
        {
            
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

    void delete_lista(ListaDupla *lista){
        while(lista->size > 0)
            remover(lista,0);

        free(lista->primeiro);

    }


// Pokemon* procurar_Lista(ListaDupla *lista, int id) {

//     Pokemon *pokemon;
//     CelulaDupla *i;
//     int j = 0;
//     for (i = lista->primeiro->prox; j < id; i = i->prox, j++) {
//             pokemon = i->pokemon;
//     }
    
//     return pokemon; 
// }


CelulaDupla* obter_pivo(ListaDupla *lista, int pos) {
    CelulaDupla *atual = lista->primeiro->prox;
    for (int i = 0; i < pos && atual != NULL; i++) {
        atual = atual->prox;
    }
    return atual; // Retorna a célula do pivô
}


// ------------------------------------ END - Lista Dupla Flexível -------------------------------------------- //

// ------------------------------------ Quicksort Adaptado para TADS -------------------------------------------- //

void swap(ListaDupla *lista, CelulaDupla *ci, CelulaDupla *cj) {
    Pokemon* tmp = ci->pokemon;
    ci->pokemon = cj->pokemon;
    cj->pokemon = tmp;
}

void OrdenarPokemonsQuickSort(ListaDupla *lista, int esq, int dir, CelulaDupla *cEsq, CelulaDupla *cDir) {

    CelulaDupla *ci = cEsq, *cj = cDir;
    int i = esq, j = dir;

    CelulaDupla *pivo = obter_pivo(lista, (esq + dir) / 2);


    while (i <= j) {
        // Comparação com o pivô
        while ((ci->pokemon->generation < pivo->pokemon->generation || (ci->pokemon->generation == pivo->pokemon->generation && strcmp(ci->pokemon->name, pivo->pokemon->name) < 0))) {
            Comparacoes++;
            i++;
            if (ci->prox != NULL) ci = ci->prox;
        }
        while ( (cj->pokemon->generation > pivo->pokemon->generation || (cj->pokemon->generation == pivo->pokemon->generation && strcmp(cj->pokemon->name, pivo->pokemon->name) > 0))) {
            Comparacoes++;
            j--;
            if (cj->ant != NULL) cj = cj->ant;
        }

        if (i <= j) {
            Movimentacoes += 3;
            swap(lista, ci, cj);
            if (ci->prox != NULL) ci = ci->prox;
            i++;
            if (cj->ant != NULL) cj = cj->ant;
            j--;
        }
     }

    // //Chamadas recursivas
    if (esq < j)
        OrdenarPokemonsQuickSort(lista, esq, j, cEsq, cj);
    if (i < dir)
        OrdenarPokemonsQuickSort(lista, i, dir, ci, cDir);
 }


// ---------------------------------- END - Quicksort Adaptado para TADS ------------------------------------------ //


int main(void) {

    clock_t startClock, endClock;
    double timeTotal;


    Pokemon* pokemons = lerTodoArquivo(FILE_PATH);

    ListaDupla *listaPokemons = new_Lista_Dupla();

    char entrada[30];
    int id;
    
    while (scanf("%s", entrada) && !isFim(entrada)) {
        sscanf(entrada, "%d", &id);

        inserirFim( listaPokemons, procurar(pokemons, id));
    }

    startClock = clock();

    // printarPokemon(listaPokemons.primeiro->prox->pokemon);

    OrdenarPokemonsQuickSort(listaPokemons, 0, listaPokemons->size-1, listaPokemons->primeiro->prox, listaPokemons->ultimo); // 


    endClock = clock();

    print_lista(listaPokemons);


    // Finaliza a contagem do tempo
    timeTotal = ((double)(endClock - startClock));

    GravarArquivoDeExecucao("857859_quicksort2.txt", timeTotal);

    free(pokemons);
    delete_lista(listaPokemons);

    return 0;
}