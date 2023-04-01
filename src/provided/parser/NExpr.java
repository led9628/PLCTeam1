package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class NExpr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public NExpr(ArrayList<Token> tokens) throws ConstructionFailure {
        // Attempt to create a FuncCall
        try {
            this.children.add(new FuncCall(tokens));
            return;
        } catch (ConstructionFailure e) {}
        // Attempt to create an Id Op NExpr
        Token token = tokens.get(0);
        try {
            if (token.getTokenType() != TokenType.ID_KEYWORD || (tokens.get(0).getTokenType() != TokenType.MATH_OP)) {
                throw new ConstructionFailure("Unexpected error", -1);
            }
            ID id = new ID(tokens);
            ////////////asdfghjk54weyudrtyfiglihnokljmp;,u65editrfygbljhnk;olm',''
            tokens.remove(0);
            this.children.add(id);
            this.children.add(new Op(tokens));
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {
            // tokens.add(0, token);
        }
        // Attempt to create an Id
        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            ////////////asdfghjk54weyudrtyfiglihnokljmp;,u65editrfygbljhnk;olm',''
            ID id = new ID(tokens);
            tokens.remove(0);
            this.children.add(id);
            return;
        }
        // Attempt to create a Num
        try {
            this.children.add(new Num(tokens));
            return;
        } catch (ConstructionFailure e) {}
        // Attempt to create a FuncCall Op NExpr
        try {
            this.children.add(new FuncCall(tokens));
            this.children.add(new Op(tokens));
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {}
        // Attempt to create a Num Op NExpr
        try {
            this.children.add(new Num(tokens));
            this.children.add(new Op(tokens));
            this.children.add(new NExpr(tokens));
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