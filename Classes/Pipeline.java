package Classes;
public class Pipeline {

    private static int pc = 0;

    
    // public static void main(String[] args) {
    //     System.out.println("fgjdndvd");
    // }

    public static class IFID {
        public static String instruction;
        public static boolean valid = false;
        
        public void clear() {
            instruction = "00000000000000000000000000010011"; //addi zero, zero, 0
            valid = false;
        }

        public static void setIFID(String new_instruction){
            instruction = new_instruction;
        }

        public static String getIFID(){
            return instruction;
        }
    }

    public static void setPc(int newPc) {
        if (newPc % 4 == 0) {
            pc = newPc;
        } else {
            throw new IllegalArgumentException("PC deve ser múltiplo de 4.");
        }
    }

    public static int getPC(){
        return pc;
    }

}
