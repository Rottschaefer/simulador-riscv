package UnidadeFuncionais;

import Auxiliares.AssemblyParser;
import Auxiliares.Encoder;

public class Memoria {

    public static byte[] memory = new byte[1000];

    public static void main(String[] args){
        AssemblyParser.parseFile("exemplo.asm");
        printMemoria();

    }

    public static void printMemoria(){

        for(int i=0; i<500; i+=4){

            String address = "0x" + Integer.toHexString(i);

            //Usa mascara para transformar um byte com sinal para um int sem sinal
            String content = Integer.toHexString((memory[i] & 0xFF) +
                      ((memory[i+1] & 0xFF) << 8) +
                      ((memory[i+2] & 0xFF) << 16) +
                      ((memory[i+3] & 0xFF) << 24));

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
        int value = (memory[address] & 0xFF) +
                    ((memory[address + 1] & 0xFF) << 8) +
                    ((memory[address + 2] & 0xFF) << 16) +
                    ((memory[address + 3] & 0xFF) << 24);

        return Encoder.intToBinary(value, 32);
    }
    
}
