
import java.util.*;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;


public class arquivo {
    
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);

        String filename = "numeros.txt";
        
        int n = sc.nextInt();

        try(RandomAccessFile arquivo = new RandomAccessFile(filename, "rw")){
            
            // Verifica se o próximo valor é um INT
            for (int i = 0; i < n; i++) {
                 if(sc.hasNextDouble()) {
                    double num = sc.nextDouble();
                    arquivo.writeDouble(num);
                } else {
                    // Se não for um double nem um int, tenta ler como String e converter
                    String numSTR = sc.next();
                    double num = Double.parseDouble(numSTR);
                    arquivo.writeDouble(num);
                }
            }

            
            double num2 = 0.0;
            for(int i = n-1; i>= 0;i-- ){
                arquivo.seek(i*8);
                num2 = arquivo.readDouble();
                if( num2 == (int)num2)
                    System.out.println((int) num2);
                else
                    System.out.println(num2);
            }

            arquivo.close();

        }catch(IOException err){
            System.err.println("Erro no arquivo criado" + err.getMessage());
        }



        
        
    }
}
