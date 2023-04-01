package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Params implements JottTree {

    ArrayList<JottTree> children = new ArrayList<>();

    public Params(ArrayList<Token> tokens, String funcName) throws ConstructionFailure, SemanticFailure{
        //attempt to create Expr Params_t
        this.children.add(new Expr(tokens, funcName));
        //check for 2nd+ params
        while(tokens.get(0).getToken().equals(",")){
            tokens.remove(0); //remove ,
    
            this.children.add(new Expr(tokens, funcName));
        }
        //this.children.add(new ParamsT(tokens)); TODO: WE CAN'T HANDLE MULTI-PARAM FUNCTIONS UNLESS WE IMPLEMENT THIS CORRECTLY.
        return;
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder("");
        for (var child : this.children) {
            sb.append(child.convertToJott());
        }
        return sb.toString();
    }

    @Override
    public String convertToJava(String className) {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
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
