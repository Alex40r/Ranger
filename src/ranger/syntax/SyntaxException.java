package ranger.syntax;

/**
 * Class representing a syntax exception.
 */
public class SyntaxException extends RuntimeException {
    /**
     * Constructs a new syntax exception.
     * 
     * @param message The message.
     */
    public SyntaxException(String message) {
        super(message);
    }
}
