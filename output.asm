DATA SEGMENT
	a DD
DATA ENDS
CODE SEGMENT
	mov eax, 5
	push eax
	mov eax, 7
	pop ebx
	cmp ebx, eax
	jge vrai_ge_1
	mov eax, 0
	jmp sortie_ge_1
vrai_ge_1:
	mov eax, 1
sortie_ge_1:
	mov a, eax
CODE ENDS