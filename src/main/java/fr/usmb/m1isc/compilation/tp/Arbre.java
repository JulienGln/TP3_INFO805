package fr.usmb.m1isc.compilation.tp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Arbre {
    private Arbre fg, fd; // fils gauche droit
    private String symbol; // un opérateur

    private static int cpt_COMP = 0; // compteur des opérateurs de comparaison
    private static int cpt_IF = 0; // compteur de conditions
    private static int cpt_LOOP = 0; // compteur de boucles
    private static int cpt_BOOL = 0; // compteur pour les booléens

    public Arbre(String symbol, Arbre fg, Arbre fd) {
        this.symbol = symbol;
        this.fg = fg;
        this.fd = fd;
        if (fd == null && fg == null)
            System.out.println("Nouvel arbre : " + symbol);
        else if (fd == null)
            System.out.println("Nouvel arbre : " + symbol + " " + fg);
            //System.out.println("Nouvel arbre : " + symbol + " " + fg + " " + fd);
        else if (fg == null)
            System.out.println("Nouvel arbre : " + symbol + " " + fd);
        else
            System.out.println("Nouvel arbre : " + symbol + " " + fg + " " + fd);
    }

    public Arbre(String symbol, Arbre fg) {
        this(symbol, fg, null);
    }

    public Arbre() {
        this(null, null, null);
    }

    public Arbre getFg() {
        return fg;
    }

    public void setFg(Arbre fg) {
        this.fg = fg;
    }

    public Arbre getFd() {
        return fd;
    }

    public void setFd(Arbre fd) {
        this.fd = fd;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        String res = "";
        res += "(";
        res += symbol + " ";
        res += fg.toString();
        if (fd != null) res += " " + fd.toString();
        res += ")";
        return res;
    }

    /**
     * Génération du code assembleur
     * @return une châine représentant le code en assembleur
     */
    public String genererCode(){
        String code = "";
        switch (symbol) {
            // OPERATIONS ARITHMETIQUES
            case "+":
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tadd eax, ebx\n";
                break;
            case "-":
                if (fd == null) {
                    // MOINS UNAIRE
                    code += fg.genererCode();
                    code += "\tmul eax, -1\n";
                } else {
                    code += fg.genererCode();
                    code += "\tpush eax\n";
                    code += fd.genererCode();
                    code += "\tpop ebx\n\tsub ebx, eax\n\tmov eax, ebx\n";
                }
                break;
            case "*":
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tmul eax, ebx\n";
                break;
            case "/":
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tdiv ebx, eax\n\tmov eax, ebx\n";
                break;
            case "%":
                code += fd.genererCode();
                code += "\tpush eax\n";
                code += fg.genererCode();
                code += "\tpop ebx\n\tmov ecx, eax\n\tdiv ecx, ebx\n\tmul ecx, ebx\n\tsub eax, ecx\n";
                break;
            // OPERATEURS DE COMPARAISON
            case "=":
                cpt_COMP++;
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tsub eax, ebx\n";
                code += "\tjnz faux_eq_" + cpt_COMP + "\n";
                code += "\tmov eax, 1\n\tjmp sortie_eq_" + cpt_COMP + "\n";
                code += "faux_eq_" + cpt_COMP + ":\n\tmov eax, 0\n";
                code += "sortie_eq_" + cpt_COMP + ":\n";
                break;
            case "<":
                cpt_COMP++;
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tsub eax, ebx\n";
                code += "\tjle faux_lt_" + cpt_COMP + "\n";
                code += "\tmov eax, 1\n\tjmp sortie_lt_" + cpt_COMP + "\n";
                code += "faux_lt_" + cpt_COMP + ":\n\tmov eax, 0\n";
                code += "sortie_lt_" + cpt_COMP + ":\n";
                break;
            case "<=":
                cpt_COMP++;
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tsub eax, ebx\n";
                code += "\tjl faux_lte_" + cpt_COMP + "\n";
                code += "\tmov eax, 1\n\tjmp sortie_lte_" + cpt_COMP + "\n";
                code += "faux_lte_" + cpt_COMP + ":\n\tmov eax, 0\n";
                code += "sortie_lte_" + cpt_COMP + ":\n";
                break;
            case ">":
                cpt_COMP++;
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tsub eax, ebx\n";
                code += "\tjg faux_gt_" + cpt_COMP + "\n";
                code += "\tmov eax, 1\n\tjmp sortie_gt_" + cpt_COMP + "\n";
                code += "faux_gt_" + cpt_COMP + ":\n\tmov eax, 0\n";
                code += "sortie_gt_" + cpt_COMP + ":\n";
                break;
            case ">=":
                cpt_COMP++;
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tsub eax, ebx\n";
                code += "\tjge faux_gte_" + cpt_COMP + "\n";
                code += "\tmov eax, 1\n\tjmp sortie_gte_" + cpt_COMP + "\n";
                code += "faux_gte_" + cpt_COMP + ":\n\tmov eax, 0\n";
                code += "sortie_gte_" + cpt_COMP + ":\n";
                break;
            // BOOLEENS
            case "!":
                break;
            case "or":
                cpt_BOOL++;
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tsub eax, ebx\n";
                code += "\tjnz vrai_or_" + cpt_BOOL + "\n";
                code += "\tmov eax, 0\n\tjmp sortie_or_" + cpt_BOOL + "\n";
                code += "vrai_or_" + cpt_BOOL + ":\n\tmov eax, 1\n";
                code += "sortie_or_" + cpt_BOOL + ":\n";
                break;
            case "and":
                cpt_BOOL++;
                code += fg.genererCode();
                code += "\tpush eax\n";
                code += fd.genererCode();
                code += "\tpop ebx\n\tsub eax, ebx\n";
                code += "\tjz vrai_and_" + cpt_BOOL + "\n";
                code += "\tmov eax, 0\n\tjmp sortie_and_" + cpt_BOOL + "\n";
                code += "vrai_and_" + cpt_BOOL + ":\n\tmov eax, 1\n";
                code += "sortie_and_" + cpt_BOOL + ":\n";
                break;
            // CONDITIONELLES
            case "if":
                cpt_IF++;
                code += fg.genererCode();
                code += "\tjz else_if_" + cpt_IF + "\n";
                code += fd.getFg().genererCode(); //code += fd.genererCode();
                code += "\tjmp sortie_if_" + cpt_IF + "\n";
                code += "else_if_" + cpt_IF + ":\n";
                code += fd.getFd().genererCode(); //code += fd.genererCode();
                code += "sortie_if_" + cpt_IF + ":\n";
                break;
            case "while":
                cpt_LOOP++;
                code += "debut_while_" + cpt_LOOP + ":\n";
                code += fg.genererCode();
                code += "\tjz sortie_while_" + cpt_LOOP + "\n";
                code += fd.genererCode();
                code += "\tjmp debut_while_" + cpt_LOOP + "\nsortie_while_" + cpt_LOOP + ":\n";
                break;
            // AUTRES
            case "let":
                // let a = 5; fg = a et fd = 5
                code += fd.genererCode();
                code += "\tmov " + ((Noeud) fg).getValeur() + ", eax\n";
                break;
            case "output":
                code += fg.genererCode();
                code += "\tout eax\n";
                break;
            case ";":
                code += fg.genererCode();
                if (fd != null) code += fd.genererCode();
                break;
        }
        return code;
    }

    /**
     * Génération du code asm pour le DATA SEGMENT
     * @param variables l'ensemble de vraiables déjà déclarées dans de précédents appels
     * @return le code du DATA SEGMENT en chaîne de caractères
     */
    public String genererData(Set<String> variables) {
        String data = "";
        if (symbol != null) {
            if (symbol.equals("let")) {
                String var = fg.toString();
                if (!variables.contains(var)) {
                    data += "\t" + var + " DD\n";
                    variables.add(var);
                }
            } else {
                if (fg != null) data += fg.genererData(variables);
                if (fd != null) data += fd.genererData(variables);
            }
        }
        return data;
    }

    /**
     * Encapsulation du code assembleur dans un fichier Assembleur avec les DATA SEGMENTS etc.
     */
    public String generer() {
        String code = "DATA SEGMENT\n";
        code += genererData(new HashSet<>());
        code +=  "DATA ENDS\nCODE SEGMENT\n";
        // génération du code
        code += genererCode();
        code += "CODE ENDS";

        try {
            FileWriter out = new FileWriter("output.asm");
            out.write(code);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return code;
    }
}
