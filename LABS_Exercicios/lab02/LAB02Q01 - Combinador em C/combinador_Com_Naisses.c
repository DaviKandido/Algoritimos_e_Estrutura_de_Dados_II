#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>


int ContaChars(char string[]) {
    int i = 0;
    while (string[i] != '\0') {
        i++;
    }
    return i;
}


char* combinador(char palavra1[], char palavra2[]) {
    int len1 = ContaChars(palavra1);
    int len2 = ContaChars(palavra2);
    int i = 0, k = 0, j = 0;
    char *palavra3 = (char*) malloc((len1 + len2 + 1)*sizeof(char));


    for(i = 0, k = 0, j = 0; i < len1 && j < len2; i++,j++){
        palavra3[k++] = palavra1[i];
        palavra3[k++] = palavra2[j];
    }

    while(i < len1){
        palavra3[k++] = palavra1[i++];
    }
    while(j < len2){
        palavra3[k++] = palavra2[j++];
    }

    palavra3[k++] = '\0';
    //printf("%s\n", palavra3)
    return palavra3;
}

int main() {
    char palavra1[1000];
    char palavra2[1000];

    while (scanf("%s", palavra1) == 1 && scanf("%s", palavra2) == 1) {
        char *palavraFInal = combinador(palavra1, palavra2);
        printf("%s", palavraFInal);
    }

    return 0;
}