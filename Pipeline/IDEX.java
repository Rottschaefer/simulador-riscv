package Pipeline;

public class IDEX {
    // --- Control Signals ---
    // EX
    public static int RegDst;
    public static int ALUOp;
    public static int ALUSrc;
    // MEM
    public static int Branch;
    public static int MemRead;
    public static int MemWrite;
    // WB
    public static int RegWrite;
    public static int MemToReg;

    // --- Data ---
    public static int pc_plus_4;
    public static int rs1_val;
    public static int rs2_val;
    public static int imm;
    public static int rs1;
    public static int rs2;
    public static int rd;
    public static String instructionString;

    public static void flush() {
        RegDst = 0;
        ALUOp = 0;
        ALUSrc = 0;
        Branch = 0;
        MemRead = 0;
        MemWrite = 0;
        RegWrite = 0;
        MemToReg = 0;
        pc_plus_4 = 0;
        rs1_val = 0;
        rs2_val = 0;
        imm = 0;
        rs1 = 0;
        rs2 = 0;
        rd = 0;
        instructionString = "nop";
    }
}
