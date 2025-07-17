
import Auxiliares.AssemblyParser;
import Auxiliares.Decoder;
import Pipeline.EXMEM;
import Pipeline.IDEX;
import Pipeline.IFID;
import Pipeline.MEMWB;
import Pipeline.Pipeline;
import UnidadeFuncionais.BancoRegistradores;
import UnidadeFuncionais.Memoria;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Processador {


    public static void main(String[] args){
        BancoRegistradores registersl = new BancoRegistradores();
        Memoria memory = new Memoria();
        try{
            String[] memoria = memory.getMemoria();
            File arquivo = new File("saida.out");
            arquivo.createNewFile();
            FileWriter fw = new FileWriter ("saida.out");
            int regs[]=registersl.getRegisters();  
            
            //Linhas de código para a interface(Swing)
            //JFrame frame = new JFrame();
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setSize(500, 500);
            //JPanel painel = new JPanel();
            //painel.setSize(250, 250);
            //painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
            //JLabel cabecalho = new JLabel();
            //JLabel IFIDl = new JLabel();
            //JLabel IFEXl = new JLabel();
            //JLabel EXMEMl = new JLabel();
            //JLabel MEMWBl = new JLabel();
            //JLabel Joao = new JLabel();
            //Joao.setText("Hello World!");
            //painel.add(cabecalho); painel.add(IFIDl); painel.add(IFEXl); painel.add(EXMEMl); painel.add(MEMWBl); painel.add(Joao);
            //frame.add(painel);
            //frame.setVisible(true);

            AssemblyParser.parseFile("exemplo.asm");
            Pipeline.setPc(0);

            for(int i = 0; i < 7; i++){
                Pipeline.runCycle();


            fw.write("\n=== Pipeline Stages " + "ciclo " + i + " ===" + "\n");
            fw.write(("IF/ID: " + Decoder.decodeInstruction(IFID.getIFIDInstruction())) + "\n");
            fw.write(("ID/EX: " + Decoder.decodeInstruction(IDEX.getIDEXInstruction())) + "\n");
            fw.write(("EX/MEM: " + Decoder.decodeInstruction(EXMEM.getEXMEMInstruction())) + "\n");
            fw.write(("MEM/WB: " + Decoder.decodeInstruction(MEMWB.getMEMWBInstruction())) + "\n");
            fw.write("=====================" + "\n");
            //System.out.println("=== Pipeline Stages " + "ciclo " + i + " ===" + "\n");
            //System.out.println(("IF/ID: " + Decoder.decodeInstruction(IFID.getIFIDInstruction())) + "\n");
            //System.out.println(("ID/EX: " + Decoder.decodeInstruction(IDEX.getIDEXInstruction())) + "\n");
            //System.out.println(("EX/MEM: " + Decoder.decodeInstruction(EXMEM.getEXMEMInstruction())) + "\n");
            //System.out.println(("MEM/WB: " + Decoder.decodeInstruction(MEMWB.getMEMWBInstruction())) + "\n");
            //System.out.println("=====================" + "\n");
            //System.out.println(i);

            fw.write("=== Registradores(ciclo " + i + "): ===" + "\n");
            for(int j=0; j<32; j++){
                //if(regs[j]!=0){
                    fw.write("Registradores[" + j +"] = " + regs[j] + "\n");
                    fw.write("=====================" + "\n");
                //}
            }
            fw.write("\n");

            fw.write("=== Memória(ciclo " + i + "): ===" + "\n");
            for (int k=0; k<1000; k++){
                if (memoria[k]!=null){
                    String linha = memoria[k];
                    int conv = Integer.parseInt(linha, 2);
                    if(conv!=0){
                    String convhex = Integer.toHexString(conv).toUpperCase();
                    fw.write(convhex + "; " );
                    }
                }
            }
            fw.write("\n");
            fw.write("=====================" + "\n");
            //try{
                //Thread.sleep(500);
            //}
            //catch(Exception e){
                //System.out.println("Error!");
            //}


        }
        
        fw.close();
    //public static void printPipelineStages() {
        //cabecalho.setText("=== Pipeline Stages ===");
        //System.out.println("IF/ID: " + Decoder.decodeInstruction(IFID.getIFIDInstruction()));
        //System.out.println("ID/EX: " + Decoder.decodeInstruction(IDEX.getIDEXInstruction()));
        //System.out.println("EX/MEM: " + Decoder.decodeInstruction(EXMEM.getEXMEMInstruction()));
        //System.out.println("MEM/WB: " + Decoder.decodeInstruction(MEMWB.getMEMWBInstruction()));
        //System.out.println("=====================");
    //}
        }
        catch(IOException e){
             System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("END!");        
    }
}
