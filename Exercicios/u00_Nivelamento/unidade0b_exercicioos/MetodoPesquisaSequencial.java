// ATV - 1 - Slide 2

public class MetodoPesquisaSequencial { 

    public static boolean pesquisaSequencial(int[] array, int x) { 
        int n = array.length;
        boolean contido = false;

        for (int i = 0; i < n; i++) {
            if (array[i] == x) {
                contido = true;
            }
        }

        return contido;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7}; 
        int procurado = 5;

        boolean estaContido = pesquisaSequencial(array, procurado);
        System.out.println("O número " + procurado + " está contido no array? " + estaContido);
    }
}