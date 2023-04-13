package provided.parser;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionInfo {
    public boolean settingParams;
    public HashMap<String, Variable> localSymtab;
    public CheckType returnType;
    public ArrayList<CheckType> paramTypes;

    public FunctionInfo(){
        settingParams = true;
        localSymtab = new HashMap<String, Variable>();
        returnType = null;
        this.paramTypes = new ArrayList<CheckType>();
    }
}
