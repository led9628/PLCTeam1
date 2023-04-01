package provided.parser;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionInfo {
    public HashMap<String, Variable> localSymtab;
    public CheckType returnType;
    public ArrayList<CheckType> paramTypes;

    public FunctionInfo(){
        localSymtab = new HashMap<String, Variable>();
        returnType = null;
        this.paramTypes = new ArrayList<CheckType>();
    }
}
