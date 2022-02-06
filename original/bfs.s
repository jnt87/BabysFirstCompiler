.text
.globl main
main:
	li $v0, 9
	li $a0, 56
	syscall
	move $gp, $v0
	move $30, $a0
	li $v0, 9
	li $a0, 4000
	syscall
	sw $v0, 16($gp)
	move $a0, $30
	move $30, $a0
	li $v0, 9
	li $a0, 400
	syscall
	sw $v0, 20($gp)
	move $a0, $30
	move $30, $a0
	li $v0, 9
	li $a0, 400
	syscall
	sw $v0, 24($gp)
	move $a0, $30
	move $30, $a0
	li $v0, 9
	li $a0, 400
	syscall
	sw $v0, 28($gp)
	move $a0, $30
	#array_assign
	li $30, 0
	lw $t8, 28($gp)
	sw $30, 0($t8)
	lw $t8, 28($gp)
	sw $30, 4($t8)
	lw $t8, 28($gp)
	sw $30, 8($t8)
	lw $t8, 28($gp)
	sw $30, 12($t8)
	lw $t8, 28($gp)
	sw $30, 16($t8)
	lw $t8, 28($gp)
	sw $30, 20($t8)
	lw $t8, 28($gp)
	sw $30, 24($t8)
	lw $t8, 28($gp)
	sw $30, 28($t8)
	lw $t8, 28($gp)
	sw $30, 32($t8)
	lw $t8, 28($gp)
	sw $30, 36($t8)
	lw $t8, 28($gp)
	sw $30, 40($t8)
	lw $t8, 28($gp)
	sw $30, 44($t8)
	lw $t8, 28($gp)
	sw $30, 48($t8)
	lw $t8, 28($gp)
	sw $30, 52($t8)
	lw $t8, 28($gp)
	sw $30, 56($t8)
	lw $t8, 28($gp)
	sw $30, 60($t8)
	lw $t8, 28($gp)
	sw $30, 64($t8)
	lw $t8, 28($gp)
	sw $30, 68($t8)
	lw $t8, 28($gp)
	sw $30, 72($t8)
	lw $t8, 28($gp)
	sw $30, 76($t8)
	lw $t8, 28($gp)
	sw $30, 80($t8)
	lw $t8, 28($gp)
	sw $30, 84($t8)
	lw $t8, 28($gp)
	sw $30, 88($t8)
	lw $t8, 28($gp)
	sw $30, 92($t8)
	lw $t8, 28($gp)
	sw $30, 96($t8)
	lw $t8, 28($gp)
	sw $30, 100($t8)
	lw $t8, 28($gp)
	sw $30, 104($t8)
	lw $t8, 28($gp)
	sw $30, 108($t8)
	lw $t8, 28($gp)
	sw $30, 112($t8)
	lw $t8, 28($gp)
	sw $30, 116($t8)
	lw $t8, 28($gp)
	sw $30, 120($t8)
	lw $t8, 28($gp)
	sw $30, 124($t8)
	lw $t8, 28($gp)
	sw $30, 128($t8)
	lw $t8, 28($gp)
	sw $30, 132($t8)
	lw $t8, 28($gp)
	sw $30, 136($t8)
	lw $t8, 28($gp)
	sw $30, 140($t8)
	lw $t8, 28($gp)
	sw $30, 144($t8)
	lw $t8, 28($gp)
	sw $30, 148($t8)
	lw $t8, 28($gp)
	sw $30, 152($t8)
	lw $t8, 28($gp)
	sw $30, 156($t8)
	lw $t8, 28($gp)
	sw $30, 160($t8)
	lw $t8, 28($gp)
	sw $30, 164($t8)
	lw $t8, 28($gp)
	sw $30, 168($t8)
	lw $t8, 28($gp)
	sw $30, 172($t8)
	lw $t8, 28($gp)
	sw $30, 176($t8)
	lw $t8, 28($gp)
	sw $30, 180($t8)
	lw $t8, 28($gp)
	sw $30, 184($t8)
	lw $t8, 28($gp)
	sw $30, 188($t8)
	lw $t8, 28($gp)
	sw $30, 192($t8)
	lw $t8, 28($gp)
	sw $30, 196($t8)
	lw $t8, 28($gp)
	sw $30, 200($t8)
	lw $t8, 28($gp)
	sw $30, 204($t8)
	lw $t8, 28($gp)
	sw $30, 208($t8)
	lw $t8, 28($gp)
	sw $30, 212($t8)
	lw $t8, 28($gp)
	sw $30, 216($t8)
	lw $t8, 28($gp)
	sw $30, 220($t8)
	lw $t8, 28($gp)
	sw $30, 224($t8)
	lw $t8, 28($gp)
	sw $30, 228($t8)
	lw $t8, 28($gp)
	sw $30, 232($t8)
	lw $t8, 28($gp)
	sw $30, 236($t8)
	lw $t8, 28($gp)
	sw $30, 240($t8)
	lw $t8, 28($gp)
	sw $30, 244($t8)
	lw $t8, 28($gp)
	sw $30, 248($t8)
	lw $t8, 28($gp)
	sw $30, 252($t8)
	lw $t8, 28($gp)
	sw $30, 256($t8)
	lw $t8, 28($gp)
	sw $30, 260($t8)
	lw $t8, 28($gp)
	sw $30, 264($t8)
	lw $t8, 28($gp)
	sw $30, 268($t8)
	lw $t8, 28($gp)
	sw $30, 272($t8)
	lw $t8, 28($gp)
	sw $30, 276($t8)
	lw $t8, 28($gp)
	sw $30, 280($t8)
	lw $t8, 28($gp)
	sw $30, 284($t8)
	lw $t8, 28($gp)
	sw $30, 288($t8)
	lw $t8, 28($gp)
	sw $30, 292($t8)
	lw $t8, 28($gp)
	sw $30, 296($t8)
	lw $t8, 28($gp)
	sw $30, 300($t8)
	lw $t8, 28($gp)
	sw $30, 304($t8)
	lw $t8, 28($gp)
	sw $30, 308($t8)
	lw $t8, 28($gp)
	sw $30, 312($t8)
	lw $t8, 28($gp)
	sw $30, 316($t8)
	lw $t8, 28($gp)
	sw $30, 320($t8)
	lw $t8, 28($gp)
	sw $30, 324($t8)
	lw $t8, 28($gp)
	sw $30, 328($t8)
	lw $t8, 28($gp)
	sw $30, 332($t8)
	lw $t8, 28($gp)
	sw $30, 336($t8)
	lw $t8, 28($gp)
	sw $30, 340($t8)
	lw $t8, 28($gp)
	sw $30, 344($t8)
	lw $t8, 28($gp)
	sw $30, 348($t8)
	lw $t8, 28($gp)
	sw $30, 352($t8)
	lw $t8, 28($gp)
	sw $30, 356($t8)
	lw $t8, 28($gp)
	sw $30, 360($t8)
	lw $t8, 28($gp)
	sw $30, 364($t8)
	lw $t8, 28($gp)
	sw $30, 368($t8)
	lw $t8, 28($gp)
	sw $30, 372($t8)
	lw $t8, 28($gp)
	sw $30, 376($t8)
	lw $t8, 28($gp)
	sw $30, 380($t8)
	lw $t8, 28($gp)
	sw $30, 384($t8)
	lw $t8, 28($gp)
	sw $30, 388($t8)
	lw $t8, 28($gp)
	sw $30, 392($t8)
	lw $t8, 28($gp)
	sw $30, 396($t8)
	#callr
	#geti
	li $v0, 5
	syscall
	sw $v0, 52($gp)
	#assign
	li $30, 0
	sw $30, 32($gp)
