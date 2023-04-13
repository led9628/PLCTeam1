package provided.parser;

public class SemanticFailure extends Exception {
    public String message;
    public int line;
    public SemanticFailure(String message, int line) {
        this.message = message;
    }
}
