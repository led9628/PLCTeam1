package provided.parser;

import java.util.HashMap;

import provided.JottTree;

public class FunctionInfo {
    public HashMap<String, JottTree> localSymtab;
    public Type returnType;

    public FunctionInfo(){
        localSymtab = new HashMap<String, JottTree>();
        returnType = null;
    }

    public Type getReturnType(){
        return returnType;
    }

    public void setReturnType(Type rt){
        returnType = rt;
    }
}
