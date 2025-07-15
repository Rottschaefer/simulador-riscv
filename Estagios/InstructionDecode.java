package Estagios;

import Auxiliares.Decoder;
import Pipeline.IFID;
import Pipeline.IDEX;
import UnidadeFuncionais.BancoRegistradores;

public class InstructionDecode {

    /**
     * Realiza o estágio de Decodificação da Instrução e Leitura de Registradores (ID).
     * 1. Decodifica a instrução vinda de IF/ID.
     * 2. Gera os sinais de controle para as fases futuras.
     * 3. Lê os valores dos registradores de origem (rs1, rs2).
     * 4. Estende o sinal do imediato.
     * 5. Passa todos os dados e sinais para o registrador ID/EX.
     */
    
    public static void decodeAndRead() {
        String instruction = IFID.getIFIDInstruction();
        if (instruction == null || instruction.length() != 32) {
            return;
        }

        // Decodifica o nome e o formato da instrução
        String instructionName = Decoder.decodeInstruction(instruction);
        String format = Decoder.getInstructionType(instruction);


        // Gera os sinais de controle com base no nome da instrução
        // UnidadeDeControle.generateControlSignals(instructionName);

        // Variáveis para os operandos
        int rs1 = 0, rs2 = 0, rd = 0, immediate = 0;
        int readData1 = 0, readData2 = 0;

        // Decodifica os operandos com base no formato da instrução
        if (format != null) {
            switch (format) {
                case "R":
                    rs1 = Integer.parseInt(instruction.substring(12, 17), 2);
                    rs2 = Integer.parseInt(instruction.substring(7, 12), 2);
                    rd = Integer.parseInt(instruction.substring(20, 25), 2);
                    readData1 = BancoRegistradores.getRegisterValue(rs1);
                    readData2 = BancoRegistradores.getRegisterValue(rs2);
                    IDEX.setAluOp(instructionName);
                    IDEX.setAluSrc(false); //Segundo operando vem do registrador
                    IDEX.setRegWrite(true);   // Escreve no registrador destino
                    IDEX.setMemRead(false);   // Não lê da memória
                    IDEX.setMemWrite(false);  // Não escreve na memória
                    IDEX.setMemToReg(false);  // Resultado da ALU vai para o registrador (false: ALU, true: Mem)
                    break;

                case "I-Arith":
                case "I-Load":
                case "I-JALR":
                    rs1 = Integer.parseInt(instruction.substring(12, 17), 2);
                    rd = Integer.parseInt(instruction.substring(20, 25), 2);
                    readData1 = BancoRegistradores.getRegisterValue(rs1);
                    // Extrai e estende o sinal do imediato de 12 bits
                    immediate = signExtend(instruction.substring(0, 12));
                    // Sinais de controle para instruções I
                    if (format.equals("I-Arith")) {
                        IDEX.setAluOp(instructionName);
                        IDEX.setAluSrc(true);     // Usa imediato como segundo operando
                        IDEX.setRegWrite(true);   // Escreve no registrador destino
                        IDEX.setMemRead(false);   // Não lê da memória
                        IDEX.setMemWrite(false);  // Não escreve na memória
                        IDEX.setMemToReg(false);  // Resultado da ALU vai para o registrador
                    } else if (format.equals("I-Load")) {
                        IDEX.setAluOp("add");     // Calcula endereço (base + offset)
                        IDEX.setAluSrc(true);     // Usa imediato como offset
                        IDEX.setRegWrite(true);   // Escreve no registrador destino
                        IDEX.setMemRead(true);    // Lê da memória
                        IDEX.setMemWrite(false);  // Não escreve na memória
                        IDEX.setMemToReg(true);   // Dado da memória vai para o registrador
                    } else if (format.equals("I-JALR")) {
                        IDEX.setAluOp("add");     // Calcula endereço de salto
                        IDEX.setAluSrc(true);     // Usa imediato
                        IDEX.setRegWrite(true);   // Salva PC+4 no registrador
                        IDEX.setMemRead(false);   // Não lê da memória
                        IDEX.setMemWrite(false);  // Não escreve na memória
                    }
                    break;

                case "S":
                    rs1 = Integer.parseInt(instruction.substring(12, 17), 2);
                    rs2 = Integer.parseInt(instruction.substring(7, 12), 2);
                    readData1 = BancoRegistradores.getRegisterValue(rs1);
                    readData2 = BancoRegistradores.getRegisterValue(rs2);
                    String imm_11_5 = instruction.substring(0, 7);
                    String imm_4_0 = instruction.substring(20, 25);
                    immediate = signExtend(imm_11_5 + imm_4_0);

                    IDEX.setAluOp("add");     // Calcula endereço (base + offset)
                    IDEX.setAluSrc(true);     // Usa imediato como offset
                    IDEX.setRegWrite(false);  // Não escreve no registrador destino
                    IDEX.setMemRead(false);   // Não lê da memória
                    IDEX.setMemWrite(true);   // Escreve na memória
                    IDEX.setMemToReg(false);  // Não importa, não escreve em registrador
                    break;

                case "B":
                    rs1 = Integer.parseInt(instruction.substring(12, 17), 2);
                    rs2 = Integer.parseInt(instruction.substring(7, 12), 2);
                    readData1 = BancoRegistradores.getRegisterValue(rs1);
                    readData2 = BancoRegistradores.getRegisterValue(rs2);
                    String imm_12 = instruction.substring(0, 1);
                    String imm_10_5 = instruction.substring(1, 7);
                    String imm_4_1 = instruction.substring(20, 24);
                    String imm_11 = instruction.substring(24, 25);
                    // O bit 0 do imediato é sempre 0 e não é armazenado
                    immediate = signExtend(imm_12 + imm_11 + imm_10_5 + imm_4_1 + "0");

                    IDEX.setAluOp(instructionName); // Operação de comparação (beq, bne, blt, etc.)
                    IDEX.setAluSrc(false);    // Compara dois registradores
                    IDEX.setRegWrite(false);  // Não escreve no registrador destino
                    IDEX.setMemRead(false);   // Não lê da memória
                    IDEX.setMemWrite(false);  // Não escreve na memória
                    IDEX.setMemToReg(false);  // Não importa, não escreve em registrador
                    IDEX.setBranch(true);     // Sinal de branch ativo
                    break;
               
            }
        }

        // Carregar o registrador de pipeline IDEX com todos os dados e sinais
        IDEX.setIDEXInstruction(instruction);
        IDEX.setReadData1(readData1);
        IDEX.setReadData2(readData2);
        IDEX.setImmediate(immediate);
        IDEX.setRs1(rs1);
        IDEX.setRs2(rs2);
        IDEX.setRd(rd);
        // O PC também precisa ser passado adiante
        IDEX.setPc(IFID.getIFIDPC()); 
    }

    
    private static int signExtend(String binaryString) {
        int value = Integer.parseInt(binaryString, 2);
        int bits = binaryString.length();
        int signBit = 1 << (bits - 1);
        // Se o bit de sinal estiver ligado, estende o sinal
        if ((value & signBit) != 0) {
            // Preenche os bits superiores com 1s
            value = value - (1 << bits);
        }
        return value;
    }
}
