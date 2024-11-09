#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>


typedef struct Aluno{
    char nome[20];
    char regiao;
    int distancia;
} Aluno;

void ordenarAlunos(Aluno* alunos, int n){

    for(int i = 1; i < n; i++){
        Aluno tmp = alunos[i];
        int j = i -1;

        while((j>= 0) && ((alunos[j].distancia > tmp.distancia) 
        || (alunos[j].distancia == tmp.distancia && alunos[j].regiao > tmp.regiao)
        || (alunos[j].distancia == tmp.distancia && alunos[j].regiao == tmp.regiao && alunos[j].nome[0] > tmp.nome[0]))){
            alunos[j+1] = alunos[j];
            j--;
        }
        alunos[j+1] = tmp;

    }

}


int main(){

    int n = 0;
    scanf("%d", &n);

    Aluno alunos[n];

    for(int i = 0; i < n; i++){
        scanf("%s", alunos[i].nome); getchar();
        scanf("%c", &alunos[i].regiao); getchar();
        scanf("%d", &alunos[i].distancia); getchar();
    }

    ordenarAlunos(alunos, n);


    
    for(int i = 0; i < n; i++){
        printf("%s\n", alunos[i].nome);
    }


    return 0;
}