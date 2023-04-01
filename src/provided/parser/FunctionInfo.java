package provided.parser;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionInfo {
    public HashMap<String, Variable> localSymtab;
    public Type returnType;
    public ArrayList<Type> paramTypes;

    public FunctionInfo(){
        localSymtab = new HashMap<String, Variable>();
        returnType = null;
        this.paramTypes = new ArrayList<Type>();
    }

    public Type getReturnType(){
        return returnType;
    }

    public void setReturnType(Type rt){
        returnType = rt;
    }
}
