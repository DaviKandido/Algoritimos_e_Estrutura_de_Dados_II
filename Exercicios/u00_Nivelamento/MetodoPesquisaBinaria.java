//ATV - 2 - Slide 2

public class MetodoPesquisaBinaria {
    public static boolean pesquisaBinaria(int[] array, int x) { 

        int n = array.length;
        boolean resp = false;
        int contador = 0;

        int dir = n-1, esq = 0, meio = n/2, diferenca;

        while (esq <= dir) { 
            meio = (esq + dir)/2;
            diferenca = (x-array[meio]);
            if (diferenca == 0) {
                resp = true;
                esq = n;
            } else if (diferenca > 0){
                esq = meio + 1;
            } else{
                dir = meio - 1;
            }
            contador++;
        }



        System.out.println("Com "+contador+" repetições sabe-se que: ");

        return resp;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7}; 
        int procurado = 10;

        boolean estaContido = pesquisaBinaria(array, procurado);
        System.out.println("O número " + procurado + " está contido no array? " + estaContido);
    }
}

/*

Com 2 repetições sabe-se que: 
O número 2 está contido no array? true

Com 3 repetições sabe-se que: 
O número 10 está contido no array? false

 */