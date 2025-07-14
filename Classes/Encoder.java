package Classes;

import java.util.HashMap;

//Classe que codifica uma instrucao riscv texto em 32 bits

public class Encoder {

   // Hashtable para mapear instrução -> informações completas
    private static HashMap<String, InstructionInfo> instructionTypes = new HashMap<>();
    
    // Hashtable para mapear nome do registrador -> número
    private static HashMap<String, Integer> registerMap = new HashMap<>();
    
    static{
        // Tipo R (opcode = 0110011)
        instructionTypes.put("add", new InstructionInfo("R", "0110011", "000", "0000000"));
        instructionTypes.put("sub", new InstructionInfo("R", "0110011", "000", "0100000"));
        instructionTypes.put("mul", new InstructionInfo("R", "0110011", "000", "0000001"));
        instructionTypes.put("div", new InstructionInfo("R", "0110011", "100", "0000001"));
        instructionTypes.put("rem", new InstructionInfo("R", "0110011", "110", "0000001"));
        instructionTypes.put("xor", new InstructionInfo("R", "0110011", "100", "0000000"));
        instructionTypes.put("and", new InstructionInfo("R", "0110011", "111", "0000000"));
        instructionTypes.put("or", new InstructionInfo("R", "0110011", "110", "0000000"));
        instructionTypes.put("sll", new InstructionInfo("R", "0110011", "001", "0000000"));
        instructionTypes.put("srl", new InstructionInfo("R", "0110011", "101", "0000000"));
        
        // Tipo I 
        instructionTypes.put("addi", new InstructionInfo("I", "0010011", "000", ""));
        instructionTypes.put("lw", new InstructionInfo("I", "0000011", "010", ""));
        instructionTypes.put("jalr", new InstructionInfo("I", "1100111", "000", ""));
        
        // Tipo S
        instructionTypes.put("sw", new InstructionInfo("S", "0100011", "010", ""));
        
        // Tipo B
        instructionTypes.put("beq", new InstructionInfo("B", "1100011", "000", ""));
        instructionTypes.put("bne", new InstructionInfo("B", "1100011", "001", ""));
        instructionTypes.put("bge", new InstructionInfo("B", "1100011", "101", ""));
        instructionTypes.put("blt", new InstructionInfo("B", "1100011", "100", ""));
        
        // Tipo J
        instructionTypes.put("j", new InstructionInfo("J", "1101111", "", ""));
        instructionTypes.put("jal", new InstructionInfo("J", "1101111", "", ""));
        
        // Mapeamento de registradores RISC-V
        registerMap.put("zero", 0); registerMap.put("x0", 0);
        registerMap.put("ra", 1);   registerMap.put("x1", 1);
        registerMap.put("sp", 2);   registerMap.put("x2", 2);
        registerMap.put("gp", 3);   registerMap.put("x3", 3);
        registerMap.put("tp", 4);   registerMap.put("x4", 4);
        registerMap.put("t0", 5);   registerMap.put("x5", 5);
        registerMap.put("t1", 6);   registerMap.put("x6", 6);
        registerMap.put("t2", 7);   registerMap.put("x7", 7);
        registerMap.put("fp", 8);   registerMap.put("x8", 8);
        registerMap.put("s0", 8);   // fp e s0 são o mesmo registrador
        registerMap.put("s1", 9);   registerMap.put("x9", 9);
        registerMap.put("a0", 10);  registerMap.put("x10", 10);
        registerMap.put("a1", 11);  registerMap.put("x11", 11);
        registerMap.put("a2", 12);  registerMap.put("x12", 12);
        registerMap.put("a3", 13);  registerMap.put("x13", 13);
        registerMap.put("a4", 14);  registerMap.put("x14", 14);
        registerMap.put("a5", 15);  registerMap.put("x15", 15);
        registerMap.put("a6", 16);  registerMap.put("x16", 16);
        registerMap.put("a7", 17);  registerMap.put("x17", 17);
        registerMap.put("s2", 18);  registerMap.put("x18", 18);
        registerMap.put("s3", 19);  registerMap.put("x19", 19);
        registerMap.put("s4", 20);  registerMap.put("x20", 20);
        registerMap.put("s5", 21);  registerMap.put("x21", 21);
        registerMap.put("s6", 22);  registerMap.put("x22", 22);
        registerMap.put("s7", 23);  registerMap.put("x23", 23);
        registerMap.put("s8", 24);  registerMap.put("x24", 24);
        registerMap.put("s9", 25);  registerMap.put("x25", 25);
        registerMap.put("s10", 26); registerMap.put("x26", 26);
        registerMap.put("s11", 27); registerMap.put("x27", 27);
        registerMap.put("t3", 28);  registerMap.put("x28", 28);
        registerMap.put("t4", 29);  registerMap.put("x29", 29);
        registerMap.put("t5", 30);  registerMap.put("x30", 30);
        registerMap.put("t6", 31);  registerMap.put("x31", 31);
    }

