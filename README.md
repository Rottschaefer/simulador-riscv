# simulador-riscv

Este repositório contém um simulador educacional de um processador RISC-V implementado em Java. O projeto simula um pipeline clássico de 5 estágios, permitindo a execução, visualização e depuração de programas em Assembly RISC-V.

## Estrutura do Projeto

- **/Auxiliares**: Classes utilitárias para parsing, encoding e decodificação de instruções.
  - `AssemblyParser.java`: Montador que lê arquivos `.asm`, resolve labels e carrega instruções/dados na memória.
  - `Encoder.java`: Codifica instruções Assembly em binário RISC-V.
  - `Decoder.java`: Decodifica instruções binárias para identificar operação, registradores e imediatos.
  - `InstructionInfo.java`: Estrutura auxiliar para informações de cada instrução.

- **/Estagios**: Implementação dos estágios do pipeline.
  - `InstructionFetch.java`: Busca a próxima instrução na memória.
  - `ReadRegister.java`: Decodifica a instrução e lê os registradores.
  - `Execution.java`: Executa operações aritméticas/lógicas e resolve branches.
  - `ReadMemory.java`: Realiza acesso à memória de dados (load/store).
  - `WriteBack.java`: Escreve resultados de volta no banco de registradores.

- **/Pipeline**: Registradores de pipeline entre os estágios (IFID, IDEX, EXMEM, MEMWB) e controle do PC.

- **/Unidade Funcionais**: Componentes funcionais do processador.
  - `BancoRegistradores.java`: Banco de 32 registradores inteiros.
  - `Memoria.java`: Memória de instruções e dados.
  - `ALU.java`: Classe que representa Unidade Lógica e Aritmética responsável pelas operações aritméticas e lógicas do processador.

- **exemplo.asm**: Exemplo de programa Assembly RISC-V para teste do simulador.

## Como funciona

1. O `AssemblyParser` lê o arquivo `.asm`, resolve labels e carrega instruções/dados na memória.
2. O pipeline executa instruções em 5 estágios, propagando sinais e dados entre os registradores de pipeline.
3. O simulador suporta instruções aritméticas, lógicas, loads, stores, branches e jumps do RISC-V básico.

## Como executar

1. Compile todos os arquivos Java:
   ```
   javac */*.java *.java
   ```
2. Execute a classe principal (exemplo):
   ```
   java Processador
   ```
3. Edite o arquivo `exemplo.asm` para testar diferentes programas.

## Observações
- O simulador é voltado para fins didáticos e não cobre todas as instruções do RISC-V.
- O projeto pode ser expandido para incluir interface gráfica, forwarding, detecção de hazards e suporte a mais instruções.

## Autoria

Eduardo Rottschaefer
João Victor Machado Sperle
Allan Vignoli dos Santos
João Vitor Pereira Rodrigues
José Enrique Viana de Oliveira
