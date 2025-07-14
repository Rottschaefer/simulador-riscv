package Pipeline;

public class EXMEM {
    // --- Control Signals ---
    // MEM
    public static int Branch;
    public static int MemRead;
    public static int MemWrite;
    // WB
    public static int RegWrite;
    public static int MemToReg;

    // --- Data ---
    public static int branch_target;
    public static int alu_result;
    public static int rs2_val;
    public static int rd;
    public static String instructionString;

    public static void flush() {
        Branch = 0;
        MemRead = 0;
        MemWrite = 0;
        RegWrite = 0;
        MemToReg = 0;
        branch_target = 0;
        alu_result = 0;
        rs2_val = 0;
        rd = 0;
        instructionString = "nop";
    }
}
