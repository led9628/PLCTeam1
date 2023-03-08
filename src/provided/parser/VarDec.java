package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class VarDec implements JottTree{

    ArrayList<JottTree> children;

    public VarDec(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);
        //Try to add a type id end  statement
        try {
            this.children.add(new Type(tokens));
            this.children.add(new Literal(token.getToken()));
            this.children.add(new End_stmt(tokens));
            return;
        } catch (ConstructionFailure e) {}
        throw new ConstructionFailure("Failed to create an Var_dec.");
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
