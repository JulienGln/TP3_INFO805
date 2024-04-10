package fr.usmb.m1isc.compilation.tp;

import java.io.FileWriter;
import java.io.IOException;

public class Arbre {
    private Arbre fg, fd; // fils gauche droit
    private String symbol; // un opérateur

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
        String code = "\t";
        switch (symbol) {
            // OPERATIONS ARITHMETIQUES
            case "+":
                code += fg.genererCode();
                code += fd.genererCode();
                code += "pop ebx\npop eax\nadd eax, ebx\npush eax\n";
                break;
            case "-":
                code += fg.genererCode();
                code += fd.genererCode();
                code += "pop ebx\npop eax\nsub eax, ebx\npush eax\n";
                break;
            case "*":
                code += fg.genererCode();
                code += fd.genererCode();
                code += "pop ebx\npop eax\nmul eax, ebx\npush eax\n";
                break;
            case "/":
                code += fg.genererCode();
                code += fd.genererCode();
                code += "pop ebx\npop eax\ndiv eax, ebx\npush eax\n";
                break;
            // OPERATEURS DE COMPARAISON
            /*case "=":
                code += fd.genererCode();
                code += "pop eax\nmov x, eax\npush eax\n";
                break;*/
            // AUTRES
            case "let":
                // let a = 5; fg = a et fd = 5
                code += fd.genererCode();
                code += fg.genererCode();
                break;
            case "input":
                code += "in eax\n";
                break;
            case "output":
                code += "out eax\n";
                break;
            case ";":
                code += fg.genererCode();
                // code += "pop eax\n";
                code += fd.genererCode();
                break;
        }
        return code;
    }

    /**
     * Génération du code asm pour le DATA SEGMENT
     * @return le code du DATA SEGMENT en chaîne de caractères
     */
    public String genererData() {
        String data = "";

        if (symbol.equals("let"))
            data += "\t" + fg.toString() + " DD\n";
        else {
            if (fg != null) data += fg.genererData();
            if (fd != null) data += fd.genererData();
        }

        return data;
    }

    /**
     * Encapsulation du code assembleur dans un fichier Assembleur avec les DATA SEGMENTS etc.
     */
    public String generer() {
        String code = "DATA SEGMENT\n";
        code += genererData();
        code +=  "DATA ENDS\nCODE SEGMENT\n";
        // génération du code
        code += genererCode();
        code += "\nCODE ENDS";

        try {
            FileWriter out = new FileWriter("outputAssembleur.asm");
            out.write(code);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return code;
    }
}
