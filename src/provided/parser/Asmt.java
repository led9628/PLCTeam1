package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Asmt implements JottTree {


    ArrayList<JottTree> children = new ArrayList<>();

    public Asmt(ArrayList<Token> tokens) throws ConstructionFailure{
        //attempt to create type id = expr end_stmt
        Token a = null, b = null;
        try {
            this.children.add(new Type(tokens));
            this.children.add(new Literal(tokens.get(0).getToken()));
            this.children.add(new Literal(tokens.get(1).getToken()));
            Token equalsToken = tokens.get(1);
            a = tokens.remove(0);
            b = tokens.remove(0);
            if (!equalsToken.getToken().equals("=")) {
                throw new ConstructionFailure("asmt should have =", tokens.get(1).getLineNum());
            }
            this.children.add(new Expr(tokens));
            this.children.add(new EndStmt(tokens));
            return;
        } catch (ConstructionFailure e) {
            if (b != null) {
                tokens.add(0, b);
            }
            if (a != null) {
                tokens.add(0, a);
            }
        }
        try {
            this.children.add(new Literal(tokens.get(0).getToken()));
            this.children.add(new Literal(tokens.get(1).getToken()));
            Token equalsToken = tokens.get(1);
            a = tokens.remove(0);
            b = tokens.remove(0);
            if (!equalsToken.getToken().equals("=")) {
                throw new ConstructionFailure("asmt should have =", tokens.get(1).getLineNum());
            }
            this.children.add(new Expr(tokens));
            this.children.add(new EndStmt(tokens));
            return;
        } catch (ConstructionFailure e) {
            if (b != null) {
                tokens.add(0, b);
            }
            if (a != null) {
                tokens.add(0, a);
            }
        }
        throw new ConstructionFailure("Failed to create an Asmt.", tokens.get(0).getLineNum());
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
