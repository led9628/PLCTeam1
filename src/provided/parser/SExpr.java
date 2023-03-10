package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class SExpr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public SExpr(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);

        switch (token.getTokenType()) {
            case STRING -> this.children.add(new Literal(token.getToken()));
            case ID_KEYWORD -> {
                try {
                    this.children.add(new FuncCall(tokens));
                } catch (ConstructionFailure e) {
                    this.children.add(new Literal(token.getToken()));
                }
            }
            default -> {
                throw new ConstructionFailure("Failed to create an SExpr.", token.getLineNum());
            }
        }
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
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