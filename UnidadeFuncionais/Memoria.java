package UnidadeFuncionais;

import Auxiliares.AssemblyParser;

public class Memoria {

    public static String[] memory = new String[1000];

    public static void main(String[] args){
        AssemblyParser.parseFile("exemplo.asm");
        printMemoria();

    }

    public static void printMemoria(){

        for(int i=0; i<20; i+=4){

            String address = "0x" + Integer.toHexString(i);

            //Usa mascara para transformar um byte com sinal para um int sem sinal
            String content = (memory[i]) + (memory[i + 1]) + (memory[i + 2]) + (memory[i + 3]);

            if(!content.equals("0")) System.out.println(address + " - " + content);
        }
    }

    public static String getInstruction(int address){
        if (address < 0 || address + 3 >= memory.length) {
            throw new IndexOutOfBoundsException("Endereço fora do limite da memória.");
        }
        else if(address%4 != 0){
            return "";
        }
        String value = (memory[address]) + (memory[address + 1]) + (memory[address + 2]) + (memory[address + 3]);

        return value;
    }
    
}
