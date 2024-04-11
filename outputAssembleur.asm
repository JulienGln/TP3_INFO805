DATA SEGMENT
	a DD
DATA ENDS
CODE SEGMENT
	mov eax, 5
	mul eax, -1
	push eax
	mov eax, 1
	pop ebx
	add eax, ebx
	mov a, eax
CODE ENDS