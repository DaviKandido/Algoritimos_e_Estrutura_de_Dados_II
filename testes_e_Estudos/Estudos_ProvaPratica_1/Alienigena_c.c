#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>


void verificaAlien(char alfabeto[], char msg[], int k_alf, int N_num){

    bool resp = true;

    for(int i = 0; i < N_num && resp; i++){
        resp = false;
        for (int j = 0; j < k_alf; j++){
            if(alfabeto[j] == msg[i]){
                resp = true;
            }
        }
    }

    if(resp){
        printf("S\n");
    }else{
        printf("N\n");
    }
}

int main(){

    int k_alf = 0;
    int N_num = 0;

    scanf("%d", &k_alf);
    scanf("%d", &N_num);


    do{
        char alfabeto[k_alf+1];
        char msg[N_num+1];

        scanf("%s", alfabeto);
        scanf("%s", msg);

        verificaAlien(alfabeto, msg, k_alf, N_num);

        scanf("%d", &k_alf);
        scanf("%d", &N_num);
        
    }while(k_alf <= 68 && k_alf >= 1 && N_num >= 1 && N_num <= 1000);


}