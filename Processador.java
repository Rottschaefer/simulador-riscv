
import Auxiliares.AssemblyParser;
import Auxiliares.Decoder;
import Pipeline.IDEX;
import Pipeline.IFID;
import Pipeline.Pipeline;

public class Processador {


    public static void main(String[] args){
        
        AssemblyParser.parseFile("exemplo.asm");
        Pipeline.setPc(0);

        for(int i = 0; i < 10; i++){
            Pipeline.runCycle();

            String instruction = IFID.getIFIDInstruction();
            System.out.println(Decoder.decodeInstruction(instruction) +", "+ IDEX.getRs1()  +", "+ IDEX.getRs2());


        }


    }
    

}
