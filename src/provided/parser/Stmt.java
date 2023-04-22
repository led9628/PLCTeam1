package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class Stmt implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>();

    public Stmt(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure{
        Token token1 = tokens.get(0);
        Token token2 = tokens.get(1);
        Token token3 = tokens.get(2);

        // actual vardec
        if(token1.getTokenType()==TokenType.ID_KEYWORD &&
        token2.getTokenType() == TokenType.ID_KEYWORD &&
        token3.getTokenType() == TokenType.SEMICOLON ) {
            this.children.add(new VarDec(tokens, funcName, depth));

        }

        // POSSIBLE BROKEN LEGACY CODE, REMOVE IF NECESSARY -Benson
        // if curr token is an id/key and the 2nd token after is a semicolon, var_dec
        if(token1.getTokenType()==TokenType.ID_KEYWORD && token2.getTokenType() == TokenType.SEMICOLON){
            this.children.add(new VarDec(tokens, funcName, depth));

        }
        // assignment
        else if(token1.getTokenType()==TokenType.ID_KEYWORD &&
            (
                tokens.get(1).getTokenType() == TokenType.ASSIGN || 
                tokens.get(2).getTokenType() == TokenType.ASSIGN
            ))
            
        {
            this.children.add(new Asmt(tokens, funcName, depth));
        }
        // function call
        else if(token1.getTokenType()==TokenType.ID_KEYWORD && token2.getTokenType() == TokenType.L_BRACKET){
            this.children.add(new FuncCall(tokens, funcName, depth));
            this.children.add(new EndStmt(tokens, depth));
        }
        else {
            throw new ConstructionFailure("Statement is Invalid", token1.getLineNum());
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
        for (var child : this.children ) {
            sb.append(child.convertToJava(className));
        }
        return sb.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            sb.append(child.convertToC());
        }
        return sb.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder sb = new StringBuilder();
        sb.append("    ".repeat(Program.dep));
        for (var child : this.children) {
            sb.append(child.convertToPython());
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
    
}
