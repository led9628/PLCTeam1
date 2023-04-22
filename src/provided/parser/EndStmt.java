package provided.parser;

import java.util.ArrayList;

import provided.JottTree;
import provided.Token;
import provided.TokenType;


public class EndStmt implements JottTree{
    ArrayList<JottTree> children = new ArrayList<>();

    public EndStmt(ArrayList<Token> tokens, int depth) throws ConstructionFailure{
        //System.out.println(tokens.get(0).getToken());
        Token token = tokens.remove(0);
        if (token.getTokenType() == TokenType.SEMICOLON){
            children.add(new Literal(token.getToken()));
        }else{
            throw new ConstructionFailure("Invalid End statement", token.getLineNum());
        }
    }

   @Override
   public String convertToJott() {
       return ";\n";
   }

   @Override
   public String convertToJava(String className) {
       return ";\n";
   }

   @Override
   public String convertToC() {
        return ";\n";
   }

   @Override
   public String convertToPython() {
       // TODO Auto-generated method stub
       return "\n";
   }

   @Override
   public boolean validateTree() {
       return !this.children.isEmpty();
   }

}

