package ranger.syntax.token;

/**
 * Class representing a function token.
 */
public class FunctionToken implements Token {
    /**
     * The name of the function.
     */
    private final String name;

    /**
     * Constructs a new function token.
     * 
     * @param name The name of the function.
     */
    public FunctionToken(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the function.
     * 
     * @return The name of the function.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the string representation of this function token.
     * 
     * @return The string representation of this function token.
     */
    @Override
    public String toString() {
        return name;
    }
}
