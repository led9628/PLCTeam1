package provided.parser;

import provided.JottTree;
import provided.Token;
import java.util.ArrayList;

public class BodyStmt implements JottTree {
    ArrayList<JottTree> children;

    public BodyStmt(ArrayList<Token> tokens)  throws ConstructionFailure{ // <if_stmt> | <while_loop> | <stmt>
        if (tokens.get(0).getToken() == "if")
                children.add(new IfStmt(tokens));
            else if (tokens.get(0).getToken() == "While")
                children.add(new WhileLoop(tokens));
            else
                children.add(new Stmt(tokens));
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