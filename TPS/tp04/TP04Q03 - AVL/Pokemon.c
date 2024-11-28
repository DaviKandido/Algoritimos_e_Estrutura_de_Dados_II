#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <math.h>
#include <sys/types.h>
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


// ------------------------------------  Struct No AVL -------------------------------------------- //

typedef struct No {
    Pokemon *pokemon;
    struct No *esq, *dir;
    int nivel;
}No;

No *set_No(Pokemon *pokemon, No *esq, No *dir, int nivel) {
    No *temp = (No*)malloc(sizeof(No));
    temp->pokemon = pokemon;
    temp->dir = dir;
    temp->esq = esq;
    temp->nivel = nivel;

    Movimentacoes+=3;
    return temp;
}

No *new_No(Pokemon *pokemon) {
    return set_No(pokemon, NULL, NULL, 1);
}

int getNivel(No *no){
    return (no == NULL) ? 0 : no->nivel;
}

void setNivel(No *no){
    if (no != NULL) {
        no->nivel = 1 + (getNivel(no->esq) > getNivel(no->dir) ? getNivel(no->esq) : getNivel(no->dir));
    }
}



// ------------------------------------ END - Struct No AVL -------------------------------------------- //


// ------------------------------------ Arvore AVL -------------------------------------------- //

    /**
    * Arvore AVL
    * @author Davi Cândido de almeida
    * @version 2 10/2024
    */


    No* raiz;


   void start(){
        raiz = NULL;
    }


    bool pesquisarRec(char* name, No* i){
        bool resp;
        if(i == NULL){
            resp = false;
        }else if(strcmp(name, i->pokemon->name) == 0){
            Comparacoes++;
            resp = true;
        }else if(strcmp(name, i->pokemon->name) < 0){
            Comparacoes++;
            printf("esq ");
            resp = pesquisarRec(name, i->esq);
        }else{
            Comparacoes+=2;
            printf("dir ");
            resp = pesquisarRec(name, i->dir);
        }

        return resp;
    }


    bool pesquisar(char* name){
        printf("%s\n",name);
        printf("raiz ");
        return pesquisarRec(name, raiz);
    }

    //-----Caminhar central========

    void caminharCentralRecRec(No* i) {
		if (i != NULL) {
			caminharCentralRecRec(i->esq); // Elementos da esquerda.
			printf("%s", i->pokemon->name); // Conteudo do no.
			caminharCentralRecRec(i->dir); // Elementos da direita.
		}
	}
    void caminharCentralRec(No *raiz) {
		printf("[ ");
		caminharCentralRecRec(raiz);
		printf("]\n");
	}
    void caminharCentral() {

		caminharCentralRec(raiz);

	}


    No* rotacionarDir(No* no){
        No* noEsq = no->esq;
        No* noEsqDir = noEsq->dir;

        noEsq->dir = no;
        no->esq = noEsqDir;
        setNivel(no);
        setNivel(noEsq);

        Movimentacoes+=4;
        return noEsq;
    }

    No* rotacionarEsq(No* no){
        No* noDir = no->dir;
        No* noDirEsq = noDir->esq;

        noDir->esq = no;
        no->dir = noDirEsq;
        setNivel(no);
        setNivel(noDir);

        Movimentacoes+=4;
        return noDir;
    }


    No* balancear(No* no){
        if(no != NULL){
            int fator = getNivel(no->dir) - getNivel(no->esq);
            //se balanceada
            if(fator >= -1 && fator <= 1){
                setNivel(no);
            }else if (fator == 2){
                int fatorFilhoDir = getNivel(no->dir->dir) - getNivel(no->dir->esq);
                // Se o filho a direita tambem estiver desbalanceado
				if (fatorFilhoDir == -1) {
					no->dir = rotacionarDir(no->dir);
				}
                no = rotacionarEsq(no);
            }else if (fator == -2){
                int fatorFilhoEsq = getNivel(no->esq->dir) - getNivel(no->esq->esq);
                // Se o filho a esquerda tambem estiver desbalanceado
                if(fatorFilhoEsq == 1){
                    no->esq = rotacionarEsq(no->esq);
                }
                no = rotacionarDir(no);
            }else{
                printf("Erro no No( %s ) com fator de balanceamento ( %d ) invalido!", no->pokemon->name, fator);
                exit(0);
            }
        }

        return no;
    }


    No* inserirRec(Pokemon *pokemon, No* i){
        if(i == NULL){
            i = new_No(pokemon);
        } else if( strcmp(pokemon->name, i->pokemon->name) < 0){
            i->esq = inserirRec(pokemon, i->esq);
        } else if( strcmp(pokemon->name, i->pokemon->name) > 0){
            i->dir = inserirRec(pokemon, i->dir);
        } else{
            printf("Erro ao insirir");
            exit(0);
        }

        return balancear(i);
    }


    void inserir(Pokemon *pokemon){
        raiz = inserirRec(pokemon, raiz);
    }


// ------------------------------------ END - Arvore AVL -------------------------------------------- //

int main(void) {

    clock_t startClock, endClock;
    double timeTotal;


    Pokemon* pokemons = lerTodoArquivo(FILE_PATH);

    start();

    char entrada[30];
    int id;

    startClock = clock();
    
    while (scanf("%s", entrada) && !isFim(entrada)) {
        sscanf(entrada, "%d", &id);

        inserir(procurar(pokemons, id));
    }




    while (scanf("%s", entrada) && !isFim(entrada)) {

        bool resp = pesquisar(entrada);

        printf("%s\n", resp ? "SIM" : "NAO");
    }


    endClock = clock();


    // Finaliza a contagem do tempo
    timeTotal = ((double)(endClock - startClock));

    GravarArquivoDeExecucao("857859_avl.txt", timeTotal);

    free(pokemons);

    return 0;
}