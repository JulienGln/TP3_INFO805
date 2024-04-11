DATA SEGMENT
	a DD
	b DD
DATA ENDS
CODE SEGMENT
	mov eax, 5
	push eax
	mov eax, 3
	pop ebx
	sub eax, ebx
	jl faux_lt_1
	mov eax, 1
	jmp sortie_lt_1
faux_lt_1:
	mov eax, 0
sortie_lt_1:
	mov a, eax
	mov eax, 5
	push eax
	mov eax, 1
	pop ebx
	sub eax, ebx
	jl faux_lt_2
	mov eax, 1
	jmp sortie_lt_2
faux_lt_2:
	mov eax, 0
sortie_lt_2:
	mov b, eax
CODE ENDS