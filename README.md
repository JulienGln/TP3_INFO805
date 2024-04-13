# TP Compilation : Génération de code pour un sous ensemble du langage λ-ada.
### GALERNE Julien

## Instructions qui marchent bien 
- let
- input
- opérations arithmétiques
    - Exemple avec ```let a = -9 + 7 * 3 / 2 % 5;.```
      - ```; (let a (+ (- 9) (% (/ (* 7 3) 2) 5)))```
      - ```
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
        ```
      - ```
        eip: 22 --> mov a, eax
        reg: eip: 23
        reg: eax: -9
        reg: ebx: -9
        reg: ecx: 10
        reg: edx: 0
        reg: ebp: 65536
        reg: esp: 65532
        eflags: ZF=0 LT=1
        mem:65532: -9
        mem:65528: -9
        mem:65524: 5
        mem:65520: 21
        ```
      - Le résultat affiche -9 (au lieu de -8.5) car le compilateur n'utilise que des nombres entiers.
- opérateurs de comparaison
  - Exemple avec ```let a = 5 < 9;.```
    - ```; (let a (< 5 9))```
    - ```
      DATA SEGMENT
        a DD
      DATA ENDS
      CODE SEGMENT
        mov eax, 5
        push eax
        mov eax, 9
        pop ebx
        sub eax, ebx
        jle faux_lt_1
        mov eax, 1
        jmp sortie_lt_1
      faux_lt_1:
        mov eax, 0
      sortie_lt_1:
        mov a, eax
      CODE ENDS
      ```
    - ```
        eip: 9 --> mov a, eax
        reg: eip: 10
        reg: eax: 1
        reg: ebx: 5
        reg: ecx: 0
        reg: edx: 0
        reg: ebp: 65536
        reg: esp: 65532
        eflags: ZF=0 LT=0
        mem:65532: 1
        mem:65528: 5
      ```
    - Le résultat dans `eax` est de 1 donc **vrai**.
- and
  - Exemple avec ```let a = 5 and 7;.```
    - ```; (let a (and 5 7))```
    - ```
      DATA SEGMENT
        a DD
      DATA ENDS
      CODE SEGMENT
        mov eax, 5
        push eax
        mov eax, 7
        pop ebx
        sub eax, ebx
        jz vrai_and_1
        mov eax, 0
        jmp sortie_and_1
      vrai_and_1:
        mov eax, 1
      sortie_and_1:
        mov a, eax
      CODE ENDS
      ```
    - ```
        eip: 9 --> mov a, eax
        reg: eip: 10
        reg: eax: 0
        reg: ebx: 5
        reg: ecx: 0
        reg: edx: 0
        reg: ebp: 65536
        reg: esp: 65532
        eflags: ZF=1 LT=0
        mem:65532: 0
        mem:65528: 5
      ```
    - Le résultat dans `eax` donne 0 donc **faux**.
- or
  - Exemple avec ```let a = 1; let b = a or 0;.```
    - ```; (let a 1) (; (let b (or a 0)))```
    - ```
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
      ```
    - ```
      eip: 11 --> mov b, eax
      reg: eip: 12
      reg: eax: 1
      reg: ebx: 1
      reg: ecx: 0
      reg: edx: 0
      reg: ebp: 65536
      reg: esp: 65528
      eflags: ZF=0 LT=0
      mem:65532: 1
      mem:65528: 1
      mem:65524: 1
      ```
    - Le résultat dans `eax` est de 1 donc **vrai**.

## Instructions qui ne marchent pas
- output (plante)
- not (non implémentée)
- if
- while