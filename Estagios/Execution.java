package Estagios;

import Pipeline.IDEX;
import UnidadeFuncionais.ALU;
import Pipeline.EXMEM;

public class Execution {

    /**
     * Realiza o estágio de Execução (EX) do pipeline.
     * 1. Seleciona os operandos para a ALU.
     * 2. Executa a operação na ALU.
     * 3. Calcula o resultado do branch (se aplicável).
     * 4. Propaga os sinais de controle e dados para o registrador EX/MEM.
     */
    public static void execute() {
        // --- 1. Seleção de Operandos para a ALU ---
        int operand1 = IDEX.getReadData1();
        int operand2;

        if (IDEX.isAluSrc()) {
            // A segunda entrada da ALU é o imediato

            operand2 = IDEX.getImmediate();

        } else {
            // A segunda entrada da ALU é o valor do registrador rs2
            operand2 = IDEX.getReadData2();
        }

        // --- 2. Execução da ALU ---
        String aluOp = IDEX.getAluOp();
        int aluResult = ALU.execute(aluOp, operand1, operand2);

        System.out.println("Volta da ALU:" + aluResult);

        // --- 3. Lógica de Branch ---
        boolean zeroFlag = (aluResult == 0); // O zero flag é ativado se o resultado da subtração for 0
        boolean branchTaken = false;
        
        // A decisão do branch é tomada aqui
        // if (IDEX.isBranch()) {
        //     switch (aluOp) {
        //         case "beq":
        //             if (zeroFlag) branchTaken = true;
        //             break;
        //         case "bne":
        //             if (!zeroFlag) branchTaken = true;
        //             break;
        //         case "blt":
        //             if (aluResult < 0) branchTaken = true; // (rs1 - rs2) < 0  => rs1 < rs2
        //             break;
        //         case "bge":
        //             if (aluResult >= 0) branchTaken = true; // (rs1 - rs2) >= 0 => rs1 >= rs2
        //             break;
        //     }
        // }
        
        // // O endereço de destino do branch (PC + imediato) é calculado aqui
        // int branchTargetAddress = IDEX.getPc() + IDEX.getImmediate();

        // --- 4. Propagação de dados e sinais para EX/MEM ---
        
        EXMEM.setEXMEMInstruction(IDEX.getIDEXInstruction());
        // Sinais de controle para a fase MEM
        EXMEM.setMemRead(IDEX.isMemRead());
        EXMEM.setMemWrite(IDEX.isMemWrite());
        EXMEM.setBranch(IDEX.isBranch()); // Propaga o sinal de branch
        
        // Sinais de controle para a fase WB
        EXMEM.setRegWrite(IDEX.isRegWrite());
        EXMEM.setMemToReg(IDEX.isMemToReg());

        // Dados a serem passados
        EXMEM.setAluResult(aluResult);
        EXMEM.setWriteData(IDEX.getReadData2()); // Dado a ser escrito na memória (para SW)
        EXMEM.setRd(IDEX.getRd()); // Endereço do registrador de destino
        
        // Informações do Branch
        EXMEM.setBranchTaken(branchTaken);
        // EXMEM.setBranchTargetAddress(branchTargetAddress);
    }
}
