package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class SExpr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    public CheckType type;
    String funcName;
    int lineNo=0;

    public SExpr(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure{
        this.funcName = funcName;

        switch (tokens.get(0).getTokenType()) {
            case STRING -> {
                String tokenStr = tokens.get(0).getToken();
                this.type = new CheckType("String");
                this.children.add(new Literal(tokenStr));
                tokens.remove(0);
            }
            case ID_KEYWORD -> {
                try {
                    FuncCall fc = new FuncCall(tokens, funcName, depth);
                    this.type = fc.type;
                    this.children.add(fc);
                } catch (ConstructionFailure e) {
                    ID id = new ID(tokens, funcName, null);

                    // if (!Program.functions.get(funcName).settingParams){
                    //     if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                    //         throw new SemanticFailure("id not found", tokens.get(0).getLineNum());
                    //     }
                    //     id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
                    // }
                    // this.type = id.type;
                    tokens.remove(0);
                    this.children.add(id);
                }
            }
            default -> {
                throw new ConstructionFailure("String Expression is not Valid", tokens.get(0).getLineNum());
            }
        }
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
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
        }
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
        if(children.get(0) instanceof ID){
            ID id = (ID) children.get(0);
            if(id.validateTree() == false){
                return false;
            }

            if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                throw new SemanticFailure("id not found", lineNo);
            }

            id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
            this.type = id.type;
        }
        else if(children.get(0) instanceof FuncCall){
            FuncCall fc = (FuncCall)children.get(0);
            this.type = fc.type;
            if(fc.validateTree() == false){
                return false;
            }
        }else{
            return children.get(0).validateTree();
        }
        return true;
        // for(var child : this.children) {
        //     boolean result = child.validateTree();
        //     if (!result)
        //         return false;
        // }
        // return true;
    }
}