    public static int encode_asm(String instruction){

        String[] partes = instruction.replace(",", "").split(" ");

        InstructionInfo info = instructionTypes.get(partes[0]);
        
        if (info == null) {
            System.out.println("Instrução não encontrada: " + partes[0]);
            return -1;
        }
        
        String encodedInstruction = "";
        
        switch (info.getTipo()) {
            case "R":
                encodedInstruction = encode_r(partes, info);
                break;
            case "I":
                encodedInstruction = encode_i(partes, info);
                break;
            case "S":
                encodedInstruction = encode_s(partes, info);
                break;
            case "B":
                encodedInstruction = encode_b(partes, info);
                break;
            case "J":
                encodedInstruction = encode_j(partes, info);
                break;
            default:
                System.out.println("Tipo de instrução desconhecido: " + info.getTipo());
                return -1;
        }
        
        System.out.println("Instrução codificada: " + encodedInstruction);
        return 1;
    }

    public static String intToBinary(int numero, int bits) {

    int mask = (1 << bits) - 1;
    
    int resultado = numero & mask;
    
    // Converte para binário com o número correto de bits
    return String.format("%" + bits + "s", Integer.toBinaryString(resultado)).replace(' ', '0');
}

    public static String encode_i(String[] args, InstructionInfo info){
        
        String imediato = intToBinary(Integer.parseInt(args[3]), 12);
        String rs1 = intToBinary(registerMap.get(args[2]), 5);
        String rd = intToBinary(registerMap.get(args[1]), 5);
        
        // Formato I: imm[11:0] rs1[4:0] funct3[2:0] rd[4:0] opcode[6:0]
        String instruction = imediato + rs1 + info.getFunct3() + rd + info.getOpcode(); 

        return instruction;
    }
    
    public static String encode_r(String[] args, InstructionInfo info){
        // Formato R: funct7[6:0] rs2[4:0] rs1[4:0] funct3[2:0] rd[4:0] opcode[6:0]
        String rs2 = intToBinary(registerMap.get(args[3]), 5);
        String rs1 = intToBinary(registerMap.get(args[2]), 5);
        String rd = intToBinary(registerMap.get(args[1]), 5);
        
        String instruction = info.getFunct7() + rs2 + rs1 + info.getFunct3() + rd + info.getOpcode();
        return instruction;
    }
    
    public static String encode_s(String[] args, InstructionInfo info){
        // Formato S: imm[11:5] rs2[4:0] rs1[4:0] funct3[2:0] imm[4:0] opcode[6:0]
        int imediato = Integer.parseInt(args[3]);
        String imm_11_5 = intToBinary((imediato >> 5) & 0x7F, 7);  
        String imm_4_0 = intToBinary(imediato & 0x1F, 5);         
        String rs2 = intToBinary(registerMap.get(args[1]), 5);
        String rs1 = intToBinary(registerMap.get(args[2]), 5);
        
        String instruction = imm_11_5 + rs2 + rs1 + info.getFunct3() + imm_4_0 + info.getOpcode();
        return instruction;
    }
    
    public static String encode_b(String[] args, InstructionInfo info){
        // Formato B: imm[12|10:5] rs2[4:0] rs1[4:0] funct3[2:0] imm[4:1|11] opcode[6:0]
        String imediato = intToBinary(Integer.parseInt(args[3]), 12);
        String rs2 = intToBinary(registerMap.get(args[2]), 5);
        String rs1 = intToBinary(registerMap.get(args[1]), 5);
        
        String instruction = imediato.charAt(12) + imediato.substring(10,5) + rs2 + rs1 + info.getFunct3() + imediato.substring(4, 0) +imediato.charAt(11) + info.getOpcode();

        return instruction;
    }
    
    public static String encode_j(String[] args, InstructionInfo info){
        // Formato J: imm[20|10:1|11|19:12] rd[4:0] opcode[6:0]
        String imediato = intToBinary(Integer.parseInt(args[2]), 20);
        String rd = intToBinary(registerMap.get(args[1]), 5);
        
        String instruction = imediato.charAt(19) + imediato.substring(9,0) + imediato.charAt(10) + imediato.substring(18,11) + rd + info.getOpcode();
        return instruction;
    }
}
