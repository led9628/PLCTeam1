import java.util.ArrayList;

import provided.Token;
import provided.parser.JottParser;
import provided.JottTokenizer;
import provided.JottTree;

class Main {

    /*
     * Arguments for main:
     * 0: Jott
     * 1: input=> fileIn.jott
     * 2: output=> fileOut.jott
     * 3: output language(Jott, Java, C, Python)
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Invalid number of arguments.");
        } else {
            ArrayList<Token> tokenList = JottTokenizer.tokenize(args[1]);
            if(tokenList == null){
                return;
            }
            JottTree parseTree = JottParser.parse(tokenList);
            if(parseTree == null){
                return;
            }
            String result = switch (args[3]) {
                case "Jott" -> parseTree.convertToJott();
                case "Java" -> parseTree.convertToJava(args[3].substring(0, args[3].length()-5));
                case "Python" -> parseTree.convertToPython();
                case "C" -> parseTree.convertToC();
                default -> "Last command line argument is an invalid language.  Should be Jott, Java, Python, or C.";
            };
            System.out.println(result);
        }
    }
}
