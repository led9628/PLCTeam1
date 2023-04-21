package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FunctionParam implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>(); //will only store an id, :, a type
    String funcName;
    // HashMap<String, JottTree> localSymTab;

    public FunctionParam(ArrayList<Token> tokens, String funcName, int depth) throws ConstructionFailure, SemanticFailure{
        // this.localSymTab = localSymTab;
        this.funcName = funcName;
        parse(tokens, depth);
    }

    private void parse(ArrayList<Token> tokens, int depth) throws ConstructionFailure, SemanticFailure{
        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
            // add param id

            //create and add id for parameter
            ID paramID = new ID(tokens, funcName, null);
            
            children.add(paramID);
            tokens.remove(0);
            // Program.functions.get(funcName).paramTypes.add(this);
            
            //check for param colon and type:
            if(tokens.get(0).getToken().equals(":")){
                tokens.remove(0); // remove :

                Token token = tokens.get(0);
                children.add(new Type(tokens));

                //add param types to functioninfo.
                CheckType ctype = new CheckType(token.getToken());
                Program.functions.get(funcName).paramTypes.add(ctype);

                //set paramId type
                paramID.type = ctype;
                
                //add this parameter as local var.
                Variable vari = new Variable(ctype, null, paramID.toString());
                Program.functions.get(funcName).localSymtab.put(paramID.toString(), vari);
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
        StringBuilder sb = new StringBuilder();
        for (var child : this.children ) {
            if(child instanceof Type){
                sb.insert(0, child.convertToJava(className));
            }
            else{
                sb.append(child.convertToJava(className));
            }
        }
        return sb.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder sb = new StringBuilder();
        for (var child : this.children) {
            if (child instanceof Type) {
                sb.insert(0, child.convertToC());
            } else {
                sb.append(child.convertToC());
            }
            
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
        for(JottTree child : children){
            if(child.validateTree()==false){
                return false;
            }
        }
        return true;
        //validate paramID && param type.
    }
}
