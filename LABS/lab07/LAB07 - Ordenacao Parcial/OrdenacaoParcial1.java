import java.util.*;

public class OrdenacaoParcial1{

     /**
      * Produz um array ordenado de modo crescente.
      */
      public static void crescente(int array[]) {
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
    }


    /**
     * Produz um array ordenado de modo decrescente.
     */
    public static void decrescente(int array[]) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array.length - 1 - i;
        }
    }


    /**
     * Produz um array com numeros aleatorios.
     */
    public static void aleatorio(int array[]) {
        Random rand = new Random();
        crescente(array);	
        for (int i = 0; i < array.length; i++) {
            swap(array, i, Math.abs(rand.nextInt()) % array.length);
        }
    }


    private static void swap(int array[], int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    

    public static boolean isOrdenado(int array[]){
        boolean resp = true;
        for(int i = 1; i < array.length; i++){
            if(array[i] < array[i-1]){
                i = array.length;
                resp = false;
            }
        }
        return resp;
    }


   public static void mostrar(int var1, int array[]) {
    System.out.print("[");

    for(int var2 = 0; var2 < var1; ++var2) {
       System.out.print(" (" + var2 + ")" + array[var2]);
    }

    System.out.println("]");
 }


 //----Metodos de ordenação parcial----//


    //Selecao Parcial
    public static void selecaoParcial(int array[], int k){
        for(int i = 0; i < k; i++){
            int menor = i;
            for(int j = (i+1); j<array.length;j++){
                if(array[menor] > array[j]){
                    menor = j;
                }
            }
            swap(array, i, menor);
        }
    }

     //insercao Parcial
    public static void insercaoParcial(int array[], int k){
        for(int i = 1; i < array.length; i++){
            int tmp = array[i];
            int j = (i<k) ? i-1 : k-1;
            while((j>=0) && (array[j] > tmp)){
                array[j+1] = array[i];
                j--;
            }
            array[j+1] = tmp;
        }
    }

     //Ordenação Parcial
    public static void quicksortParcial(int array[], int esq, int dir, int k){
        int i = esq,
        j = dir,
        pivo = array[(esq+dir)/2];
        while(i <= j){
            while(array[i] < pivo){
                i++;
            }
            while(array[j] > pivo){
                j--;
            }
            if(i <= j){
                swap(array, i,j); i++; j--;
            }
        }
        if(esq < j)
            quicksortParcial(array, esq, j, k);
        if(i < k && i < dir)
            quicksortParcial(array, i, dir, k);
    }
    public void quicksortParcial(int array[], int k){
        quicksortParcial(array ,0, array.length, k);
    }

    //heapsort Parcial
    public static void heapsort(int array[], int k){

        for(int tam = 2; tam <= k; tam++)
            construir(tam);

        for(int i = k +1; i <= array.length; i++){
            if(array[i] < array[1]){
                swap(array, i, 1);
                reconstruir(k);
            }
        }

        int tam = k;
        while(tam > 1){
            swap(array, 1, tam--);
            reconstruir(tam);
        }

    }
}