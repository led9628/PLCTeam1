package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Params implements JottTree {

    ArrayList<JottTree> children = new ArrayList<>();
    String funcName;
    String callFunc;
    int ln;

    public Params(ArrayList<Token> tokens, String funcName, String callFunc, int depth) throws ConstructionFailure, SemanticFailure{
        this.funcName = funcName;
        this.callFunc = callFunc;

        //attempt to create Expr Params_t
        ln = tokens.get(0).getLineNum();
        Expr e = new Expr(tokens, funcName, depth);
        this.children.add(e);

        //check for 2nd+ params
        while(tokens.get(0).getToken().equals(",")){
            tokens.remove(0); //remove ,
            
            Expr e2 = new Expr(tokens, funcName, depth);
            this.children.add(e2);
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
        StringBuilder sb = new StringBuilder();
        for (var child : this.children ) {
            sb.append(child.convertToJava(className));
        }
        return sb.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            sb.append(child.convertToC());
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            sb.append(child.convertToPython());
        }
        return sb.toString();
    }

    @Override
    public boolean validateTree() throws SemanticFailure{
        //check for correct param
        int paramNo = 0;
        for(JottTree jt : children){
            Expr e = (Expr)jt;
            if(!e.validateTree()){
                return false;
            }
            if(Program.functions.get(callFunc).paramTypes.size() == 0){
                throw new SemanticFailure("Tried to pass in a parameter to a function without params", ln);
            }else if(Program.functions.get(callFunc).paramTypes.size() > 0 && !Program.functions.get(callFunc).paramTypes.get(paramNo).equals(e.type)){
                throw new SemanticFailure("Incorrect parameter type: " + e.type + ". Expected " + Program.functions.get(callFunc).paramTypes.get(paramNo), ln);
            }
            paramNo++;
        }

        for(var child : this.children) {
            boolean result = child.validateTree();
            if (!result)
                return false;
        }
        return true;
    }
}
