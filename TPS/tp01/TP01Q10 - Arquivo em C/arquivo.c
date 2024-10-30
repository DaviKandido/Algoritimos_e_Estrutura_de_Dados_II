#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <locale.h>


//OBS: o uso de fwrite e fread foi mais adequado para o problema proposto por se tartar de arquivo binário (w+b)
// o que facilitou a transformação da leitura em double ou int
int main() {
    int n = 0;
    scanf("%d", &n);

    char filename[] = "numero.txt";
    FILE *arquivo = fopen(filename, "w+b");

    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo");
        return 1;
    }

    double num = 0.0;
    for (int i = 0; i < n; i++) {
        scanf("%lf", &num);
        fwrite(&num, sizeof(double), 1, arquivo); 
    }

    double num2 = 0.0;
    for (int i = n - 1; i >= 0; i--) {
        fseek(arquivo, i * sizeof(double), SEEK_SET);  // Posiciona o ponteiro no início do i-ésimo double
        fread(&num2, sizeof(double), 1, arquivo);  // Lê um num de tamanho double

        if (num2 == (int)num2) {
            printf("%d\n", (int)num2);
        } else {
            printf("%.12g\n", num2);
        }
    }

    fclose(arquivo);
    return 0;
}
