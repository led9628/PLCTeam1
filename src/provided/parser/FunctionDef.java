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
                children.add(new Literal(tokens.remove(0).getToken())); //add ID literal
            }else{
                throw new ConstructionFailure("Missing function name ID", tokens.get(0).getLineNum()); //throw missing id
            }

            if(tokens.get(0).getTokenType() == TokenType.L_BRACKET){
                tokens.remove(0); // remove L bracket

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
                if(tokens.get(0).getTokenType() == TokenType.R_BRACKET){
                    tokens.remove(0); //remove r bracket

                    if(tokens.get(0).getToken().equals(":")){
                        tokens.remove(0); //remove colon
                        children.add(new FunctionReturn(tokens)); //check return type:
                    }else{
                        throw new ConstructionFailure("Missing colon (:)", tokens.get(0).getLineNum()); // throw missing colon
                    }
                    
                }else{
                    throw new ConstructionFailure("Missing right bracket (])", tokens.get(0).getLineNum()); // throw no r bracket
                }

                //check for body curly brackets
                if(tokens.get(0).getTokenType() == TokenType.L_BRACE){
                    tokens.remove(0); // remove L brace
                    children.add(new Body(tokens));

                    if(tokens.get(0).getTokenType() != TokenType.R_BRACE){
                        tokens.remove(0); // remove R brace
                        throw new ConstructionFailure("Missing right brace (})", tokens.get(0).getLineNum()); //throw no r brace
                    }
                }else{
                    throw new ConstructionFailure("Missing left brace ({)", tokens.get(0).getLineNum()); // throw no l brace
                }
            } else {
                throw new ConstructionFailure("Missing left bracket ([)", tokens.get(0).getLineNum()); // throw no l brace
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
