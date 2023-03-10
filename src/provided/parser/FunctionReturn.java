package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class FunctionReturn implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>();

    public FunctionReturn(ArrayList<Token> tokens) throws ConstructionFailure{
        //Try to add a type
        int lnm;
        try {
            this.children.add(new Type(tokens));
            return;
        } catch (ConstructionFailure e) {
            lnm = e.line;
        }
        Token token = tokens.remove(0);
        if (token.getToken().equals("Void")){
            this.children.add(new Literal(token.getToken()));
            return;
        }
        tokens.add(token);
        throw new ConstructionFailure("Failed to create a function return. ", lnm);
    }

    @Override
    public String convertToJott() {
        return this.children.get(0).convertToJott();
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
