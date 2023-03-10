package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class Expr implements JottTree {
    ArrayList<JottTree> children;

    public Expr(ArrayList<Token> tokens) throws ConstructionFailure {
        try {
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {}
        try {
            this.children.add(new BExpr(tokens));
            return;
        } catch (ConstructionFailure e) {}
        try {
            this.children.add(new SExpr(tokens));
            return;
        } catch (ConstructionFailure e) {}
        throw new ConstructionFailure("Failed to create Expr.", 0 /* TODO */);
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