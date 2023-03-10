package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionParam implements JottTree{
    ArrayList<JottTree> children; //will only store an id and a type

    public FunctionParam(ArrayList<Token> tokens) throws ConstructionFailure{
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens) throws ConstructionFailure{
        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
            // add param id
            children.add(new Literal(tokens.remove(0).getToken()));

            //check for param colon and type:
            if(tokens.get(0).getToken().equals(":")){
                tokens.remove(0); // remove :

                String a = tokens.get(0).getToken();
                if(a.equals("Double") || a.equals("Integer") || a.equals("String") || a.equals("Boolean")){
                    tokens.remove(0);
                    children.add(new Literal(a));
                }else{
                    throw new ConstructionFailure("Invalid parameter type", tokens.get(0).getLineNum()); //throw no/bad type
                }
            }else{
                //throw missing :
                throw new ConstructionFailure("Missing colon (:)", tokens.get(0).getLineNum());
            }
        }else{
            throw new ConstructionFailure("Missing function parameter id", tokens.get(0).getLineNum());
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
