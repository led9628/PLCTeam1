package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class Expr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    CheckType type;

    public Expr(ArrayList<Token> tokens, String funcName) throws ConstructionFailure, SemanticFailure{
        var ex = new ConstructionFailure("Expression is Invalid", 0);
        try {
            NExpr n = new NExpr(tokens, funcName);
            this.type = n.type;
            this.children.add(n);
            return;
        } catch (ConstructionFailure e) {
            ex.line = e.line;
            ex.message = e.message;
        }

        try {
            SExpr s = new SExpr(tokens, funcName);
            this.type = s.type;
            this.children.add(s);
            return;
        } catch (ConstructionFailure e) {
            ex.line = e.line;
            ex.message = e.message;
        } 

        try {
            BExpr b = new BExpr(tokens, funcName);
            this.type = b.type;
            this.children.add(b);
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
    public boolean validateTree() throws SemanticFailure {
        for(var child : this.children) {
            boolean result = child.validateTree();
            if (!result)
                return false;
        }
        return true;
    }
}