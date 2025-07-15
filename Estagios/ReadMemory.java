package Estagios;

import Pipeline.EXMEM;
import Pipeline.MEMWB;
import UnidadeFuncionais.Memoria;

public class ReadMemory {
    
    public static void execute() {
        // Ler os sinais de controle e dados do registrador EX/MEM
        boolean memRead = EXMEM.isMemRead();
        boolean memWrite = EXMEM.isMemWrite();
        int aluResult = EXMEM.getAluResult();
        int writeData = EXMEM.getWriteData();

        int readData = 0; // Valor padrão

        // Se for uma instrução de leitura da memória (lw)
        if (memRead) {
            // Acessa a memória para ler o dado
            readData = Memoria.readWord(aluResult);
        }

        // Se for uma instrução de escrita na memória (sw)
        if (memWrite) {
            // Acessa a memória para escrever o dado
            Memoria.writeWord(aluResult, writeData);
        }

        // Propagar os dados e sinais de controle para o registrador MEM/WB
        MEMWB.setMEMWBInstruction(EXMEM.getEXMEMInstruction());
        MEMWB.setMemToReg(EXMEM.isMemToReg());
        MEMWB.setRegWrite(EXMEM.isRegWrite());
        MEMWB.setMemoryReadData(readData);
        MEMWB.setAluResult(aluResult);
        MEMWB.setRd(EXMEM.getRd());
    }
}
