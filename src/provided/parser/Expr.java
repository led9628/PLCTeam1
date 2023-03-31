package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class Expr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public Expr(ArrayList<Token> tokens) throws ConstructionFailure {
        var ex = new ConstructionFailure("Expression is Invalid", 0);
        try {
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {
            ex.line = e.line;
            ex.message = e.message;
        }
        try {
            this.children.add(new SExpr(tokens));
            return;
        } catch (ConstructionFailure e) {
            ex.line = e.line;
            ex.message = e.message;
        }
        try {
            this.children.add(new BExpr(tokens));
            return;
        } catch (ConstructionFailure e) {
            ex.line = e.line;
            ex.message = e.message;
        }
        throw ex;
    }

    @Override
    public String convertToJott() {
        return this.children.get(0).convertToJott();
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
        for(var child : this.children) {
            boolean result = child.validateTree();
            if (!result)
                return false;
        }
        return true;
    }
}