package provided.parser;

import java.util.ArrayList;
import java.util.HashMap;

import provided.JottTree;

public class FunctionInfo {
    public HashMap<String, JottTree> localSymtab;
    public Type returnType;
    public ArrayList<Type> paramTypes;

    public FunctionInfo(ArrayList<Type> paramTypes){
        localSymtab = new HashMap<String, JottTree>();
        returnType = null;
        this.paramTypes = paramTypes;
    }

    public Type getReturnType(){
        return returnType;
    }

    public void setReturnType(Type rt){
        returnType = rt;
    }
}
