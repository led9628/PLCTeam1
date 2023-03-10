package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class Expr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public Expr(ArrayList<Token> tokens) throws ConstructionFailure {
        var ex = new ConstructionFailure("", 0);
        try {
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {
            ex.line = e.line;
            ex.message = e.message;
        }
        try {
            this.children.add(new SExpr(tokens));
            System.out.println("xx " + tokens.get(0).getToken());
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
        // TODO Auto-generated method stub
        return false;
    }
}