# TP Compilation : Génération de code pour un sous ensemble du langage λ-ada.
## GALERNE Julien

Le dépôt contient :
- l'analyseur syntaxique [cup](./src/main/cup/AnalyseurSyntaxique.cup)
- l'analyseur lexical [jflex](./src/main/jflex/AnalyseurLexical.jflex)
  - l'analyseur lexical permet aussi de reconnaître les instructions en français
- les [classes](./src/main/java/fr/usmb/m1isc/compilation/tp/) de manipulation de l'arbre, à savoir Arbre.java et Noeud.java
  - le code assembleur est généré dans la classe [Arbre.java](./src/main/java/fr/usmb/m1isc/compilation/tp/Arbre.java), dans la méthode `generer()`
  - la méthode `generer()` est appelée lorsque le point est détecté (cf. le fichier [cup](./src/main/cup/AnalyseurSyntaxique.cup))
  - le fichier assembleur écrit s'intitule `output.asm` et contient le code assembleur correspondant au code écrit par l'utilisateur dans le terminal
- un fichier contenant 3 [programmes](./programmes.lambada), illustrant les exemples de l'exercice 1 et 2 ainsi qu'un exemple supplémentaire
- un README pour le compte-rendu, avec des exemples sur les fonctionnalités les plus intéressantes
  - les exemples indiquent le code λ-ada utilisé, la traduction en arbre abstrait, le code assembleur généré et enfin l'exécution de ce code avec [l'émulateur](vm-0.9.jar) en entrant la commande `java -jar vm-0.9.jar output.asm --debug`
## Fonctionnalités développées
- let
- input
- output
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
- if
  - Exemple avec le test de parité du fichier de [programmes](programmes.lambada)
    - ```; (let a input) (; (if (= (% a 2) 0) (then (let b 1) (let b 0))) (output b))```
    - ```
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
      ```
    - ```
        >48
        ...
        >>>>1
        reg: eip: 26
        reg: eax: 1
        reg: ebx: 0
        reg: ecx: 48
        reg: edx: 0
        reg: ebp: 65536
        reg: esp: 65528
        eflags: ZF=0 LT=0
        mem:65532: 48
        mem:65528: 1
        mem:65524: 0
      ```
    - Le nombre 48 est bien pair (output = 1 donc **vrai**).
- while
  - Exemple avec le code de l'exercice 2 du fichier de [programmes](programmes.lambada)
    - ```; (let a input) (; (let b input) (; (while (< 0 b) (; (let aux (% a b)) (; (let a b) (let b aux)))) (output a)))```
    - ```
      DATA SEGMENT
        a DD
        b DD
        aux DD
      DATA ENDS
      CODE SEGMENT
        in eax
        mov a, eax
        in eax
        mov b, eax
      debut_while_1:
        mov eax, 0
        push eax
        mov eax, b
        pop ebx
        sub eax, ebx
        jle faux_lt_1
        mov eax, 1
        jmp sortie_lt_1
      faux_lt_1:
        mov eax, 0
      sortie_lt_1:
        jz sortie_while_1
        mov eax, b
        push eax
        mov eax, a
        pop ebx
        mov ecx, eax
        div ecx, ebx
        mul ecx, ebx
        sub eax, ecx
        mov aux, eax
        mov eax, b
        mov a, eax
        mov eax, aux
        mov b, eax
        jmp debut_while_1
      sortie_while_1:
        mov eax, a
        out eax
      CODE ENDS
      ```
    - ```
        >221
        ...
        >782
        ...
        >>>>17
        reg: eip: 30
        reg: eax: 17
        reg: ebx: 0
        reg: ecx: 102
        reg: edx: 0
        reg: ebp: 65536
        reg: esp: 65524
        eflags: ZF=0 LT=0
        mem:65532: 17
        mem:65528: 0
        mem:65524: 0
        mem:65520: 0
      ```
    - Le PGCD de 221 et 782 donne 17.