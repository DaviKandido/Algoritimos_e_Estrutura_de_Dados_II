#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>z


void combinador(char palavra1[], char palavra2[]) {
    int i = 0, j = 0;
    int len1 = strlen(palavra1);
    int len2 = strlen(palavra2);

    while (i < len1 || j < len2) {
        if (i < len1) {
            printf("%c", palavra1[i]);
            i++;
        }
        if (j < len2) {
            printf("%c", palavra2[j]);
            j++;
        }
    }
}

int main() {
    char palavra1[1000];
    char palavra2[1000];

    while (scanf("%s", palavra1) == 1 && scanf("%s", palavra2) == 1) {
        combinador(palavra1, palavra2);
        printf("\n");
    }

    return 0;
}