package provided.parser;

import provided.JottTree;

/*
* Class used to store literals like '$$' and 'epsilon' in the parse tree.
*/
public class Literal implements JottTree{
   String lit;

   public Literal(String lit){
       this.lit = lit;
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
