package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionDef implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>();
    String funcName;

    public FunctionDef(ArrayList<Token> tokens) throws ConstructionFailure, SemanticFailure{
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens) throws ConstructionFailure, SemanticFailure{
        if(tokens.remove(0).getToken().equals("def")){
            children.add(new Literal("def"));

            if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                funcName = tokens.get(0).getToken();

                //Sem: Duplicate function check.
                if(Program.functions.get(funcName) == null){
                    children.add(new ID(tokens)); //add ID literal
                    tokens.remove(0);
                    Program.functions.put(funcName, new FunctionInfo()); //create local symbol table
                }else{
                    throw new SemanticFailure("Duplicate function: "+funcName+" already exists.", tokens.get(0).getLineNum());
                }
                
            }else{
                throw new ConstructionFailure("Missing function name ID", tokens.get(0).getLineNum()); //throw missing id
            }

            if(tokens.get(0).getTokenType() == TokenType.L_BRACKET){
                children.add(new Literal(tokens.remove(0).getToken())); // add L bracket

                //function def params check:
                if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                    children.add(new FunctionParam(tokens, funcName));

                    //check for 2nd+ params
                    while(tokens.get(0).getToken().equals(",")){
                        tokens.remove(0); //remove ,
                
                        children.add(new FunctionParam(tokens, funcName));
                    }
                }

                //check for end of params:
                if(tokens.get(0).getTokenType() == TokenType.R_BRACKET){
                    children.add(new Literal(tokens.remove(0).getToken())    ); //remove r bracket

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
                    children.add(new Literal(tokens.remove(0).getToken())); // add L brace
                    children.add(new Body(tokens, funcName));
                    if(tokens.get(0).getTokenType() == TokenType.R_BRACE){
                        children.add(new Literal(tokens.remove(0).getToken())); // add R brace
                    }else{
                        throw new ConstructionFailure("Missing right brace (})", tokens.get(0).getLineNum()); //throw no r brace
                    }
                }else{
                    throw new ConstructionFailure("Missing left brace ({)", tokens.get(0).getLineNum()); // throw no l brace
                }
            } else {
                throw new ConstructionFailure("Missing left bracket ([)", tokens.get(0).getLineNum()); // throw no l brace
            }     
        } else {
            throw new ConstructionFailure("Missing def for Function Definition", tokens.get(0).getLineNum());
        }
        validateTree();
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        boolean firstParam = true;
        for (var child : this.children) {
            if(child instanceof FunctionParam){
                if(firstParam){
                    firstParam = false;
                }else{
                    sb.append(",");
                }
            }

            String currString = child.convertToJott();
            sb.append(currString);
            if(currString.equals("] ")){
                sb.append(": ");
            }
        }
        return sb.toString();
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
        for(JottTree child : children){
            if(!child.validateTree() || (child.getClass().getName().equals("provided.parser.FunctionReturn") && this.children.contains(new Literal("void "))) || (!child.getClass().getName().equals("provided.parser.FunctionReturn") && !this.children.contains(new Literal("void ")))){
                return false;
            }
            System.out.print(child.getClass());
        }
        return true;
    }
}
