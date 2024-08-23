
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>
#include <stdbool.h>


#include <stdint.h>

typedef struct {
    uint64_t seed;
    } JavaRandom;

    // Função para inicializar a seed
    void setSeed(JavaRandom* JRandom, uint64_t seed) {
        JRandom->seed = (seed ^ 0x5DEECE66DLL) & ((1LL << 48) - 1);
    }

    // Função que imita o comportamento do next() do JRandom do Java
    int32_t next(JavaRandom* JRandom, int bits) {
        JRandom->seed = (JRandom->seed * 0x5DEECE66DLL + 0xBLL) & ((1LL << 48) - 1);
        return (int32_t)(JRandom->seed >> (48 - bits));
    }

    // Função que imita o comportamento do nextInt() do JRandom do Java
    int32_t nextInt(JavaRandom* JRandom) {
        return next(JRandom, 32);
}
JavaRandom JRandom;

bool isFim(char entrada[]) {
    return (strlen(entrada) == 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M');
}

void alteracaoAleatoria(char entrada[], char saida[], int n, char char1, char char2) {
    
    if (n < strlen(entrada)) {
        if (entrada[n] == char1) {
            saida[n] = char2;
        } else {
            saida[n] = entrada[n];
        }
        alteracaoAleatoria(entrada, saida, n + 1, char1, char2); 
    } else {
        saida[n] = '\0'; // Adiciona o caractere \o para finalizar a string
        printf("%s\n", saida);
    }
}

int main() {
    setlocale(LC_ALL, "pt_BR.UTF-8");  // Linux

    char palavra[1000];
    char saida[1000];
    bool parar = true;

    setSeed(&JRandom, 4); // Define a seed, por exemplo 4

    while (parar) {

            // Gerar caracteres aleatórios
            char char1 = (char) ('a' + abs(nextInt(&JRandom)%26));    
            char char2 = (char) ('a' + abs(nextInt(&JRandom)%26));

        scanf(" %[^\n]", palavra);
        if (isFim(palavra)) {
            parar = false; 
        }
        if(parar)
        alteracaoAleatoria(palavra, saida, 0, char1, char2);
    }

    return 0;
}