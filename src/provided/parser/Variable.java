package provided.parser;

import provided.JottTree;

public class Variable {
    public Type varType;
    public JottTree value;

    public Variable(Type vT, JottTree val){
        this.varType = vT;
        this.value = val;
    }
}
