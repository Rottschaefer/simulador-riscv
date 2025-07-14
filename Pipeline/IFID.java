package Pipeline;

public class IFID {
    // Atributos estáticos para armazenar o estado do registrador IF/ID
    public static int instruction;
    public static int pc_plus_4;
    public static String instructionString; // Para depuração

    // Métodos estáticos para acessar e modificar os atributos
    public static void setInstruction(int instruction) {
        IFID.instruction = instruction;
    }

    public static int getInstruction() {
        return instruction;
    }

    public static void setPcPlus4(int pc_plus_4) {
        IFID.pc_plus_4 = pc_plus_4;
    }

    public static int getPcPlus4() {
        return pc_plus_4;
    }

    public static void setInstructionString(String instructionString) {
        IFID.instructionString = instructionString;
    }

    public static String getInstructionString() {
        return instructionString;
    }

    // Método para "zerar" ou invalidar o registrador (útil para bolhas)
    public static void flush() {
        instruction = 0; // Uma instrução NOP (ex: addi x0, x0, 0)
        pc_plus_4 = 0;
        instructionString = "nop";
    }
}
