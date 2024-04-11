package fr.usmb.m1isc.compilation.tp;

public class Noeud extends Arbre {
    private Object valeur;

    public Noeud(Object valeur) {
        this.valeur = valeur;
    }

    public Object getValeur() {
        return valeur;
    }

    @Override
    public String genererCode() {
        return "\tmov eax, " + valeur + "\n";
    }

    @Override
    public String toString() {
        return valeur.toString();
    }
}
