# exemplo.asm - Código Assembly RISC-V básico

.data
    numero: .word 42
    array: .word 1, 2, 3, 4, 5

.text
    addi x10, x0, 10
    addi x11, x0, 0
    addi x12, x0, 1
    addi x13, x0, 2

    beq x10, x11, fim  
    beq x10, x13, fim  

loop:
    add x14, x11, x12  

    addi x13, x13, 1   
    blt x13, x10, loop 



fim:
    add x3, x1, x2
    
   

    