L0_main:
	#branch
	lw $t8, 32($gp)
	lw $t9, 52($gp)
	bge $t8, $t9, EOI_main
	#callr
	#geti
	li $v0, 5
	syscall
	sw $v0, 0($gp)
	#array_store
	li $30, 4
	lw $t8, 32($gp)
	mul $30, $t8, $30
	lw $t8, 20($gp)
	add $30, $t8, $30
	lw $t8, 0($gp)
	sw $t8, 0($30)
	#assign
	li $30, 0
	sw $30, 4($gp)
L1_main:
	#branch
	lw $t8, 4($gp)
	lw $t9, 0($gp)
	bge $t8, $t9, L2_main
	#callr
	#geti
	li $v0, 5
	syscall
	sw $v0, 8($gp)
	#or
	li $30, 10
	lw $t9, 32($gp)
	mul $t9, $30, $t9
	sw $t9, 12($gp)
	#add
	lw $t8, 12($gp)
	lw $t9, 4($gp)
	add $t8, $t8, $t9
	sw $t8, 12($gp)
	#array_store
	li $30, 4
	lw $t8, 12($gp)
	mul $30, $t8, $30
	lw $t8, 16($gp)
	add $30, $t8, $30
	lw $t8, 8($gp)
	sw $t8, 0($30)
	#add
	lw $t9, 4($gp)
	addi $t9, $t9, 1
	sw $t9, 4($gp)
	#goto
	j L1_main
