package ranger.syntax;

import java.util.HashSet;
import java.util.Set;

/**
 * Enum representing a delimiter.
 */
public enum Delimiter {
    /**
     * The open parenthesis delimiter.
     * This is used to denote the start of a function, or a sub-expression.
     */
    OPEN("("),

    /**
     * The close parenthesis delimiter.
     * This is used to denote the end of a function, or a sub-expression.
     */
    CLOSE(")"),

    /**
     * The comma delimiter.
     * This is used to separate arguments in a function.
     */
    SEPARATOR(",");

    /**
     * The set of characters that are used as delimiters.
     */
    public static final Set<Character> CHARACTERS = getCharacters();

    /**
     * The maximum length of a delimiter.
     */
    public static final int MAXIMUM_LENGTH = getMaximumLength();

    /**
     * The symbol of the delimiter.
     */
    private final String symbol;

    /**
     * Constructs a new delimiter with the specified symbol.
     * 
     * @param symbol The symbol of the delimiter.
     */
    private Delimiter(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol of the delimiter.
     * 
     * @return The symbol of the delimiter.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the delimiter with the specified symbol.
     * 
     * @param symbol The symbol of the delimiter.
     * @return The delimiter with the specified symbol.
     */
    public static Delimiter getDelimiter(String symbol) {
        for (Delimiter delimiter : Delimiter.values())
            if (delimiter.getSymbol().equals(symbol))
                return delimiter;

        return null;
    }

    /**
     * Returns the set of characters that are used as delimiters. The result
     * should already be present in the CHARACTERS constant.
     * 
     * @return The set of characters that are used as delimiters.
     */
    private static Set<Character> getCharacters() {
        Set<Character> characters = new HashSet<Character>();

        for (Delimiter delimiter : Delimiter.values())
            for (char character : delimiter.getSymbol().toCharArray())
                characters.add(character);

        return characters;
    }

    /**
     * Returns the maximum length of a delimiter. The result should already be
     * present in the MAXIMUM_LENGTH constant.
     * 
     * @return The maximum length of a delimiter.
     */
    private static int getMaximumLength() {
        int maximumLength = 0;

        for (Delimiter delimiter : Delimiter.values())
            if (delimiter.getSymbol().length() > maximumLength)
                maximumLength = delimiter.getSymbol().length();

        return maximumLength;
    }
}
