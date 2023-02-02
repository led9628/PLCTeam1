import java.util.ArrayList;

import provided.Token;
import provided.JottTokenizer;

class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            // TODO: Yell at the user for not having the right command line arguments.
        } else {
            ArrayList<Token> tokenList = JottTokenizer.tokenize(args[0]);
        }
        
        
    }
}
