package provided.parser;
import provided.Token;
import provided.JottTree;

import java.util.ArrayList;

public class ReturnStmt implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>();

    public ReturnStmt(ArrayList<Token> tokens, String funcName) throws ConstructionFailure, SemanticFailure{ // return <expr><end_stmt>
        var token = tokens.remove(0);
        if(token.getToken().equals("return")){
            children.add(new Literal("return"));
            children.add(new Expr(tokens, funcName));
            children.add(new EndStmt(tokens));
        }else{
            throw new ConstructionFailure("Return statement is Invalid", token.getLineNum());
        }
    }
    

   @Override
   public String convertToJott() {
       String s1 = this.children.get(1).convertToJott();
       String s2 = this.children.get(2).convertToJott();
       return "return " + s1 + s2;
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
