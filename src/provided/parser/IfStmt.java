package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class IfStmt implements JottTree, Returnable{
    ArrayList<JottTree> children = new ArrayList<>();
    
    public IfStmt(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure{
        Token token = tokens.remove(0);

        //check if the token is "if"
        if(!token.getToken().equals("if")){
            throw new ConstructionFailure("Unexpected symbol or id", token.getLineNum());
        }
        this.children.add(new Literal("if"));
        // next token
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.L_BRACKET){
            throw new ConstructionFailure("Missing left operand", token.getLineNum());
        }
        this.children.add(new Literal(token.getToken()));
        this.children.add(new BExpr(tokens, funcName, depth));
        
        //after the bool expr stuff is done, i need the next token
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.R_BRACKET){
            throw new ConstructionFailure("Missing right operand", token.getLineNum());
        }
        this.children.add(new Literal(token.getToken()));

        // {
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.L_BRACE){
            throw new ConstructionFailure("Missing left operand", token.getLineNum());
        }
        this.children.add(new Literal(token.getToken()));
        this.children.add(new Body(tokens, funcName, depth));

        // after trying to make body, get }
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.R_BRACE){
            throw new ConstructionFailure("Missing right operand", token.getLineNum());
        }
        this.children.add(new Literal(token.getToken()));

        //check token after closing ifstmt body
        token = tokens.get(0);

        if(token.getToken().equals("elseif")){
            while(token.getToken().equals("elseif")){
                //remove "elseif" token from list
                tokens.remove(0);
                this.children.add(new Literal("elseif"));
                // next token
                token = tokens.remove(0);
                if(token.getTokenType() != TokenType.L_BRACKET){
                    throw new ConstructionFailure("Missing left operand", token.getLineNum());
                }

                this.children.add(new Literal(token.getToken()));
                this.children.add(new BExpr(tokens, funcName, depth));
        
                //after the bool expr stuff is done, i need the next token
                token = tokens.remove(0);
                if(token.getTokenType() != TokenType.R_BRACKET){
                    throw new ConstructionFailure("Missing right operand", token.getLineNum());
                }
                this.children.add(new Literal(token.getToken()));

                // {
                token = tokens.remove(0);
                if(token.getTokenType() != TokenType.L_BRACE){
                    throw new ConstructionFailure("Missing left operand", token.getLineNum());
                }
                this.children.add(new Literal(token.getToken()));
                this.children.add(new Body(tokens, funcName, depth));

                // after trying to make body, get }
                token = tokens.remove(0);
                if(token.getTokenType() != TokenType.R_BRACE){
                    throw new ConstructionFailure("Missing right operand", token.getLineNum());
                }
                this.children.add(new Literal(token.getToken()));
                //check token after closing ifstmt body
                token = tokens.get(0);
            }
        }
        
        // next token is else
        if(token.getToken().equals("else")){
            this.children.add(new Literal("else"));
            //remove "else" token from list 
            tokens.remove(0);
            token = tokens.remove(0);
            if(token.getTokenType() != TokenType.L_BRACE){
                throw new ConstructionFailure("Missing left operand", token.getLineNum());
            }
            this.children.add(new Literal(token.getToken()));

            this.children.add(new Body(tokens, funcName, depth));

            // after trying to make body, get }
            token = tokens.remove(0);
            if(token.getTokenType() != TokenType.R_BRACE){
                throw new ConstructionFailure("Missing right operand", token.getLineNum());
            }
            this.children.add(new Literal(token.getToken()));

            token = tokens.get(0);
            if(token.getToken().equals("else")){
                throw new ConstructionFailure("More than one else for single if block", token.getLineNum());
            }
        
        }

   
    }
    


    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for(var child : this.children){
            sb.append(child.convertToJott());
        }
        String str = sb.toString();
        return str;
    }

    @Override
    public String convertToJava(String className) {
        StringBuilder sb = new StringBuilder();
        for(var child : this.children) {
            String s = child.convertToJava(className);
            if(s.equals("[ ")){ s="( "; }
            if(s.equals("] ")){ s=") "; }
            if (s.equals("elseif ")) { s = "else if "; }
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            String s = child.convertToC();
            if (s.equals("[ ")) { s = "( "; }
            if (s.equals("] ")) { s = ") "; }
            if (s.equals("elseif ")) { s = "else if "; }
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ".repeat(Program.dep));
        for (var child : this.children) {
            if(child.toString().equals("else ") || child.toString().equals("elseif ")){sb.append("    ".repeat(Program.dep));}
            String s = child.convertToPython();
            if (s.equals("[ ")) { s = "( "; }
            if (s.equals("] ")) { s = ") "; }
            if (s.equals("elseif ")) { s = "elif "; }
            if (s.equals("{ ")){
                Program.dep++;
                s =":\n";
            }
            if (s.equals("} ")){
                Program.dep--;
                s ="";
            }
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public boolean validateTree() throws SemanticFailure{
        for(var child : this.children) {
            boolean result = child.validateTree();
            if (!result)
                return false;
        }
        return true;
    }

    @Override
    public boolean checkReturn() {
        for(var child: this.children){
            if(child instanceof Returnable){
                Returnable c = (Returnable) child;
                if(c.checkReturn() != true){
                    return false;
                }
            }
        }
        return true;
    }
}
