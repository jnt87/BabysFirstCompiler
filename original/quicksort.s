.text
.globl main
quicksort:
	li $v0, 9
	li $a0, 28
	syscall
	move $gp, $v0
	lw $a0, ($sp)
	addi $sp, $sp, 4
	lw $a1, ($sp)
	addi $sp, $sp, 4
	lw $a2, ($sp)
	addi $sp, $sp, 4
	#branch
	bge $a1, $a2, end_quicksort
	#add
	add $t8, $a1, $a2
	sw $t8, 24($gp)
	#or
	li $30, 2
	lw $t9, 24($gp)
	div $t9, $t9, $30
	sw $t9, 24($gp)
	#array_load
	li $30, 4
	lw $t8, 24($gp)
	mul $30, $30, $t8
	add $30, $a0, $30
	lw $t8, 0($30)
	sw $t8, 4($gp)
	#sub
	addi $t8, $a1, -1
	sw $t8, 8($gp)
	#add
	addi $t8, $a2, 1
	sw $t8, 12($gp)
loop0_quicksort:
loop1_quicksort:
	#add
	lw $t9, 8($gp)
	addi $t9, $t9, 1
	sw $t9, 8($gp)
	#array_load
	li $30, 4
	lw $t8, 8($gp)
	mul $30, $30, $t8
	add $30, $a0, $30
	lw $t8, 0($30)
	sw $t8, 16($gp)
	#assign
	lw $t9, 16($gp)
	sw $t9, 20($gp)
	#branch
	lw $t8, 16($gp)
	lw $t9, 4($gp)
	blt $t8, $t9, loop1_quicksort
loop2_quicksort:
	#sub
	lw $t9, 12($gp)
	addi $t9, $t9, -1
	sw $t9, 12($gp)
	#array_load
	li $30, 4
	lw $t8, 12($gp)
	mul $30, $30, $t8
	add $30, $a0, $30
	lw $t8, 0($30)
	sw $t8, 16($gp)
	#assign
	lw $t9, 16($gp)
	sw $t9, 0($gp)
	#branch
	lw $t8, 16($gp)
	lw $t9, 4($gp)
	bgt $t8, $t9, loop2_quicksort
	#branch
	lw $t8, 8($gp)
	lw $t9, 12($gp)
	bge $t8, $t9, exit0_quicksort
	#array_store
	li $30, 4
	lw $t8, 12($gp)
	mul $30, $t8, $30
	add $30, $a0, $30
	lw $t8, 20($gp)
	sw $t8, 0($30)
	#array_store
	li $30, 4
	lw $t8, 8($gp)
	mul $30, $t8, $30
	add $30, $a0, $30
	lw $t8, 0($gp)
	sw $t8, 0($30)
	#goto
	j loop0_quicksort
exit0_quicksort:
	#call
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
	lw $t8, 12($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	sw $a1, ($sp)
	addi $sp, $sp, -4
	sw $a0, ($sp)
	jal quicksort
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
	#add
	lw $t9, 12($gp)
	addi $t9, $t9, 1
	sw $t9, 12($gp)
	#call
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
	sw $a2, ($sp)
	addi $sp, $sp, -4
	lw $t8, 12($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	sw $a0, ($sp)
	jal quicksort
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
end_quicksort:
	jr $ra
main:
	li $v0, 9
	li $a0, 16
	syscall
	move $gp, $v0
	move $30, $a0
	li $v0, 9
	li $a0, 400
	syscall
	sw $v0, 0($gp)
	move $a0, $30
	#callr
	#geti
	li $v0, 5
	syscall
	sw $v0, 4($gp)
	#branch
	li $30, 100
	lw $t8, 4($gp)
	bgt $t8, $30, return_main
	#sub
	lw $t9, 4($gp)
	addi $t9, $t9, -1
	sw $t9, 4($gp)
	#assign
	li $30, 0
	sw $30, 8($gp)
loop0_main:
	#branch
	lw $t8, 8($gp)
	lw $t9, 4($gp)
	bgt $t8, $t9, exit0_main
	#callr
	#geti
	li $v0, 5
	syscall
	sw $v0, 12($gp)
	#array_store
	li $30, 4
	lw $t8, 8($gp)
	mul $30, $t8, $30
	lw $t8, 0($gp)
	add $30, $t8, $30
	lw $t8, 12($gp)
	sw $t8, 0($30)
	#add
	lw $t9, 8($gp)
	addi $t9, $t9, 1
	sw $t9, 8($gp)
	#goto
	j loop0_main
exit0_main:
	#call
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
	lw $t8, 4($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	li $30, 0
	#call
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
	lw $t8, 4($gp)
	sw $t8, ($sp)
	addi $sp, $sp, -4
	li $30, 0
	sw $30, ($sp)
	addi $sp, $sp, -4
	lw $t8, 0($gp)
	sw $t8, ($sp)
	jal quicksort
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
	#assign
	li $30, 0
	sw $30, 8($gp)
loop1_main:
	#branch
	lw $t8, 8($gp)
	lw $t9, 4($gp)
	bgt $t8, $t9, exit1_main
	#array_load
	li $30, 4
	lw $t8, 8($gp)
	mul $30, $30, $t8
	lw $t8, 0($gp)
	add $30, $t8, $30
	lw $t8, 0($30)
	sw $t8, 12($gp)
	#call
	#puti
	move $30, $a0
	lw $a0, 12($gp)
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
	#add
	lw $t9, 8($gp)
	addi $t9, $t9, 1
	sw $t9, 8($gp)
	#goto
	j loop1_main
exit1_main:
return_main:
	jr $ra
	li $v0, 10
	syscall
	move $a0, $t8
