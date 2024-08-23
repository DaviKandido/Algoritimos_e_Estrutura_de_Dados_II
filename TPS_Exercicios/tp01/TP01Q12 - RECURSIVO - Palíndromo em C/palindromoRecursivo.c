#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <locale.h>

int ContaChars(char string[]) {
    int i = 0;
    while (string[i] != '\0') {
        i++;
    }
    return i;
}

// Verifica se a string analisada é a "FIM"
bool isFim(char entrada[]) {
    return (entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M' && entrada[3] == '\0');
}

// Verifica se e polindromo de forma recursiva
void classificarPalindromo(char palavra[], int esq, int dir, bool *resultado) {
    if (esq < dir) {
        if (palavra[esq] != palavra[dir]) {
            *resultado = false; 
        } else {
            classificarPalindromo(palavra, esq + 1, dir - 1, resultado);
        }
    }
}

int main() {    
    setlocale(LC_ALL, "pt_BR.UTF-8");  // Linux

    char palavra[1000];

    scanf(" %[^\n]", palavra);

    while (!isFim(palavra)) {

        for (int i = 0; palavra[i] != '\0'; i++) {
            if (!((palavra[i] <= 'Z' && palavra[i] >= 'A') || (palavra[i] <= 'z' && palavra[i] >= 'a') || (palavra[i] <= '9' && palavra[i] >= '0'))) {
                palavra[i] = 'A';
            }
        }

        bool resultado = true;
        classificarPalindromo(palavra, 0, ContaChars(palavra) - 1, &resultado);

        if (resultado) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }

        scanf(" %[^\n]", palavra);
    }

    return 0;
}
