package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class VarDec implements JottTree{

    ArrayList<JottTree> children = new ArrayList<>();

    public VarDec(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);
        //Try to add a type id end  statement
        try {
            this.children.add(new Type(tokens));
            this.children.add(new Literal(token.getToken()));
            this.children.add(new EndStmt(tokens));
            return;
        } catch (ConstructionFailure e) {}
        throw new ConstructionFailure("Failed to create an Var_dec.", token.getLineNum());
    }

    @Override
    public String convertToJott() {
        String type = this.children.get(0).convertToJott();
        String id = this.children.get(1).convertToJott();
        String end = this.children.get(2).convertToJott();
        return type + " " + id + end;
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
