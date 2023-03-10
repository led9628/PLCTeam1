package provided.parser;

public class ConstructionFailure extends Exception {
    public String message;
    public int line;
    public ConstructionFailure(String message, int line) {
        this.message = message;
    }
}
