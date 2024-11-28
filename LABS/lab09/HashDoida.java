class HashDoida{


        class Lista{

        }
        class Arvore{

        }


        class T1{
            int [] tabela;
            T2 t2;

            public void inserir(int x){
                int i = hash(x);
                if(i == null){
                    tabela[i] = x;
                }else{
                    t2.inserir(x);
                }
            }

        }

        class T2{
            T3 t3;
            Lista lista;
            Arvore arvore;

            public void inserir(int x){
                int i = hash(x);
                if(i == 0){
                    t3.inserir(x);
                }else if(i == 1){
                    lista.inserir(x);
                }else{
                    arvore.inserir(x);
                }
            }
        }

        class T3{
            int[] tabela;
            Arvore arvore;

            public void inserir(int x){
                int i = hash(x);
                if(i == null){
                    tabela[i] = x;
                }else{
                    arvore.inserir(x);
                }
            }
        }



}