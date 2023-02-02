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
            int i = input.read();
            int line = 0;
            while (i != -1) {
                if (i == '\n') {
                    line += 1;
                } else if (Character.isWhitespace(i)) {
                    // pass
                } else if (i == '#') {
                    while (i != '\n') {
                        i = input.read();
                    }
                    line += 1;
                } else if (i == ',') {
                    tokens.add(new Token(",", filename, line, TokenType.COMMA));
                } else if (i == ']') {
                    tokens.add(new Token("]", filename, line, TokenType.R_BRACKET));
                } else if (i == '[') {
                    tokens.add(new Token("[", filename, line, TokenType.L_BRACKET));
                } else if (i == '}') {
                    tokens.add(new Token("}", filename, line, TokenType.R_BRACKET));
                } else if (i == '{') {
                    tokens.add(new Token("{", filename, line, TokenType.L_BRACKET));
                } else if (i == '=') {
                    i = input.read();
                    if (i == '=') {
                        tokens.add(new Token("==", filename, line, TokenType.REL_OP));
                    } else {
                        tokens.add(new Token("=", filename, line, TokenType.ASSIGN));
                    }
                    continue;
                } else if (i == '>' || i == '<') {
                    int j = i;
                    i = input.read();
                    if (i == '=') {
                        tokens.add(new Token(j+"=", filename, line, TokenType.REL_OP));
                    } else {
                        tokens.add(new Token(j+"", filename, line, TokenType.REL_OP));
                    }
                    continue;
                } else if (i == '+' || i == '-' || i == '*' || i == '/') {
                    tokens.add(new Token(((char)i)+"", filename, line, TokenType.MATH_OP));
                } else if (i == ';') {
                    tokens.add(new Token(";", filename, line, TokenType.SEMICOLON));
                } else if (i == ':') {
                    tokens.add(new Token(":", filename, line, TokenType.COLON));
                } else if (i == '"') {
                    StringBuilder s = new StringBuilder("\"");
                    while (true) {
                        i = input.read();
                        if (i == '"') { break; }
                        if (Character.isWhitespace(i) || Character.isDigit(i) || Character.isAlphabetic(i)) {
                            s.append(i);
                        } else {
                            // TODO: Throw because the user just put something invalid in their string.
                        }
                    }
                    s.append("\"");
                    tokens.add(new Token(s.toString(), filename, line, TokenType.STRING));
                }

                // TODO: Add digit.
                // TODO: Add letter.
                // TODO: Add ! etc.
                
                // Get the next character.
                i = input.read();
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