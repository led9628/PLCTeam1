package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionDef implements JottTree{
    ArrayList<JottTree> children;

    public FunctionDef(ArrayList<Token> tokens){
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens){
        //<id>
        children.add(new ID(tokens));
        
        //[func_def_params]
        if(tokens.get(JottParser.getIndex()).getTokenType() == TokenType.L_BRACKET){
            children.add(new Literal("["));
            JottParser.incrementToken();  

            
            if(tokens.get(JottParser.getIndex()).getTokenType() == TokenType.ID_KEYWORD){
                children.add(new ID(tokens));
                JottParser.incrementToken();
                    
                //type
                //<func_def_params_t>
            }
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
