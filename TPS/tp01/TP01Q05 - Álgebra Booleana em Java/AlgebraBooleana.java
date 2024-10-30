import java.util.Scanner;

public class AlgebraBooleana {


    public static String TrocaVariaveis(String entrada, int valor[]){

        String TrocaTroca = "";

        for(int i = 1; i < entrada.length(); i++){

            if(entrada.charAt(i) == 'A'){
                TrocaTroca += valor[1];
            }else if (entrada.charAt(i) == 'B'){
                TrocaTroca += valor[2];
            }else if (entrada.charAt(i) == 'C'){
                TrocaTroca += valor[3];
             }else{
                  TrocaTroca += entrada.charAt(i);
                }
        }

        return TrocaTroca;  
    }

    public static String Remove_Not(String entrada){

        String noNot = "";
        for(int i = 0; i < entrada.length(); i++){

            // verifica ocorrencia de not
            if (i + 5 < entrada.length()      && //Impede que acesse index inexistente
                entrada.charAt(i) == 'n'      &&
                entrada.charAt(i + 1) == 'o'  &&
                entrada.charAt(i + 2) == 't'  &&
                entrada.charAt(i + 3) == '('  &&
                entrada.charAt(i + 4) == '0'  &&
                entrada.charAt(i + 5) == ')') {
                
                noNot += '1';
                i += 5; // Avance de índice para pular o not()
            }else if(i + 5 < entrada.length()  && //Impede que acesse index inexistente
                entrada.charAt(i) == 'n'       &&
                entrada.charAt(i + 1) == 'o'   &&
                entrada.charAt(i + 2) == 't'   &&
                entrada.charAt(i + 3) == '('   &&
                entrada.charAt(i + 4) == '1'   &&
                entrada.charAt(i + 5) == ')'){
                
                noNot += '0';
                i += 5; // Avance de índice para pular o not()
            }else{
                noNot += entrada.charAt(i);
            }
    }

        return noNot;
    }


    public static String Remove_And(String entrada){

        String noAnd = "";
        for(int i = 0; i < entrada.length(); i++){

            // verifica ocorrencia de And
            if (i + 9 < entrada.length()      && //Impede que acesse index inexistente
                entrada.charAt(i) == 'a'      &&
                entrada.charAt(i + 1) == 'n'  &&
                entrada.charAt(i + 2) == 'd'  &&
                entrada.charAt(i + 3) == '('  &&


                entrada.charAt(i + 9) == ')') {

                    if(entrada.charAt(i + 4) == entrada.charAt(i + 8))
                        noAnd += entrada.charAt(i+4);
                    else
                        noAnd += '0';
                
                i += 9; // Avance de índice para pular o And()

            }else if(i + 13 < entrada.length()      && //Impede que acesse index inexistente
            entrada.charAt(i) == 'a'      &&
            entrada.charAt(i + 1) == 'n'  &&
            entrada.charAt(i + 2) == 'd'  &&
            entrada.charAt(i + 3) == '('  &&
                entrada.charAt(i + 4) != 'n'  &&
                entrada.charAt(i + 4) != 'o'  &&
                entrada.charAt(i + 4) != 'a'  &&


            entrada.charAt(i + 13) == ')'){

                if(entrada.charAt(i + 4) == entrada.charAt(i + 12) && entrada.charAt(i + 4) == entrada.charAt(i + 8))
                    noAnd += entrada.charAt(i+4);
                else
                    noAnd += '0';
        
                i += 13; // Avance de índice para pular o And()
            }
            else{
                noAnd += entrada.charAt(i);
            }
    }

        return noAnd;
    }

    public static String Remove_or(String entrada){

        String noOr = "";
        for(int i = 0; i < entrada.length(); i++){

            // verifica ocorrencia de And
            if (i + 8 < entrada.length()      && //Impede que acesse index inexistente
                entrada.charAt(i) == 'o'      &&
                entrada.charAt(i + 1) == 'r'  &&
                entrada.charAt(i + 2) == '('  &&
                

                entrada.charAt(i + 8) == ')') {

                    if(entrada.charAt(i + 3) == entrada.charAt(i + 7))
                        noOr += entrada.charAt(i+3);
                    else
                        noOr += '1';
                
                i += 8; // Avance de índice para pular o And()

            } else if(i + 12 < entrada.length()      && //Impede que acesse index inexistente
                       entrada.charAt(i) == 'o'      &&
                       entrada.charAt(i + 1) == 'r'  &&
                       entrada.charAt(i + 2) == '('  &&
                            entrada.charAt(i + 3) != 'o'  &&
                            entrada.charAt(i + 3) != 'a'  &&
                            entrada.charAt(i + 3) != 'n'  &&

                      entrada.charAt(i + 12) == ')'){

                    if(entrada.charAt(i + 3) == entrada.charAt(i + 11) && entrada.charAt(i + 3) == entrada.charAt(i + 7))
                        noOr += entrada.charAt(i+3);
                    else
                        noOr += '1';
                
                i += 12; // Avance de índice para pular o And()

                //Lidando com a excessão de um or de 8 letras
            }else if(i + 7 < entrada.length()      && //Impede que acesse index inexistente
            entrada.charAt(i) == 'o'      &&
            entrada.charAt(i + 1) == 'r'  &&
            entrada.charAt(i + 2) == '('  &&
                

            

            entrada.charAt(i + 7) == ')') {

                if(entrada.charAt(i + 3) == entrada.charAt(i + 6))
                    noOr += entrada.charAt(i+3);
                else
                    noOr += '1';
            
                i += 7; // Avance de índice para pular o And()

            }else if(i + 16 < entrada.length()      && //Impede que acesse index inexistente
            entrada.charAt(i) == 'o'      &&
            entrada.charAt(i + 1) == 'r'  &&
            entrada.charAt(i + 2) == '('  &&
                entrada.charAt(i + 3) != 'o'  &&
                entrada.charAt(i + 3) != 'a'  &&
                entrada.charAt(i + 3) != 'n'  &&
            

            entrada.charAt(i + 16) == ')') {

                if(entrada.charAt(i + 3) == '1' || entrada.charAt(i + 7) == '1' || entrada.charAt(i + 11) == '1' || entrada.charAt(i + 15) == '1')
                    noOr += '1';
                else
                    noOr += '0';
            
                i += 16; // Avance de índice para pular o And()

            }else{
                noOr += entrada.charAt(i);
            }
    }

        return noOr;
    }


    public static String AlgebraBooleana(String entrada, int valor[]) {
        
        String saida = "";


                //System.out.println(entrada);        
        saida = TrocaVariaveis(entrada, valor);
                //System.out.println(saida);

        //for(int i = 0; 1 < saida.length(); i++)
        for(int i = 0; i < 10; i++){
            saida = Remove_Not(saida);
                    //System.out.println(saida);

            saida = Remove_And(saida);
                    //System.out.println(saida);

            saida = Remove_or(saida);
                    //System.out.println(saida);
        }

        

        return saida;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        int valor[] = new int[4];
        boolean parar = true;
        String entrada = "";
        
        while (parar) {

            
            valor[0] = sc.nextInt();
            
            if (valor[0] != 0) {
                
                valor[1] = sc.nextInt();

                    valor[2] = sc.nextInt();
                    
                    if(valor[0] > 2)
                        valor[3] = sc.nextInt();

                entrada = sc.nextLine();


                System.out.println(AlgebraBooleana(entrada, valor));

            }else{
                parar = false;

            }

        }
        sc.close();
    }
}
