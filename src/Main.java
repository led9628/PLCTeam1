import java.util.ArrayList;

import provided.Token;
import provided.JottTokenizer;

class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid number of arguments.");
        } else {
            ArrayList<Token> tokenList = JottTokenizer.tokenize(args[0]);
            if (tokenList != null) {
                for (Token token : tokenList) {
                    System.out.println(token.getTokenType() + ": " + token.getToken());
                }
            }
        }
        
        
    }
}
