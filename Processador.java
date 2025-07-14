import Auxiliares.AssemblyParser;
import Estagios.InstructionFetch;
import Pipeline.Pipeline;
import UnidadeFuncionais.Memoria;

public class Processador {

    public static byte memoria[] = UnidadeFuncionais.Memoria.memory;

    public static void main(String[] args){
        
        AssemblyParser.parseFile("exemplo.asm");
        Pipeline.setPc(0);

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
