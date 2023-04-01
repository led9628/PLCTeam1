package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Asmt implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();

    public Asmt(ArrayList<Token> tokens, String funcName) throws ConstructionFailure, SemanticFailure{
        //attempt to create type id = expr end_stmt
        Token a = null, b = null;
        try {
            
            CheckType ctype = new CheckType(tokens);
            Type type = new Type(tokens);
            ID id = new ID(tokens, funcName, ctype);

            this.children.add(type);
            this.children.add(id);//id
            this.children.add(new Literal(tokens.get(1).getToken()));//=
        
            Token equalsToken = tokens.get(1);
            a = tokens.remove(0);//id
            b = tokens.remove(0);//=

            //creating new variable with id and ctype.
            Variable newVar = new Variable(ctype, null, id.toString());
            Program.functions.get(funcName).localSymtab.put(id.toString(), newVar);// adding new var to symtab.

            if (!equalsToken.getToken().equals("=")) {
                throw new ConstructionFailure("Assignment Statement should have =", tokens.get(1).getLineNum());
            }
            Expr expr = new Expr(tokens, funcName);
            //CHECK IF ID.TYPE = EXPR.TYPE.
            if(!id.type.equals(expr.type)){
                throw new SemanticFailure("Assignment between wrong types", tokens.get(0).getLineNum());
            }
            
            this.children.add(expr);
            this.children.add(new EndStmt(tokens));
            return;
        } catch (ConstructionFailure e) {
            if (b != null) {
                tokens.add(0, b);
            }
            if (a != null) {
                tokens.add(0, a);
            }
        }

        //id = sth
        try {
            //create and check id exists
            ID id = new ID(tokens, funcName, null);
            if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                throw new SemanticFailure("Uninitialized variable: " + id.toString(), tokens.get(0).getLineNum());
            }
            id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
            this.children.add(id);//id
            this.children.add(new Literal(tokens.get(1).getToken()));//=
            Token equalsToken = tokens.get(1);
            
            //check =
            a = tokens.remove(0);
            b = tokens.remove(0);
            if (!equalsToken.getToken().equals("=")) {
                throw new ConstructionFailure("Assignment Statement should have =", tokens.get(1).getLineNum());
            }
            //CHECK IF TYPES ARE THE SAME
            Expr expr = new Expr(tokens, funcName);
            if(!id.type.equals(expr.type)){
                throw new SemanticFailure("Assignment between wrong types", tokens.get(1).getLineNum());
            }

            this.children.add(expr);
            this.children.add(new EndStmt(tokens));
            return;
        } catch (ConstructionFailure e) {
            if (b != null) {
                tokens.add(0, b);
            }
            if (a != null) {
                tokens.add(0, a);
            }
        }
        throw new ConstructionFailure("Assignment Statement is Invalid", tokens.get(0).getLineNum());
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
