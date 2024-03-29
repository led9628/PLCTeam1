package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionDef implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>();
    String funcName;

    public FunctionDef(ArrayList<Token> tokens, int depth) throws ConstructionFailure, SemanticFailure{
        parse(tokens, depth);
    }

    private void parse(ArrayList<Token> tokens, int depth) throws ConstructionFailure, SemanticFailure{
        depth = 1;
        if(tokens.remove(0).getToken().equals("def")){
            children.add(new Literal("def"));

            //check function id(name)
            if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                funcName = tokens.get(0).getToken()+" ";

                //Sem: Duplicate function check.
                if(!Program.functions.containsKey(funcName)){
                    children.add(new ID(tokens, tokens.get(0).getToken(), null)); //add ID literal
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
                    children.add(new FunctionParam(tokens, funcName, depth));

                    //check for 2nd+ params
                    while(tokens.get(0).getToken().equals(",")){
                        tokens.remove(0); //remove ,
                
                        children.add(new FunctionParam(tokens, funcName, depth));
                    }
                }

                //check for end of params:
                if(tokens.get(0).getTokenType() == TokenType.R_BRACKET){
                    Program.functions.get(funcName).settingParams = false;
                    children.add(new Literal(tokens.remove(0).getToken())    ); //remove r bracket

                    if(tokens.get(0).getToken().equals(":")){
                        tokens.remove(0); //remove colon
                        children.add(new FunctionReturn(tokens, funcName, depth)); //check return type:
                    }else{
                        throw new ConstructionFailure("Missing colon (:)", tokens.get(0).getLineNum()); // throw missing colon
                    }
                    
                }else{
                    throw new ConstructionFailure("Missing right bracket (])", tokens.get(0).getLineNum()); // throw no r bracket
                }

                //check for body curly brackets
                if(tokens.get(0).getTokenType() == TokenType.L_BRACE){
                    children.add(new Literal(tokens.remove(0).getToken())); // add L brace
                    children.add(new Body(tokens, funcName, depth));
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
        // validateTree();
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
        StringBuilder sb = new StringBuilder("public static ");
        boolean firstParam = true;
        String currString = null;
        String type = "Void";
        for (var child : this.children) {
            if (currString != null && currString.equals(") ")) {
                currString = child.convertToJava(className);
                type = currString;
                continue;
            }
            if(child instanceof FunctionParam){
                if(firstParam){
                    firstParam = false;
                }else{
                    sb.append(",");
                }
            }

            currString = child.convertToJava(className);
            if (currString.equals("[ ")) {
                currString = "(";
            } else if (currString.equals("] ")) {
                currString = ") ";
            } else if (currString.equals("{ ")) {
                currString = "{\n";
            } else if (currString.equals("} ")) {
                currString = "}\n";
            }
            sb.append(currString);
        }
        String finalString = sb.toString();
        finalString = finalString.replace("def", type);
        finalString = finalString.replace("main ()", "main(String[] args)");
        return finalString;
    
    }

    @Override
    public String convertToC() {
        StringBuilder sb = new StringBuilder();
        boolean firstParam = true;
        String currString = null;
        String type = "Void";
        for (var child : this.children) {
            if (currString != null && currString.equals(") ")) {
                currString = child.convertToC();
                type = currString;
                continue;
            }
            if(child instanceof FunctionParam){
                if(firstParam){
                    firstParam = false;
                }else{
                    sb.append(",");
                }
            }

            currString = child.convertToC();
            if (currString.equals("[ ")) {
                currString = "(";
            } else if (currString.equals("] ")) {
                currString = ") ";
            } else if (currString.equals("{ ")) {
                currString = "{\n";
            } else if (currString.equals("} ")) {
                currString = "}\n";
            }
            sb.append(currString);
        }
        String finalString = sb.toString();
        return finalString.replace("def", type);

    }

    @Override
    public String convertToPython() {
        StringBuilder sb = new StringBuilder();
        boolean firstParam = true;
        String currString = null;
        for (var child : this.children) {
            if (currString != null && currString.equals(") ")) {
                currString = child.convertToPython();
                continue;
            }
            if(child instanceof FunctionParam){
                if(firstParam){
                    firstParam = false;
                }else{
                    sb.append(",");
                }
            }

            currString = child.convertToPython();

            if (currString.equals("[ ")) {
                currString = "(";
            } else if (currString.equals("] ")) {
                currString = ")";
            } else if (currString.equals("{ ")) {
                Body.global_depth += 1;
                currString = ":\n";
            } else if (currString.equals("} ")) {
                Body.global_depth -= 1;
                currString = "\n";
            }

            // Node Debugging
            //currString = "{" + child.getClass().getSimpleName().toUpperCase() + "}" + currString;

            sb.append(currString);
        }
        String finalString = sb.toString();
        return finalString;
    }

    @Override
    public boolean validateTree() throws SemanticFailure {
        for(var child : children){
            if(!child.validateTree()){
                return false;
            }

        }
        return true;

        // boolean isValid = true;
        // for (var child : children) {
        //     if (child instanceof ArrayList<?>) {
        //         isValid = isValid && child.validateTree();
        //     }
        //     if (!isValid) {
        //         return false;
        //     }
        //     if ((child instanceof FunctionReturn && !this.children.contains(new Literal("void "))) || (!(child instanceof FunctionReturn) && !this.children.contains(new Literal("void ")))) {
        //         return false;
        //     }
        //     System.out.print(child.getClass());
        // }
        // return true;
    }

}
