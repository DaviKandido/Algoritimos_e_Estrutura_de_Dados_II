// ATV - 3- Slide 3

public class ProcuraDeMaiorMenor {

    public static int  ProcuraMaior(int array[]) {
     
        int tmp = array[0];
        int n = array.length -1;
        for ( int i = 0; i < n; i++){
            if (tmp < array[i]) {
                tmp = array[i];
            }
        }
        return tmp;
    }

    public static int ProcuraMenor(int array[]) {
    
        int tmp = array[0];
        int n = array.length;
        for ( int i = 0; i < n; i++){
            if (tmp > array[i]) {
                tmp = array[i];
               }
            }

        return tmp;
    }

    public static void main(String[] args) {
        int[] array = {8, 7, 20, 5, 3, 10, 2}; 

        int maior = ProcuraMaior(array);
        int menor = ProcuraMenor(array);

        System.out.println("O menor número contido array é: " + menor + " e o maior: " + maior);
    }
}
