package provided;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/
public class JottTokenizer {
	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){
        // Default initialize a list to store tokens in.
	    ArrayList<Token> tokens = new ArrayList<>();

        // Attempt to open the file.
        try (BufferedReader input = new BufferedReader(
                new InputStreamReader( new FileInputStream(filename) )
            )
        ) {
            // Read the file character by character.
            int i;
            int line = 0;
            while ((i = input.read()) == -1) {
                if (Character.isWhitespace(i)) {
                    continue;
                } else if (i == '#') {
                    while (i != '\n') {
                        input.read();
                    }
                    continue;
                } else if (i == ',') {
                    tokens.add(new Token(",", filename, line, TokenType.COMMA));
                    continue;
                } else if (i == ']') {
                    tokens.add(new Token("]", filename, line, TokenType.R_BRACKET));
                    continue;
                } else if (i == '[') {
                    tokens.add(new Token("[", filename, line, TokenType.L_BRACKET));
                    continue;
                } else if (i == '}') {
                    tokens.add(new Token("}", filename, line, TokenType.R_BRACKET));
                    continue;
                } else if (i == '{') {
                    tokens.add(new Token("{", filename, line, TokenType.L_BRACKET));
                    continue;
                }  
                
            }

        } catch (FileNotFoundException e) {
            // TODO Handle failing to open the file.
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Handle an IO error.
            e.printStackTrace();
        }
        // Return the list of tokens.
        return tokens;
	}
}