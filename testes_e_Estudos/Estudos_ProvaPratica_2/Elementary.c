#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>


int calculaTrocas(int *array, int n){
    int count = 0;
    for(int i = 1; i < n; i++){
        int tmp = array[i];
        int j = i- 1;


        while((j >= 0) && (array[j] > tmp)){
            array[j+1] = array[j];
            j--;
            count++;
        }
        array[j+1] = tmp;
    }

    return count;
}


int main(){

    int op = 0;
    scanf("%d", &op);

    for(int i = 0; i < op; i++){
        int n = 0;
        scanf("%d", &n);
        int array[n];
        for(int j = 0; j < n; j++){
            scanf("%d", &array[j]);
        }
        int result = calculaTrocas(array, n);

        printf("%d\n", result);
    }

    return 0;
}