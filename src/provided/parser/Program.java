package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;

public class Program implements JottTree{
    ArrayList<FunctionDef> children;

    public Program(ArrayList<Token> tokens) throws ConstructionFailure{
        parse(tokens);
    }
    
    private void parse(ArrayList<Token> tokens) throws ConstructionFailure{
        while(tokens.size()>1){
            children.add(new FunctionDef(tokens));
        }
        if(tokens.get(0).getToken() == "$$"){
            //THROW missing $$
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
