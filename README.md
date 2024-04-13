# TP Compilation : Génération de code pour un sous ensemble du langage λ-ada.
### GALERNE Julien

## Instructions qui marchent bien 
- let
- input
  - opérations arithmétiques
      - Exemple avec ```let a = -9 + 7 * 3 / 2 % 5;```
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
        - Le résultat affiche -9 (au lieu de -8.5) car le compilateur n'utilise que des nombres entiers

## Instructions qui plantent
- output