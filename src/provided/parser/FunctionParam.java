package provided.parser;

import java.util.ArrayList;
import java.util.HashMap;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionParam implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>(); //will only store an id, :, a type
    String funcName;
    // HashMap<String, JottTree> localSymTab;

    public FunctionParam(ArrayList<Token> tokens, String funcName) throws ConstructionFailure{
        // this.localSymTab = localSymTab;
        this.funcName = funcName;
        parse(tokens);
    }

    private void parse(ArrayList<Token> tokens) throws ConstructionFailure{
        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
            // add param id
            Literal paramID = new Literal(tokens.get(0).getToken());
            children.add(paramID);
            tokens.remove(0);
            // Program.functions.get(funcName).paramTypes.add(this);
            
            //check for param colon and type:
            if(tokens.get(0).getToken().equals(":")){
                tokens.remove(0); // remove :

                children.add(new Type(tokens));
                
                //add param types to functioninfo.
                Program.functions.get(funcName).paramTypes.add((Type)(children.get(children.size() - 1)));
                //add this parameter as local var.
                Program.functions.get(funcName).localSymtab.put(paramID.toString(), this);
            }else{
                //throw missing :
                throw new ConstructionFailure("Missing colon (:)", tokens.get(0).getLineNum());
            }
        }else{
            throw new ConstructionFailure("Missing function parameter id", tokens.get(0).getLineNum());
        }
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();

        for (var child : this.children) {
            if(child instanceof Type){
                sb.append(":");
                
            }
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
        return children.get(0).validateTree() && children.get(2).validateTree();
        //validate paramID && param type.
    }
}
