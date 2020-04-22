.text
.globl main
	li $a0, 0
	li $a1, 0
	li $a2, 0
	li $a3, 0
	li $8, 0
	li $9, 0
	li $10, 0
	li $11, 0
	li $12, 0
	li $13, 0
	li $14, 0
	li $15, 0
	li $16, 0
	li $17, 0
	li $18, 0
	li $19, 0
	li $20, 0
	li $21, 0
	li $22, 0
	li $23, 0
	li $24, 0
	li $25, 0
	li $29, 0
	li $ra, 0
j main
quicksort:
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $9, ($sp)
	addi $sp, $sp, 4
	lw $10, ($sp)
	addi $sp, $sp, 4
	bge $9, $10, end_quicksort
	add $11, $9, $10
	li $30, 2
	div $11, $11, $30
	li $30, 4
	mul $30, $30, $11
	add $30, $8, $30
	lw $12, 0($30)
	addi $13, $9, -1
	addi $14, $10, 1
loop0_quicksort:
loop1_quicksort:
	addi $13, $13, 1
	li $30, 4
	mul $30, $30, $13
	add $30, $8, $30
	lw $15, 0($30)
	move $16, $15
	blt $16, $12, loop1_quicksort
loop2_quicksort:
	addi $14, $14, -1
	li $30, 4
	mul $30, $30, $14
	add $30, $8, $30
	lw $15, 0($30)
	move $17, $15
	bgt $17, $12, loop2_quicksort
	bge $13, $14, exit0_quicksort
	li $30, 4
	mul $30, $14, $30
	add $30, $8, $30
	sw $16, 0($30)
	li $30, 4
	mul $30, $13, $30
	add $30, $8, $30
	sw $17, 0($30)
	j loop0_quicksort
