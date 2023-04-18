package provided.parser;

import provided.Token;
import provided.JottTree;

import java.util.ArrayList;

public class Body implements JottTree, Returnable {
    ArrayList<JottTree> children = new ArrayList<>();
    String funcName;
    int lineNo;

    public Body(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure { // <body_stmt><body>
                                                                                                        // |
                                                                                                        // <return_stmt>
                                                                                                        // | *Nothing*
        this.funcName = funcName;
        lineNo = tokens.get(0).getLineNum();
        depth = depth + 1;
        
        if (tokens.size() != 0) {
            var token = tokens.get(0);
            if (token.getToken() == "}") {
                // if (Program.functions.get(funcName).returnType != null) {
                //     throw new SemanticFailure("Function needs to return " + Program.functions.get(funcName).returnType,
                //             token.getLineNum());
                // }

                // System.out.println("TOKENS: ");
                // for(var i : tokens){
                // System.out.println(i.getToken());
                // }
                // System.out.println("END");
                return;
            }

            if (!token.getToken().equals("return")) {
                children.add(new BodyStmt(tokens, funcName, depth));
                children.add(new Body(tokens, funcName, depth));
            } else if (token.getToken().equals("return")) {
                children.add(new ReturnStmt(tokens, funcName, depth));
            } else {
                throw new ConstructionFailure("Body is Invalid", token.getLineNum());
            }
        }
    }

    @Override
    public String convertToJott() {
        Boolean hasReturn = false;

        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            sb.append(child.convertToJott());
            if (child instanceof ReturnStmt) {
                hasReturn = true;
            }
        }

        if (!hasReturn) {
            // checkChildren for Returns
        }

        return sb.toString();
    }

    public boolean checkReturn() {
        for(var child: this.children){
            if(child instanceof Returnable){
                Returnable c = (Returnable) child;
                if(c.checkReturn() == true){
                    return true;
                }
            }
        }
        return false;
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
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            sb.append(child.convertToPython());
        }
        return sb.toString();
    }

    @Override
    public boolean validateTree() throws SemanticFailure{
        for (var child : this.children) {
            boolean result = child.validateTree();
            if(!result){
                return false;
            }
            // if (result == false || result2 == false)
            //     return false;
        }
        
        if(checkReturn() == false && Program.functions.get(funcName).returnType != null){
            throw new SemanticFailure("Function needs to return " + Program.functions.get(funcName).returnType,
                    lineNo);
        }

        return checkReturn();
        // return true;
    }
}
