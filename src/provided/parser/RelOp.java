package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class RelOp implements JottTree {
    ArrayList<JottTree> children;

    public RelOp(ArrayList<Token> tokens){
        Token token = tokens.remove(0);
        if (token.getTokenType() == TokenType.REL_OP) {
            this.children.add(new Literal(token.getToken()));
        }
        // TODO: Throw on failure.
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