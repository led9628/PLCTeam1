package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class VarDec implements JottTree{

    ArrayList<JottTree> children = new ArrayList<>();

    public VarDec(ArrayList<Token> tokens, String funcName) throws ConstructionFailure{
        Token token = tokens.remove(0);
        //Try to add a type id end  statement
        try {
            ID b = new ID(tokens);
            tokens.remove(0);

            Type a = new Type(tokens);
            EndStmt c = new EndStmt(tokens);
            
            this.children.add(a);
            this.children.add(b);
            this.children.add(c);

            Variable newVar = new Variable(a, null, b.toString());
            Program.functions.get(funcName).localSymtab.put(b.toString(), newVar);// adding new var to symtab.
            return;
        } catch (ConstructionFailure e) {}
        throw new ConstructionFailure("Variable Declaration is Invalid", token.getLineNum());
    }

    @Override
    public String convertToJott() {
        String type = this.children.get(0).convertToJott();
        String id = this.children.get(1).convertToJott();
        String end = this.children.get(2).convertToJott();
        return type + " " + id + end;
    }

    @Override
    public String convertToJava(String className) {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython() {
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
