DATA SEGMENT
	a DD
	b DD
DATA ENDS
CODE SEGMENT
	in eax
	mov a, eax
	mov eax, 2
	push eax
	mov eax, a
	pop ebx
	mov ecx, eax
	div ecx, ebx
	mul ecx, ebx
	sub eax, ecx
	push eax
	mov eax, 0
	pop ebx
	sub eax, ebx
	jnz faux_eq_1
	mov eax, 1
	jmp sortie_eq_1
faux_eq_1:
	mov eax, 0
sortie_eq_1:
	jz else_if_1
	mov eax, 1
	mov b, eax
	jmp sortie_if_1
else_if_1:
	mov eax, 0
	mov b, eax
sortie_if_1:
	mov eax, b
	out eax
CODE ENDS