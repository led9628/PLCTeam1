package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Params_t implements JottTree {

    ArrayList<JottTree> children;

    public Params_t(ArrayList<Token> tokens) throws ConstructionFailure {
        try {
            this.children.add(new Expr(tokens));
            this.children.add(new Params_t(tokens));
            return;
        }  catch (ConstructionFailure e) {}
        throw new ConstructionFailure("Failed to create an Param_t.");
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
