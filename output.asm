DATA SEGMENT
	a DD
DATA ENDS
CODE SEGMENT
	mov eax, 5
	push eax
	mov eax, 5
	pop ebx
	sub eax, ebx
	jnz vrai_or_1
	mov eax, 0
	jmp sortie_or_1
vrai_or_1:
	mov eax, 1
sortie_or_1:
	mov a, eax
CODE ENDS