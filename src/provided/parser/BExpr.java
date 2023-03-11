package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class BExpr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public BExpr(ArrayList<Token> tokens) throws ConstructionFailure{
        // Attempt to create an NExpr RelOp NExpr
        try {
            this.children.add(new NExpr(tokens));
            this.children.add(new RelOp(tokens));
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {}
        // If that doesn't work, keep trying to make things.
        Token token = tokens.get(0);
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            // Try to create a FuncCall.
            try {
                this.children.add(new FuncCall(tokens));
                return;
            } catch (ConstructionFailure e) {}
            // Try to create a Bool.
            try {
                this.children.add(new Bool(tokens));
                return;
            } catch (ConstructionFailure e) {}
            // Create an ID if it can't be anything else.
            this.children.add(new Literal(tokens.remove(0).getToken()));
            return;
        }
        // If we failed to turn BExpr into anything, throw.
        tokens.add(0, token);
        throw new ConstructionFailure("Boolean Expression is Invalid", token.getLineNum());
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