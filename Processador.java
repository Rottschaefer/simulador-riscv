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
import java.awt.Font;

public class Processador {

    
    public static void main(String[] args){
        BancoRegistradores registersl = new BancoRegistradores();
        Memoria memory = new Memoria();
        try{
            String[] memoria = memory.getMemoria();
            File arquivo = new File("saida.out");
            arquivo.createNewFile();
            FileWriter fw = new FileWriter ("saida.out");
            
            //Linhas de código para a interface(Swing)
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1500, 1000);
            JPanel painel = new JPanel();
            painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

            // CRIAR O SCROLL PANE
            JScrollPane scrollPane = new JScrollPane(painel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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

            for(int i = 0; i < 15; i++){ // Aumentar de 7 para 10
                Pipeline.runCycle();
                
                // CAPTURAR OS REGISTRADORES ATUALIZADOS A CADA CICLO
                int regs[] = registersl.getRegisters(); // <- MOVER PARA AQUI

                fw.write("\n=== Pipeline Stages " + "ciclo " + i + " ===" + "\n");
                fw.write(("IF/ID: " + Decoder.decodeInstruction(IFID.getIFIDInstruction())) + "\n");
                fw.write(("ID/EX: " + Decoder.decodeInstruction(IDEX.getIDEXInstruction())) + "\n");
                fw.write(("EX/MEM: " + Decoder.decodeInstruction(EXMEM.getEXMEMInstruction())) + "\n");
                fw.write(("MEM/WB: " + Decoder.decodeInstruction(MEMWB.getMEMWBInstruction())) + "\n");
                fw.write("=====================" + "\n");

                JLabel cabecalho = new JLabel();
                cabecalho.setText("\n=== Pipeline Stages " + "ciclo " + i + " ===" + "\n");
                cabecalho.setFont(new Font("Arial", Font.BOLD, 12));
                JLabel IFIDl = new JLabel();
                IFIDl.setText(("IF/ID: " + Decoder.decodeInstruction(IFID.getIFIDInstruction())) + "\n");
                IFIDl.setFont(new Font("Arial", Font.BOLD, 12));
                JLabel IDEXl = new JLabel();
                IDEXl.setText(("ID/EX: " + Decoder.decodeInstruction(IDEX.getIDEXInstruction())) + "\n");
                IDEXl.setFont(new Font("Arial", Font.BOLD, 12));
                JLabel EXMEMl = new JLabel();
                EXMEMl.setText(("EX/MEM: " + Decoder.decodeInstruction(EXMEM.getEXMEMInstruction())) + "\n");
                EXMEMl.setFont(new Font("Arial", Font.BOLD, 12));
                JLabel MEMWBl = new JLabel();
                MEMWBl.setText(("MEM/WB: " + Decoder.decodeInstruction(MEMWB.getMEMWBInstruction())) + "\n");
                MEMWBl.setFont(new Font("Arial", Font.BOLD, 12));
                JLabel rodape = new JLabel();
                rodape.setText("=====================" + "\n");
                rodape.setFont(new Font("Arial", Font.BOLD, 12));
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
                    fw.write("Registradores[" + j +"] = " + regs[j] + "\n");
                    fw.write("=====================" + "\n");
                }
                
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
                }
            
            // CAPTURAR REGISTRADORES APÓS TODOS OS CICLOS
            int[] regs = registersl.getRegisters(); // <- UMA ÚNICA DECLARAÇÃO
            
            // Agora criar os labels do Swing com valores corretos
            JLabel cabecalhoReg = new JLabel();
            cabecalhoReg.setText("=== Registradores ===" + "\n");
            cabecalhoReg.setFont(new Font("Arial", Font.BOLD, 10));
            JLabel bancoReg1 = new JLabel();
            JLabel bancoReg2 = new JLabel();
            JLabel bancoReg3 = new JLabel();
            JLabel bancoReg4 = new JLabel();
            String linhaLabel1 = "";
            String linhaLabel2 = "";
            String linhaLabel3 = "";
            String linhaLabel4 = "";
            for(int j=0; j<32; j++){
                    if(j<8){
                    linhaLabel1 = (linhaLabel1 + "Registradores[" + j +"] = " + regs[j] + ";  ");
                    }
                    if(j>=8 && j<16){
                    linhaLabel2 = (linhaLabel2 + "Registradores[" + j +"] = " + regs[j] + ";  ");
                    }
                    if(j>=16 && j<24){
                    linhaLabel3 = (linhaLabel3 + "Registradores[" + j +"] = " + regs[j] + ";  ");
                    }
                    if(j>=24){
                    linhaLabel4 = (linhaLabel4 + "Registradores[" + j +"] = " + regs[j] + ";  ");
                    }
            }
            bancoReg1.setText(linhaLabel1);
            bancoReg1.setFont(new Font("Arial", Font.BOLD, 12));
            bancoReg2.setText(linhaLabel2);
            bancoReg2.setFont(new Font("Arial", Font.BOLD, 12));
            bancoReg3.setText(linhaLabel3);
            bancoReg3.setFont(new Font("Arial", Font.BOLD, 12));
            bancoReg4.setText(linhaLabel4);
            bancoReg4.setFont(new Font("Arial", Font.BOLD, 12));
            painel.add(cabecalhoReg);
            painel.add(bancoReg1);
            painel.add(bancoReg2);
            painel.add(bancoReg3);
            painel.add(bancoReg4);
            fw.close();
            frame.add(scrollPane); // <- MUDAR AQUI
            frame.setVisible(true);

        
        // REMOVER as linhas duplicadas no final:
        // fw.close();
        // frame.add(painel);
        // frame.setVisible(true);

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