package provided.parser;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class Asmt implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    String funcName;
    int lineNo;

    public Asmt(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure{
        this.funcName = funcName;
        // if (tokens.get(0).getToken().equals("Integer")) {
        //     System.out.println("-----------------------------");
        //     System.out.print(tokens.get(0).getToken() + " ");
        //     System.out.print(tokens.get(1).getToken() + " ");
        //     System.out.print(tokens.get(2).getToken() + " ");
        //     System.out.print(tokens.get(3).getToken() + " ");
        //     System.out.print(tokens.get(4).getToken() + " ");
        //     System.out.print(tokens.get(5).getToken() + " ");
        //     System.out.print(tokens.get(6).getToken() + " ");
        //     System.out.print(tokens.get(7).getToken() + " ");
        //     System.out.print(tokens.get(8).getToken() + " ");
        //     System.out.println(tokens.get(9).getToken());
        //     System.out.println("-----------------------------");
        // }
        
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
            
            Expr expr = new Expr(tokens, funcName, depth);
            //CHECK IF ID.TYPE = EXPR.TYPE.
            // if(!id.type.equals(expr.type)){
            //     throw new SemanticFailure("Assignment between wrong types", tokens.get(0).getLineNum());
            // }
            
            this.children.add(expr);
            
            this.children.add(new EndStmt(tokens, depth));
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
            // if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
            //     throw new SemanticFailure("Uninitialized variable: " + id.toString(), tokens.get(0).getLineNum());
            // }
            // id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
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
            Expr expr = new Expr(tokens, funcName, depth);
            // if(!id.type.equals(expr.type)){
            //     throw new SemanticFailure("Assignment between wrong types", tokens.get(1).getLineNum());
            // }

            this.children.add(expr);
            this.children.add(new EndStmt(tokens, depth));
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
        StringBuilder sb = new StringBuilder();
        for (var child : this.children ) {
            sb.append(child.convertToJava(className));
        }
        return sb.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            sb.append(child.convertToC());
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            sb.append(child.convertToPython());
        }
        return sb.toString();
    }

    @Override
    public boolean validateTree() throws SemanticFailure{
        // if(!id.type.equals(expr.type)){
        //     throw new SemanticFailure("Assignment between wrong types", tokens.get(0).getLineNum());
        // }
        
        if(children.get(0) instanceof Type){
            ID id = (ID)children.get(1);
            if(!id.validateTree()){
                return false;
            }
            
            if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                throw new SemanticFailure("Variable with name " + id.toString() + " already exists", lineNo);
            }

            Expr expr = (Expr)children.get(3);
            if(!expr.validateTree()){
                return false;
            }

            if(!id.type.equals(expr.type)){
                throw new SemanticFailure("Assignment between wrong types: " + id.type + " and " + expr.type, lineNo);
            }
        }else{
            ID id = (ID)children.get(0);
            if(!id.validateTree()){
                return false;
            }

            if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                throw new SemanticFailure("Variable with name " + id.toString() + " does not exist", lineNo);
            }
            id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;

            Expr expr = (Expr)children.get(2);
            if(!expr.validateTree()){
                return false;
            }

            if(!id.type.equals(expr.type)){
                throw new SemanticFailure("Assignment between wrong types: " + id.type + " and " + expr.type, lineNo);
            }

        }


        // for(var child : this.children) {
        //     boolean result = child.validateTree();
        //     if (!result)
        //         return false;
        // }
        return true;
    }
}
