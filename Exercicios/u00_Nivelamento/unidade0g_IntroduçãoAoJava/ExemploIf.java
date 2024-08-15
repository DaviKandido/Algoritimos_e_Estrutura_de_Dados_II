
import java.util.Scanner;

public class ExemploIf {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\nDigite a nota do aluno: ");
        double nota = sc.nextDouble();

        if(nota >= 80){
            System.out.println("Parabéns, muito bom!");
        } else if(nota >=70 && nota < 80){
            System.out.println("Parabéns, aprovado");
        } else{
            System.out.println("Infelizmente, reprovado");
        }
    }
}
