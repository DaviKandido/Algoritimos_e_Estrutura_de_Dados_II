import java.util.*;

class teste{
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int a = 1;

        for(int i = n; i>0; i/=2){
            a*=2;
        }
        
        System.out.println(a);

        sc.close();
    }

}