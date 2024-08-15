import java.util.*;

class ExemploBuffered {

   public static Scanner sc = new Scanner(System.in);

   public static void main(String[] args) {

      // Criação do objeto Scanner para leitura de entrada do usuário
      // Leitura de uma string
      System.out.println("\nEscreva uma String: ");
      String str = sc.next();

      // Leitura de um número inteiro
      System.out.println("Escreva um inteiro: ");
      int inteiro = sc.nextInt();

      // Leitura de um número real
      System.out.println("Escreva um nº real: ");
      double real = sc.nextDouble();

      // Impressão dos valores lidos
      System.out.println("Sua string: " + str);
      System.out.println("Seu inteiro: " + inteiro);
      System.out.println("Seu número real: " + real);

      // Fechamento do Scanner
      sc.close();
   }
}