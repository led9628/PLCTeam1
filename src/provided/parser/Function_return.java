package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Function_return {
    ArrayList<JottTree> children;

    public Function_return(ArrayList<Token> tokens) throws ConstructionFailure{
        Token token = tokens.remove(0);
        //Try to add a type
        try {
            this.children.add(new Type(tokens));
            return;
        } catch (ConstructionFailure e) {}
        if (token.getToken() == "Void"){
            this.children.add(new Literal(token.getToken()));
            return;
        }
        throw new ConstructionFailure("Failed to create a Function_return.");
    }
}
