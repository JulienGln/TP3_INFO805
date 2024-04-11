DATA SEGMENT
	prixHt DD
	prixTtc DD
DATA ENDS
CODE SEGMENT
	mov eax, 200
	mov eax, prixHt
	mov eax, prixHt
	mov eax, 119
	pop ebx
	pop eax
	mul eax, ebx
	push eax
	mov eax, 100
	pop ebx
	pop eax
	div eax, ebx
	push eax
	mov eax, prixTtc
CODE ENDS