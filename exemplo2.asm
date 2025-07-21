.data
    numero: .word 42
    array: .word 1, 2, 3, 4, 5
.text
    addi x1, x0, 2
    addi x2, x0, 2
    addi x1, x1, -3
fim:
    jalr x0, x1, 0