package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class FunctionList implements JottTree{
    ArrayList<JottTree> children;

    public FunctionList(ArrayList<Token> tokens){
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens){
        if(tokens.get(JottParser.getIndex()).getToken().equals("def")){
            JottParser.incrementToken();
            children.add(new FunctionDef(tokens));
            children.add(new FunctionList(tokens));
        }else{
            children.add(new Literal("E"));
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
