#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>




int main(){

    int oeste[100]; //maior Prioridade
    int norte[100]; //Revesa com Sul
    int sul[100];   //Revesa com norte
    int leste[100]; //Ultimo

    int numL = 0; //-4
    int numN = 0; //-3
    int numS = 0; //-2
    int numO = 0; //-1
    
    int entrada;
    char trash;
    scanf("%c""%d", &trash, &entrada);

    while (entrada != 0 && trash != '0') {

        if(entrada == 4){
            while (entrada != 1 && entrada != 2 && entrada != 3 && trash != '0') {
                scanf("%c""%d", &trash, &entrada);
                leste[numL++] = entrada;
            }
        }else if (entrada == 3) {
            while (entrada != 1 && entrada != 2 && entrada != 4 && trash != '0') {
                 scanf("%c""%d", &trash, &entrada);
                norte[numN++] = entrada;
            }
        }else if(entrada == 2){
            while (entrada != 1 && entrada != 3 && entrada != 4 && trash != '0') {
                 scanf("%c""%d", &trash, &entrada);
                sul[numS++] = entrada;
            }
        }else if (entrada == 1) {
            while ( entrada != 2 && entrada != 3 && entrada != 4 && trash != '0') {
                 scanf("%c""%d", &trash, &entrada);            
                oeste[numO++] = entrada;
            }
        }
        scanf("%d", &entrada);
    }
}