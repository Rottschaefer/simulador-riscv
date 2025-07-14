# exemplo.asm - Código Assembly RISC-V básico

.data
    numero: .word 42
    array: .word 1, 2, 3, 4, 5

.text
    # Carrega um valor imediato
    addi sp, sp, -4
    addi x1, x0, 10
    addi x2, x0, 20
    
    # Operações aritméticas
    add x3, x1, x2      # x3 = x1 + x2 = 30
    sub x4, x2, x1      # x4 = x2 - x1 = 10
    mul x5, x1, x2      # x5 = x1 * x2 = 200
    
    # Operações lógicas
    and x6, x1, x2      # x6 = x1 & x2
    or x7, x1, x2       # x7 = x1 | x2
    xor x8, x1, x2      # x8 = x1 ^ x2
    
    # Shifts
    sll x9, x1, x2      # x9 = x1 << x2
    srl x10, x1, x2     # x10 = x1 >> x2
    
    # Acesso à memória
    lw x11, 0(sp)       # carrega palavra da memória
    sw x3, 4(sp)        # armazena palavra na memória
    
    # Instruções de branch
    beq x1, x2, fim     # branch se x1 == x2
    bne x1, x2, loop    # branch se x1 != x2
    blt x1, x2, loop    # branch se x1 < x2
    bge x1, x2, fim     # branch se x1 >= x2
    
loop:
    addi x1, x1, 1      # incrementa x1
    addi x2, x2, -1     # decrementa x2
    bne x1, x2, loop    # volta ao loop se x1 != x2
    
fim:
    jal x1, 100         # jump and link
    jalr x0, x1, 0      # jump and link register