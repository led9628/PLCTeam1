package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;


public class Num implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    public CheckType type;
    Boolean isDouble;

    public Num(ArrayList<Token> tokens) throws ConstructionFailure {
        Token token = tokens.get(0);
        isDouble = false;

        // Make sure it's actually a number.
        for (char c : token.getToken().toCharArray()) {
            if (!Character.isDigit(c) && (c != '.')) {
                // If it's not actually a number, fail and throw an exception.
                throw new ConstructionFailure("Number is Invalid", token.getLineNum());
            }
            if(c == '.'){
                isDouble = true;
            }
        }

        if(isDouble == true){
            this.type = new CheckType("Double");
        }else{
            this.type = new CheckType("Integer");
        }
        // If it is actually a number, succeed.
        this.children.add(new Literal(token.getToken()));
        tokens.remove(0);
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
        return this.children.get(0).convertToC();
    }

    @Override
    public String convertToPython() {
        return this.children.get(0).convertToPython();
    }

    @Override
    public boolean validateTree() {
        return !this.children.isEmpty();
    }


}