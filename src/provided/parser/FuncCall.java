package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FuncCall implements JottTree {
    ArrayList<JottTree> children = new ArrayList<>();
    public CheckType type;

    public FuncCall(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure{
        Token token1 = tokens.get(0);//id

        // System.out.println("FUNCTOKENS: ");
        // for(var i : tokens){
        //     System.out.println(i.getToken() + "   " + i.getTokenType());
        // }

        if(token1.getTokenType() != TokenType.ID_KEYWORD){
            // tokens.add(0, token1);
            throw new ConstructionFailure("Unexpected symbol or id", token1.getLineNum());
        }
        ID id = new ID(tokens, funcName, null);

        // type = Program.functions.get(id.toString()).returnType;
        // id.type = Program.functions.get(funcName).localSymtab.get(id.toString()).varType;
        // id.type = type;
        this.children.add(id);
        tokens.remove(0);

        Token token2 = tokens.remove(0);
        if(token2.getTokenType() != TokenType.L_BRACKET){
            tokens.add(0, token2);
            tokens.add(0, token1);
            throw new ConstructionFailure("Missing left operand", token2.getLineNum());
        }
        this.children.add(new Literal(token2.getToken())); // l brakcet
        // da params
        this.children.add(new Params(tokens, funcName, token1.getToken()+" ", depth));
        
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
        StringBuilder sb = new StringBuilder();
        for(var child: this.children) {
            String s = child.convertToJava(className); 
            if(s.equals("[ ")){
                s = "( ";
            }
            else if(s.equals("] ")){
                s = ") ";
            }
            else if(s.equals("print ")){
                s = "System.out.println";
            }
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.children.size(); ++i){
            String s = this.children.get(i).convertToC();
            if (s.equals("[ ")) {
                s = "( ";
            } else if (s.equals("] ")) {
                s = ") ";
            }
            if (s.equals("print ")) {
                JottTree q = ((Expr)((Params)this.children.get(i+2)).children.get(0)).children.get(0);
                
                if (q instanceof SExpr) {
                    s = "print_s";
                } else if (q instanceof BExpr) {
                    s = "print_i";
                } else if (q instanceof NExpr) {
                    
                    if (((NExpr)q).children.get(0) instanceof FuncCall) {
                        s = "print_s";
                    } else {
                        s = "print_i";
                    }
                    
                } else {
                    s = "print_f";
                }
            }
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public String convertToPython() {
        StringBuilder sb = new StringBuilder();
        for(var child : this.children){
            String s = child.convertToC();
            if (s.equals("[ ")) {
                s = "( ";
            } else if (s.equals("] ")) {
                s = ") ";
            }
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    //You need to add params stuff
    public boolean validateTree() throws SemanticFailure{
        //validate function exists.
        ID id = (ID)children.get(0);
        id.validateTree();

        if(!Program.functions.containsKey(id.toString())){
            throw new SemanticFailure("function "+id.toString()+" not found", id.lineNo);
        }
        
        this.type = Program.functions.get(id.toString()).returnType;
        id.type = type;

        for(JottTree child : children){
            boolean valid = child.validateTree();
            if(valid == false){
                return false;
            }

        }

        return true;
        // for(int i = 0; i < this.children.size(); i++) {
        //     //if function does not exist
        //     if (i == 0){
        //         try {
        //             Program.functions.get(this.children.get(0).toString());
        //         }
        //         catch (Exception e){
        //             return false;
        //         }
        //     }
        //     //if validate tree bad
        //     else{
        //         boolean result = this.children.get(i).validateTree();
        //         if (!result)
        //             return false;
        //     }
        // }
        // return true;
    }
}
