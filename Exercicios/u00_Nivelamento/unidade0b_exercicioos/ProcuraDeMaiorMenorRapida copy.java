// ATV - 4 - Slide 3

public class ProcuraDeMaiorMenorRapida {

    public static int[]  ProcuraMaiorMenor(int array[]) {
     
        int contador = 0;
        int maior = array[0];
        int menor = array[0];

        int n = array.length;
        for ( int i = 1; i < n; i++){
            if (maior < array[i]) {
                maior = array[i];
                contador++;
            }
        }

        for ( int i = 1; i < n; i++){
            if (menor > array[i]) {
                menor = array[i];
                contador++;
               }
            }

        
        System.out.println("Com "+contador+" repetições sabe-se que: ");

        return new int[] {maior, menor};
    }


    public static void main(String[] args) {
        int[] array = {8, 7, 21, 5, 3, 12, 2}; 

        int[] resultado = ProcuraMaiorMenor(array);
        int maior = resultado[0];
        int menor = resultado[1];

        System.out.println("O menor número contido array é: " + menor + " e o maior: " + maior);
    }
}
