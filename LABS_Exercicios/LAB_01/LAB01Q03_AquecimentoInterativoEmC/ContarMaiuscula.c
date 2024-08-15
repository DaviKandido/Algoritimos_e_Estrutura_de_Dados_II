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

        char entrada[50][100];

        int i = 0;

    do {

        fgets(entrada[i], sizeof(entrada[i]), stdin);


        //O /n impedia a interupção do loop
        entrada[i][strcspn(entrada[i], "\n")] = '\0'; // // Remover a nova linha do final da string


        i++;
        
    }while (isFim(entrada[i-1]) == false);


    for(int j = 0; j < i-1; j++){
        printf("%d\n", contarLetrasMaiusculas(entrada[j]));
    }


    return 0;
}