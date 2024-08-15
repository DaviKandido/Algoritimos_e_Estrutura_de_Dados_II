#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

//Verifica se o caracter enviado é maiusculo
bool isMaiuscula (char c){
    return (c >= 'A' && c<= 'Z');
}

//Verifica se a string analisada é a "FIM"
bool isFim(char entrada[]){
        
    return (strlen(entrada) == 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M');
}

//Contabiliza a quantidade de Maiusculas na String
int contarLetrasMaiusculas(char entrada[]){
    int quantMiaus = 0;

    for(int i = 0; i < strlen(entrada); i++ ){

        if(isMaiuscula(entrada[i])){
            quantMiaus++;
         }
    }

    return quantMiaus;

}



void main(){

        char entrada[1000][1000];

        int i = 0;

    do {

        scanf(" %[^\n]", entrada[i]);


        i++;
        
    }while (isFim(entrada[i-1]) == false);


    for(int j = 0; j < i-1; j++){
        printf("%d\n", contarLetrasMaiusculas(entrada[j]));
    }


    return 0;
}