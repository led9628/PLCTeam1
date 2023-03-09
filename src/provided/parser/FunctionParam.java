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
            if(tokens.remove(0).getToken().equals(":")){
                String a = tokens.remove(0).getToken();
                if(a.equals("Double") || a.equals("Integer") || a.equals("String") || a.equals("Boolean")){
                    children.add(new Literal(a));
                }else{
                    //throw no/bad type
                    throw new ConstructionFailure("invalid param type");
                }
            }else{
                //throw missing :
                throw new ConstructionFailure("missing colon (':')");
            }
        }else{
            throw new ConstructionFailure("missing id");
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
