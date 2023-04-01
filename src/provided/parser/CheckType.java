package provided.parser;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class CheckType {
    varType type;
    public enum varType {
        NUMBER,
        STRING,
        BOOLEAN
    }

    public CheckType(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.get(0);
        if (token.getTokenType() == TokenType.ID_KEYWORD){
            if (token.getToken().equals("Boolean")) {
                this.type = varType.BOOLEAN;
                return;
            }
            if (token.getToken().equals("Integer") || token.getToken().equals("Double") ){
                this.type = varType.NUMBER;
                return;
            }
            if (token.getToken().equals("String")){
                this.type = varType.STRING;
                return;
            }
            tokens.add(0, token);
            throw new ConstructionFailure("is not Boolean, Integer, String, or Double.", token.getLineNum());
        }

        return;
    }

    public CheckType(TokenType tt){
        if(tt == TokenType.NUMBER){
            this.type = varType.NUMBER;
            return;
        }else if(tt == TokenType.STRING){
            this.type = varType.STRING;
            return;
        }
    }
}
