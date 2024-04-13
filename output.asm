DATA SEGMENT
	i DD
	a DD
DATA ENDS
CODE SEGMENT
	mov eax, 5
	mov i, eax
	mov eax, i
	push eax
	mov eax, 5
	pop ebx
	add eax, ebx
	mov a, eax
CODE ENDS