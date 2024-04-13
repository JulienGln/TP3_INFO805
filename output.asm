DATA SEGMENT
	a DD
DATA ENDS
CODE SEGMENT
	mov eax, 5
	push eax
	mov eax, 6
	pop ebx
	sub eax, ebx
	jnz faux_eq_1
	mov eax, 1
	jmp sortie_eq_1
faux_eq_1:
	mov eax, 0
sortie_eq_1:
	mov a, eax
CODE ENDS