# exemplo.asm - Código Assembly RISC-V básico

.data
    numero: .word 42
    array: .word 1, 2, 3, 4, 5

.text
    addi sp, sp, -4
    addi x1, x0, 10
    addi x2, x0, 20
    
    add x3, x1, x2
    sub x4, x2, x1
   

    