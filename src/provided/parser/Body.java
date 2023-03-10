package provided.parser;
import provided.Token;
import provided.JottTree;

import java.util.ArrayList;

public class Body implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>();

    public Body(ArrayList<Token> tokens) throws ConstructionFailure{ // <body_stmt><body> | <return_stmt> | *Nothing*
        if (tokens.size() != 0){
            var token = tokens.get(0);
            if (token.getToken() != "return"){
                children.add(new BodyStmt(tokens));
                children.add(new Body(tokens));
            }else if(token.getToken() == "return"){ 
                children.add(new ReturnStmt(tokens));
            }else{
                throw new ConstructionFailure("Invalid Body.", token.getLineNum());
            }
        }
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
