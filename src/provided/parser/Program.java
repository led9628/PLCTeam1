package provided.parser;

import java.util.ArrayList;
import java.util.HashMap;

import provided.JottTree;
import provided.Token;

public class Program implements JottTree{
    ArrayList<FunctionDef> children = new ArrayList<>(); //lsit of functions
    public static HashMap<String, HashMap<String, JottTree>> localSymtabs = new HashMap<String, HashMap<String, JottTree>>(); //function name to hashmap
    public static HashMap<String, JottTree> symtab = new HashMap<String, JottTree>(); //global variable name to vairable as JottTree hashmap.

    public Program(ArrayList<Token> tokens) throws ConstructionFailure{
        parse(tokens);
    }
    
    private void parse(ArrayList<Token> tokens) throws ConstructionFailure{
        while(tokens.size()>1){
            children.add(new FunctionDef(tokens));
        }
        // if(tokens.get(0).getToken() == "$$"){
        //     //THROW missing $$
        //     throw new ConstructionFailure("No end of file symbol ($$)", tokens.get(0).getLineNum());
        // }
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
        // TODO Auto-generated method stub
        return false;
    }
}
