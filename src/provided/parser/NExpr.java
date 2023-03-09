package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class NExpr implements JottTree {
    ArrayList<JottTree> children;

    public NExpr(ArrayList<Token> tokens) throws ConstructionFailure {
        Token token = tokens.remove(0);

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
        // Attempt to create an Id Op NExpr
        try {
            this.children.add(new Literal(token.getToken()));
            this.children.add(new Op(tokens));
            this.children.add(new NExpr(tokens));
            return;
        } catch (ConstructionFailure e) {}
        // Attempt to create a FuncCall
        try {
            this.children.add(new FuncCall(tokens));
            return;
        } catch (ConstructionFailure e) {}
        // Attempt to create a Num
        try {
            this.children.add(new Num(tokens));
            return;
        } catch (ConstructionFailure e) {}
        // Attempt to create an Id
        //try {
        this.children.add(new Literal(token.getToken()));
        return;
        //} catch (ConstructionFailure e) {}
        // If we failed to turn BExpr into anything, throw.
        //throw new ConstructionFailure("Failed to create an NExpr.");
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        return null;
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