package UnidadeFuncionais;

public class BancoRegistradores {

    private static final int[] registers = new int[32];

   
    static {
        registers[0] = 0;
    }

    public static int[] getRegisters(){
        return (registers);
    }
    public static int getRegisterValue(int registerNumber) {
        if (registerNumber < 0 || registerNumber >= 32) {
            System.err.println("Erro: Tentativa de ler registrador inválido: " + registerNumber);
            return 0;
        }
        if (registerNumber == 0) {
            return 0;
        }
        return registers[registerNumber];
    }

   
    public static void setRegisterValue(int registerNumber, int value) {
        if (registerNumber < 0 || registerNumber >= 32) {
            System.err.println("Erro: Tentativa de escrever em registrador inválido: " + registerNumber);
            return;
        }
        if (registerNumber != 0) {
            registers[registerNumber] = value;
        }
    }

  
    public static void printRegisters() {
        System.out.println("--- Estado do Banco de Registradores ---");
        for (int i = 0; i < 32; i++) {
            System.out.printf("x%d: %d\n", i, registers[i]);
        }
        System.out.println("----------------------------------------");
    }
}
