
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>
#include <stdbool.h>


bool isFim(char entrada[]) {
    return (strlen(entrada) == 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M');
}

void alteracaoAleatoria(char entrada[], char saida[], int n) {
    
    srand(4); 
    // Gerar caracteres aleatórios
    char char1 = ('a' + rand() % 26);
    char char2 = ('a' + rand() % 26);

    if (n < strlen(entrada)) {
        if (entrada[n] == char1) {
            saida[n] = char2;
        } else {
            saida[n] = entrada[n];
        }
        alteracaoAleatoria(entrada, saida, n + 1); 
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

    while (parar) {
        scanf(" %[^\n]", palavra);
        if (isFim(palavra)) {
            parar = false; 
        }
        if(parar)
        alteracaoAleatoria(palavra, saida, 0);
    }

    return 0;
}