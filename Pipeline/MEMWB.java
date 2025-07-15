package Pipeline;

public class MEMWB {

    private static String instruction = "00000000000000000000000000010011";

    // --- Sinais de Controle para a fase de Write-Back (WB) ---
    private static boolean regWrite; // Habilita a escrita no banco de registradores
    private static boolean memToReg; // Seleciona a fonte do dado a ser escrito (false: ALU, true: Memória)

    // --- Dados a serem passados para a fase WB ---
    private static int aluResult;      // Resultado vindo da ALU (passado através da fase MEM)
    private static int memoryReadData; // Dado lido da memória na fase MEM
    private static int rd;             // Endereço do registrador de destino

    /**
     * Limpa (zera) todos os valores no registrador de pipeline.
     * Usado para simular uma bolha (nop).
     */
    public static void flush() {
        regWrite = false;
        memToReg = false;
        aluResult = 0;
        memoryReadData = 0;
        rd = 0;
    }

    // --- Getters e Setters ---

    public static void setMEMWBInstruction(String nova_instrucao){
        instruction = nova_instrucao;
    }

    public static String getMEMWBInstruction(){
        return instruction;
    }

    public static boolean isRegWrite() {
        return regWrite;
    }

    public static void setRegWrite(boolean regWrite) {
        MEMWB.regWrite = regWrite;
    }

    public static boolean isMemToReg() {
        return memToReg;
    }

    public static void setMemToReg(boolean memToReg) {
        MEMWB.memToReg = memToReg;
    }

    public static int getAluResult() {
        return aluResult;
    }

    public static void setAluResult(int aluResult) {
        MEMWB.aluResult = aluResult;
    }

    public static int getMemoryReadData() {
        return memoryReadData;
    }

    public static void setMemoryReadData(int memoryReadData) {
        MEMWB.memoryReadData = memoryReadData;
    }

    public static int getRd() {
        return rd;
    }

    public static void setRd(int rd) {
        MEMWB.rd = rd;
    }
}
