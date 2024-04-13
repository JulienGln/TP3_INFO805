DATA SEGMENT
	a DD
	b DD
DATA ENDS
CODE SEGMENT
	mov eax, 1
	mov a, eax
	mov eax, a
	push eax
	mov eax, 0
	pop ebx
	sub eax, ebx
	jnz vrai_or_1
	mov eax, 0
	jmp sortie_or_1
vrai_or_1:
	mov eax, 1
sortie_or_1:
	mov b, eax
CODE ENDS