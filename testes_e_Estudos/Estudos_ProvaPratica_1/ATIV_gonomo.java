import java.util.*;

public class ATIV_gonomo {

    public static class Gnomo {  // Tornando a classe Gnomo estática para ser acessível em métodos estáticos
        public String nome;
        public int idade;

        public Gnomo(String nome, int idade) {
            this.nome = nome;
            this.idade = idade;
        }
    }

    // Método para ordenar os gnomos pela idade
    public static void ordenaGnomos(Gnomo[] gnomo, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (gnomo[i].idade < gnomo[j].idade) { 
                    Gnomo aux = gnomo[i];
                    gnomo[i] = gnomo[j];
                    gnomo[j] = aux;
                }
            }
        }
    }

    // Método para dividir os gnomos em times
    public static void divideTime(Gnomo[] gnomo, int n) {
        int qnt_times = n / 3;

        // Inicializar um array de strings para armazenar os times
        String[] time = new String[qnt_times];

        for (int i = 0; i < qnt_times; i++) {
            time[i] = "Time " + (i + 1) + ":\n"; 
        }

        // Atribuir os gnomos aos times
        int cont = 0;
        for (int i = 0; i <= n; i = i + 3) {
            for (int j = 0; j < qnt_times; j++) {
                if (cont < n) {
                    time[j] += gnomo[cont].nome + " " + gnomo[cont].idade + "\n";
                    cont++;
                }
            }
        }

        // Exibir os times
        for (int i = 0; i < qnt_times; i++) {
            System.out.println(time[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Número de gnomos
        int n = sc.nextInt();
        sc.nextLine();  // Consumir a quebra de linha

        Gnomo[] gnomo = new Gnomo[n];

        // Preencher o array de gnomos
        for (int i = 0; i < n; i++) {
            String nome = sc.next();   // Lê o nome do gnomo
            int idade = sc.nextInt();  // Lê a idade do gnomo
            gnomo[i] = new Gnomo(nome, idade);  // Cria a instância de Gnomo
        }

        // Ordenar os gnomos
        ordenaGnomos(gnomo, n);

        // Dividir os gnomos em times e exibir os resultados
        divideTime(gnomo, n);
    }
}
