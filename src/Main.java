import java.util.ArrayList;

import provided.Token;
import provided.JottTokenizer;

class Main {
    public static void main(String[] args) {
        ArrayList<Token> tokenList = JottTokenizer.tokenize(args[0]);
    }
}
