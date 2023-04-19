package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class ID implements JottTree {
    
    JottTree child = null;
    public String funcName;
    public CheckType type;
    public int lineNo;

    public ID(ArrayList<Token> tokens, String funcName, CheckType type){
        this.funcName = funcName;
        this.type = type;
        child = new Literal(tokens.get(0).getToken()); 
    }

    @Override
    public String toString(){
        return child.toString();
    }

    @Override
    public String convertToJott() {
        return this.child.convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return this.child.convertToJava(className);
    }

    @Override
    public String convertToC() {
        return this.child.convertToC();
    }

    @Override
    public String convertToPython() {
        return this.child.convertToPython();
    }

    @Override
    public boolean validateTree() throws SemanticFailure {
        if(child instanceof Literal){
            Literal l = (Literal) child;
            String str = l.lit;
            if(Program.kw.contains(str)){
                throw new SemanticFailure("Invalid ID: " + l.lit, -1);
            }
        }
        return child.validateTree();
    }
}
