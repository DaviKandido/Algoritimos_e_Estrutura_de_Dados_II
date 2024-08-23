import java.util.Scanner;

public class AlgebraBooleana {

    public static boolean isZero(String entrada){
        return (entrada.length() == 1 && entrada.charAt(0) == '0');
    }

    public static boolean evaluateExpression(String expression, int[] values, int index) {
        // Caso base: se a expressão contém apenas uma variável (A, B ou C), retorna o valor dela
        if (expression.charAt(index) == 'A') {
            return values[0] == 1;
        } else if (expression.charAt(index) == 'B') {
            return values[1] == 1;
        } else if (expression.charAt(index) == 'C') {
            return values.length > 2 ? values[2] == 1 : false;
        }

        // Processar operações lógicas: not, and, or
        if (expression.startsWith("not", index)) {
            // Avaliar o not recursivamente para a próxima parte da expressão
            return !evaluateExpression(expression, values, index + 4);
        } else if (expression.startsWith("and", index)) {
            // Avaliar and recursivamente
            int endIndex = findComma(expression, index + 4);
            boolean left = evaluateExpression(expression, values, index + 4);
            boolean right = evaluateExpression(expression, values, endIndex + 2);
            return left && right;
        } else if (expression.startsWith("or", index)) {
            // Avaliar or recursivamente
            int endIndex = findComma(expression, index + 3);
            boolean left = evaluateExpression(expression, values, index + 3);
            boolean right = evaluateExpression(expression, values, endIndex + 2);
            return left || right;
        }

        return false; // Caso de erro (não deveria ocorrer com entradas válidas)
    }

    // Função auxiliar para encontrar a posição da vírgula que separa as duas partes de uma operação
    public static int findComma(String expression, int start) {
        int openParentheses = 0;
        for (int i = start; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                openParentheses++;
            } else if (expression.charAt(i) == ')') {
                openParentheses--;
            } else if (expression.charAt(i) == ',' && openParentheses == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (isZero(line)) break;

            String[] parts = line.split(" ");
            int n = Integer.parseInt(parts[0]);
            int[] values = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = Integer.parseInt(parts[i + 1]);
            }

            String expression = line.substring(line.indexOf(parts[n + 1]));

            boolean result = evaluateExpression(expression, values, 0);

            if (result) {
                System.out.println("1");
            } else {
                System.out.println("0");
            }
        }
        sc.close();
    }
}
