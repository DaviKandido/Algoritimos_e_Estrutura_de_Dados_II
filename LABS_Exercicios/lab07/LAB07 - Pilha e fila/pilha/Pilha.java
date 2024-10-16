/**
 * Pilha dinamica
 * 
 * @author Max do Val Machado
 * @version 2 01/2015
 */
public class Pilha {
	private Celula topo;

	/**
	 * Construtor da classe que cria uma fila sem elementos.
	 */
	public Pilha() {
		topo = null;
	}

	/**
	 * Insere elemento na pilha (politica FILO).
	 * 
	 * @param x int elemento a inserir.
	 */
	public void inserir(int x) {
		Celula tmp = new Celula(x);
		tmp.prox = topo;
		topo = tmp;
		tmp = null;
	}

	/**
	 * Remove elemento da pilha (politica FILO).
	 * 
	 * @return Elemento removido.
	 * @trhows Exception Se a sequencia nao contiver elementos.
	 */
	public int remover() throws Exception {
		if (topo == null) {
			throw new Exception("Erro ao remover!");
		}
		int resp = topo.elemento;
		Celula tmp = topo;
		topo = topo.prox;
		tmp.prox = null;
		tmp = null;
		return resp;
	}

	/**
	 * Mostra os elementos separados por espacos, comecando do topo.
	 */
	public void mostrar() {
		System.out.print("[ ");
		for (Celula i = topo; i != null; i = i.prox) {
			System.out.print(i.elemento + " ");
		}
		System.out.println("] ");
	}

	public int getSoma() {
		return getSoma(topo);
	}

	private int getSoma(Celula i) {
		int resp = 0;
		if (i != null) {
			resp += i.elemento + getSoma(i.prox);
		}
		return resp;
	}

	public int getMax() {
		int max = topo.elemento;
		for (Celula i = topo.prox; i != null; i = i.prox) {
			if (i.elemento > max)
				max = i.elemento;
		}
		return max;
	}

	public void mostraPilha() {
		mostraPilha(topo);
	}

	private void mostraPilha(Celula i) {
		if (i != null) {
			mostraPilha(i.prox);
			System.out.println("" + i.elemento);
		}
	}

	public Fila toFila() throws Exception {
		if(topo == null) {
			throw new Exception("Erro ao Transformar pilha em fila!");
		}
		Fila fila = new Fila();
		Celula i = topo;
		while(i != null) {
			fila.inserir(i.elemento);
			i = i.prox;
		}


		return fila;
	}

	public Fila toFilaOrdemRecursivo() throws Exception {
		Fila fila = new Fila();

		return toFilaOrdemRecursivo(topo, fila);
	}

	private Fila toFilaOrdemRecursivo(Celula i, Fila fila) throws Exception {
		if(topo == null) {
			throw new Exception("Erro ao Transformar pilha em fila!");
		}

		if(i.prox != null) {
			toFilaOrdemRecursivo(i.prox, fila);
		}
		fila.inserir(i.elemento);


		return fila;
	}

	public Fila toFilaOrdemInterativo() throws Exception {

		Pilha pilhatemp = new Pilha();
		for(Celula i = topo; i != null; i = i.prox) {
			pilhatemp.inserir(i.elemento);
		}
		return pilhatemp.toFila();
	}
}
