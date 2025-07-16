
import Auxiliares.AssemblyParser;
import Auxiliares.Decoder;
import Pipeline.EXMEM;
import Pipeline.IDEX;
import Pipeline.IFID;
import Pipeline.MEMWB;
import Pipeline.Pipeline;

public class Processador {


    public static void main(String[] args){
        
        AssemblyParser.parseFile("exemplo_copy.asm");
        Pipeline.setPc(0);

        for(int i = 0; i < 7; i++){
            Pipeline.runCycle();


            printPipelineStages();            


        }


    }
    public static void printPipelineStages() {

        
        System.out.println("=== Pipeline Stages ===");
        System.out.println("IF/ID: " + Decoder.decodeInstruction(IFID.getIFIDInstruction()) + " " + IDEX.getRs1() + " " + IDEX.getImmediate());
        System.out.println("ID/EX: " + Decoder.decodeInstruction(IDEX.getIDEXInstruction()));
        System.out.println("EX/MEM: " + Decoder.decodeInstruction(EXMEM.getEXMEMInstruction()));
        System.out.println("MEM/WB: " + Decoder.decodeInstruction(MEMWB.getMEMWBInstruction()));
        System.out.println("=====================");
    }
    

}
