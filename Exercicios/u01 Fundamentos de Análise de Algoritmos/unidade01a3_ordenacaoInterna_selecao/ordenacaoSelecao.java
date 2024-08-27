import java.util.*;

class OrdenacaoSelecao {

    public static void ordenaArray(int[] array, int n) {
        int cont = 0;
        for (int i = 0; i < n - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < n; j++) {
                if (array[menor] > array[j]) {
                    menor = j;
                }
            }
            if (menor != i) {
                int tmp = array[i];
                array[i] = array[menor];
                array[menor] = tmp;
                cont += 3;  // Contando as três operações de swap
            }
        }
        System.out.println("Com " + cont + " movimentações.");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int[] array = new int[100000];

        System.out.print("\nDigite o número de elementos no array: ");
        int n = sc.nextInt();
        System.out.println();

        Random gerador = new Random();
        gerador.setSeed(0);

        for (int i = 0; i < n; i++) {
            array[i] = Math.abs(gerador.nextInt()) % 26;
        }

        System.out.print("O array gerado foi: [");
        for (int i = 0; i < n; i++) {
            System.out.print(array[i]);
            if (i < n - 1) System.out.print(", ");
        }
        System.out.print("]\n");

        ordenaArray(array, n);

        System.out.print("O array ordenado será: [");
        for (int i = 0; i < n; i++) {
            System.out.print(array[i]);
            if (i < n - 1) System.out.print(", ");
        }
        System.out.print("]\n");

        sc.close();
    }
}
