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
import javax.swing.*;
import java.util.*;


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
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 1000);
            JPanel painel = new JPanel();
            painel.setSize(250, 150);
            painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

            // Substituir entrada do terminal por caixa de diálogo
            String nome_arquivo = JOptionPane.showInputDialog(
                frame, 
                "Digite o nome do arquivo (incluindo o .asm):", 
                "Selecionar Arquivo", 
                JOptionPane.QUESTION_MESSAGE
            );

            // Verificar se o usuário cancelou ou não digitou nada
            if (nome_arquivo == null || nome_arquivo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nenhum arquivo selecionado. Encerrando programa.");
                return;
            }

            nome_arquivo = nome_arquivo.trim();

            AssemblyParser.parseFile(nome_arquivo);
            Pipeline.setPc(0);

            for(int i = 0; i < 7; i++){
                Pipeline.runCycle();


            fw.write("\n=== Pipeline Stages " + "ciclo " + i + " ===" + "\n");
            fw.write(("IF/ID: " + Decoder.decodeInstruction(IFID.getIFIDInstruction())) + "\n");
            fw.write(("ID/EX: " + Decoder.decodeInstruction(IDEX.getIDEXInstruction())) + "\n");
            fw.write(("EX/MEM: " + Decoder.decodeInstruction(EXMEM.getEXMEMInstruction())) + "\n");
            fw.write(("MEM/WB: " + Decoder.decodeInstruction(MEMWB.getMEMWBInstruction())) + "\n");
            fw.write("=====================" + "\n");

            JLabel cabecalho = new JLabel();
            cabecalho.setText("\n=== Pipeline Stages " + "ciclo " + i + " ===" + "\n");
            JLabel IFIDl = new JLabel();
            IFIDl.setText(("IF/ID: " + Decoder.decodeInstruction(IFID.getIFIDInstruction())) + "\n");
            JLabel IDEXl = new JLabel();
            IDEXl.setText(("ID/EX: " + Decoder.decodeInstruction(IDEX.getIDEXInstruction())) + "\n");
            JLabel EXMEMl = new JLabel();
            EXMEMl.setText(("EX/MEM: " + Decoder.decodeInstruction(EXMEM.getEXMEMInstruction())) + "\n");
            JLabel MEMWBl = new JLabel();
            MEMWBl.setText(("MEM/WB: " + Decoder.decodeInstruction(MEMWB.getMEMWBInstruction())) + "\n");
            JLabel rodape = new JLabel();
            rodape.setText("=====================" + "\n");
            painel.add(cabecalho); painel.add(IFIDl); painel.add(IDEXl); painel.add(EXMEMl); painel.add(MEMWBl); painel.add(rodape);

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
        frame.add(painel);
        frame.setVisible(true);

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