.text
.globl main
fib:
	lw $a0, ($sp)
	addi $sp, $sp, 4
	#branch
	li $30, 1
	bgt $a0, $30, if_label0_fib
	#assign
	move $11, $a0
	#assign
	move $10, $11
	#goto
	j end_fib
if_label0_fib:
	#sub
	addi $a0, $a0, -1
	#callr
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
	sw $28, ($sp)
	addi $sp, $sp, -4
	sw $29, ($sp)
	addi $sp, $sp, -4
	sw $ra, ($sp)
	addi $sp, $sp, -4
	sw $a0, ($sp)
	jal fib
	move $30, $v0
	lw $ra, ($sp)
	addi $sp, $sp, 4
	lw $29, ($sp)
	addi $sp, $sp, 4
	lw $28, ($sp)
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
	move $9, $30
	#sub
	addi $8, $a0, -1
	#callr
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
	sw $28, ($sp)
	addi $sp, $sp, -4
	sw $29, ($sp)
	addi $sp, $sp, -4
	sw $ra, ($sp)
	addi $sp, $sp, -4
	sw $8, ($sp)
	jal fib
	move $30, $v0
	lw $ra, ($sp)
	addi $sp, $sp, 4
	lw $29, ($sp)
	addi $sp, $sp, 4
	lw $28, ($sp)
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
	move $8, $30
	#add
	add $11, $9, $8
	#assign
	move $10, $11
end_fib:
	#return
	move $v0, $10
	jr $ra
main:
	#callr
	#geti
	li $v0, 5
	syscall
	move $9, $v0
	#callr
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
	sw $28, ($sp)
	addi $sp, $sp, -4
	sw $29, ($sp)
	addi $sp, $sp, -4
	sw $ra, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	jal fib
	move $30, $v0
	lw $ra, ($sp)
	addi $sp, $sp, 4
	lw $29, ($sp)
	addi $sp, $sp, 4
	lw $28, ($sp)
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
	move $8, $30
	#assign
	move $9, $8
	#call
	#puti
	move $30, $a0
	move $a0, $9
	li $v0, 1
	syscall
	move $a0, $30
	#call
	#putc
	move $30, $a0
	li $v0, 11
	li $a0, 10
	syscall
	move $a0, $30
	jr $ra
	li $v0, 10
	syscall
