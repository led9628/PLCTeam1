package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class NExpr implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    String funcName;
    int lineNo;
    public CheckType type;

    public NExpr(ArrayList<Token> tokens, String funcName) throws ConstructionFailure, SemanticFailure {
        this.funcName = funcName;
        this.lineNo = tokens.get(0).getLineNum();
        
        // Attempt to create a FuncCall
        try {
            FuncCall func = new FuncCall(tokens, funcName);
            this.type = func.type;
            this.children.add(func);
            return;
        } catch (ConstructionFailure e) {}
        // Attempt to create an Id Op NExpr
        Token token = tokens.get(0);
        lineNo = tokens.get(0).getLineNum();
        if(tokens.get(1).getTokenType() == TokenType.MATH_OP){
            try {
                if (token.getTokenType() != TokenType.ID_KEYWORD) {
                    throw new ConstructionFailure("Unexpected error", -1);
                }
                // else if((tokens.get(0).getTokenType() != TokenType.MATH_OP)){
                //     throw new ConstructionFailure("Unexpected error", -1);
                // }

                ID id = new ID(tokens, funcName, null);
                // if (!Program.functions.get(funcName).settingParams){
                //     if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                //         throw new SemanticFailure("id not found", tokens.get(0).getLineNum());
                //     }
                //     id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
                
                // }
                // this.type = id.type;
                tokens.remove(0);
                this.children.add(id);

                this.children.add(new Op(tokens));

                lineNo = tokens.get(0).getLineNum();
                NExpr n = new NExpr(tokens, funcName);
                
                this.children.add(n);
                return;
            } catch (ConstructionFailure e) {
                // tokens.add(0, token);
            }
        }

        // Attempt to create an Id
        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            ID id = new ID(tokens, funcName, null);

            // if (!Program.functions.get(funcName).settingParams){
            //     if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
            //         throw new SemanticFailure("id not found", tokens.get(0).getLineNum());
            //     }
            //     id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
            // }
            // type = id.type;
            // if(Program.functions.get(funcName))
            tokens.remove(0);
            this.children.add(id);
            return;
        }
        
        // Attempt to create a Num
        try {
            // Token tok = tokens.get(0);
            Num num = new Num(tokens);
            this.children.add(num);
            this.type = num.type;
            // tokens.add(tok);//since num removes it but we need to create a type with it.
            // this.type = new Type(tokens);
            return;
        } catch (ConstructionFailure e) {}
        
        // Attempt to create a FuncCall Op NExpr
        try {
            FuncCall fc = new FuncCall(tokens, funcName);
            this.children.add(fc);

            this.children.add(new Op(tokens));

            lineNo = tokens.get(0).getLineNum();
            NExpr n = new NExpr(tokens, funcName);
            this.children.add(n);

            // if(!fc.type.equals(n.type)){
            //     throw new SemanticFailure("Invalid comparison between " + fc.type + " and " + n.type, ln);
            // }
            return;
        } catch (ConstructionFailure e) {}

        // Attempt to create a Num Op NExpr
        try {
            Num num = new Num(tokens);
            this.children.add(num);
            
            this.children.add(new Op(tokens));

            lineNo = tokens.get(0).getLineNum();
            NExpr n = new NExpr(tokens, funcName);
            this.children.add(n);

            // if(!num.type.equals(n.type)){
            //     throw new SemanticFailure("Invalid comparison between " + num.type + " and " + n.type, ln);
            // }
            return;
        } catch (ConstructionFailure e) {}
        throw new ConstructionFailure("Number Expression is Invalid", tokens.get(0).getLineNum());
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
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            sb.append(child.convertToC());
        }
        return sb.toString();
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean validateTree() throws SemanticFailure{
        if(children.size()==1){
            //id
            if(children.get(0) instanceof ID){
                ID id = (ID) children.get(0);
                if(id.validateTree() == false){
                    return false;
                }

                if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                    throw new SemanticFailure("Id not found: " + id.toString(), lineNo);
                }
                id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
                this.type = id.type;
            }
            
            //num
            else if(children.get(0) instanceof Num){
                Num n = (Num) children.get(0);
                if(n.validateTree() == false){
                    return false;
                }

                this.type = n.type;
            }
            //funcCall
            else if(children.get(0) instanceof FuncCall){
                FuncCall fc = (FuncCall)children.get(0);
                if(fc.validateTree() == false){
                    return false;
                }

                this.type = fc.type;
            }
        }else{
            //id op nexpr
            if(children.get(0) instanceof ID){
                ID id = (ID)children.get(0);
                if(id.validateTree() == false){
                    return false;
                }

                if(!Program.functions.get(funcName).localSymtab.containsKey(id.toString())){
                    throw new SemanticFailure("id not found"+ id.toString(), lineNo);
                }
                id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
                this.type = id.type;
                
                NExpr n = (NExpr)children.get(2);
                if(!n.validateTree()){
                    return false;
                }
                if(!n.type.equals(id.type)){
                    throw new SemanticFailure("Invalid operation between" + id.type + " and " + n.type, lineNo);
                }
            }

            //num op nexpr
            else if(children.get(0) instanceof Num){
                Num num = (Num)children.get(0);
                NExpr n = (NExpr)children.get(2);
                if(!num.validateTree() || !n.validateTree()){
                    return false;
                }
                if(!num.type.equals(n.type)){
                    throw new SemanticFailure("Invalid comparison between " + num.type + " and " + n.type, lineNo);
                }
            }

            //funccall op nexpr
            else if(children.get(0) instanceof FuncCall){
                FuncCall fc = (FuncCall)children.get(0);
                NExpr n = (NExpr)children.get(2);
                if(!fc.validateTree() || !n.validateTree()){
                    return false;
                }
                if(!fc.type.equals(n.type)){
                    throw new SemanticFailure("Invalid comparison between " + fc.type + " and " + n.type, lineNo);
                }
            }
        }
        return true;
        // for(var child : this.children) {
        //     boolean result = child.validateTree();
        //     if (!result)
        //         return false;
        // }
        // return true;
    }
}