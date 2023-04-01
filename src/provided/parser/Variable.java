package provided.parser;

import provided.JottTree;

public class Variable {
    public String name;
    public Type varType;
    public JottTree value;

    public Variable(Type vT, JottTree val, String name){
        this.name = name;
        this.varType = vT;
        this.value = val;
    }
}
