package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class BExpr implements JottTree {
    ArrayList<JottTree> children;

    public BExpr(ArrayList<Token> tokens){
        Token token = tokens.remove(0);

        // Attempt to create an NExpr RelOp NExpr
        try {
            this.children.add(new NExpr(tokens));
            this.children.add(new RelOp(tokens));
            this.children.add(new NExpr(tokens));
        } catch (ConstructionFailure e) {}
        // If that doesn't work, keep trying to make things.
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            // Try to create a FuncCall.
            try {
                this.children.add(new FuncCall(tokens));
            } catch (ConstructionFailure e) {}
            // Try to create a Bool.
            try {
                this.children.add(new Bool(tokens));
            } catch (ConstructionFailure e) {}
            // Create an ID if it can't be anything else.
            this.children.add(new Literal(token.getToken()));
        }
        // If we failed to turn BExpr into anything, throw.
        throw new ConstructionFailure("Failed to create a BExpr.");
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