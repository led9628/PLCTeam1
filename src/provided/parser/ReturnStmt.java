package provided.parser;
import provided.Token;
import provided.JottTree;

import java.util.ArrayList;

public class ReturnStmt implements JottTree{
    ArrayList<JottTree> children;

    public ReturnStmt(ArrayList<Token> tokens){ // return <expr><end_stmt>
        if(tokens.remove(0).getToken() == "return"){
            children.add(new Literal("return"));
            children.add(new Expr(tokens));
            children.add(new EndStmt());
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
