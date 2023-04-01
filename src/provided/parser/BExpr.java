package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class BExpr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    public Type type;


    public BExpr(ArrayList<Token> tokens, String funcName) throws ConstructionFailure, SemanticFailure{
        // Attempt to create an NExpr RelOp NExpr
        try {
            NExpr n1 = new NExpr(tokens, funcName);
            this.children.add(n1);

            this.children.add(new RelOp(tokens));

            NExpr n2 = new NExpr(tokens, funcName);
            this.children.add(n2);

            if(!n1.type.equals(n2.type)){
                //LINE NUMBER MAY BE WRONG.
                throw new SemanticFailure(("Invalid attempt to compare" + n1.type + " and " + n2.type + "."), tokens.get(0).getLineNum());
            }
            return;
        } catch (ConstructionFailure e) {}
        // If that doesn't work, keep trying to make things.
        Token token = tokens.get(0);
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            // Try to create a FuncCall.
            try {
                FuncCall f = new FuncCall(tokens, funcName);
                this.children.add(f);
                this.type = f.type;
                return;
            } catch (ConstructionFailure e) {}
            // Try to create a Bool.
            try {
                Bool b = new Bool(tokens);
                this.children.add(b);
                this.type = b.type;
                return;
            } catch (ConstructionFailure e) {}
            // Create an ID if it can't be anything else.

            ID id = new ID(tokens, funcName, null);
            if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                throw new SemanticFailure("id not found", tokens.get(0).getLineNum());
            }
            id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
            this.type = id.type;
            tokens.remove(0);
            this.children.add(id);
            return;
        }
        // If we failed to turn BExpr into anything, throw.
        tokens.add(0, token);
        throw new ConstructionFailure("Boolean Expression is Invalid", token.getLineNum());
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            sb.append(child.convertToJott());
        }
        return sb.toString();
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
    public boolean validateTree() {
        for(var child : this.children) {
            boolean result = child.validateTree();
            if (!result)
                return false;
        }
        return true;
    }
}