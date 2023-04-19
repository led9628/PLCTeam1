package provided.parser;

import provided.Token;
import provided.JottTree;

import java.util.ArrayList;

public class ReturnStmt implements JottTree, Returnable {
    ArrayList<JottTree> children = new ArrayList<>();
    String funcName;
    int lineNo;

    public ReturnStmt(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure { // return
                                                                                                              // <expr><end_stmt>
        this.funcName = funcName;
        lineNo = tokens.get(0).getLineNum();
        var token = tokens.remove(0);

        // return expr endstmt
        if (token.getToken().equals("return")) {
            children.add(new Literal("return"));

            Expr e = new Expr(tokens, funcName, depth);
            children.add(e);

            children.add(new EndStmt(tokens, depth));
        } else {
            throw new ConstructionFailure("Return statement is Invalid", token.getLineNum());
        }
    }

    @Override
    public String convertToJott() {
        String s1 = this.children.get(1).convertToJott();
        String s2 = this.children.get(2).convertToJott();
        return "return " + s1 + s2;
    }

    @Override
    public String convertToJava(String className) {
        String s1 = this.children.get(1).convertToJava(className);
        String s2 = this.children.get(2).convertToJava(className);
        return "return "+ s1 + s2;
    }

    @Override
    public String convertToC() {
        String s1 = this.children.get(1).convertToC();
        String s2 = this.children.get(2).convertToC();
        return "return " + s1 + s2;
    }

    @Override
    public String convertToPython() {
        String s1 = this.children.get(1).convertToPython();
        String s2 = this.children.get(2).convertToPython();
        return "return " + s1 + s2;
    }

    @Override
    public boolean validateTree() throws SemanticFailure {
        Expr e = (Expr) children.get(1);
        if(!e.validateTree()){
            return false;
        }
        // check for incorrect return types.
        if (Program.functions.get(funcName).returnType == null || !Program.functions.get(funcName).returnType.equals(e.type)) {
            throw new SemanticFailure("Function needs to return " + Program.functions.get(funcName).returnType + " but returned " + e.type, lineNo);
        }
        return true;
    }

    @Override
    public boolean checkReturn() {
        return true;
    }
}
