package ranger.syntax.token;

import ranger.syntax.Delimiter;

/**
 * Class representing a delimiter token.
 */
public class DelimiterToken implements Token {
    /**
     * The delimiter.
     */
    private final Delimiter delimiter;

    /**
     * Constructs a new delimiter token.
     * 
     * @param delimiter The delimiter.
     */
    public DelimiterToken(Delimiter delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Returns the delimiter.
     * 
     * @return The delimiter.
     */
    public Delimiter getDelimiter() {
        return delimiter;
    }

    /**
     * Returns the string representation of this delimiter token.
     * 
     * @return The string representation of this delimiter token.
     */
    @Override
    public String toString() {
        return delimiter.getSymbol();
    }
}
