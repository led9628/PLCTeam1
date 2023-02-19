package provided;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import java.util.ArrayList;

public class JottParser {
    //static index to determine which token is to be parsed next.
    private static int tokenIndex;

    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens){
        JottParser.tokenIndex = 0;
        try{
            JottTree prg = new program(tokens);
            return prg;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * Increments the static token index by 1.
     */
    public static void incrementToken(){
        JottParser.tokenIndex += 1;
    }

    /**
     * Returns the static toiken index
     * @return tokenIndex value
     */
    public static int getIndex(){
        return JottParser.tokenIndex;
    }
}


class program implements JottTree{
    ArrayList<JottTree> children;

    public program(ArrayList<Token> tokens){
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens){

        children.add(new function_list(tokens));

        if(tokens.get(JottParser.getIndex()).getToken().equals("$$")){
            children.add(new literal("$$"));
        }
        
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

}

class function_list implements JottTree{
    ArrayList<JottTree> children;

    public function_list(ArrayList<Token> tokens){
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens){
        if(tokens.get(JottParser.getIndex()).getToken().equals("def")){
            JottParser.incrementToken();
            children.add(new function_def(tokens));
            children.add(new function_list(tokens));
        }else{
            children.add(new literal("E"));
        }
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }
}

/*
 * Class used to store literals like '$$' and 'epsilon' in the parse tree.
 */
class literal implements JottTree{
    String lit;

    public literal(String lit){
        this.lit = lit;
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }

}


//NOT WORKING YET
class function_def implements JottTree{
    ArrayList<JottTree> children;

    public function_def(ArrayList<Token> tokens){
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens){
        //<id>
        children.add(new id(tokens));
        
        //[func_def_params]
        if(tokens.get(JottParser.getIndex()).getTokenType() == TokenType.L_BRACKET){
            children.add(new literal("["));
            JottParser.incrementToken();  

            
            if(tokens.get(JottParser.getIndex()).getTokenType() == TokenType.ID_KEYWORD){
                children.add(new id(tokens));
                JottParser.incrementToken();
                    
                //type
                //<func_def_params_t>
            }
        }
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }
}

//NOT WORKING YET
class id implements JottTree{
    ArrayList<JottTree> children;

    public id(ArrayList<Token> tokens){
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens){
        if(tokens.get(JottParser.getIndex()).getTokenType() == TokenType.ID_KEYWORD){
            children.add(new literal(tokens.get(JottParser.getIndex()).getToken()));
            JottParser.incrementToken();
        }
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }
}

//NOT WORKING YET
class token implements JottTree{
    ArrayList<JottTree> children;

    public token(ArrayList<Token> tokens){
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens){
        if(tokens.get(JottParser.getIndex()).getToken().equals(":")){
            children.add(new id(tokens));
            JottParser.incrementToken();
            String a = tokens.get(JottParser.getIndex()).getToken();
            if(a.equals("Double") || a.equals("Integer") || a.equals("String") || a.equals("Boolean")){ 
                children.add(new id(tokens));
                JottParser.incrementToken();
            }
        }
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        return false;
    }
}