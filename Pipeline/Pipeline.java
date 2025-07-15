package Pipeline;

import Estagios.Execution;
import Estagios.InstructionFetch;
import Estagios.ReadMemory;
import Estagios.ReadRegister;
import Estagios.WriteBack;

public class Pipeline {

    private static int pc = 0;

    
    // public static void main(String[] args) {
    //     System.out.println("fgjdndvd");
    // }

    public static void runCycle(){

        WriteBack.execute();

        ReadMemory.execute();

        Execution.execute();

        ReadRegister.decodeAndRead();

        InstructionFetch.buscaProximaInstrucao();
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
