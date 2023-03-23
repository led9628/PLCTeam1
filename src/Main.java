import java.util.ArrayList;

import provided.Token;
import provided.parser.JottParser;
import provided.JottTokenizer;
import provided.JottTree;

class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid number of arguments.");
        } else {
            ArrayList<Token> tokenList = JottTokenizer.tokenize(args[0]);
            JottTree parseTree = JottParser.parse(tokenList);
            String result = switch (args[2]) {
                case "Jott" -> parseTree.convertToJott();
                case "Java" -> parseTree.convertToJava(args[2].substring(0, args[2].length()-5));
                case "Python" -> parseTree.convertToPython();
                case "C" -> parseTree.convertToC();
                default -> "Last command line argument is an invalid language.  Should be Jott, Java, Python, or C.";
            };
            System.out.println(result);
        }
        
        
    }
}
