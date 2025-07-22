.data
    numero: .word 42
    array: .word 1, 2, 3, 4, 5
.text
    addi x1, x0, 1
    addi x3, x0, 10
    addi x4, x0, 20
    addi x5, x0, 30
    addi x0, zero, 0   # NOP para dar tempo ao pipeline
    addi x2, x1, 1
    addi x0, zero, 0
    addi x0, zero, 0
    addi x0, zero, 0     # Agora x1 já foi escrito
    addi x3, x2, 2
    addi x0, zero, 0
    addi x0, zero, 0
    addi x0, zero, 0
fim:
    jalr x0, x1, 0