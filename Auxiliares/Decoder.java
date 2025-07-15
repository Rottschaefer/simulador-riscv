package Auxiliares;

import java.util.HashMap;
import java.util.Map;

public class Decoder {

    // Mapa principal: Opcode -> Formato da Instrução
    private static final Map<String, String> opcodeToFormatMap = new HashMap<>();

    // Mapas secundários para cada formato, baseados em funct3 ou funct7+funct3
    private static final Map<String, String> rTypeMap = new HashMap<>();
    private static final Map<String, String> iTypeLoadMap = new HashMap<>();
    private static final Map<String, String> iTypeArithMap = new HashMap<>();
    private static final Map<String, String> iTypeJalrMap = new HashMap<>();
    private static final Map<String, String> sTypeMap = new HashMap<>();
    private static final Map<String, String> bTypeMap = new HashMap<>();

    // Bloco estático para inicializar todos os mapas de decodificação
    static {
        // 1. Mapeamento de Opcode para Formato
        opcodeToFormatMap.put("0110011", "R");
        opcodeToFormatMap.put("0010011", "I-Arith");
        opcodeToFormatMap.put("0000011", "I-Load");
        opcodeToFormatMap.put("1100111", "I-JALR");
        opcodeToFormatMap.put("0100011", "S");
        opcodeToFormatMap.put("1100011", "B");
        opcodeToFormatMap.put("1101111", "J"); // JAL não precisa de mapa secundário

        // 2. Mapeamentos específicos por formato

        // Tipo R (chave = funct7 + funct3)
        rTypeMap.put("0000000" + "000", "add");
        rTypeMap.put("0100000" + "000", "sub");
        rTypeMap.put("0000001" + "000", "mul");
        rTypeMap.put("0000000" + "001", "sll");
        rTypeMap.put("0000001" + "100", "div");
        rTypeMap.put("0000000" + "100", "xor");
        rTypeMap.put("0000000" + "101", "srl");
        rTypeMap.put("0000001" + "110", "rem");
        rTypeMap.put("0000000" + "110", "or");
        rTypeMap.put("0000000" + "111", "and");

        // Tipo I - Aritmética (chave = funct3)
        iTypeArithMap.put("000", "addi");

        // Tipo I - Load (chave = funct3)
        iTypeLoadMap.put("010", "lw");
        
        // Tipo I - JALR (chave = funct3)
        iTypeJalrMap.put("000", "jalr");

        // Tipo S - Store (chave = funct3)
        sTypeMap.put("010", "sw");

        // Tipo B - Branch (chave = funct3)
        bTypeMap.put("000", "beq");
        bTypeMap.put("001", "bne");
        bTypeMap.put("100", "blt");
        bTypeMap.put("101", "bge");
    }

    
    public static String decodeInstruction(String instruction) {
        if (instruction == null || instruction.length() != 32) {
            return "Invalid";
        }

        String opcode = instruction.substring(25, 32);
        String format = opcodeToFormatMap.get(opcode);

        if (format == null) {
            return "Unknown";
        }

        String funct3 = instruction.substring(17, 20);
        String funct7 = instruction.substring(0, 7);

        switch (format) {
            case "R":
                String rKey = funct7 + funct3;
                return rTypeMap.getOrDefault(rKey, "Unknown");
            case "I-Arith":
                return iTypeArithMap.getOrDefault(funct3, "Unknown");
            case "I-Load":
                return iTypeLoadMap.getOrDefault(funct3, "Unknown");
            case "I-JALR":
                return iTypeJalrMap.getOrDefault(funct3, "Unknown");
            case "S":
                return sTypeMap.getOrDefault(funct3, "Unknown");
            case "B":
                return bTypeMap.getOrDefault(funct3, "Unknown");
            case "J":
                return "jal"; // JAL é único pelo opcode
            default:
                return "Unknown";
        }
    }

    public static String getInstructionType(String instruction){
        String opcode = instruction.substring(25, 32);

        return opcodeToFormatMap.get(opcode);
    }
}
