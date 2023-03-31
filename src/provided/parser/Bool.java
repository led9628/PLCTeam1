package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class Bool implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public Bool(ArrayList<Token> tokens) throws ConstructionFailure {
        Token token = tokens.remove(0);
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            if (token.getToken() == "True" || token.getToken() == "False") {
                this.children.add(new Literal(token.getToken()));
            }
            tokens.add(0, token);
            throw new ConstructionFailure("Boolean is not True or False.", token.getLineNum());
        }
        tokens.add(0, token);
        throw new ConstructionFailure("Boolean is not a valid ID_KEYWORD.", token.getLineNum());
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
        if (this.children.isEmpty()) {
            return false;
        }
        return true;
    }
}