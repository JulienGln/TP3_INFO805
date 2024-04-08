package fr.usmb.m1isc.compilation.tp;

import java_cup.runtime.Symbol;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws Exception  {
		LexicalAnalyzer yy;
		if (args.length > 0)
			yy = new LexicalAnalyzer(new FileReader(args[0])) ;
		else
			yy = new LexicalAnalyzer(new InputStreamReader(System.in)) ;
		@SuppressWarnings("deprecation")
		parser p = new parser (yy);
		Symbol s = p.parse( );
		Arbre arbre = (Arbre) s.value;
		String codeAsm = arbre.generer();

		try {
			PrintWriter out = new PrintWriter("outputAssembleur.asm");
			out.println(codeAsm);
			out.close();
		} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

		System.out.println("ARBRE FINAL :\n" + arbre.toString());
    }

}
