package Pipeline;

public class IDEX {

    // Sinais de Controle para a fase de Execução (EX)
    private static String aluOp = "";      // Define a operação da ALU (ex: "add", "sub")
    private static boolean aluSrc = false; // Seleciona a segunda entrada da ALU (false: Reg, true: Imm)

    // Sinais de Controle para a fase de Memória (MEM)
    private static boolean branch = false;   // Indica se a instrução é um branch
    private static boolean memRead = false;  // Habilita a leitura da memória
    private static boolean memWrite = false; // Habilita a escrita na memória

    // Sinais de Controle para a fase de Write-Back (WB)
    private static boolean regWrite = false; // Habilita a escrita no banco de registradores
    private static boolean memToReg = false; // Seleciona o que escrever no registrador (false: ALU, true: Mem)

    // Dados passados para as próximas fases
    private static int readData1 = 0;       // Valor lido do registrador rs1
    private static int readData2 = 0;       // Valor lido do registrador rs2
    private static int immediate = 0;       // Valor do imediato estendido
    private static int rs1 = 0;             // Endereço do registrador rs1
    private static int rs2 = 0;             // Endereço do registrador rs2
    private static int rd = 0;              // Endereço do registrador de destino rd
    private static int pc = 0;              // PC da instrução atual (para cálculo de branch)


    public static String getAluOp() {
        return aluOp;
    }

    public static void setAluOp(String aluOp) {
        IDEX.aluOp = aluOp;
    }

    public static boolean isAluSrc() {
        return aluSrc;
    }

    public static void setAluSrc(boolean aluSrc) {
        IDEX.aluSrc = aluSrc;
    }

    public static boolean isBranch() {
        return branch;
    }

    public static void setBranch(boolean branch) {
        IDEX.branch = branch;
    }

    public static boolean isMemRead() {
        return memRead;
    }

    public static void setMemRead(boolean memRead) {
        IDEX.memRead = memRead;
    }

    public static boolean isMemWrite() {
        return memWrite;
    }

    public static void setMemWrite(boolean memWrite) {
        IDEX.memWrite = memWrite;
    }

    public static boolean isRegWrite() {
        return regWrite;
    }

    public static void setRegWrite(boolean regWrite) {
        IDEX.regWrite = regWrite;
    }

    public static boolean isMemToReg() {
        return memToReg;
    }

    public static void setMemToReg(boolean memToReg) {
        IDEX.memToReg = memToReg;
    }

    public static int getReadData1() {
        return readData1;
    }

    public static void setReadData1(int readData1) {
        IDEX.readData1 = readData1;
    }

    public static int getReadData2() {
        return readData2;
    }

    public static void setReadData2(int readData2) {
        IDEX.readData2 = readData2;
    }

    public static int getImmediate() {
        return immediate;
    }

    public static void setImmediate(int immediate) {
        IDEX.immediate = immediate;
    }

    public static int getRs1() {
        return rs1;
    }

    public static void setRs1(int rs1) {
        IDEX.rs1 = rs1;
    }

    public static int getRs2() {
        return rs2;
    }

    public static void setRs2(int rs2) {
        IDEX.rs2 = rs2;
    }

    public static int getRd() {
        return rd;
    }

    public static void setRd(int rd) {
        IDEX.rd = rd;
    }

    public static int getPc() {
        return pc;
    }

    public static void setPc(int pc) {
        IDEX.pc = pc;
    }
}
