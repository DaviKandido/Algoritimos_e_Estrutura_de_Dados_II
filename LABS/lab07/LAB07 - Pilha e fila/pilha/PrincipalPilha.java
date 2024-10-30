/**
 * Pilha dinamica
 * @author Max do Val Machado
 * @version 2 01/2015
 */
public class PrincipalPilha {
	public static void main(String[] args) {
		try {
			System.out.println(" ==== PILHA FLEXIVEL ====");
			Pilha pilha = new Pilha();
            int x1, x2, x3;

			pilha.inserir(0);
			pilha.inserir(1);
			pilha.inserir(2);
			pilha.inserir(3);
			pilha.inserir(4);
			pilha.inserir(5);

			System.out.println("\n==== Exercício 1 ====");
			pilha.mostrar();


			Fila filaGerado = pilha.toFila();

			filaGerado.mostrar();

			System.out.println("\n ==== Exercício 2 ====");
			Fila filaRecursiva = filaGerado = pilha.toFilaOrdemRecursivo();

			filaRecursiva.mostrar();

			System.out.println("\n ==== Exercício 3 ====");
			Fila filaInterativo = filaGerado = pilha.toFilaOrdemInterativo();

			filaInterativo.mostrar();

			System.out.println("\n ==== Exercício 4 ====");

			Celula celulaGerada = pilha.toFilaSemFila();
		}
		catch(Exception erro) {
			System.out.println(erro.getMessage());
		}
	}
}
