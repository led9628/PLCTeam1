package provided.parser;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class JottParser {
    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens){
        try{
            JottTree prg = new Program(tokens);
            return prg;
        }catch(Exception e){
            return null;
        }
    }
}


// //NOT WORKING YET
// class token implements JottTree{
//     ArrayList<JottTree> children;

//     public token(ArrayList<Token> tokens){
//         parse(tokens);
//     }

//     private void parse(ArrayList<Token> tokens){
//         if(tokens.get(JottParser.getIndex()).getToken().equals(":")){
//             children.add(new id(tokens));
//             JottParser.incrementToken();
//             String a = tokens.get(JottParser.getIndex()).getToken();
//             if(a.equals("Double") || a.equals("Integer") || a.equals("String") || a.equals("Boolean")){ 
//                 children.add(new id(tokens));
//                 JottParser.incrementToken();
//             }
//         }
//     }
// }