#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>



typedef struct Avistamento{
    char horario[5];
    char nome[50];
    int minutos;
    
} Avistamento;

int converteParaMinutos(char horario[]){
    int hora, minutos;

    sscanf(horario, "%d:%d", &hora, &minutos);

    return hora * 60 + minutos;
}


void ordenarAvistamentos(Avistamento *avistamentos, int numAvist){


    for(int i = 0; i < numAvist; i++){
        avistamentos[i].minutos = converteParaMinutos(avistamentos[i].horario);
    }

    for(int i = 0; i < numAvist-1; i++){
        for(int j = i + 1; j < numAvist; j++){
            if(avistamentos[i].minutos > avistamentos[j].minutos){
                Avistamento tmp = avistamentos[i];
                avistamentos[i] = avistamentos[j];
                avistamentos[j] = tmp;
            }
        }
    }
}

int main(){

    int parametro, numAvist = 0;

    scanf("%d %d", &parametro, &numAvist);
    Avistamento avistamentos[numAvist];

    for(int i = 0; i < numAvist; i++){
        scanf(" %s" " %s", avistamentos[i].horario, avistamentos[i].nome);
    }

    ordenarAvistamentos(avistamentos, numAvist);

    for(int i = 0; i < numAvist; i++){
        if(avistamentos[i].minutos - 15 < 0){
            printf("%s %d\n", avistamentos[i].nome, avistamentos[i].minutos);   
        }
    }



    return 0;
}