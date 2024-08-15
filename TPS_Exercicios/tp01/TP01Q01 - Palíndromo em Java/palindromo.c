#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <locale.h>


//Verifica se a string analisada é a "FIM"
bool isFim(char entrada[]){
        
    return (strlen(entrada) == 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M');
}


void classificarPalindromo(char palavra[]) {
    int esq = 0;
    int dir = strlen(palavra) - 1;
    char resposta[] = "SIM";

    for(int i = 0; i < dir; i++){
        if(!((palavra[i] <= 'Z' && palavra[i] >= 'A') || (palavra[i] <= 'z' && palavra[i] >= 'a') || (palavra[i] < '10' && palavra[i] >= '0'))){
            palavra[i] = 'A';
        }
    }

    while (esq < dir) {
        if (palavra[esq] != palavra[dir]) {
            resposta[0] = 'N';
            resposta[1] = 'A';
            resposta[2] = 'O';
        }
        esq++;
        dir--;
    }

    printf("%s\n", resposta);
}

int main() {    
    
    setlocale(LC_ALL, "pt_BR.UTF-8");  // Linux

    char palavra[500];

    scanf(" %[^\n]", palavra);

    while (isFim(palavra) == false) {

        classificarPalindromo(palavra);
        scanf(" %[^\n]", palavra);
    }

    return 0;
}