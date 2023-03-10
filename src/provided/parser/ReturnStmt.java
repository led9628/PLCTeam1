package provided.parser;
import provided.Token;
import provided.JottTree;

import java.util.ArrayList;

public class ReturnStmt implements JottTree{
    ArrayList<JottTree> children;

    public ReturnStmt(ArrayList<Token> tokens) throws ConstructionFailure{ // return <expr><end_stmt>
        var token = tokens.remove(0);
        if(token.getToken() == "return"){
            children.add(new Literal("return"));
            children.add(new Expr(tokens));
            children.add(new EndStmt(tokens));
        }else{
            throw new ConstructionFailure("Invalid Return statement.", token.getLineNum());
        }
    }
    

   @Override
   public String convertToJott() {
       // TODO Auto-generated method stub
       return null;
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
