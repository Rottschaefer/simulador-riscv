package Auxiliares;

import java.util.HashMap;
import java.util.List;

import UnidadeFuncionais.Memoria;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class AssemblyParser {
    protected static HashMap<String, Integer> labels = new HashMap<>();

    private static final HashMap<String, Integer> typeSizes = new HashMap<>();
    static {
        typeSizes.put(".word", 4);
        typeSizes.put(".half", 2);
        typeSizes.put(".byte", 1);
        typeSizes.put(".dword", 8);
    }

    public static void main(String[] args) {
        
        parseFile("exemplo.asm"); //Caminho aqui tem que ser relativo à pasta aonde o programa está sendo executado

        // System.out.println(labels.get("array"));

        for (int i = 0; i < 100; i++) {
            System.out.println("memory[" + i + "] = " + Memoria.memory[i]);

        }
    }
    
    public static void parseFile(String filename) {
        int textAddress = 0;
        int dataAddress = 500;

        //Serão 2 passadas no arquivo. Uma aloca os dados do .data e mapeia todos os labels.
        //Na segunda cada instrucao será codificada usando o Encoder já com os labels mapeados

        try {

            List<String> lines = Files.readAllLines(Paths.get(filename));

            String currentSection = "";

            for (String line : lines) {

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
                        if (line.contains(":")) {
                            String labelName = line.split(":")[0].trim();
                            labels.put(labelName, textAddress);
                        } else if (!line.isEmpty()) {
                            // Guarda 4 bytes pra cada instrução dentro daquele label
                            Memoria.memory[textAddress] = -1;
                            textAddress += 4;
                        }
                        break;
                    default:
                        break;
                }

                
            }
            
            textAddress = 0; 
            currentSection = "";

            for (String line : lines) {
                line = line.trim();
                    
                if (line.isEmpty()) continue;
                if (line.startsWith("#")) continue;

                if (line.equals(".data")) {
                    currentSection = ".data";
                    continue;
                }
                if (line.equals(".text")) {
                    currentSection = ".text";
                    continue;
                }

                switch (currentSection) {
                    case ".text":
                        if (line.contains(":")) {
                            // Instrucoes precisam ser alocadas dentro do espaco do label definido no hashmap

                            String label = line.split(":")[0].trim();

                            textAddress = labels.get(label);
                            continue;
                        } 
                        else if (!line.isEmpty()) {
                            try {
                                String encodedInstruction = Encoder.encode_asm(line.trim());
                                    
                                //Passa a string pra int. É usado long pq o parseInt com strings muito longas aprsenta erros
                                int instructionInt = (int)(Long.parseLong(encodedInstruction, 2));
                                writeData(instructionInt, textAddress, ".word");
                                    
                                textAddress += 4;
                            } catch (Exception e) {
                                System.err.println("Erro ao processar instrução '" + line + "': " + e.getMessage());
                            }
                        }
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
            for(int j = 0; j < dataSize; j++){
                Memoria.memory[currentAddress] = (byte)(num % 256);
                num = num / 256;
                currentAddress++;
            }
        } else {
            System.err.println("Tipo de dado não suportado: " + dataType);
        }

        return currentAddress;
    }
        
       
}
