package provided.parser;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class Type implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public Type(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            if (token.getToken().equals("Boolean") || token.getToken().equals("Integer") || token.getToken().equals("String") || token.getToken().equals("Double")) {
                this.children.add(new Literal(token.getToken()));
                return;
            }
            tokens.add(0, token);
            throw new ConstructionFailure("is not Boolean, Integer, String, or Double.", token.getLineNum());
        }
        tokens.add(0, token);
        throw new ConstructionFailure("Not a valid Type.", token.getLineNum());
    }

    @Override
    public String convertToJott() {
        return this.children.get(0).convertToJott();
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
