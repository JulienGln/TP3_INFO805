package fr.usmb.m1isc.compilation.tp;

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
            case "+":
                code += fg.genererCode();
                code += fd.genererCode();
                code += "pop ebx\npop eax\nadd eax, ebx\npush eax";
                break;
            case "-":
                code += fg.genererCode();
                code += fd.genererCode();
                code += "pop ebx\npop eax\nsub eax, ebx\npush eax";
                break;
            case "=":
                code += fd.genererCode();
                code += "pop eax\nmov x, eax\npush eax";
                break;
            case ";":
                code += fg.genererCode();
                code += "pop eax\n";
                code += fd.genererCode();
                break;
            default:
                break;
        }
        return code;
    }

    /**
     * Encapsulation du code assembleur dans un fichier Assembleur avec les DATA SEGMENTS etc.
     */
    public String generer() {
        String file = "DATA SEGMENT\n";
        // génération des variables ...
        file +=  "DATA ENDS\nCODE SEGMENT\n";
        // génération du code
        file += genererCode();
        return file + "\nCODE ENDS";
    }
}
