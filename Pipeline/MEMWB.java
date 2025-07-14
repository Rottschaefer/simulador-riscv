package Pipeline;

public class MEMWB {
    // --- Control Signals ---
    // WB
    public static int RegWrite;
    public static int MemToReg;

    // --- Data ---
    public static int mem_read_data;
    public static int alu_result;
    public static int rd;
    public static String instructionString;

    public static void flush() {
        RegWrite = 0;
        MemToReg = 0;
        mem_read_data = 0;
        alu_result = 0;
        rd = 0;
        instructionString = "nop";
    }
}
