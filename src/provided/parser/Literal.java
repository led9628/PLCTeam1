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
        if (this.lit.equals("switch")) {
            return "_switch";
        }
       return this.toString();
    
   }

   @Override
   public String convertToC() {
        if (this.lit.equals("True")) {
            return "true";
        }
        if (this.lit.equals("False")) {
            return "false";
        }
        if (this.lit.equals("switch")) {
            return "_switch";
        }
        return this.toString();
   }

   @Override
   public String convertToPython() {
        if (this.lit.equals("switch")) {
            return "_switch";
        }
        return this.toString();
   }

   @Override
   public boolean validateTree() {
       return true;
   }

}

