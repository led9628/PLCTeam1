package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FuncCall implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public FuncCall(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);

        if(token.getTokenType() != TokenType.ID_KEYWORD){
            // throw error
        }
        this.children.add(new Literal(token.getToken()));

        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.L_BRACKET){
            //throw
        }
        this.children.add(new Literal(token.getToken()));
        // da params
        this.children.add(new Params(tokens));
        
        //after the param stuff is done, i need the next token
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.R_BRACKET){
            //throw
        }
        this.children.add(new Literal(token.getToken()));


    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for(var child : this.children){
            sb.append(child.convertToJott());
        }
        return sb.toString();
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
