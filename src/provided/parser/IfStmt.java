package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class IfStmt implements JottTree{
    ArrayList<JottTree> children;
    
    public IfStmt(ArrayList<Token> tokens){
        Token token = tokens.remove(0);

        //check if the token is "if"
        if(token.getToken() != "if"){
            //throw error
        }
        this.children.add(new Literal("if"));
        // next token
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.L_BRACKET){
            //throw
        }
        this.children.add(new Literal(token.getToken()));
        this.children.add(new BExpr(tokens));
        
        //after the bool expr stuff is done, i need the next token
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.R_BRACKET){
            //throw
        }
        this.children.add(new Literal(token.getToken()));

        // {
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.L_BRACE){
            //throw
        }
        this.children.add(new Literal(token.getToken()));
        this.children.add(new Body(tokens));

        // after trying to make body, get }
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.R_BRACE){
            //throw
        }
        this.children.add(new Literal(token.getToken()));

        // TODO: ELSEIF, ELSE




        
        //try {
        //    this.children.add(new BExpr(tokens));
       //    return;
       // } catch (ConstructionFailure e){}



        
    }
    
    //then i will actually do the parsing
    private void parse(ArrayList<Token> tokens){
        if(tokens.remove(0).getToken() == "if"){
            children.add(new Literal("if"));
            //check next tokens for brachet and parse bool expr
            if(tokens.remove(0).getTokenType() == TokenType.L_BRACKET){
                children.add(new BExpr(tokens));
                if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
                    // TODO: throw this error
                }
                //children.add()

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
