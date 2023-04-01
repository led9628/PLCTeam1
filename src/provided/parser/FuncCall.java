package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FuncCall implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public FuncCall(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token1 = tokens.remove(0);

        if(token1.getTokenType() != TokenType.ID_KEYWORD){
            tokens.add(0, token1);
            throw new ConstructionFailure("Unexpected symbol or id", token1.getLineNum());
        }
        this.children.add(new ID(tokens));

        Token token2 = tokens.remove(0);
        if(token2.getTokenType() != TokenType.L_BRACKET){
            tokens.add(0, token2);
            tokens.add(0, token1);
            throw new ConstructionFailure("Missing left operand", token2.getLineNum());
        }
        this.children.add(new Literal(token2.getToken())); // l brakcet
        // da params
        this.children.add(new Params(tokens));
        
        //after the param stuff is done, i need the next token
        Token token3 = tokens.remove(0);
        if(token3.getTokenType() != TokenType.R_BRACKET){
            tokens.add(0, token3);
            tokens.add(0, token2);
            tokens.add(0, token1);
            throw new ConstructionFailure("Missing right operand", token3.getLineNum());
        }
        this.children.add(new Literal(token3.getToken()));


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
    //You need to add params stuff
    public boolean validateTree() {
        for(int i = 0; i < this.children.size(); i++) {
            //if function does not exist
            if (i == 0){
                try {
                    Program.functions.get(this.children.get(0).toString());
                }
                catch (Exception e){
                    return false;
                }
            }
            //if validate tree bad
            else{
                boolean result = this.children.get(i).validateTree();
                if (!result)
                    return false;
            }
        }
        return true;
    }
}
