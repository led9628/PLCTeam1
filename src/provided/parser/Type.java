package provided.parser;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class Type implements JottTree {
    ArrayList<JottTree> children;

    public Type(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            if (token.getToken() == "Boolean" || token.getToken() == "Integer" || token.getToken() == "String" || token.getToken() == "Double") {
                this.children.add(new Literal(token.getToken()));
                return;
            }
            throw new ConstructionFailure("is not Boolean, Integer, String, or Double.");
        }
        throw new ConstructionFailure("Not a valid ID_KEYWORD.");
    }

    @Override
    public String convertToJott() {
        return null;
    }

    @Override
    public String convertToJava(String className) {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
