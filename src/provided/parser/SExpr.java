package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class SExpr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    CheckType type;

    public SExpr(ArrayList<Token> tokens, String funcName) throws ConstructionFailure, SemanticFailure{
        switch (tokens.get(0).getTokenType()) {
            case STRING -> {
                String tokenStr = tokens.get(0).getToken();
                this.type = new CheckType("String");
                this.children.add(new Literal(tokenStr));
                tokens.remove(0);
            }
            case ID_KEYWORD -> {
                try {
                    FuncCall fc = new FuncCall(tokens, funcName);
                    this.type = fc.type;
                    this.children.add(fc);
                } catch (ConstructionFailure e) {
                    ID id = new ID(tokens, funcName, null);
                    if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                        throw new SemanticFailure("id not found", tokens.get(0).getLineNum());
                    }
                    id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
                    this.type = id.type;
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