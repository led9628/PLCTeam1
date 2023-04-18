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

   public String toString() {
        return this.lit + " ";
   }

   @Override
   public String convertToJott() {
       return this.toString();
   }

   @Override
   public String convertToJava(String className) {
       // TODO Auto-generated method stub
       return null;
   }

   @Override
   public String convertToC() {
        return this.toString();
   }

   @Override
   public String convertToPython() {
        return this.toString();
   }

   @Override
   public boolean validateTree() {
       return true;
   }

}

