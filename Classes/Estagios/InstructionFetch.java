import java.util.List;

// Classe que busca a próxima instrução da memória de instruções usando o PC (Contador de Programa)

public class BuscaInstrucao {

    private int pc;
    private List<String> memoriaInstrucoes;
    private String instrucaoAtualTexto;
    private String instrucaoAtualBinario;

    public BuscaInstrucao(List<String> memoriaInstrucoes) {
        this.pc = 0;
        this.memoriaInstrucoes = memoriaInstrucoes;
    }

    // Busca a próxima instrução da memória
    public String buscar() {
        if (pc < memoriaInstrucoes.size()) {
            instrucaoAtualTexto = memoriaInstrucoes.get(pc);
            instrucaoAtualBinario = Codificador.codificarAsm(instrucaoAtualTexto); // Retorna o binário
            pc++;
            return instrucaoAtualBinario;
        } else {
            instrucaoAtualTexto = null;
            throw new IllegalStateException("Fim da memória de instruções.");
        }
    }

    public String getInstrucaoAtualTexto() {
        return instrucaoAtualTexto;
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int novoPC) {
        this.pc = novoPC;
    }

    public boolean temMaisInstrucoes() {
        return pc < memoriaInstrucoes.size();
    }

    // Verifica se há dependência de dados entre a instrução atual e a anterior
    public boolean temDependenciaDados(String instrucaoAtual, String instrucaoAnterior) {
        // Exemplo: Verifica se o registrador de destino da instrução anterior é usado como fonte na instrução atual
        String registradorDestino = getRegistradorDestino(instrucaoAnterior);
        List<String> registradoresFonte = getRegistradoresFonte(instrucaoAtual);

        return registradoresFonte.contains(registradorDestino);
    }

    // Detecção de dependência de dados (hazard)
    public void detectarHazard(String instrucaoAnterior) {
        String instrucaoAtual = memoriaInstrucoes.get(pc);
        if (temDependenciaDados(instrucaoAtual, instrucaoAnterior)) {
            System.out.println("Dependência de dados detectada! Aplicando stall...");
            // Lógica para pausar o pipeline
            pc--;
        }
    }

    // Métodos auxiliares para extrair registradores (implementação depende do formato das instruções)
    private String getRegistradorDestino(String instrucao) {
        // Extrai o registrador de destino da instrução
        // Implementação depende do formato da instrução
        String[] partes = instrucao.split(" ");
        return partes.length > 1 ? partes[1] : null;
    }

    // Extrai os registradores de origem da instrução
    private List<String> getRegistradoresFonte(String instrucao) {
        // Implementação depende do formato da instrução
        String[] partes = instrucao.split(" ");
        return partes.length > 2 ? List.of(partes[2], partes[3]) : List.of();
    }
}
