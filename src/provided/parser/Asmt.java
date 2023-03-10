package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Asmt implements JottTree {


    ArrayList<JottTree> children = new ArrayList<>();

    public Asmt(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);
        //attempt to create type id = expr end_stmt
        try {
            this.children.add(new Type(tokens));
            this.children.add(new Literal(token.getToken()));
            //TODO I am not sure if this is right for the = so I need to test for it
            this.children.add(new Literal(token.getToken()));
            this.children.add(new Expr(tokens));
            this.children.add(new EndStmt(tokens));
            return;
        } catch (ConstructionFailure e) {}
        try {
            this.children.add(new Literal(token.getToken()));
            //TODO I am not sure if this is right for the = so I need to test for it
            this.children.add(new Literal(token.getToken()));
            this.children.add(new Expr(tokens));
            this.children.add(new EndStmt(tokens));
            return;
        } catch (ConstructionFailure e) {}
        throw new ConstructionFailure("Failed to create an Asmt.", token.getLineNum());
    }

    @Override
    public String convertToJott() {
        return null;
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
        return false;
    }
}
