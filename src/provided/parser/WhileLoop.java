package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class WhileLoop implements JottTree {
    ArrayList<JottTree> children;

    public WhileLoop(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);

        if(token.getToken()!= "while"){
            //throw error
        }
        this.children.add(new Literal(token.getToken()));


        if(token.getTokenType() != TokenType.L_BRACKET){
            //throw
        }
        // bool expression
        this.children.add(new BExpr(tokens));
        
        //after the bool expr stuff is done, i need the next token
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.R_BRACKET){
            //throw
        }

        // {
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.L_BRACE){
            //throw
        }
    
        // get body
        this.children.add(new Body(tokens));

        // after trying to make body, get }
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.R_BRACE){
            //throw
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
