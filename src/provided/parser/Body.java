package provided.parser;
import provided.Token;
import provided.JottTree;

import java.util.ArrayList;

public class Body implements JottTree{
    ArrayList<JottTree> children;

    public Body(ArrayList<Token> tokens) throws ConstructionFailure{ // <body_stmt><body> | <return_stmt> | *Nothing*
        if (tokens.size() != 0 && tokens.get(0).getToken() != "return"){
            if (tokens.get(0).getToken() == "if")
                children.add(new IfStmt(tokens));
            else if (tokens.get(0).getToken() == "While")
                children.add(new WhileLoop(tokens));
            else
                children.add(new Stmt(tokens));

            //children.add(new Body());

        } else if(tokens.get(0).getToken() == "return"){ 
            tokens.remove(0);
            children.add(new ReturnStmt(tokens));
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
