package ranger.syntax.token;

/**
 * Class representing a number token.
 */
public class NumberToken implements Token {
    /**
     * The value of the number.
     */
    private final double value;

    /**
     * Constructs a new number token.
     * 
     * @param value The value of the number.
     */
    public NumberToken(double value) {
        this.value = value;
    }

    /**
     * Returns the value of the number.
     * 
     * @return The value of the number.
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns the string representation of this number token.
     * 
     * @return The string representation of this number token.
     */
    @Override
    public String toString() {
        return Double.toString(value);
    }
}