L2_main:
	#add
	lw $t9, 32($gp)
	addi $t9, $t9, 1
	sw $t9, 32($gp)
	#goto
	j L0_main
EOI_main:
	#array_store
	li $30, 1
	lw $t8, 28($gp)
	sw $30, 0($t8)
	#array_store
	li $30, 0
	lw $t8, 24($gp)
	sw $30, 0($t8)
	#assign
	li $30, 0
	sw $30, 44($gp)
	#assign
	li $30, 0
	sw $30, 32($gp)
	#assign
	li $30, 1
	sw $30, 4($gp)
L3_main:
	#branch
	lw $t8, 44($gp)
	lw $t9, 4($gp)
	beq $t8, $t9, FIN_main
	#array_load
	li $30, 4
	lw $t8, 32($gp)
	mul $30, $30, $t8
	lw $t8, 24($gp)
	add $30, $t8, $30
	lw $t8, 0($30)
	sw $t8, 36($gp)
	#add
	lw $t9, 44($gp)
	addi $t9, $t9, 1
	sw $t9, 44($gp)
	#assign
	lw $t9, 44($gp)
	sw $t9, 32($gp)
	#array_load
	li $30, 4
	lw $t8, 36($gp)
	mul $30, $30, $t8
	lw $t8, 20($gp)
	add $30, $t8, $30
	lw $t8, 0($30)
	sw $t8, 0($gp)
	#assign
	li $30, 0
	sw $30, 40($gp)
L4_main:
	#branch
	lw $t8, 40($gp)
	lw $t9, 0($gp)
	bge $t8, $t9, L5_main
	#or
	li $30, 10
	lw $t9, 36($gp)
	mul $t9, $30, $t9
	sw $t9, 12($gp)
	#add
	lw $t8, 12($gp)
	lw $t9, 40($gp)
	add $t8, $t8, $t9
	sw $t8, 12($gp)
	#array_load
	li $30, 4
	lw $t8, 12($gp)
	mul $30, $30, $t8
	lw $t8, 16($gp)
	add $30, $t8, $30
	lw $t8, 0($30)
	sw $t8, 8($gp)
	#array_load
	li $30, 4
	lw $t8, 8($gp)
	mul $30, $30, $t8
	lw $t8, 28($gp)
	add $30, $t8, $30
	lw $t8, 0($30)
	sw $t8, 48($gp)
	#branch
	li $30, 1
	lw $t8, 48($gp)
	beq $t8, $30, L6_main
	#array_store
	li $v1, 1
	li $30, 4
	lw $t8, 8($gp)
	mul $30, $t8, $30
	lw $t8, 28($gp)
	add $30, $t8, $30
	sw $v1, 0($30)
	#array_store
	li $30, 4
	lw $t8, 4($gp)
	mul $30, $t8, $30
	lw $t8, 24($gp)
	add $30, $t8, $30
	lw $t8, 8($gp)
	sw $t8, 0($30)
	#add
	lw $t9, 4($gp)
	addi $t9, $t9, 1
	sw $t9, 4($gp)
L6_main:
	#add
	lw $t9, 40($gp)
	addi $t9, $t9, 1
	sw $t9, 40($gp)
	#goto
	j L4_main
L5_main:
	#goto
	j L3_main
FIN_main:
	#assign
	li $30, 0
	sw $30, 32($gp)
L7_main:
	#branch
	lw $t8, 32($gp)
	lw $t9, 4($gp)
	beq $t8, $t9, L8_main
	#array_load
	li $30, 4
	lw $t8, 32($gp)
	mul $30, $30, $t8
	lw $t8, 24($gp)
	add $30, $t8, $30
	lw $t8, 0($30)
	sw $t8, 8($gp)
	#call
	#puti
	move $30, $a0
	lw $a0, 8($gp)
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
	lw $t9, 32($gp)
	addi $t9, $t9, 1
	sw $t9, 44($gp)
	#assign
	lw $t9, 44($gp)
	sw $t9, 32($gp)
	#goto
	j L7_main
L8_main:
	jr $ra
	li $v0, 10
	syscall
	move $a0, $t8
