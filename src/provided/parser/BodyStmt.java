package provided.parser;

import provided.JottTree;
import provided.Token;
import java.util.ArrayList;

public interface BodyStmt extends JottTree {
    
    static BodyStmt parse_body_stmt(ArrayList<Token> tokens) {
        if (tokens.get(0).getToken().equals("if"))
            return IfStmt.parse_if(tokens);
        else if (tokens.get(0).getToken().equals("While"))
            return WhileLoop.parse_while(tokens);
        else
            return Stmt.parse_stmt(tokens);
    }
}