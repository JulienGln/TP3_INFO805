DATA SEGMENT
	a DD
DATA ENDS
CODE SEGMENT
	mov eax, 9
	mul eax, -1
	push eax
	mov eax, 5
	push eax
	mov eax, 7
	push eax
	mov eax, 3
	pop ebx
	mul eax, ebx
	push eax
	mov eax, 2
	pop ebx
	div ebx, eax
	mov eax, ebx
	pop ebx
	mov ecx, eax
	div ecx, ebx
	mul ecx, ebx
	sub eax, ecx
	pop ebx
	add eax, ebx
	mov a, eax
CODE ENDS