package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class FunctionReturn implements JottTree{
    ArrayList<JottTree> children;

    public FunctionReturn(ArrayList<Token> tokens) throws ConstructionFailure{
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
        throw new ConstructionFailure("Failed to create a Function_return.", token.getLineNum());
    }

    @Override
    public String convertToJott() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJott'");
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToC'");
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
}
