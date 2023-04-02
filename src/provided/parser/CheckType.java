package provided.parser;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class CheckType {
    varType type;
    public enum varType {
        INTEGER,
        DOUBLE,
        STRING,
        BOOLEAN,
        ANYDATATYPE
    }

    public CheckType(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.get(0);
        if (token.getTokenType() == TokenType.ID_KEYWORD){
            if (token.getToken().equals("Boolean")) {
                this.type = varType.BOOLEAN;
                return;
            }
            if (token.getToken().equals("Integer")) {
                this.type = varType.INTEGER;
                return;
            }
            if (token.getToken().equals("Double")) {
                this.type = varType.STRING;
                return;
            }
            if (token.getToken().equals("String")) {
                this.type = varType.STRING;
                return;
            }
            throw new ConstructionFailure("is not Boolean, Integer, String, or Double.", token.getLineNum());
        }

        return;
    }

    public CheckType(String token) throws ConstructionFailure{
        if (token.equals("Boolean")) {
            this.type = varType.BOOLEAN;
            return;
        }
        if (token.equals("Integer")) {
            this.type = varType.INTEGER;
            return;
        }
        if (token.equals("Double")) {
            this.type = varType.DOUBLE;
            return;
        }
        if (token.equals("String")) {
            this.type = varType.STRING;
            return;
        }
        if (token.equals("Any")){
            this.type = varType.ANYDATATYPE;
            return;
        }
    }

    public CheckType(TokenType tt){
        if(tt == TokenType.NUMBER){
            this.type = varType.DOUBLE;
            return;
        }else if(tt == TokenType.STRING){
            this.type = varType.STRING;
            return;
        }
    }
    
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }

        if(!(o instanceof CheckType)){
            return false;
        }
        
        CheckType t = (CheckType)o;

        if(this.type==t.type){
            return true;
        }
        if(this.type==varType.ANYDATATYPE && (t.type==varType.STRING || t.type==varType.DOUBLE || t.type==varType.BOOLEAN || t.type==varType.INTEGER)){
            return true;
        }
        if(t.type==varType.ANYDATATYPE && (this.type==varType.STRING || this.type==varType.DOUBLE || this.type==varType.BOOLEAN || this.type==varType.INTEGER)){
            return true;
        }
        // else if((this.type==varType.DOUBLE && t.type==varType.INTEGER) || (this.type==varType.INTEGER && t.type==varType.DOUBLE)){
        //     return true;
        // }
        return false;
    }

    @Override
    public String toString(){
        return type.name();
    }
}
