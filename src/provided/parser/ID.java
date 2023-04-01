package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class ID implements JottTree {
    
    JottTree child = null;

    public ID(ArrayList<Token> tokens){
        child = new Literal(tokens.get(0).getToken()); 
    }

    @Override
    public String toString(){
        return child.toString();
    }

    @Override
    public String convertToJott() {
        return this.child.convertToJott();
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
