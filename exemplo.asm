# exemplo.asm - Código Assembly RISC-V básico

.data
    numero: .word 42
    array: .word 1, 2, 3, 4, 5

.text
    addi zero, zero, 0
    addi sp, sp, -4
    add x1, x2, x3
    addi x1, x2, 10
    
    add x3, x1, x2
    sub x4, x2, x1
    mul x5, x1, x2
    
    # Operações lógicas
    and x6, x1, x2
    or x7, x1, x2
    xor x8, x1, x2
    
    sll x9, x1, x2
    srl x10, x1, x2
    
    # Acesso à memória
    lw x11, 0(sp)
    sw x3, 4(sp)
    
    # Instruções de branch
    beq x1, x2, fim
    bne x1, x2, loop
    blt x1, x2, loop
    bge x1, x2, fim
    
loop:
    addi x1, x1, 1
    addi x2, x2, -1
    bne x1, x2, loop
    
fim:
    jal x1, 100
    jalr x0, x1, 0