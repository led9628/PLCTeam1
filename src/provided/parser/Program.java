package provided.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import provided.JottTree;
import provided.Token;

public class Program implements JottTree{
    ArrayList<FunctionDef> children = new ArrayList<>(); //lsit of functions
    public static HashMap<String, FunctionInfo> functions = new HashMap<String, FunctionInfo>(); //function name to hashmap
    public static HashSet<String> kw;
    
    // public static HashMap<String, JottTree> symtab = new HashMap<String, JottTree>(); //global variable name to vairable as JottTree hashmap.

    public Program(ArrayList<Token> tokens) throws ConstructionFailure, SemanticFailure{
        functions.put("print ", new FunctionInfo());
        functions.get("print ").paramTypes.add(new CheckType("Any"));

        functions.put("concat", new FunctionInfo());
        functions.get("concat").paramTypes.add(new CheckType("String"));
        functions.get("concat").paramTypes.add(new CheckType("String"));

        functions.put("length ", new FunctionInfo());
        functions.get("length ").paramTypes.add(new CheckType("String"));

        kw = new HashSet<String>();
        kw.add("while");
        kw.add("if");
        kw.add("def");
        kw.add("else");
        kw.add("return");
        kw.add("elseif");
        kw.add("Double");
        kw.add("Integer");
        kw.add("String");
        kw.add("Boolean");
        kw.add("Void");
        kw.add("True");
        kw.add("False");

        parse(tokens);
        //if(!validateTree()){
        //   System.out.println("INVALID");
        //}
        
    }
    
    private void parse(ArrayList<Token> tokens) throws ConstructionFailure, SemanticFailure{
        while(tokens.size()>1){
            int depth  = 0;
            children.add(new FunctionDef(tokens, depth));
        }
        if(!functions.containsKey("main ")){
            throw new SemanticFailure("No main function", 0);
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
        StringBuilder sb = new StringBuilder();
        sb.append("public class "+className+"{ ");
        for (var child : this.children ) {
            sb.append(child.convertToJava(className));
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder sb = new StringBuilder();
        sb.append("""
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
int length(char* s) {
    return strlen(s);
}
char* concat(char* s1, char* s2) {
    char* s = malloc(strlen(s1) + strlen(s2));
    strcpy(s, s1);
    return strcat(s, s2);
}
void print_s(char* s) {
    printf(\"%s\\n\", s);
}
void print_i(int i) {
    printf(\"%d\\n\", i);
}
void print_f(float d) {
    printf(\"%f\\n\", d);
} 
""");
        for (var child : this.children) {
            sb.append(child.convertToC());
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
        for(var child : children){
            if(!child.validateTree()){
                return false;
            }
        }
        return true;
    }
}
