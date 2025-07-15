package Pipeline;

//Não tem sinal de controle pois nesse estágio a instrução ainda nao foi decodificada

public class IFID {

    private static String instruction = "00000000000000000000000000010011";
    private static int pc = 0;

    public static void setIFIDPC(int novoPC) {
        pc = novoPC;
    }

    public static int getIFIDPC() {
        return pc;
    }

    public static void setIFIDInstruction(String nova_instrucao){
        instruction = nova_instrucao;
    }

    public static String getIFIDInstruction(){
        return instruction;
    }
    
}
