package Classes;

public class Memoria {

    protected static byte[] memory = new byte[1000];

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
    
}
