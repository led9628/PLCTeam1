package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Asmt implements JottTree {


    ArrayList<JottTree> children = new ArrayList<>();

    public Asmt(ArrayList<Token> tokens) throws ConstructionFailure{
        //attempt to create type id = expr end_stmt
        try {
            this.children.add(new Type(tokens));
            this.children.add(new Literal(tokens.get(0).getToken()));
            this.children.add(new Literal(tokens.get(1).getToken()));
            if (!tokens.get(1).getToken().equals("=")) {
                throw new ConstructionFailure("asmt should have =", tokens.get(1).getLineNum());
            }
            this.children.add(new Expr(tokens));
            this.children.add(new EndStmt(tokens));
            tokens.remove(0);
            tokens.remove(0);
            return;
        } catch (ConstructionFailure e) {}
        try {
            this.children.add(new Literal(tokens.get(0).getToken()));
            this.children.add(new Literal(tokens.get(1).getToken()));
            if (!tokens.get(1).getToken().equals("=")) {
                throw new ConstructionFailure("asmt should have =", tokens.get(1).getLineNum());
            }
            this.children.add(new Expr(tokens));
            this.children.add(new EndStmt(tokens));
            tokens.remove(0);
            tokens.remove(0);
            return;
        } catch (ConstructionFailure e) {}
        throw new ConstructionFailure("Failed to create an Asmt.", tokens.get(0).getLineNum());
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
