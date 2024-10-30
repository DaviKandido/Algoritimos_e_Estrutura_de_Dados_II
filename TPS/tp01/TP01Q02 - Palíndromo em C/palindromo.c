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


//Verifica se a string analisada é a "FIM"
bool isFim(char entrada[]){
        
    return (ContaChars(entrada) == 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M');
}


void classificarPalindromo(char palavra[]) {
    int esq = 0;
    int dir = ContaChars(palavra) - 1;
    char resposta[] = "SIM";

    for(int i = 0; i < dir; i++){
        if(!((palavra[i] <= 'Z' && palavra[i] >= 'A') || (palavra[i] <= 'z' && palavra[i] >= 'a') || (palavra[i] <= '9' && palavra[i] >= '0'))){
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

    char palavra[1000];

    scanf(" %[^\n]", palavra);

    while (isFim(palavra) == false) {

        classificarPalindromo(palavra);
        scanf(" %[^\n]", palavra);
    }

    return 0;
}