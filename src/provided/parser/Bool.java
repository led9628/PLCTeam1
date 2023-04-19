package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class Bool implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    public CheckType type;

    public Bool(ArrayList<Token> tokens) throws ConstructionFailure {
        Token token = tokens.get(0);
        this.type = new CheckType("Boolean");
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
        String s = this.children.get(0).toString();
        if(s.equalsIgnoreCase("True")) {
            return "True";
        }
        else {
            return "False";
        }
    }

    @Override
    public String convertToC() {
        String s = this.children.get(0).toString();
        if (s.equals("True")) {
            return "1";
        } else {
            return "0";
        }
    }

    @Override
    public String convertToPython() {
        String s = this.children.get(0).toString();
        if (s.equals("True")) {
            return "True";
        } else {
            return "False";
        }
    }

    @Override
    public boolean validateTree() {
        if (this.children.isEmpty()) {
            return false;
        }
        return true;
    }
}