package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class Stmt implements JottTree{
    ArrayList<JottTree> children;

    public Stmt(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token1 = tokens.get(0);
        Token token2 = tokens.get(2);

        // if curr token is an id/key and the 2nd token after is a semicolon, var_dec
        if(token1.getTokenType()==TokenType.ID_KEYWORD && token2.getTokenType() == TokenType.SEMICOLON){
            this.children.add(new VarDec(tokens));

        }
        // assignment
        else if(token1.getTokenType()==TokenType.ID_KEYWORD && token2.getTokenType() == TokenType.ASSIGN){
            this.children.add(new Asmt(tokens));
        }
        // function call
        else if(token1.getTokenType()==TokenType.ID_KEYWORD && token2.getTokenType() == TokenType.L_BRACE){
            this.children.add(new FuncCall(tokens));
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
