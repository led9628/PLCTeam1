package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class IfStmt implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>();
    
    public IfStmt(ArrayList<Token> tokens) throws ConstructionFailure{
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

        //check token after closing ifstmt body
        token = tokens.get(0);

        if(token.getToken()=="elseif"){
            while(token.getToken()=="elseif"){
                //remove "elseif" token from list
                tokens.remove(0);
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
                //check token after closing ifstmt body
                token = tokens.get(0);
            }
        }

        // next token is else
        if(token.getToken() == "else"){

            //remove "else" token from list
            tokens.remove(0);
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

            token = tokens.get(0);
            if(token.getToken() == "else"){
                // throw multiple else error
            }
        }

   
    }
    


    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for(var child : this.children){
            sb.append(child.convertToJott());
        }
        String str = sb.toString();
        return str;
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
