package Classes;

import java.util.HashMap;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class AssemblyParser {
    private static byte[] memory = new byte[1000];
    private static HashMap<String, Integer> labels = new HashMap<>();

    private static final HashMap<String, Integer> typeSizes = new HashMap<>();
    static {
        typeSizes.put(".word", 4);
        typeSizes.put(".half", 2);
        typeSizes.put(".byte", 1);
        typeSizes.put(".dword", 8);
    }

    public static void main(String[] args) {
        
        parseFile("exemplo.asm"); //Caminho aqui tem que ser relativo à pasta aonde o programa está sendo executado

        System.out.println(labels.get("array"));

        for (int i = 500; i < 540; i++) {
            System.out.println("memory[" + i + "] = " + memory[i]);

        }
    }
    
    public static void parseFile(String filename) {
        int textAddress = 0;
        int dataAddress = 500;

        try {

            List<String> lines = Files.readAllLines(Paths.get(filename));

            String currentSection = "";

            for (String line : lines) {
                // System.out.println(line);

                line = line.trim();
    
                if (line.isEmpty()) continue;

                if(line.startsWith("#")) continue; //Pula comentário

                if(line.equals(".data")) currentSection = ".data";
                else if(line.equals(".text")) currentSection = ".text";

                switch (currentSection) {
                    case ".data":
                    if(line.contains(":")){
                        try {
                            dataAddress = processDataLine(line, dataAddress);
                        } catch (Exception e) {
                            
                            System.err.println(e.getMessage());
                        }
                    }
                        break;
                    case ".text":
                        // Lógica para a seção .text
                        break;
                    default:
                        break;
                }

                
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());


        }
    }

    private static int processDataLine(String line, int currentAddress) throws Exception{
        //Considera que só tem labels no .data
            String partes[] = line.split(":");
            String labelName = partes[0].trim();

            labels.put(labelName, currentAddress);

            String specs[] = partes[1].trim().replace(",", "").split(" ");

            Integer dataSize = typeSizes.get(specs[0]);

            if(dataSize == null){
                throw new Exception("Tipo de dado não reconhecido: " + specs[0]);
                
                }
            for(int i = 1; i < specs.length; i++){
                int num = Integer.parseInt(specs[i]);

                currentAddress = writeData(num, currentAddress, specs[0]);
            }

            return currentAddress;
            

    }

    private static int writeData(int num, int currentAddress, String dataType){
        Integer dataSize = typeSizes.get(dataType);
        
        if(dataSize != null) {
            // Versão otimizada: usa o dataSize diretamente
            for(int j = 0; j < dataSize; j++){
                memory[currentAddress] = (byte)(num % 256);
                num = num / 256;
                currentAddress++;
            }
        } else {
            System.err.println("Tipo de dado não suportado: " + dataType);
        }

        return currentAddress;
    }
        
       
}
