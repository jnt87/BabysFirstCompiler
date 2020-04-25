main:
	addi $29, $29, -400
	move $A, $29 # this is a local array
	li $v0, 5
	syscall
	move $n, $v0
	bgt $n, 100, return
	subi $n, $n, 1
	li $i, 0
loop0_main:
	bgt $i, $n, exit0_main
	li $v0, 5
	syscall
	move $t, $v0
	add $temp, $A, $i
	sw $t, 0($temp)
	addi $i, $i, 1
	jump loop0_main
exit0_main:
	addi $29, $29, -104
	sw $t0, 64($29)
	sw $t1, 68($29)
	sw $t2, 72($29)
	sw $t3, 76($29)
	sw $t4, 80($29)
	sw $t5, 84($29)
	sw $t6, 88($29)
	sw $t7, 92($29)
	sw $t8, 96($29)
	sw $t9, 100($29)
	sw $a0 20($29)
	sw $a1 20($29)
	sw $a2 20($29)
	sw $s0, 0($29)
	sw $s1, 4($29)
	sw $s2, 8($29)
	sw $s3, 12($29)
	sw $s4, 16($29)
	sw $s5, 20($29)
	sw $s6, 24($29)
	sw $s7, 28($29)
	sw $s8, 32($29)
	sw $31, 36($29)
	move $a0, $A
	li $a1, $0
	move $a2, $n
	jal quicksort
	lw $31, 36($29)
	lw $t0, 64($29)
	lw $t1, 68($29)
	lw $t2, 72($29)
	lw $t3, 76($29)
	lw $t4, 80($29)
	lw $t5, 84($29)
	lw $t6, 88($29)
	lw $t7, 92($29)
	lw $t8, 96($29)
	lw $t9, 100($29)
	lw $s0, 0($29)
	lw $s1, 4($29)
	lw $s2, 8($29)
	lw $s3, 12($29)
	lw $s4, 16($29)
	lw $s5, 20($29)
	lw $s6, 24($29)
	lw $s7, 28($29)
	lw $s8, 32($29)
	lw $a0 20($29)
	lw $a1 20($29)
	lw $a2 20($29)
	addi $29, $29, 104
	li $i, 0
loop1_main:
	bgt $i, n, exit1_main
	add $temp, $A, $i
	lw $t, 0($temp)
	addi $29, $29, -4
	sw $a0, 0($29)
	li $a0, $t
	li $v0, 1
	lw $a0, 0($29)
	addi $29, $29, 4
	syscall
	addi $29, $29, -4
	sw $a0, 0($29)
	li $a0, $10
	li $v0, 11
	lw $a0, 0($29)
	addi $29, $29, 4
	syscall
	addi $i, $i, 1
	jump loop1
exit1_main:
	jr $31
	
	
quicksort:
	bge $lo, hi, end
	add $mid, $lo, $hi
	div $mid, $mid, $t1
	add $temp, $A, $mid
	lw $pivot, 0($temp)
	subi $i, $lo, 1
	addi $j, $hi, 1
loop0_quicksort:
loop1_quicksort:
	addi $i, $i, 1
	add $temp, $A, $i
	lw $x, 0($temp)
	move $ti, $x
	blt $ti, pivot, loop1
loop2_quicksort:
	subi $j, $j, 1
	add $temp, $A, $j
	lw $x, 0($temp)
	move $tj, $x
	bgt $tj, pivot, loop2
	bge $i, j, exit0
	add $temp, $A, $j
	sw $ti, 0($temp)
	add $temp, $A, $i
	sw $tj, 0($temp)
	jump $loop0
exit0_quicksort:
	addi $29, $29, -104
	sw $t0, 64($29)
	sw $t1, 68($29)
	sw $t2, 72($29)
	sw $t3, 76($29)
	sw $t4, 80($29)
	sw $t5, 84($29)
	sw $t6, 88($29)
	sw $t7, 92($29)
	sw $t8, 96($29)
	sw $t9, 100($29)
	sw $a0 20($29)
	sw $a1 20($29)
	sw $a2 20($29)
	sw $s0, 0($29)
	sw $s1, 4($29)
	sw $s2, 8($29)
	sw $s3, 12($29)
	sw $s4, 16($29)
	sw $s5, 20($29)
	sw $s6, 24($29)
	sw $s7, 28($29)
	sw $s8, 32($29)
	sw $31, 36($29)
	move $a0, $quicksort
	move $a1, $A
	move $a2, $lo
	move $a3, $j
	jal quicksort
	lw $31, 36($29)
	lw $t0, 64($29)
	lw $t1, 68($29)
	lw $t2, 72($29)
	lw $t3, 76($29)
	lw $t4, 80($29)
	lw $t5, 84($29)
	lw $t6, 88($29)
	lw $t7, 92($29)
	lw $t8, 96($29)
	lw $t9, 100($29)
	lw $s0, 0($29)
	lw $s1, 4($29)
	lw $s2, 8($29)
	lw $s3, 12($29)
	lw $s4, 16($29)
	lw $s5, 20($29)
	lw $s6, 24($29)
	lw $s7, 28($29)
	lw $s8, 32($29)
	lw $a0 20($29)
	lw $a1 20($29)
	lw $a2 20($29)
	addi $29, $29, 104
	addi $j, $j, 1
	addi $29, $29, -104
	sw $t0, 64($29)
	sw $t1, 68($29)
	sw $t2, 72($29)
	sw $t3, 76($29)
	sw $t4, 80($29)
	sw $t5, 84($29)
	sw $t6, 88($29)
	sw $t7, 92($29)
	sw $t8, 96($29)
	sw $t9, 100($29)
	sw $a0 20($29)
	sw $a1 20($29)
	sw $a2 20($29)
	sw $s0, 0($29)
	sw $s1, 4($29)
	sw $s2, 8($29)
	sw $s3, 12($29)
	sw $s4, 16($29)
	sw $s5, 20($29)
	sw $s6, 24($29)
	sw $s7, 28($29)
	sw $s8, 32($29)
	sw $31, 36($29)
	move $a0, $quicksort
	move $a1, $A
	move $a2, $j
	move $a3, $hi
	jal quicksort
	lw $31, 36($29)
	lw $t0, 64($29)
	lw $t1, 68($29)
	lw $t2, 72($29)
	lw $t3, 76($29)
	lw $t4, 80($29)
	lw $t5, 84($29)
	lw $t6, 88($29)
	lw $t7, 92($29)
	lw $t8, 96($29)
	lw $t9, 100($29)
	lw $s0, 0($29)
	lw $s1, 4($29)
	lw $s2, 8($29)
	lw $s3, 12($29)
	lw $s4, 16($29)
	lw $s5, 20($29)
	lw $s6, 24($29)
	lw $s7, 28($29)
	lw $s8, 32($29)
	lw $a0 20($29)
	lw $a1 20($29)
	lw $a2 20($29)
	addi $29, $29, 104
end_quicksort:
	
	
