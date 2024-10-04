/**
 * Teste e Analise de métodos de ordenação QuickSort
 * @author  Davi Cândido de Almeida 
 * @version 1 02/10/2024
 */

import java.util.*;


public class VariacaoPivoQuickshort{

    public static void main(String[] args) {

     //Delcaracao de variaveis para medição de tempo
      double inicioNano, fimNano;
      double inicioSeg, fimSeg;
      

        Scanner sc = new Scanner(System.in);
        int tamanhoArray;
        int lugarPivo;
        int formaPreench;



        do{
            System.out.println("\nEscolha um tamanho de array: ");
            System.out.println("100 - Pequeno");
            System.out.println("1000 - Médio ");
            System.out.println("10000 - Grande");
            System.out.println("DIGITE OUTRO Nº - Personalizado");
            System.out.println("0 ou < 1 - Parar programa");
            System.out.print("Opção: ");
            tamanhoArray = sc.nextInt();

            if(tamanhoArray > 0){
            System.out.println("\nO tamanho escolhido foi " + tamanhoArray);

                Quicksort array = new Quicksort(tamanhoArray);

                System.out.println("\nEscolha uma forma de Preenchimento: ");
                System.out.println("1 - Ordenado");
                System.out.println("2 - Quase Ordenados ");
                System.out.println("3 - Aleatório");
                System.out.print("Opção: ");

                formaPreench = sc.nextInt();

                if(formaPreench == 1) array.crescente();
                else if(formaPreench == 2){ array.crescente();
                    Random rand = new Random();

                    for(int i = 0; i < tamanhoArray / 10; i++){
                        array.swap((Math.abs(rand.nextInt()) % (tamanhoArray-1)), (Math.abs(rand.nextInt()) % (tamanhoArray-1)));
                    }
                }
                else if(formaPreench == 3) array.aleatorio();
                else { System.out.println("\nOpção invalida"); break;}

                System.out.println("\nEscolha a entrada do pivo: ");
                System.out.println("1 - Primeiro elemento");
                System.out.println("2 - Ultimo elemento ");
                System.out.println("3 - Elemento aleatório");
                System.out.println("4 - Mediana");
                System.out.print("Opção: ");
                lugarPivo = sc.nextInt();




                    if(lugarPivo == 1){

                        System.out.println("\nArray antes: ");
                        array.mostrar();

                        inicioSeg = array.now();
                        inicioNano = array.nanoNow();
                        array.QuickSortFirstPivot(0, tamanhoArray -1);
                        fimNano = array.nanoNow();
                        fimSeg = array.now();


                        System.out.println("\nArray depois: ");
                        array.mostrar();

                        System.out.println("\nO tamanho escolhido foi " + tamanhoArray);
                        System.out.println("\nTempo para ordenar: " + (fimSeg - inicioSeg)/1000.0 + " s");
                        System.out.println("Tempo para ordenar: " + (fimNano - inicioNano) + " nanosegundos");

                        System.out.println("\nisOrdenado: " + array.isOrdenado());


                    }else if(lugarPivo == 2){

                        System.out.println("\nArray antes: ");
                        array.mostrar();

                        inicioSeg = array.now();
                        inicioNano = array.nanoNow();
                        array.QuickSortLastPivot(0, tamanhoArray -1);
                        fimNano = array.nanoNow();
                        fimSeg = array.now();



                        System.out.println("\nArray depois: ");
                        array.mostrar();

                        System.out.println("\nO tamanho escolhido foi " + tamanhoArray);
                        System.out.println("\nTempo para ordenar: " + (fimSeg - inicioSeg)/1000.0 + " s");
                        System.out.println("Tempo para ordenar: " + (fimNano - inicioNano) + " nanosegundos");

                        System.out.println("\nisOrdenado: " + array.isOrdenado());


                    } else if(lugarPivo == 3){

                        System.out.println("\nArray antes: ");
                        array.mostrar();

                        inicioSeg = array.now();
                        inicioNano = array.nanoNow();
                        array.QuickSortRandomPivot(0, tamanhoArray -1);
                        fimNano = array.nanoNow();
                        fimSeg = array.now();


                        System.out.println("\nArray depois: ");
                        array.mostrar();

                        System.out.println("\nO tamanho escolhido foi " + tamanhoArray);
                        System.out.println("\nTempo para ordenar: " + (fimSeg - inicioSeg)/1000.0 + " s");
                        System.out.println("Tempo para ordenar: " + (fimNano - inicioNano) + " nanosegundos");

                        System.out.println("\nisOrdenado: " + array.isOrdenado());


                    } else if(lugarPivo == 4){

                        System.out.println("\nArray antes: ");
                        array.mostrar();

                        inicioSeg = array.now();
                        inicioNano = array.nanoNow();
                        array.QuickSortMedianOfThree(0, tamanhoArray -1);  // • Mediana de três elementos (início, meio e fimNano)
                        fimNano = array.nanoNow();
                        fimSeg = array.now();


                        System.out.println("\nArray depois: ");
                        array.mostrar();

                        System.out.println("\nO tamanho escolhido foi " + tamanhoArray);
                        System.out.println("\nTempo para ordenar: " + (fimSeg - inicioSeg)/1000.0 + " s");
                        System.out.println("Tempo para ordenar: " + (fimNano - inicioNano) + " nanosegundos");

                        System.out.println("\nisOrdenado: " + array.isOrdenado());

                    } else{
                         System.out.println("\nOpção invalida"); break;
                    }
                }
            }while(tamanhoArray !=0);


            sc.close();

    }  
}