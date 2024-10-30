/** 					
 * 					
 * @author: Thiago Pereira de Oliveira	
 * @matricula: 835251   
 * @description: Uniao de exercicios em C
 * @data: 16/08/24				
 * 					
*/ 					

#include <string.h>
#include <stdlib.h>
#include <stdio.h>

///////////////////////////////////////////////////////////////////////////////////////////////////

#include <stdint.h>

typedef struct {
    uint64_t seed;
} JavaRandom;

// Função para inicializar a seed
void setSeed(JavaRandom* JRandom, uint64_t seed) {
    JRandom->seed = (seed ^ 0x5DEECE66DLL) & ((1LL << 48) - 1);
}

// Função que imita o comportamento do next() do JRandom do Java
int32_t next(JavaRandom* JRandom, int bits) {
    JRandom->seed = (JRandom->seed * 0x5DEECE66DLL + 0xBLL) & ((1LL << 48) - 1);
    return (int32_t)(JRandom->seed >> (48 - bits));
}

// Função que imita o comportamento do nextInt() do JRandom do Java
int32_t nextInt(JavaRandom* JRandom) {
    return next(JRandom, 32);
}

JavaRandom JRandom;

///////////////////////////////////////////////////////////////////////////////////////////////////

#define bool short

const bool false = 0;
const bool true = 1;

bool    compare  (char* t1, char* t2, int tamanho, int x)
{
    bool resposta = true;

    if (tamanho != strlen (t2))
        resposta = false;
    else if (t1[x] != t2[x])
        resposta = false;
    else
        if (x < tamanho - 1)
            resposta = compare (t1, t2, tamanho, x + 1);

    return resposta;
}
bool    comparar (char* t1, char* t2)
{return compare  (t1, t2, strlen (t1), 0);}

char* change (char* entrada, int tamanho, int x, char c1, char c2)
{
    if (x < tamanho)
        entrada = change(entrada, tamanho, x + 1, c1, c2);

    if (entrada[x] == c1)
        entrada[x] = c2;

    return entrada;
}

char* substituir (char* entrada)
{   
    int tamanho = strlen (entrada);

    char carac1 = (char) ('a' + abs(nextInt(&JRandom)%26));    
    char carac2 = (char) ('a' + abs(nextInt(&JRandom)%26));

    return change (entrada, tamanho, 0, carac1, carac2);
}

void metodo ()
{
    char entrada       [500];
    scanf("%[^\n]", entrada); getchar();

    if (!comparar(entrada, "FIM"))
    {
        printf("%s\n", substituir (entrada));

        metodo ();
    }
}

int main (void)
{
    //srand(4);

    setSeed(&JRandom, 4); // Define a seed, por exemplo 4

    metodo();
    
    return 0;
}

// (char)('a' + (Math.abs(gerador.nextInt()) % 26)