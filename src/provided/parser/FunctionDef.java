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
                children.add(new Literal("["));

                //function def params check:
                if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                    //add ID literal
                    children.add(new Literal(tokens.remove(0).getToken()));

                    if(tokens.remove(0).getToken().equals(":")){ //not storing : because every param id will be followed by its type
                    
                        //check and add type to children list:
                        String a = tokens.remove(0).getToken();
                        if(a.equals("Double") || a.equals("Integer") || a.equals("String") || a.equals("Boolean")){
                            //add param type
                            children.add(new Literal(a));
                        }else{
                            //throw bad type
                            throw new ConstructionFailure("invalid param type");
                        }

                        //check for 2nd+ params
                        while(tokens.get(0).getToken().equals(",")){
                            tokens.remove(0); //remove ,
                    
                            if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                                // add param id
                                children.add(new Literal(tokens.remove(0).getToken()));

                                //check for param colon and type:
                                if(tokens.remove(0).getToken().equals(":")){
                                    a = tokens.remove(0).getToken();
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
                    }else{
                        //throw missing :
                        throw new ConstructionFailure("missing colon (':')");
                    }
                }

                //check for end of params:
                if(tokens.remove(0).getTokenType() == TokenType.R_BRACKET){
                    children.add(new Literal("]"));

                    if(tokens.remove(0).getToken().equals(":")){
                        String a = tokens.remove(0).getToken();
                        //check return type:
                        if(a.equals("Double") || a.equals("Integer") || a.equals("String") || a.equals("Boolean") || a.equals("Void")){
                            children.add(new Literal(a));
                        }else{
                            //throw no/bad type
                            throw new ConstructionFailure("invalid return type");
                        }
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
