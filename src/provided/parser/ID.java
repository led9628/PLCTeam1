package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ID implements JottTree{
    ArrayList<JottTree> children;

    public ID(ArrayList<Token> tokens){
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens){
        if(tokens.get(JottParser.getIndex()).getTokenType() == TokenType.ID_KEYWORD){
            children.add(new Literal(tokens.get(JottParser.getIndex()).getToken()));
            JottParser.incrementToken();
        }
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

