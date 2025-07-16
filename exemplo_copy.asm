# exemplo.asm - Código Assembly RISC-V básico

.data
    numero: .word 42
    array: .word 1, 2, 3, 4, 5

.text
    addi x1, x0, 0
    addi x2, x0, 0
    add x0, x0, x0
    beq x1, x1, fim
    add x0, x0, x0
    add x0, x0, x0
    add x0, x0, x0
    add x0, x0, x0
    add x0, x0, x0
    add x0, x0, x0

fim:
    div x3, x1, x2
    
   

    