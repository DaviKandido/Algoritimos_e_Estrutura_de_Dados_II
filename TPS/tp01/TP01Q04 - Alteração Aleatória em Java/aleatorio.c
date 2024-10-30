
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>
#include <stdbool.h>

int ContaChars(char string[]) {
    int i = 0;
    while (string[i] != '\0') {
        i++;
    }
    return i;
}


bool isFim(char entrada[]) {
    return (ContaChars(entrada) == 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M');
}

void alteracaoAleatoria(char entrada[], char saida[], int n, char char1, char char2) {
    
    // Gerar caracteres aleatórios
    if (n < ContaChars(entrada)) {
        if (entrada[n] == char1) {
            saida[n] = char2;
        } else {
            saida[n] = entrada[n];
        }
        alteracaoAleatoria(entrada, saida, n + 1, char1, char2); 
    } else {
        saida[n] = '\0'; // Adiciona o caractere \o para finalizar a string
        //printf("%s\n", saida);
    }
}

int main() {
    setlocale(LC_ALL, "pt_BR.UTF-8");  // Linux

    char palavra[1000];
    char saida[1000];
    bool parar = true;
    srand(4); 

    while (parar) {
        char char1 = ('a' + rand() % 26);
        printf("char1: %c\n", char1);

        char char2;
        do {
            char2 = ('a' + rand() % 26);
        } while (char2 == char1);
        printf("char2: %c\n", char2);

        scanf(" %[^\n]", palavra);
        if (isFim(palavra)) {
            parar = false; 
        }
        if(parar)
            alteracaoAleatoria(palavra, saida, 0, char1, char2);
    }

    return 0;
}