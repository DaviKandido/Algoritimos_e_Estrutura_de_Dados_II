import java.util.*;

class teste{
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int a = 1;
        int mult = 0;

        for(int i = n; i>0; i/=2){
            a*=2;
            mult++;
        }
        
        System.out.println(a);
        System.out.println(mult) ;
        sc.close();
    }

}