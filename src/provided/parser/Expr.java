package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class Expr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    CheckType type;
    int lineNo;

    public Expr(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure{
        var ex = new ConstructionFailure("Expression is Invalid", 0);
        try {
            NExpr n = new NExpr(tokens, funcName, depth);
            this.lineNo = n.lineNo;
            this.type = n.type;
            this.children.add(n);
            return;
        } catch (ConstructionFailure e) {
            ex.line = e.line;
            ex.message = e.message;
        }

        try {
            SExpr s = new SExpr(tokens, funcName, depth);
            this.lineNo = s.lineNo;
            this.type = s.type;
            this.children.add(s);
            return;
        } catch (ConstructionFailure e) {
            ex.line = e.line;
            ex.message = e.message;
        } 

        try {
            BExpr b = new BExpr(tokens, funcName, depth);
            this.lineNo = b.lineNo;
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
        return this.children.get(0).convertToJava(className);
    }

    @Override
    public String convertToC() {
        return this.children.get(0).convertToC();
    }

    @Override
    public String convertToPython() {
        return this.children.get(0).convertToPython();
    }

    @Override
    public boolean validateTree() throws SemanticFailure {
        if(children.get(0) instanceof NExpr){
            NExpr nexpr = (NExpr)children.get(0);
            if(!nexpr.validateTree()){
                return false;
            }
            this.type = nexpr.type;
        }else if(children.get(0) instanceof SExpr){
            SExpr sexpr = (SExpr)children.get(0);
            if(!sexpr.validateTree()){
                return false;
            }
            this.type = sexpr.type;
        }else{
            BExpr bexpr = (BExpr)children.get(0);
            if(!bexpr.validateTree()){
                return false;
            }
            this.type = bexpr.type;
        }

        // for(var child : this.children) {
        //     boolean result = child.validateTree();
        //     if (!result)
        //         return false;
        // }
        return true;
    }
}