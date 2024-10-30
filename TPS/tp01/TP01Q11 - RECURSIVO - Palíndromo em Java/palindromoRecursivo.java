import java.util.*;

class palindromoRecursivo {

    public static boolean isFim(String entrada) {
        return (entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M');
    }

    public static String verificaPolindromo(String polindromo, int esq, int dir) {
        String resposta = "SIM";

        if (esq < dir) {
            if (polindromo.charAt(esq) != polindromo.charAt(dir)) {
                resposta = "NAO";
            } else {
                resposta = verificaPolindromo(polindromo, esq + 1, dir - 1);
            }
        }

        return resposta;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String entrada[] = new String[1000];
        int i = 0;

        do {
            entrada[i] = sc.nextLine();
        } while (!isFim(entrada[i++]));

        for (int j = 0; j < i - 1; j++) {
            System.out.println(verificaPolindromo(entrada[j], 0, entrada[j].length() - 1));
        }
    }
}
