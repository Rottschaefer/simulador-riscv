package Pipeline;

public class EXMEM {

    private static String instruction = "00000000000000000000000000010011";

    // --- Sinais de Controle para a fase de Memória (MEM) ---
    private static boolean memRead;
    private static boolean memWrite;
    private static boolean branch; // Sinal que indica se a instrução é um branch

    // --- Sinais de Controle para a fase de Write-Back (WB) ---
    private static boolean regWrite;
    private static boolean memToReg;

    // --- Dados e Resultados ---
    private static int aluResult;           // Resultado da ALU
    private static int writeData;           // Dado a ser escrito na memória (valor de rs2 para SW)
    private static int rd;                  // Endereço do registrador de destino

    // --- Informações de Branch ---
    private static boolean branchTaken;         // Flag que indica se o branch foi tomado
    private static int branchTargetAddress; // Endereço de destino do branch

    /**
     * Limpa (zera) todos os valores no registrador de pipeline.
     * Usado para simular uma bolha (nop).
     */
    public static void flush() {
        memRead = false;
        memWrite = false;
        branch = false;
        regWrite = false;
        memToReg = false;
        branchTaken = false;
        
        aluResult = 0;
        writeData = 0;
        rd = 0;
        branchTargetAddress = 0;
    }

    // --- Getters e Setters ---

    public static void setEXMEMInstruction(String nova_instrucao){
        instruction = nova_instrucao;
    }

    public static String getEXMEMInstruction(){
        return instruction;
    }

    public static boolean isMemRead() {
        return memRead;
    }

    public static void setMemRead(boolean memRead) {
        EXMEM.memRead = memRead;
    }

    public static boolean isMemWrite() {
        return memWrite;
    }

    public static void setMemWrite(boolean memWrite) {
        EXMEM.memWrite = memWrite;
    }

    public static boolean isBranch() {
        return branch;
    }

    public static void setBranch(boolean branch) {
        EXMEM.branch = branch;
    }

    public static boolean isRegWrite() {
        return regWrite;
    }

    public static void setRegWrite(boolean regWrite) {
        EXMEM.regWrite = regWrite;
    }

    public static boolean isMemToReg() {
        return memToReg;
    }

    public static void setMemToReg(boolean memToReg) {
        EXMEM.memToReg = memToReg;
    }

    public static int getAluResult() {
        return aluResult;
    }

    public static void setAluResult(int aluResult) {
        EXMEM.aluResult = aluResult;
    }

    public static int getWriteData() {
        return writeData;
    }

    public static void setWriteData(int writeData) {
        EXMEM.writeData = writeData;
    }

    public static int getRd() {
        return rd;
    }

    public static void setRd(int rd) {
        EXMEM.rd = rd;
    }

    public static boolean isBranchTaken() {
        return branchTaken;
    }

    public static void setBranchTaken(boolean branchTaken) {
        EXMEM.branchTaken = branchTaken;
    }

    public static int getBranchTargetAddress() {
        return branchTargetAddress;
    }

    public static void setBranchTargetAddress(int branchTargetAddress) {
        EXMEM.branchTargetAddress = branchTargetAddress;
    }
}
