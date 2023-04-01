package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class NExpr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    public CheckType type;

    public NExpr(ArrayList<Token> tokens, String funcName) throws ConstructionFailure, SemanticFailure {
        // Attempt to create a FuncCall
        try {
            FuncCall func = new FuncCall(tokens, funcName);
            this.type = func.type;
            this.children.add(new FuncCall(tokens, funcName));
            return;
        } catch (ConstructionFailure e) {}
        // Attempt to create an Id Op NExpr
        Token token = tokens.get(0);
        try {
            if (token.getTokenType() != TokenType.ID_KEYWORD || (tokens.get(0).getTokenType() != TokenType.MATH_OP)) {
                throw new ConstructionFailure("Unexpected error", -1);
            }

            ID id = new ID(tokens, funcName, null);
            if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                throw new SemanticFailure("id not found", tokens.get(0).getLineNum());
            }
            id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
            this.type = id.type;
            tokens.remove(0);
            this.children.add(id);

            this.children.add(new Op(tokens));

            int lineNo = tokens.get(0).getLineNum();
            NExpr n = new NExpr(tokens, funcName);
            if(!n.type.equals(id.type)){
                throw new SemanticFailure("Invalid operation between" + id.type + " and " + n.type, lineNo);
            }
            this.children.add(n);
            return;
        } catch (ConstructionFailure e) {
            // tokens.add(0, token);
        }

        // Attempt to create an Id
        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            ID id = new ID(tokens, funcName, null);
            if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                throw new SemanticFailure("id not found", tokens.get(0).getLineNum());
            }
            id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
            type = id.type;
            // if(Program.functions.get(funcName))
            tokens.remove(0);
            this.children.add(id);
            return;
        }
        
        // Attempt to create a Num
        try {
            // Token tok = tokens.get(0);
            Num num = new Num(tokens);
            this.children.add(num);
            this.type = num.type;
            // tokens.add(tok);//since num removes it but we need to create a type with it.
            // this.type = new Type(tokens);
            return;
        } catch (ConstructionFailure e) {}
        
        // Attempt to create a FuncCall Op NExpr
        try {
            FuncCall fc = new FuncCall(tokens, funcName);
            this.children.add(fc);

            this.children.add(new Op(tokens));

            int ln = tokens.get(0).getLineNum();
            NExpr n = new NExpr(tokens, funcName);
            this.children.add(n);

            if(!fc.type.equals(n.type)){
                throw new SemanticFailure("Invalid comparison between " + fc.type + " and " + n.type, ln);
            }
            return;
        } catch (ConstructionFailure e) {}

        // Attempt to create a Num Op NExpr
        try {
            Num num = new Num(tokens);
            this.children.add(num);
            
            this.children.add(new Op(tokens));

            int ln = tokens.get(0).getLineNum();
            NExpr n = new NExpr(tokens, funcName);
            this.children.add(n);

            if(!num.type.equals(n.type)){
                throw new SemanticFailure("Invalid comparison between " + num.type + " and " + n.type, ln);
            }
            return;
        } catch (ConstructionFailure e) {}
        throw new ConstructionFailure("Number Expression is Invalid", tokens.get(0).getLineNum());
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