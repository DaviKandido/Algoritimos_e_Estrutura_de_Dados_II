#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

void ordenaArray(int array[], int n) {
    int cont = 0;
    for (int i = 0; i < n - 1; i++) {
        int menor = i;
        for (int j = i + 1; j < n; j++) {
            if (array[menor] > array[j]) {
                menor = j;
            }
        }
        if (menor != i) {
            int tmp = array[i];
            array[i] = array[menor];
            array[menor] = tmp;
            cont +=3;
        }
    }
    printf("Com %d movimentações: \n", cont);
}

int main() {
    int array[100000];
    int n = 0;
    srand(0);

    printf("\nDigite o número de elementos no array: ");
    scanf("%d", &n);
    printf("\n");

    for (int i = 0; i < n; i++) {
        array[i] = rand() % 26;
    }

    printf("O array gerado foi: [");
    for (int i = 0; i < n; i++) {
        printf("%d", array[i]);
        if (i < n - 1) printf(", ");
    }
    printf("]\n");

    ordenaArray(array, n);

    printf("O array ordenado será: [");
    for (int i = 0; i < n; i++) {
        printf("%d", array[i]);
        if (i < n - 1) printf(", ");
    }
    printf("]\n");

    return 0;
}
