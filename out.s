.text
.globl main
sampleFunc:
	li $v0, 9
	li $a0, 32
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
	lw $22, ($sp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 16($gp)
	addi $sp, $sp, 4
	lw $t8, ($sp)
	sw $t8, 24($gp)
	addi $sp, $sp, 4
	lw $18, ($sp)
	addi $sp, $sp, 4
	lw $17, ($sp)
	addi $sp, $sp, 4
	lw $20, ($sp)
	addi $sp, $sp, 4
	lw $19, ($sp)
	addi $sp, $sp, 4
	lw $15, ($sp)
	addi $sp, $sp, 4
	lw $14, ($sp)
	addi $sp, $sp, 4
	lw $16, ($sp)
	addi $sp, $sp, 4
	lw $23, ($sp)
	addi $sp, $sp, 4
	lw $21, ($sp)
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
	lw $t9, 20($gp)
	add $t9, $t9, $13
	sw $t9, 20($gp)
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $12
	sw $t9, 20($gp)
	#add
	lw $t9, 20($gp)
	add $t9, $t9, $11
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
	#assign
	lw $t9, 20($gp)
	sw $t9, 20($gp)
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
	sw $21, ($sp)
	addi $sp, $sp, -4
	sw $23, ($sp)
	addi $sp, $sp, -4
	sw $16, ($sp)
	addi $sp, $sp, -4
	sw $14, ($sp)
	addi $sp, $sp, -4
	sw $15, ($sp)
	addi $sp, $sp, -4
	sw $19, ($sp)
	addi $sp, $sp, -4
	sw $20, ($sp)
	addi $sp, $sp, -4
	sw $17, ($sp)
	addi $sp, $sp, -4
	sw $18, ($sp)
	addi $sp, $sp, -4
	lw $t8, 24($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	lw $t8, 16($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	sw $22, ($sp)
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
sampleFunc2:
	lw $a0, ($sp)
	addi $sp, $sp, 4
	lw $a1, ($sp)
	addi $sp, $sp, 4
	lw $a2, ($sp)
	addi $sp, $sp, 4
	lw $a3, ($sp)
	addi $sp, $sp, 4
	lw $13, ($sp)
	addi $sp, $sp, 4
	lw $12, ($sp)
	addi $sp, $sp, 4
	lw $11, ($sp)
	addi $sp, $sp, 4
	lw $10, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	lw $8, ($sp)
	addi $sp, $sp, 4
	#assign
	li $30, 0
	move $9, $30
	#assign
	li $30, 1
	move $8, $30
loop_sampleFunc2:
	#branch
	li $30, 0
	beq $8, $30, end_sampleFunc2
	#add
	add $9, $9, $a0
	#add
	add $9, $9, $a1
	#add
	add $9, $9, $a2
	#add
	add $9, $9, $a3
	#add
	add $9, $9, $13
	#add
	add $9, $9, $12
	#add
	add $9, $9, $11
	#add
	add $9, $9, $10
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
	#assign
	move $9, $9
end_sampleFunc2:
	#return
	move $v0, $9
	jr $ra
main:
	li $v0, 9
	li $a0, 40
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
	move $23, $30
	#assign
	li $30, 6
	move $22, $30
	#assign
	li $30, 7
	move $21, $30
	#assign
	li $30, 8
	move $20, $30
	#assign
	li $30, 9
	move $19, $30
	#assign
	li $30, 10
	move $18, $30
	#assign
	li $30, 11
	move $15, $30
	#assign
	li $30, 12
	move $17, $30
	#assign
	li $30, 13
	move $16, $30
	#assign
	li $30, 14
	move $12, $30
	#assign
	li $30, 15
	move $11, $30
	#assign
	li $30, 16
	move $14, $30
	#assign
	li $30, 17
	move $13, $30
	#assign
	li $30, 18
	move $9, $30
	#assign
	li $30, 19
	move $8, $30
	#assign
	li $30, 20
	move $10, $30
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
	sw $10, ($sp)
	addi $sp, $sp, -4
	sw $8, ($sp)
	addi $sp, $sp, -4
	sw $9, ($sp)
	addi $sp, $sp, -4
	sw $13, ($sp)
	addi $sp, $sp, -4
	sw $14, ($sp)
	addi $sp, $sp, -4
	sw $11, ($sp)
	addi $sp, $sp, -4
	sw $12, ($sp)
	addi $sp, $sp, -4
	sw $16, ($sp)
	addi $sp, $sp, -4
	sw $17, ($sp)
	addi $sp, $sp, -4
	sw $15, ($sp)
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
	move $8, $30
	#call
	#puti
	move $30, $a0
	move $a0, $8
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
