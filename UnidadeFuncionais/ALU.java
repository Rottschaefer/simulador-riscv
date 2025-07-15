package UnidadeFuncionais;

/**
 * A Unidade Lógica e Aritmética (ALU) realiza as operações de cálculo do processador.
 */
public class ALU {

 
    public static int execute(String aluOp, int operand1, int operand2) {
        switch (aluOp) {
            // Operações Aritméticas
            case "add":
            case "addi":
            case "lw":  // Para lw/sw, a ALU calcula o endereço: base + offset
            case "sw":
                return operand1 + operand2;
            case "sub":
                return operand1 - operand2;
            case "mul":
                return operand1 * operand2;
            case "div":
                if (operand2 == 0) {
                    System.err.println("Erro: Divisão por zero.");
                    return 0;
                }
                return operand1 / operand2;
            case "rem":
                if (operand2 == 0) {
                    System.err.println("Erro: Divisão por zero para resto.");
                    return 0;
                }
                return operand1 % operand2;

            // Operações Lógicas
            case "and":
                return operand1 & operand2;
            case "or":
                return operand1 | operand2;
            case "xor":
                return operand1 ^ operand2;

            // Operações de Shift
            case "sll": // Shift Left Logical
                return operand1 << operand2;
            case "srl": // Shift Right Logical
                return operand1 >>> operand2; // Usa >>> para shift lógico (preenche com 0)

            // Para branches, a ALU faz subtração
            case "beq":
            case "bne":
            case "blt":
            case "bge":
                return operand1 - operand2; // O resultado será usado para avaliar a condição

            default:
                System.err.println("Operação de ALU não reconhecida: " + aluOp);
                return 0;
        }
    }
}
