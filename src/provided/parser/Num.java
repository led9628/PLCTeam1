package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;


public class Num implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public Num(ArrayList<Token> tokens) throws ConstructionFailure {
        Token token = tokens.remove(0);
        // Make sure it's actually a number.
        for (char c : token.getToken().toCharArray()) {
            if (!Character.isDigit(c) && (c != '.')) {
                // If it's not actually a number, fail and throw an exception.
                tokens.add(0, token);
                throw new ConstructionFailure("Num isn't actually a number.", token.getLineNum());
            }
        }
        // If it is actually a number, succeed.
        this.children.add(new Literal(token.getToken()));
    }

    @Override
    public String convertToJott() {
        return this.children.get(0).convertToJott();
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