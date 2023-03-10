package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class Op implements JottTree {
    ArrayList<JottTree> children;

    public Op(ArrayList<Token> tokens) {
        Token token = tokens.remove(0);
        if (token.getTokenType() == TokenType.MATH_OP) {
            this.children.add(new Literal(token.getToken()));
        }
        // TODO: Throw on failure.
    }

    @Override
    public String convertToJott() {
        return this.children.get(0).toString();
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