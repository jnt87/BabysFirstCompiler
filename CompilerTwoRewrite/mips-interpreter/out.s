.text
.globl main
sampleFunc:
	li $v0, 9
	li $a0, 96
	syscall
	move $gp, $v0
	lw $a0, ($sp)
	addi $sp, $sp, 4
	lw $a1, ($sp)
	addi $sp, $sp, 4
	lw $a2, ($sp)
	addi $sp, $sp, 4
	lw $a3, ($sp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 72($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 76($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 80($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 84($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 88($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 92($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 0($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 4($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 8($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 12($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 36($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 16($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 24($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 52($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 56($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 44($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 48($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 64($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 68($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 60($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 32($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 40($gp)
	addi $sp, $sp, 4
	#assign
	li $30, 0
	sw $30, 20($gp)
	#callr
	#geti
	li $v0, 5
	syscall
	sw $v0, 28($gp)
loop_sampleFunc:
	#call
	#puti
	move $30, $a0
	lw $a0, 28($gp)
	li $v0, 1
	syscall
	move $a0, $30
	#branch
	li $30, 0
	lw $t8, 28($gp)
	beq $t8, $30, end_sampleFunc
	#sub
	lw $t9, 28($gp)
	addi $t9, $t9, -1
	sw $t9, 28($gp)
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $a0
	sw $t9, 20($gp)
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $a1
	sw $t9, 20($gp)
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $a2
	sw $t9, 20($gp)
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $a3
	sw $t9, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 72($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 76($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 80($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 84($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 88($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 92($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 0($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 4($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 8($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 12($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 36($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 16($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 24($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 52($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 56($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 44($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 48($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 64($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 68($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 60($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 32($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 40($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#goto
	j loop_sampleFunc
end_sampleFunc:
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
	lw $t8, 40($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 32($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 60($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 68($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 64($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 48($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 44($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 56($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 52($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 24($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 16($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 36($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 12($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 8($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 4($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 0($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 92($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 88($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 84($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 80($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 76($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 72($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	sw $a3, ($sp)
	addi $sp, $sp, -4
	sw $a2, ($sp)
	addi $sp, $sp, -4
	sw $a1, ($sp)
	addi $sp, $sp, -4
	sw $a0, ($sp)
	jal sampleFunc2
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
	sw $30, 28($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 28($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#return
	lw $v0, 20($gp)
	jr $ra
	move $a0, $t8
	move $v0, $t8
main:
	li $v0, 9
	li $a0, 108
	syscall
	move $gp, $v0
	#assign
	li $30, 1
	sw $30, 24($gp)
	#assign
	li $30, 2
	sw $30, 28($gp)
	#assign
	li $30, 3
	sw $30, 32($gp)
	#assign
	li $30, 4
	sw $30, 36($gp)
	#assign
	li $30, 5
	sw $30, 40($gp)
	#assign
	li $30, 6
	sw $30, 44($gp)
	#assign
	li $30, 7
	sw $30, 48($gp)
	#assign
	li $30, 8
	sw $30, 52($gp)
	#assign
	li $30, 9
	sw $30, 56($gp)
	#assign
	li $30, 10
	sw $30, 60($gp)
	#assign
	li $30, 11
	sw $30, 72($gp)
	#assign
	li $30, 12
	sw $30, 64($gp)
	#assign
	li $30, 13
	sw $30, 68($gp)
	#assign
	li $30, 14
	sw $30, 84($gp)
	#assign
	li $30, 15
	sw $30, 88($gp)
	#assign
	li $30, 16
	sw $30, 76($gp)
	#assign
	li $30, 17
	sw $30, 80($gp)
	#assign
	li $30, 18
	sw $30, 96($gp)
	#assign
	li $30, 19
	sw $30, 100($gp)
	#assign
	li $30, 20
	sw $30, 92($gp)
	#assign
	li $30, 21
	sw $30, 8($gp)
	#assign
	li $30, 22
	sw $30, 12($gp)
	#assign
	li $30, 23
	sw $30, 0($gp)
	#assign
	li $30, 24
	sw $30, 4($gp)
	#assign
	li $30, 25
	sw $30, 16($gp)
	#assign
	li $30, 26
	sw $30, 20($gp)
create_array_main:
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
	lw $t8, 20($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 16($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 4($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 0($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 12($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 8($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 92($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 100($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 96($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 80($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 76($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 88($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 84($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 68($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 64($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 72($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 60($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 56($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 52($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 48($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 44($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 40($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 36($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 32($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 28($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 24($gp)
	sw $t8, ($sp)
	jal sampleFunc
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
	sw $30, 104($gp)
	#call
	#puti
	move $30, $a0
	lw $a0, 104($gp)
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
	move $a0, $t8
sampleFunc2:
	li $v0, 9
	li $a0, 96
	syscall
	move $gp, $v0
	lw $a0, ($sp)
	addi $sp, $sp, 4
	lw $a1, ($sp)
	addi $sp, $sp, 4
	lw $a2, ($sp)
	addi $sp, $sp, 4
	lw $a3, ($sp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 72($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 76($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 80($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 84($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 88($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 92($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 0($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 4($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 8($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 12($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 36($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 16($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 24($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 52($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 56($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 44($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 48($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 64($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 68($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 60($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 32($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 40($gp)
	addi $sp, $sp, 4
	#assign
	li $30, 0
	sw $30, 20($gp)
	#assign
	li $30, 1
	sw $30, 28($gp)
loop_sampleFunc2:
	#branch
	li $30, 0
	lw $t8, 28($gp)
	beq $t8, $30, end_sampleFunc2
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $a0
	sw $t9, 20($gp)
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $a1
	sw $t9, 20($gp)
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $a2
	sw $t9, 20($gp)
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $a3
	sw $t9, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 72($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 76($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 80($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 84($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 88($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 92($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 0($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 4($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 8($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 12($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 36($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 16($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 24($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 52($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 56($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 44($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 48($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 64($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 68($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 60($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 32($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
	#add
	lw $t8, 20($gp)
	lw $t9, 40($gp)
	add $t8, $t8, $t9
	sw $t8, 20($gp)
end_sampleFunc2:
	#return
	lw $v0, 20($gp)
	jr $ra
	move $v0, $t8
