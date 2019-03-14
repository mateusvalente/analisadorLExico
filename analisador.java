import java.util.List;
import java.util.ArrayList;

/*
 * Lexical analyzer for Scheme-like minilanguage:
 * (define (foo x) (bar (baz x)))
 */
public class Lexer {
    public static enum Type {
        // Esse analisador reconhece 3 tipos:
        // open parens, close parens, and an "atom" type
        LPAREN, RPAREN, ATOM;
    }
    public static class Token {
        public final Type t;
        public final String c; 
        public Token(Type t, String c) {
            this.t = t;
            this.c = c;
        }
        //retorna uma string do arquivo lido
        public String toString() {
            if(t == Type.ATOM) {
                return "ATOM<" + c + ">";
            }
            return t.toString();
        }
    }

    /*
     *  recebe a string e o index, percorre todos os caracteres até achar espaço, e terona a substring (palavra, encontrada)
     */
    public static String getAtom(String s, int i) {
        int j = i;
        for( ; j < s.length(); ) {
            if(Character.isLetter(s.charAt(j))) {
                j++;
            } else {
                return s.substring(i, j);
            }
        }
        return s.substring(i, j);
    }
    
    //lista dos tokens, para cada palavra, salva-se uma posição da lista, dizendo se é LPAREN, RPAREN ou ATOM
    public static List<Token> lex(String input) {
        List<Token> result = new ArrayList<Token>();
        for(int i = 0; i < input.length(); ) {
            switch(input.charAt(i)) {
            case '(':
                result.add(new Token(Type.LPAREN, "("));
                i++;
                break;
            case ')':
                result.add(new Token(Type.RPAREN, ")"));
                i++;
                break;
            default:
                if(Character.isWhitespace(input.charAt(i))) {
                    i++;
                } else {
                    String atom = getAtom(input, i);
                    i += atom.length();
                    result.add(new Token(Type.ATOM, atom));
                }
                break;
            }
        }
        return result;
    }
    
    //Função main
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: java Lexer \"((some Scheme) (code to) lex)\".");
            return;
        }
        List<Token> tokens = lex(args[0]);
        for(Token t : tokens) {
            System.out.println(t);
        }
    }
}
