package Auxiliares;

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

    public static String encode_asm(String instruction) throws Exception {

        String[] partes = instruction.replace(",", "").split(" ");

        // Verificar se a instrução tem argumentos suficientes
        if (partes.length < 1) {
            throw new Exception("Instrução vazia");
        }

        // Resolver labels - verifica todos os argumentos, não apenas o último
        for (int i = 1; i < partes.length; i++) {
            Integer labelAddress = AssemblyParser.labels.get(partes[i]);
            if (labelAddress != null) {
                partes[i] = String.valueOf(labelAddress);
            }
        }

        InstructionInfo info = instructionTypes.get(partes[0]);
        
        if (info == null) {
            throw new Exception("Instrução não encontrada: " + partes[0]);
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
                throw new Exception("Tipo de instrução desconhecido: " + info.getTipo());
        }
        
        return encodedInstruction;
    }

public static String intToBinary(int numero, int bits) {
    
    String binary;
    
    if (bits == 32) {
        // Para 32 bits, usa Integer.toBinaryString diretamente
        binary = Integer.toBinaryString(numero);
    } else {
        // Para menos bits, usa máscara
        int mask = (1 << bits) - 1;
        int resultado = numero & mask;
        binary = Integer.toBinaryString(resultado);
    }
    
    // Preenche com zeros à esquerda
    while (binary.length() < bits) {
        binary = "0" + binary;
    }
        
    return binary;
}

    private static String encode_i(String[] args, InstructionInfo info) throws Exception {

        if (args[0].equals("lw")) {
        String rdest = args[1];        
        String memoryArg = args[2]; 
        
        if (memoryArg.contains("(") && memoryArg.contains(")")) {
            int openParen = memoryArg.indexOf('(');
            int closeParen = memoryArg.indexOf(')');
            
            String offsetStr = memoryArg.substring(0, openParen);
            String rs1 = memoryArg.substring(openParen + 1, closeParen);
            
            args = new String[]{args[0], rdest, rs1, offsetStr};
        }
    }

        if (args.length < 4) {
            throw new Exception("Instrução tipo I requer 4 argumentos: " + String.join(" ", args));
        }
        if (!registerMap.containsKey(args[1])) {
            throw new Exception("Registrador de destino inválido: " + args[1]);
        }
        if (!registerMap.containsKey(args[2])) {
            throw new Exception("Registrador de origem inválido: " + args[2]);
        }
        try {
            Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            throw new Exception("Imediato inválido: " + args[3]);
        }
        
        String imediato = intToBinary(Integer.parseInt(args[3]), 12);
        String rs1 = intToBinary(registerMap.get(args[2]), 5);
        String rd = intToBinary(registerMap.get(args[1]), 5);
        
        // Formato I: imm[11:0] rs1[4:0] funct3[2:0] rd[4:0] opcode[6:0]
        String instruction = imediato + rs1 + info.getFunct3() + rd + info.getOpcode(); 

        return instruction;
    
    }
    
    private static String encode_r(String[] args, InstructionInfo info){

        if (args.length < 4) {
            throw new IllegalArgumentException("Instrução tipo R requer 4 argumentos: " + String.join(" ", args));
        }
        if (!registerMap.containsKey(args[1])) {
            throw new IllegalArgumentException("Registrador de destino inválido: " + args[1]);
        }
        if (!registerMap.containsKey(args[2])) {
            throw new IllegalArgumentException("Registrador de origem 1 inválido: " + args[2]);
        }
        if (!registerMap.containsKey(args[3])) {
            throw new IllegalArgumentException("Registrador de origem 2 inválido: " + args[3]);
        }
        // Formato R: funct7[6:0] rs2[4:0] rs1[4:0] funct3[2:0] rd[4:0] opcode[6:0]
        String rs2 = intToBinary(registerMap.get(args[3]), 5);
        String rs1 = intToBinary(registerMap.get(args[2]), 5);
        String rd = intToBinary(registerMap.get(args[1]), 5);
        
        String instruction = info.getFunct7() + rs2 + rs1 + info.getFunct3() + rd + info.getOpcode();
        return instruction;
    }
    
    private static String encode_s(String[] args, InstructionInfo info)throws Exception{

        if (args[0].equals("sw") && args.length == 3) {
        String rs2 = args[1];
        String memoryArg = args[2];
        
        if (memoryArg.contains("(") && memoryArg.contains(")")) {
            int openParen = memoryArg.indexOf('(');
            int closeParen = memoryArg.indexOf(')');
            
            String offsetStr = memoryArg.substring(0, openParen);
            String rs1 = memoryArg.substring(openParen + 1, closeParen);
            
            args = new String[]{args[0], rs2, rs1, offsetStr};
        }
    }

        if (args.length < 4) {
            throw new Exception("Instrução tipo S requer 4 argumentos: " + String.join(" ", args));
        }
        if (!registerMap.containsKey(args[1])) {
            throw new Exception("Registrador rs2 inválido: " + args[1]);
        }
        if (!registerMap.containsKey(args[2])) {
            throw new Exception("Registrador rs1 inválido: " + args[2]);
        }
        try {
            Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            throw new Exception("Imediato inválido: " + args[3]);
        }
        // Formato S: imm[11:5] rs2[4:0] rs1[4:0] funct3[2:0] imm[4:0] opcode[6:0]
        int imediato = Integer.parseInt(args[3]);
        String imm_11_5 = intToBinary((imediato >> 5) & 0x7F, 7);  
        String imm_4_0 = intToBinary(imediato & 0x1F, 5);         
        String rs2 = intToBinary(registerMap.get(args[1]), 5);
        String rs1 = intToBinary(registerMap.get(args[2]), 5);
        
        String instruction = imm_11_5 + rs2 + rs1 + info.getFunct3() + imm_4_0 + info.getOpcode();
        return instruction;
    }
    
    private static String encode_b(String[] args, InstructionInfo info)throws Exception{
        
        if (args.length < 4) {
            System.out.println("Instrução tipo B requer 4 argumentos: " + String.join(" ", args));
            throw new Exception("Instrução tipo B requer 4 argumentos: " + String.join(" ", args));
        }
        
        if (!registerMap.containsKey(args[1]) || !registerMap.containsKey(args[2])) {
            System.out.println("Registrador inválido em instrução B: " + String.join(" ", args));
            throw new Exception("Registrador inválido: " + String.join(" ", args));

        }
        
        try {
            // Formato B: imm[12|10:5] rs2[4:0] rs1[4:0] funct3[2:0] imm[4:1|11] opcode[6:0]
            int imediato = Integer.parseInt(args[3]);
            String immBinary = intToBinary(imediato, 13); // 13 bits para incluir o bit 12
            String rs2 = intToBinary(registerMap.get(args[2]), 5);
            String rs1 = intToBinary(registerMap.get(args[1]), 5);
            
            String imm_12 = immBinary.substring(0, 1);      // bit 12
            String imm_10_5 = immBinary.substring(2, 8);    // bits 10:5
            String imm_4_1 = immBinary.substring(8, 12);    // bits 4:1
            String imm_11 = immBinary.substring(1, 2);      // bit 11
            
            String instruction = imm_12 + imm_10_5 + rs2 + rs1 + info.getFunct3() + imm_4_1 + imm_11 + info.getOpcode();
            return instruction;
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter imediato para número: " + args[3]);
            return "-1";
        }
    }
    
    private static String encode_j(String[] args, InstructionInfo info)throws Exception{
        
        String rd;
        String imediato;

        
        
        if (args.length == 2) {
            // Formato: j label (pseudo-instrução, equivale a jal x0, label)
            rd = intToBinary(0, 5); // x0 (zero register)

            try {
                imediato = intToBinary(Integer.parseInt(args[1]), 20);
            } catch (NumberFormatException e) {
                throw new Exception("Erro ao converter imediato para número: " + args[1]);
            }
        } 
        
        else if (args.length == 3) {
            // Formato: jal rd, label
            if (!registerMap.containsKey(args[1])) {
                throw new Exception("Registrador inválido em instrução J: " + args[1]);
            }
            rd = intToBinary(registerMap.get(args[1]), 5);

            try {
                imediato = intToBinary(Integer.parseInt(args[2]), 20);
            } 
            catch (NumberFormatException e) {
                throw new Exception("Erro ao converter imediato para número: " + args[2]);
            }
        } 
        
        else {
            throw new Exception("Instrução tipo J requer 2 ou 3 argumentos: " + String.join(" ", args));
        }

        
        String instruction = imediato.charAt(19) + imediato.substring(0,9) + imediato.charAt(10) + imediato.substring(11,18) + rd + info.getOpcode();
        return instruction;
    }


}


