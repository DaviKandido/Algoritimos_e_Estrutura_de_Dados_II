#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

typedef struct Assasino {
    char nome[50];
    int assasinados;
    bool morreu;
} Assasino;

// Função para imprimir o resultado final
void printFinal(Assasino assasino[], int n) {
    printf("HALL OF MURDERERS\n");

    // Ordenar os assassinos em ordem lexicográfica
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (strcmp(assasino[i].nome, assasino[j].nome) > 0) {
                Assasino temp = assasino[i];
                assasino[i] = assasino[j];
                assasino[j] = temp;
            }
        }
    }

    // Imprimir apenas os assassinos vivos
    for (int i = 0; i < n; i++) {
        if (!assasino[i].morreu) {
            printf("%s %d\n", assasino[i].nome, assasino[i].assasinados);
        }
    }
}

int main() {
    Assasino assasino[50]; // Garantir espaço suficiente
    int n = 0;
    int m = 0;

    char nome[50], mortos[50][50];

    while (scanf(" %s %s", nome, mortos[m++]) == 2) {

        // Verificar se o assassino já está na lista
        int achou = -1;
        for (int i = 0; i < n; i++) {
            if (strcmp(assasino[i].nome, nome) == 0) {
                achou = i;
            }
        }

        if (achou == -1) { // Novo assassino
            strcpy(assasino[n].nome, nome);
            assasino[n].assasinados = 1;
            assasino[n].morreu = false;
            n++;
        } else { // Assassino já está na lista
            assasino[achou].assasinados++;
        }

        // Marcar o assassinado como mortos, se ele estiver na lista
        for (int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if (strcmp(assasino[i].nome, mortos[j]) == 0) {
                    assasino[i].morreu = true;
                }
            }
        }
    }

    printFinal(assasino, n);

    return 0;
}
