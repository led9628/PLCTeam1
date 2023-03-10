package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class NExpr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public NExpr(ArrayList<Token> tokens) throws ConstructionFailure {

        ArrayList<Token> prior = new ArrayList<Token>(tokens);

        // Attempt to create a Num
        try {
            System.out.println(tokens.get(0).getToken());
            this.children.add(new Num(tokens));
            System.out.println(tokens.get(0).getToken());
            return;
        } catch (ConstructionFailure e) {}
        tokens = prior;
        // Attempt to create a FuncCall Op NExpr
        try {
            this.children.add(new FuncCall(tokens));
            this.children.add(new Op(tokens));
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {}
        tokens = prior;
        // Attempt to create a Num Op NExpr
        try {
            this.children.add(new Num(tokens));
            this.children.add(new Op(tokens));
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {}
        tokens = prior;
        // Attempt to create an Id Op NExpr
        try {
            Token token = tokens.remove(0);
            this.children.add(new Literal(token.getToken()));
            this.children.add(new Op(tokens));
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {}
        tokens = prior;
        // Attempt to create a FuncCall
        try {
            this.children.add(new FuncCall(tokens));
            return;
        } catch (ConstructionFailure e) {}
        tokens = prior;
        // Attempt to create an Id
        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            this.children.add(new Literal(tokens.remove(0).getToken()));
            return;
        }
        tokens = prior;
        throw new ConstructionFailure("NExpr failure", tokens.get(0).getLineNum());
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
        // TODO Auto-generated method stub
        return false;
    }
}