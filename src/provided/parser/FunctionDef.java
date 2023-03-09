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
            if(tokens.remove(0).getTokenType() == TokenType.L_BRACKET){
                if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                    children.add(new Literal(tokens.remove(0).getToken()));
                    if(tokens.remove(0).getToken().equals(":")){
                        String a = tokens.remove(0).getToken();
                        if(a.equals("Double") || a.equals("Integer") || a.equals("String") || a.equals("Boolean")){
                            children.add(new Literal(a));
                        }else{
                            //throw bad type
                        }
                        while(tokens.get(0).getToken().equals(",")){
                            tokens.remove(0);
                            if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                                children.add(new Literal(tokens.remove(0).getToken()));
                                if(tokens.remove(0).getToken().equals(":")){
                                    a = tokens.remove(0).getToken();
                                    if(a.equals("Double") || a.equals("Integer") || a.equals("String") || a.equals("Boolean") || a.equals("Void")){
                                        children.add(new Literal(a));
                                    }else{
                                        //throw bad type
                                    }
                                }else{
                                    //throw missing :
                                }
                            }
                        }
                    }else{
                        //throw missing :
                    }
                }
                if(tokens.remove(0).getTokenType() == TokenType.R_BRACKET){
                    if(tokens.remove(0).getToken().equals(":")){
                        String a = tokens.remove(0).getToken();
                        if(a.equals("Double") || a.equals("Integer") || a.equals("String") || a.equals("Boolean") || a.equals("Void")){
                            children.add(new Literal(a));
                        }else{
                            //throw bad type
                        }
                    }else{
                        //throw no colon
                    }
                }else{
                    //no r bracket
                }
                if(tokens.remove(0).getTokenType() == TokenType.L_BRACE){
                    children.add(new Body(tokens));
                    if(tokens.remove(0).getTokenType() != TokenType.R_BRACE){
                        //throw no r brace
                    }
                }else{
                    //throw no l brace
                }
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
