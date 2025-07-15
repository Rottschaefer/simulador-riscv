package Pipeline;

import Estagios.InstructionFetch;
import Estagios.ReadRegister;

public class Pipeline {

    private static int pc = 0;

    
    // public static void main(String[] args) {
    //     System.out.println("fgjdndvd");
    // }

    public static void runCycle(){
        InstructionFetch.buscaProximaInstrucao();
        ReadRegister.decodeAndRead();
    }

    public static void setPc(int newPc) {
        if (newPc % 4 == 0) {
            pc = newPc;
        } else {
            throw new IllegalArgumentException("PC deve ser múltiplo de 4.");
        }
    }

    public static int getPC(){
        return pc;
    }

}
