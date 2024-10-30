

/**
 * Metodos de ordenação QuickSort
 * @author Max do Val Machado
 * @author 2 Davi Cândido de Almeida (Efetuou modificações para aperfeiçoamento de analises
 *         e inclusão de métodos de escolha de pivô no QuickSort)
 * @version 4 02/10/2024
 */



import java.util.*;

class Quicksort extends Geracao {

	/**
	 * Construtor.
	 */
   public Quicksort(){
      super();
   }


	/**
	 * Construtor.
	 * @param int tamanho do array de numeros inteiros.
	 */
   public Quicksort(int tamanho){
      super(tamanho);
   }


	/**
	 * Algoritmo de ordenacao Quicksort.
	 */
   @Override
   public void sort() {
      quicksort(0, n-1);
   }

	/**
	 * Algoritmo de ordenacao Quicksort.
    * @param int esq inicio do array a ser ordenado
    * @param int dir fim do array a ser ordenado
	 */
    private void quicksort(int esq, int dir) {
        int i = esq, j = dir;
        int pivo = array[(dir+esq)/2];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quicksort(esq, j);
        if (i < dir)  quicksort(i, dir);
    }

    public void QuickSortFirstPivot(int esq, int dir) {
        int i = esq, j = dir;
        int pivo = array[esq];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quicksort(esq, j);
        if (i < dir)  quicksort(i, dir);
    }

    public void QuickSortLastPivot(int esq, int dir) {
        int i = esq, j = dir;
        int pivo = array[dir];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quicksort(esq, j);
        if (i < dir)  quicksort(i, dir);
    }

    

    public void QuickSortRandomPivot(int esq, int dir) {
        int i = esq, j = dir;

        Random rand = new Random();
        
        int pivo = array[ Math.abs(esq + rand.nextInt()) % dir];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quicksort(esq, j);
        if (i < dir)  quicksort(i, dir);
    }

    public void QuickSortMedianOfThree(int esq, int dir) {
    
        int i = esq, j = dir;
    
        if (i <= j) {

        int meio = (esq + dir) / 2;

        if (array[esq] > array[meio]) swap(esq, meio); 
        if (array[esq] > array[dir]) swap(esq, dir);   
        if (array[meio] > array[dir]) swap(meio, dir);

        int pivo = array[meio];
        
        while (i <= j) {
            while (array[i] < pivo) i++;  
            while (array[j] > pivo) j--;  
            if (i <= j) {
                swap(i, j); 
                i++;
                j--;
            }
        }

        if (esq < j) QuickSortMedianOfThree(esq, j);
        if (i < dir) QuickSortMedianOfThree(i, dir);
    }
    }
}