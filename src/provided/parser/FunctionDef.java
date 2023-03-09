package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionDef implements JottTree{
    ArrayList<JottTree> children;

    public FunctionDef(ArrayList<Token> tokens) throws ConstructionFailure{
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens) throws ConstructionFailure{
        if(tokens.remove(0).getToken() == "def"){
            children.add(new Literal("def"));
            if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                //add ID literal
                children.add(new Literal(tokens.remove(0).getToken()));
            }else{
                //throw missing id
                throw new ConstructionFailure("missing id");
            }

            if(tokens.remove(0).getTokenType() == TokenType.L_BRACKET){
                //function def params check:
                if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                    children.add(new FunctionParam(tokens));

                    //check for 2nd+ params
                    while(tokens.get(0).getToken().equals(",")){
                        tokens.remove(0); //remove ,
                
                        children.add(new FunctionParam(tokens));
                    }
                }

                //check for end of params:
                if(tokens.remove(0).getTokenType() == TokenType.R_BRACKET){
                    if(tokens.remove(0).getToken().equals(":")){
                        //check return type:
                        children.add(new FunctionReturn(tokens));
                    }else{
                        throw new ConstructionFailure("missing colon (':')");
                    }
                }else{
                    throw new ConstructionFailure("missing right bracket (']')");
                }

                //check for body curly brackets:
                if(tokens.remove(0).getTokenType() == TokenType.L_BRACE){
                    children.add(new Body(tokens));

                    if(tokens.remove(0).getTokenType() != TokenType.R_BRACE){
                        //throw no r brace
                        throw new ConstructionFailure("missing right brace for function ('}')");
                    }
                }else{
                    //throw no l brace
                    throw new ConstructionFailure("missing left brace for function ('{')");
                }
            } else {
                throw new ConstructionFailure("missing left bracket ('[')");
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
