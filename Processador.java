import Classes.AssemblyParser;
import Classes.Pipeline;
import Classes.Estagios.InstructionFetch;
import Classes.Memoria;

public class Processador {

    public static byte memoria[] = Classes.Memoria.memory;

    public static void main(String[] args){
        run();
    }
    
    public static void run(){

        AssemblyParser.parseFile("exemplo.asm");
        Pipeline.setPc(0);

        //Pega próxima instrução na memória e aloca no registrador IF/ID
        InstructionFetch.buscaProximaInstrucao(); 

        System.out.println(Pipeline.IFID.getIFID());
        System.out.println(Memoria.getInstruction(10));
        // Memoria.printMemoria();
        
    } 
}
