.text
.globl main
main:
	move $30, $a0
	li $v0, 9
	li $a0, 4000
	syscall
	move $16, $v0
	move $a0, $30
	move $30, $a0
	li $v0, 9
	li $a0, 400
	syscall
	move $15, $v0
	move $a0, $30
	move $30, $a0
	li $v0, 9
	li $a0, 400
	syscall
	move $14, $v0
	move $a0, $30
	move $30, $a0
	li $v0, 9
	li $a0, 400
	syscall
	move $13, $v0
	move $a0, $30
	#array_assign
	li $30, 0
	sw $30, 0($13)
	sw $30, 4($13)
	sw $30, 8($13)
	sw $30, 12($13)
	sw $30, 16($13)
	sw $30, 20($13)
	sw $30, 24($13)
	sw $30, 28($13)
	sw $30, 32($13)
	sw $30, 36($13)
	sw $30, 40($13)
	sw $30, 44($13)
	sw $30, 48($13)
	sw $30, 52($13)
	sw $30, 56($13)
	sw $30, 60($13)
	sw $30, 64($13)
	sw $30, 68($13)
	sw $30, 72($13)
	sw $30, 76($13)
	sw $30, 80($13)
	sw $30, 84($13)
	sw $30, 88($13)
	sw $30, 92($13)
	sw $30, 96($13)
	sw $30, 100($13)
	sw $30, 104($13)
	sw $30, 108($13)
	sw $30, 112($13)
	sw $30, 116($13)
	sw $30, 120($13)
	sw $30, 124($13)
	sw $30, 128($13)
	sw $30, 132($13)
	sw $30, 136($13)
	sw $30, 140($13)
	sw $30, 144($13)
	sw $30, 148($13)
	sw $30, 152($13)
	sw $30, 156($13)
	sw $30, 160($13)
	sw $30, 164($13)
	sw $30, 168($13)
	sw $30, 172($13)
	sw $30, 176($13)
	sw $30, 180($13)
	sw $30, 184($13)
	sw $30, 188($13)
	sw $30, 192($13)
	sw $30, 196($13)
	sw $30, 200($13)
	sw $30, 204($13)
	sw $30, 208($13)
	sw $30, 212($13)
	sw $30, 216($13)
	sw $30, 220($13)
	sw $30, 224($13)
	sw $30, 228($13)
	sw $30, 232($13)
	sw $30, 236($13)
	sw $30, 240($13)
	sw $30, 244($13)
	sw $30, 248($13)
	sw $30, 252($13)
	sw $30, 256($13)
	sw $30, 260($13)
	sw $30, 264($13)
	sw $30, 268($13)
	sw $30, 272($13)
	sw $30, 276($13)
	sw $30, 280($13)
	sw $30, 284($13)
	sw $30, 288($13)
	sw $30, 292($13)
	sw $30, 296($13)
	sw $30, 300($13)
	sw $30, 304($13)
	sw $30, 308($13)
	sw $30, 312($13)
	sw $30, 316($13)
	sw $30, 320($13)
	sw $30, 324($13)
	sw $30, 328($13)
	sw $30, 332($13)
	sw $30, 336($13)
	sw $30, 340($13)
	sw $30, 344($13)
	sw $30, 348($13)
	sw $30, 352($13)
	sw $30, 356($13)
	sw $30, 360($13)
	sw $30, 364($13)
	sw $30, 368($13)
	sw $30, 372($13)
	sw $30, 376($13)
	sw $30, 380($13)
	sw $30, 384($13)
	sw $30, 388($13)
	sw $30, 392($13)
	sw $30, 396($13)
	#assign
	li $30, 10
	move $12, $30
	#callr
	#geti
	li $v0, 5
	syscall
	move $8, $v0
	#assign
	li $30, 0
	move $21, $30
L0_main:
	#branch
	bge $21, $8, EOI_main
	#callr
	#geti
	li $v0, 5
	syscall
	move $20, $v0
	#array_store
	li $30, 4
	mul $30, $21, $30
	add $30, $15, $30
	sw $20, 0($30)
	#assign
	li $30, 0
	move $19, $30
L1_main:
	#branch
	bge $19, $20, L2_main
	#callr
	#geti
	li $v0, 5
	syscall
	move $18, $v0
	#or
	mul $17, $12, $21
	#add
	add $17, $17, $19
	#array_store
	li $30, 4
	mul $30, $17, $30
	add $30, $16, $30
	sw $18, 0($30)
	#add
	addi $19, $19, 1
	#goto
	j L1_main
L2_main:
	#add
	addi $21, $21, 1
	#goto
	j L0_main
EOI_main:
	#array_store
	li $30, 1
	sw $30, 0($13)
	#array_store
	li $30, 0
	sw $30, 0($14)
	#assign
	li $30, 0
	move $11, $30
	#assign
	move $21, $11
	#assign
	li $30, 1
	move $19, $30
L3_main:
	#branch
	beq $21, $19, FIN_main
	#array_load
	li $30, 4
	mul $30, $30, $21
	add $30, $14, $30
	lw $10, 0($30)
	#add
	addi $11, $21, 1
	#assign
	move $21, $11
	#array_load
	li $30, 4
	mul $30, $30, $10
	add $30, $15, $30
	lw $20, 0($30)
	#assign
	li $30, 0
	move $9, $30
L4_main:
	#branch
	bge $9, $20, L5_main
	#or
	mul $17, $12, $10
	#add
	add $17, $17, $9
	#array_load
	li $30, 4
	mul $30, $30, $17
	add $30, $16, $30
	lw $18, 0($30)
	#array_load
	li $30, 4
	mul $30, $30, $18
	add $30, $13, $30
	lw $8, 0($30)
	#branch
	li $30, 1
	beq $8, $30, L6_main
	#array_store
	li $v1, 1
	li $30, 4
	mul $30, $18, $30
	add $30, $13, $30
	sw $v1, 0($30)
	#array_store
	li $30, 4
	mul $30, $19, $30
	add $30, $14, $30
	sw $18, 0($30)
	#add
	addi $19, $19, 1
L6_main:
	#add
	addi $9, $9, 1
	#goto
	j L4_main
L5_main:
	#goto
	j L3_main
FIN_main:
	#assign
	li $30, 0
	move $11, $30
	#assign
	move $21, $11
L7_main:
	#branch
	beq $21, $19, L8_main
	#array_load
	li $30, 4
	mul $30, $30, $21
	add $30, $14, $30
	lw $18, 0($30)
	#call
	#puti
	move $30, $a0
	move $a0, $18
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
	addi $11, $21, 1
	#assign
	move $21, $11
	#goto
	j L7_main
L8_main:
	jr $ra
	li $v0, 10
	syscall
