/**
 * LAB07 - Grid de Largada
 * @author  Davi Cândido de Almeida 
 * @version 2 09/10/2024
 */

#include "stdio.h"
#include "stdlib.h"
#include "stdbool.h"

void atualizaCorredores(int *array, int posNum, int posInsert){

    int tmp = array[posNum];
    // Desloca todos os corredores ultrapassados para à direita da posição de ultrapassagem ('posInsert')
    for(int i = posNum; i > posInsert; i--){
        array[i] = array[i-1];
    }
    array[posInsert] = tmp;
}

int main(){
    int n = 0;

    

    while (scanf("%d", &n) == 1) {
        
        int arrayInicial[n];
        int arrayFinal[n];

        for(int i = 0; i < n; i++) scanf("%d", &arrayInicial[i]);
        for(int i = 0; i < n; i++) scanf("%d", &arrayFinal[i]);

        int ultrapassagens = 0;

        // Algorítimo para verificar sempre os nº anteriores e encontra a posição do corredor e até onde ocorreu essa ultrapassagem
        for(int i = 1; i < n; i++){
            for (int j = i-1; j >= 0; j--) {
                if(arrayInicial[i] == arrayFinal[j]){

                    ultrapassagens += i-j; // Conta o nº de posições de diferença, ou seja de ultrapassagens

                    //Verificação de calculo, teste de algorítimo
                    // printf("%d=%d -> %d-%d = ", arrayInicial[i], arrayFinal[j], i, j); 
                    // printf("%d\n", ultrapassagens);

                    atualizaCorredores(arrayInicial, i, j); // Atualiza o array com a nova posição do corredor
                    i = j; //Volta para a posição de inserção
                }
            }
        }

        printf("%d\n", ultrapassagens);

    }

    return 0;
}

/*Analise de complexidade

    Comparações entre o array:
        - Pode-se perceber que o nº de comparações entre o array será sempre n-i-1
        - Ou seja um somatório que conterá o nº de elementos totais (n) menos o índice de números ja percorridos(i) - 1 (Devido a procura em i já começar em 1)

    Trocas entre o array:
        -O numero de trocas variará conforme o nº de utrapassagens, ou seja quanto
         mais ultrapassagens ocorrer mais trocas haverá, portanto nº de trocas
         acompanha o nº de ultrapassagens ocorridas, conforme indicada no array
*/