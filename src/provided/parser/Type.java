package provided.parser;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class Type implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    TokenType type = null;
    // boolean notBad = false;

    // public Type(TokenType tokenType) throws ConstructionFailure{
    //     this.type = tokenType;
    //     notBad = true;
    // }

    public Type(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            type = token.getTokenType();
            if (token.getToken().equals("Boolean") || token.getToken().equals("Integer") || token.getToken().equals("String") || token.getToken().equals("Double")) {
                this.children.add(new Literal(token.getToken()));
                return;
            }
            tokens.add(0, token);
            throw new ConstructionFailure("is not Boolean, Integer, String, or Double.", token.getLineNum());
        }
        tokens.add(0, token);
        throw new ConstructionFailure("Not a valid Type.", token.getLineNum());
    }

    @Override
    public String convertToJott() {
        return this.children.get(0).convertToJott();
    }

    @Override
    public String convertToJava(String className) {
        return null;
    }

    @Override
    public String convertToC() {
        switch (this.children.get(0).convertToC()) {
            case "Boolean " -> { return "bool "; }
            case "Integer " -> { return "int "; }
            case "Double " -> { return "double "; }
            case "String " -> { return "char* "; }
            default -> { return "void "; }
        }
    }

    @Override
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        // if (this.children.isEmpty() && !notBad){
        //     return false;
        // }else{
        //     return true;
        // }
        return !this.children.isEmpty();
    }

    
}
