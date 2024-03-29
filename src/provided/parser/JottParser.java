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

public class JottParser {
    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens){
        String filename = tokens.get(0).getFilename();
        try{
            JottTree prg = new Program(tokens);
            return prg;
        }catch(ConstructionFailure e){
            //System.out.println("TOKENS: ");
            //for(var i : tokens){
            //    System.out.println(i.getToken());
            //}
            
            e.printStackTrace();

            System.err.println("Syntax Error:");
            System.err.println(e.message);
            System.err.println(filename + ":" + e.line);
            return null;
        }catch(SemanticFailure e){
            for(var j : Program.functions.keySet()){
                System.out.println(j);
            }
            System.out.println("TOKENS: ");
            for(var i : tokens){
                System.out.println(i.getToken());
            }
            System.out.println("END");
            e.printStackTrace();
            System.err.println("Semantic Error:");
            System.err.println(e.message);
            System.err.println(filename + ":" + e.line);
            System.out.println("END");
            return null;
        }
    }
}