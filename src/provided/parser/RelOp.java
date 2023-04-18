package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class RelOp implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public RelOp(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);
        if (token.getTokenType() == TokenType.REL_OP) {
            this.children.add(new Literal(token.getToken()));
            return;
        }
        tokens.add(0, token);
        throw new ConstructionFailure("Operator is invalid", token.getLineNum());
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
        return this.children.get(0).toString();
    }

    @Override
    public String convertToPython() {
        return this.children.get(0).toString();
    }

    @Override
    public boolean validateTree() {
        return !this.children.isEmpty();
    }
}