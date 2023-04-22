package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class WhileLoop implements JottTree, Returnable {
    ArrayList<JottTree> children = new ArrayList<>();

    public WhileLoop(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure{
        Token token = tokens.remove(0);

        if(!token.getToken().equalsIgnoreCase("while")){
            throw new ConstructionFailure("Unexpected symbol or id", token.getLineNum());
        }
        this.children.add(new Literal(token.getToken()));

        if(tokens.get(0).getTokenType() != TokenType.L_BRACKET){
            throw new ConstructionFailure("Missing left operand", token.getLineNum());
        }
        this.children.add(new Literal(tokens.remove(0).getToken()));
        // bool expression
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
        // get body
        this.children.add(new Body(tokens, funcName, depth));

        // after trying to make body, get }
        token = tokens.remove(0);
        if(token.getTokenType() != TokenType.R_BRACE){
            throw new ConstructionFailure("Missing right operand", token.getLineNum());
        }
        this.children.add(new Literal(token.getToken()));
        
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
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            String s = child.convertToC();
            if (s.equals("[ ")) { s = "( "; }
            if (s.equals("] ")) { s = ") "; }
            
            if (s.equals("{ ")){
                s=":\n";
            }
            sb.append(s);

        }
        return sb.toString();
    }

    @Override
    public boolean validateTree() throws SemanticFailure {
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
                if(c.checkReturn() == true){
                    return true;
                }
            }
        }
        return false;
    }
    
}
