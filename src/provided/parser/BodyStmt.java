package provided.parser;

import provided.JottTree;
import provided.Token;
import java.util.ArrayList;

public class BodyStmt implements JottTree, Returnable {
    ArrayList<JottTree> children = new ArrayList<>();

    public BodyStmt(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure { // <if_stmt>
                                                                                                            // |
                                                                                                            // <while_loop>
                                                                                                            // | <stmt>
        if (tokens.get(0).getToken().equals("if"))
            children.add(new IfStmt(tokens, funcName, depth+1));
        else if (tokens.get(0).getToken().equals("while"))
            children.add(new WhileLoop(tokens, funcName, depth+1));
        else
            children.add(new Stmt(tokens, funcName, depth));
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
        StringBuilder sb = new StringBuilder();
        sb.append(this.children.get(0).convertToPython());
        return sb.toString();
    }

    @Override
    public boolean validateTree() throws SemanticFailure {
        for (var child : this.children) {
            boolean result = child.validateTree();
            if (!result)
                return false;
        }
        return true;
    }

    public boolean checkReturn() {
        for(var child: this.children){
            if(child instanceof Returnable){
                Returnable c = (Returnable) child;
                if(c.checkReturn() == true){
                    return true;
                }
            }
        }
        return false;
    }

}