exit0_quicksort:
	addi $sp, $sp, -4
	sw $8, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	sw $10, ($sp)
	addi $sp, $sp, -4
	sw $a3, ($sp)
	addi $sp, $sp, -4
	sw $8, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	sw $10, ($sp)
	addi $sp, $sp, -4
	sw $11, ($sp)
	addi $sp, $sp, -4
	sw $12, ($sp)
	addi $sp, $sp, -4
	sw $13, ($sp)
	addi $sp, $sp, -4
	sw $14, ($sp)
	addi $sp, $sp, -4
	sw $15, ($sp)
	addi $sp, $sp, -4
	sw $16, ($sp)
	addi $sp, $sp, -4
	sw $17, ($sp)
	addi $sp, $sp, -4
	sw $18, ($sp)
	addi $sp, $sp, -4
	sw $19, ($sp)
	addi $sp, $sp, -4
	sw $20, ($sp)
	addi $sp, $sp, -4
	sw $21, ($sp)
	addi $sp, $sp, -4
	sw $22, ($sp)
	addi $sp, $sp, -4
	sw $23, ($sp)
	addi $sp, $sp, -4
	sw $24, ($sp)
	addi $sp, $sp, -4
	sw $25, ($sp)
	addi $sp, $sp, -4
	sw $29, ($sp)
	addi $sp, $sp, -4
	sw $ra, ($sp)
	addi $sp, $sp, -4
	sw $14, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	sw $8, ($sp)
	jal quicksort
	lw $ra, ($sp)
	addi $sp, $sp, 4
	lw $29, ($sp)
	addi $sp, $sp, 4
	lw $25, ($sp)
	addi $sp, $sp, 4
	lw $24, ($sp)
	addi $sp, $sp, 4
	lw $23, ($sp)
	addi $sp, $sp, 4
	lw $22, ($sp)
	addi $sp, $sp, 4
	lw $21, ($sp)
	addi $sp, $sp, 4
	lw $20, ($sp)
	addi $sp, $sp, 4
	lw $19, ($sp)
	addi $sp, $sp, 4
	lw $18, ($sp)
	addi $sp, $sp, 4
	lw $17, ($sp)
	addi $sp, $sp, 4
	lw $16, ($sp)
	addi $sp, $sp, 4
	lw $15, ($sp)
	addi $sp, $sp, 4
	lw $14, ($sp)
	addi $sp, $sp, 4
	lw $13, ($sp)
	addi $sp, $sp, 4
	lw $12, ($sp)
	addi $sp, $sp, 4
	lw $11, ($sp)
	addi $sp, $sp, 4
	lw $10, ($sp)
	addi $sp, $sp, 4
	lw $9, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $a3, ($sp)
	addi $sp, $sp, 4
	lw $10, ($sp)
	addi $sp, $sp, 4
	lw $9, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	addi $14, $14, 1
	addi $sp, $sp, -4
	sw $8, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	sw $10, ($sp)
	addi $sp, $sp, -4
	sw $a3, ($sp)
	addi $sp, $sp, -4
	sw $8, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	sw $10, ($sp)
	addi $sp, $sp, -4
	sw $11, ($sp)
	addi $sp, $sp, -4
	sw $12, ($sp)
	addi $sp, $sp, -4
	sw $13, ($sp)
	addi $sp, $sp, -4
	sw $14, ($sp)
	addi $sp, $sp, -4
	sw $15, ($sp)
	addi $sp, $sp, -4
	sw $16, ($sp)
	addi $sp, $sp, -4
	sw $17, ($sp)
	addi $sp, $sp, -4
	sw $18, ($sp)
	addi $sp, $sp, -4
	sw $19, ($sp)
	addi $sp, $sp, -4
	sw $20, ($sp)
	addi $sp, $sp, -4
	sw $21, ($sp)
	addi $sp, $sp, -4
	sw $22, ($sp)
	addi $sp, $sp, -4
	sw $23, ($sp)
	addi $sp, $sp, -4
	sw $24, ($sp)
	addi $sp, $sp, -4
	sw $25, ($sp)
	addi $sp, $sp, -4
	sw $29, ($sp)
	addi $sp, $sp, -4
	sw $ra, ($sp)
	addi $sp, $sp, -4
	sw $10, ($sp)
	addi $sp, $sp, -4
	sw $14, ($sp)
	addi $sp, $sp, -4
	sw $8, ($sp)
	jal quicksort
	lw $ra, ($sp)
	addi $sp, $sp, 4
	lw $29, ($sp)
	addi $sp, $sp, 4
	lw $25, ($sp)
	addi $sp, $sp, 4
	lw $24, ($sp)
	addi $sp, $sp, 4
	lw $23, ($sp)
	addi $sp, $sp, 4
	lw $22, ($sp)
	addi $sp, $sp, 4
	lw $21, ($sp)
	addi $sp, $sp, 4
	lw $20, ($sp)
	addi $sp, $sp, 4
	lw $19, ($sp)
	addi $sp, $sp, 4
	lw $18, ($sp)
	addi $sp, $sp, 4
	lw $17, ($sp)
	addi $sp, $sp, 4
	lw $16, ($sp)
	addi $sp, $sp, 4
	lw $15, ($sp)
	addi $sp, $sp, 4
	lw $14, ($sp)
	addi $sp, $sp, 4
	lw $13, ($sp)
	addi $sp, $sp, 4
	lw $12, ($sp)
	addi $sp, $sp, 4
	lw $11, ($sp)
	addi $sp, $sp, 4
	lw $10, ($sp)
	addi $sp, $sp, 4
	lw $9, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $a3, ($sp)
	addi $sp, $sp, 4
	lw $10, ($sp)
	addi $sp, $sp, 4
	lw $9, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
end_quicksort:
	jr $ra
main:
	move $30, $a0
	li $v0, 9
	li $a0, 400
	syscall
	move $8, $v0
	move $a0, $30
	li $v0, 5
	syscall
	move $9, $v0
	li $30, 100
	bgt $9, $30, return_main
	addi $9, $9, -1
	li $30, 0
	move $10, $30
loop0_main:
	bgt $10, $9, exit0_main
	li $v0, 5
	syscall
	move $11, $v0
	li $30, 4
	mul $30, $10, $30
	add $30, $8, $30
	sw $11, 0($30)
	addi $10, $10, 1
	j loop0_main
