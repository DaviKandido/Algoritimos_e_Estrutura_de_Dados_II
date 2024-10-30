

class ColetaLixo {
    private Celula primeiro, ultimo;

    public ColetaLixo () {
        primeiro = ultimo = new Celula();
    }

    public void inserir(int x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public static void main (String [] args) {
        ColetaLixo coletaLixo = new ColetaLixo ();
        while(true){
            coletaLixo.inserir(0);
        }
    }
}


