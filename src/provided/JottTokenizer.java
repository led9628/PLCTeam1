package provided;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class is responsible for tokenizing Jott code.
 **/
public class JottTokenizer {
    /**
     * To be thrown when an error occurs during Tokenization.
     */
    private static class TokenizationException extends Exception {
        public TokenizationException(int line, int token) {
            super();
            this.line = line;
            this.token = (char)token;
            if (token == -1) { this.end = true; }
        }
        public int line; /** The line that the token in question is on. */
        public char token; /** The token causing the problem. */
        public boolean end = false; /** Determines if we're at the EOF. */
    };

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
            int line = 1;
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
                    tokens.add(new Token("}", filename, line, TokenType.R_BRACE));
                } else if (i == '{') {
                    tokens.add(new Token("{", filename, line, TokenType.L_BRACE));
                } else if (i == '=') {
                    i = input.read();
                    if (i == '=') {
                        tokens.add(new Token("==", filename, line, TokenType.REL_OP));
                    } else {
                        tokens.add(new Token("=", filename, line, TokenType.ASSIGN));
                    }
                } else if (i == '>' || i == '<') {
                    int j = i;
                    i = input.read();
                    if (i == '=') {
                        tokens.add(new Token((char)j+"=", filename, line, TokenType.REL_OP));
                    } else {
                        tokens.add(new Token((char)j+"", filename, line, TokenType.REL_OP));
                    }
                } else if (i == '+' || i == '-' || i == '*' || i == '/') {
                    tokens.add(new Token(((char)i)+"", filename, line, TokenType.MATH_OP));
                } else if (i == ';') {
                    tokens.add(new Token(";", filename, line, TokenType.SEMICOLON));
                } else if (i == ':') {
                    tokens.add(new Token(":", filename, line, TokenType.COLON));
                } else if (i == '"') {
                    StringBuilder s = new StringBuilder();
                    s.append((char)i);
                    while (true) {
                        i = input.read();
                        if (i == '"') { break; }
                        if (i == '\n') { throw new TokenizationException(line, i); }
                        if (Character.isWhitespace(i) || Character.isDigit(i) || Character.isAlphabetic(i)) {
                            s.append((char)i);
                        } else {
                            throw new TokenizationException(line, i);
                        }
                    }
                    s.append("\"");
                    tokens.add(new Token(s.toString(), filename, line, TokenType.STRING));
                } else if (Character.isDigit(i) || i == '.') {
                    StringBuilder s = new StringBuilder();
                    s.append((char)i);
                    // Check if there's only one . 
                    if(s.indexOf(".") == 0) {
                       i = input.read();
                       if(!Character.isDigit(i)){
                        throw new TokenizationException(line, i);
                       }
                    } else {
                        i = input.read();
                    }
                    while (true) {
                        
                        if (Character.isDigit(i) || (i == '.' && (s.indexOf(".") == -1))) {
                            s.append((char)i);
                        }
                        else if (i == '.' && (s.indexOf(".") != -1))
                        {
                            throw new TokenizationException(line, i);
                        }
                        else {
                            break;
                        }
                        i = input.read();
                    }
                    tokens.add(new Token(s.toString(), filename, line, TokenType.NUMBER));
                    continue;
                } else if (Character.isAlphabetic(i)) {
                    StringBuilder s = new StringBuilder((char)i+"");
                    while (true) {
                        i = input.read();
                        if (Character.isAlphabetic(i) || Character.isDigit(i)) {
                            s.append((char)i);
                        } else {
                            break;
                        }
                    }
                    tokens.add(new Token(s.toString(), filename, line, TokenType.ID_KEYWORD));
                    continue;
                } else if (i == '!')  {
                    i = input.read();
                    if (i == '='){
                        tokens.add(new Token("!=", filename, line, TokenType.REL_OP));
                    }
                    else {
                        throw new TokenizationException(line, i);
                    }
                }
                
                // Get the next character.
                i = input.read();
            }

        } catch (FileNotFoundException e) {
            // user was in a silly goofy mood and input a file that can't be found
            System.err.println("ERROR: file "+filename+" not found");
            e.printStackTrace();
        } catch (IOException e) {
            // Handle an IO error.
            System.err.println("ERROR: IO exception");
            e.printStackTrace();
        } catch (TokenizationException e) {
            System.err.println("Syntax Error");
            if (e.end) {
                System.err.println("Invalid Token EOF");
            } else if (e.token == '\n') {
                System.err.println("Invalid Token NEWLINE");
            } else {
                System.err.println("Invalid Token \"" + e.token + "\"");
            }
            System.err.println(filename + ":" + e.line);
            // Return null because there was an error.
            return null;
        }
        // Return the list of tokens.
        return tokens;
	}
}