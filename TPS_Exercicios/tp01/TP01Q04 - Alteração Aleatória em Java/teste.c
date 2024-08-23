#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

void substituir_letras(char *str) {
    // Inicializar o gerador de números aleatórios com a seed 4
    srand(4);

    // Sortear duas letras minúsculas aleatórias
    char letra1 = 'a' + (rand() % 26);
    char letra2 = 'a' + (rand() % 26);

    // Substituir todas as ocorrências de letra1 por letra2 na string
    for (int i = 0; i < strlen(str); i++) {
        if (str[i] == letra1) {
            str[i] = letra2;
        }
    }
}

int main() {
    char entrada[1000];

    // Ler as linhas de entrada
    while (fgets(entrada, 1000, stdin)) {
        // Remover o caractere de nova linha
        entrada[strcspn(entrada, "\n")] = 0;

        // Verificar se a entrada é "FIM"
        if (strcmp(entrada, "FIM") == 0) {
            break;
        }

        // Aplicar a substituição de letras
        substituir_letras(entrada);

        // Imprimir a string modificada
        printf("%s\n", entrada);
    }

    return 0;
}
