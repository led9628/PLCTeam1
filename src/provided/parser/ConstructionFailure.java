package provided.parser;

public class ConstructionFailure extends Exception {
    public String message;
    public ConstructionFailure(String message) {
        this.message = message;
    }
}
