package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class FunctionReturn implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>();

    public FunctionReturn(ArrayList<Token> tokens, String funcName) throws ConstructionFailure{
        //Try to add a type
        int lnm;
        try {
            Token token = tokens.get(0);
            this.children.add(new Type(tokens));
            CheckType ctype = new CheckType(token.getToken());
            Program.functions.get(funcName).returnType = ctype;
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
        throw new ConstructionFailure("Return from function Invalid", lnm);
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
        String s = this.children.get(0).convertToC();
        if (s.equals("Void ")) {
            return "void ";
        } else {
            return s;
        }
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
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
