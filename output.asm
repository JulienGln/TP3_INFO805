DATA SEGMENT
	a DD
DATA ENDS
CODE SEGMENT
	mov eax, 5
	push eax
	mov eax, 5
	pop ebx
	sub eax, ebx
	jz vrai_and_2
	mov eax, 0
	jmp sortie_and_2
vrai_and_2:
	mov eax, 1
sortie_and_2:
	push eax
	mov eax, 6
	pop ebx
	sub eax, ebx
	jz vrai_and_2
	mov eax, 0
	jmp sortie_and_2
vrai_and_2:
	mov eax, 1
sortie_and_2:
	mov a, eax
CODE ENDS