#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdlib.h>

int Pilha[300];
int n = 0;  // Inicialize `n` aqui diretamente

void start() {
    n = 0;  // Use a variável global diretamente
}

void inserir(int x) {
    Pilha[n] = x;
    n++;
}

void voo(int O[], int o, int N[], int n1, int S[], int s, int L[], int l) {
    int indexO = 0, indexN = 0, indexS = 0, indexL = 0;
    int tamanho = o + n1 + s + l;

    for (int i = 0; i < tamanho; i++) {
        if (indexO < o) {
            inserir(O[indexO]);
            indexO++;
        }
        
        if (indexS < s) {
            inserir(S[indexS]);
            indexS++;
        }

             if (indexN < n1) {
            inserir(N[indexN]);
            indexN++;
        }
        
        if (indexL < l) {
            inserir(L[indexL]);
            indexL++;
        }
    }

    for (int i = 0; i < n; i++) {
        printf("A%d ", Pilha[i]);
    }
}

int main(void) {
    int O[1000];
    int S[1000];
    int N[1000];
    int L[1000];

    int o = 0, s = 0, n1 = 0, l = 0;
    start();
    
    int n;
    scanf("%d", &n);
    getchar();

    while(n != 0) {
        char entrada[10];
       
        if(n == -1) {
            scanf("%s", entrada);
            while(strcmp(entrada, "-1") != 0 && strcmp(entrada, "-2") != 0 && strcmp(entrada, "-3") != 0 && strcmp(entrada, "-4") != 0 && strcmp(entrada, "0") != 0) {
                char trash;
                sscanf(entrada, "%c%d", &trash, &O[o++]);
                scanf("%s", entrada);
            }
        }
        else if(n == -2) {
            scanf("%s", entrada);
            while(strcmp(entrada, "-1") != 0 && strcmp(entrada, "-2") != 0 && strcmp(entrada, "-3") != 0 && strcmp(entrada, "-4") != 0 && strcmp(entrada, "0") != 0) {
                char trash;
                sscanf(entrada, "%c%d", &trash, &N[n1++]);
                scanf("%s", entrada);
            }
        }
        else if(n == -3) {
            scanf("%s", entrada);
            while(strcmp(entrada, "-1") != 0 && strcmp(entrada, "-2") != 0 && strcmp(entrada, "-3") != 0 && strcmp(entrada, "-4") != 0 && strcmp(entrada, "0") != 0) {
                char trash;
                sscanf(entrada, "%c%d", &trash, &S[s++]);
                scanf("%s", entrada);
            }
        }
        else if(n == -4) {
            scanf("%s", entrada);
            while(strcmp(entrada, "-1") != 0 && strcmp(entrada, "-2") != 0 && strcmp(entrada, "-3") != 0 && strcmp(entrada, "-4") != 0 && strcmp(entrada, "0") != 0) {
                char trash;
                sscanf(entrada, "%c%d", &trash, &L[l++]);
                scanf("%s", entrada);
            }
        }

        sscanf(entrada, "%d", &n);
    }

    voo(O, o, N, n1, S, s, L, l);

    return 0;
}