exit0_main:
	addi $sp, $sp, -4
	sw $a0, ($sp)
	addi $sp, $sp, -4
	sw $a1, ($sp)
	addi $sp, $sp, -4
	sw $a2, ($sp)
	addi $sp, $sp, -4
	sw $a3, ($sp)
	addi $sp, $sp, -4
	sw $8, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	sw $10, ($sp)
	addi $sp, $sp, -4
	sw $11, ($sp)
	addi $sp, $sp, -4
	sw $12, ($sp)
	addi $sp, $sp, -4
	sw $13, ($sp)
	addi $sp, $sp, -4
	sw $14, ($sp)
	addi $sp, $sp, -4
	sw $15, ($sp)
	addi $sp, $sp, -4
	sw $16, ($sp)
	addi $sp, $sp, -4
	sw $17, ($sp)
	addi $sp, $sp, -4
	sw $18, ($sp)
	addi $sp, $sp, -4
	sw $19, ($sp)
	addi $sp, $sp, -4
	sw $20, ($sp)
	addi $sp, $sp, -4
	sw $21, ($sp)
	addi $sp, $sp, -4
	sw $22, ($sp)
	addi $sp, $sp, -4
	sw $23, ($sp)
	addi $sp, $sp, -4
	sw $24, ($sp)
	addi $sp, $sp, -4
	sw $25, ($sp)
	addi $sp, $sp, -4
	sw $29, ($sp)
	addi $sp, $sp, -4
	sw $ra, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	li $30, 0
	addi $sp, $sp, -4
	sw $a0, ($sp)
	addi $sp, $sp, -4
	sw $a1, ($sp)
	addi $sp, $sp, -4
	sw $a2, ($sp)
	addi $sp, $sp, -4
	sw $a3, ($sp)
	addi $sp, $sp, -4
	sw $8, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	sw $10, ($sp)
	addi $sp, $sp, -4
	sw $11, ($sp)
	addi $sp, $sp, -4
	sw $12, ($sp)
	addi $sp, $sp, -4
	sw $13, ($sp)
	addi $sp, $sp, -4
	sw $14, ($sp)
	addi $sp, $sp, -4
	sw $15, ($sp)
	addi $sp, $sp, -4
	sw $16, ($sp)
	addi $sp, $sp, -4
	sw $17, ($sp)
	addi $sp, $sp, -4
	sw $18, ($sp)
	addi $sp, $sp, -4
	sw $19, ($sp)
	addi $sp, $sp, -4
	sw $20, ($sp)
	addi $sp, $sp, -4
	sw $21, ($sp)
	addi $sp, $sp, -4
	sw $22, ($sp)
	addi $sp, $sp, -4
	sw $23, ($sp)
	addi $sp, $sp, -4
	sw $24, ($sp)
	addi $sp, $sp, -4
	sw $25, ($sp)
	addi $sp, $sp, -4
	sw $29, ($sp)
	addi $sp, $sp, -4
	sw $ra, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	li $30, 0
	sw $30, ($sp)
	addi $sp, $sp, -4
	sw $8, ($sp)
	jal quicksort
	lw $ra, ($sp)
	addi $sp, $sp, 4
	lw $29, ($sp)
	addi $sp, $sp, 4
	lw $25, ($sp)
	addi $sp, $sp, 4
	lw $24, ($sp)
	addi $sp, $sp, 4
	lw $23, ($sp)
	addi $sp, $sp, 4
	lw $22, ($sp)
	addi $sp, $sp, 4
	lw $21, ($sp)
	addi $sp, $sp, 4
	lw $20, ($sp)
	addi $sp, $sp, 4
	lw $19, ($sp)
	addi $sp, $sp, 4
	lw $18, ($sp)
	addi $sp, $sp, 4
	lw $17, ($sp)
	addi $sp, $sp, 4
	lw $16, ($sp)
	addi $sp, $sp, 4
	lw $15, ($sp)
	addi $sp, $sp, 4
	lw $14, ($sp)
	addi $sp, $sp, 4
	lw $13, ($sp)
	addi $sp, $sp, 4
	lw $12, ($sp)
	addi $sp, $sp, 4
	lw $11, ($sp)
	addi $sp, $sp, 4
	lw $10, ($sp)
	addi $sp, $sp, 4
	lw $9, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $a3, ($sp)
	addi $sp, $sp, 4
	lw $a2, ($sp)
	addi $sp, $sp, 4
	lw $a1, ($sp)
	addi $sp, $sp, 4
	lw $a0, ($sp)
	addi $sp, $sp, 4
	li $30, 0
	move $10, $30
loop1_main:
	bgt $10, $9, exit1_main
	li $30, 4
	mul $30, $30, $10
	add $30, $8, $30
	lw $11, 0($30)
	move $30, $a0
	move $a0, $11
	li $v0, 1
	syscall
	move $a0, $30
	move $30, $a0
	li $v0, 11
	li $a0, 10
	syscall
	move $a0, $30
	addi $10, $10, 1
	j loop1_main
exit1_main:
return_main:
	jr $ra
	li $v0, 10
	syscall
