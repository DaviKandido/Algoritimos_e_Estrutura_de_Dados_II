
import java.util.*;

class OrdenacaoShort {

    public static boolean isZero(int n, int m){

        return ((n > 0 && n <=10000) && (m > 0 && m <=10000));
    }


    public static void OrdenacaoShort(int array[], int n, int m) {

            
            for(int i = 0; i < n; i++){
                int tmp = array[i];
                int j = i - 1;
                while(j >= 0 && array[j] % m > tmp % m){
                    array[j+1] = array[j];
                    j--;
                }
                array[j+1] = tmp;
            }

            for(int i = 0; i < n; i++){
                int tmp = array[i];
                int j = i - 1;
                while(j >= 0 && array[j] % m == tmp % m &&
                ((tmp %2 != 0 && array[j] < tmp ) ||
                 (tmp %2 == 0 && array[j] > tmp ) )){

                    array[j+1] = array[j];
                    j--;
                }
                array[j+1] = tmp;
            }

    }

    public static void printArray(int array[]) {

        for (int i : array) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        int n;
        int m;

        n = sc.nextInt();
        m = sc.nextInt();

        System.out.println(n + " " + m);

        do{
                        
            int array[] = new int[n];
            
            for(int i = 0; i < n; i++){
                array[i] = sc.nextInt();
            }
            
            OrdenacaoShort(array, n, m);
            printArray(array);
            
            n = sc.nextInt();
            m = sc.nextInt();

            System.out.println(n + " " + m);

        }while(isZero(n,m));

        sc.close();

    }
}