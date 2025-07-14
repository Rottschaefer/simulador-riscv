package Auxiliares;

public class InstructionInfo {
    private String tipo;
    private String opcode;
    private String funct3;
    private String funct7;
    
    // Construtor
    public InstructionInfo(String tipo, String opcode, String funct3, String funct7) {
        this.tipo = tipo;
        this.opcode = opcode;
        this.funct3 = funct3;
        this.funct7 = funct7;
    }
    
    // Getters
    public String getTipo() {
        return tipo;
    }
    
    public String getOpcode() {
        return opcode;
    }
    
    public String getFunct3() {
        return funct3;
    }
    
    public String getFunct7() {
        return funct7;
    }
    
    // toString para debug
    @Override
    public String toString() {
        return String.format("InstructionInfo{tipo='%s', opcode='%s', funct3='%s', funct7='%s'}", 
                           tipo, opcode, funct3, funct7);
    }
}
