package ranger.syntax.lexer.sublexers;

import java.util.Queue;

import ranger.syntax.token.NumberToken;
import ranger.syntax.token.Token;

/**
 * Class reprensenting a number sublexer.
 */
public class NumberSublexer implements Sublexer {

    /**
     * Constructs a new number sublexer.
     */
    public NumberSublexer() {
    }

    /**
     * Tries to find a number token in the input.
     * 
     * @param input  The input to parse.
     * @param start  The index to start parsing at.
     * @param output The output queue to add the token to.
     * @return The number of characters consumed, or 0 if no token was found.
     */
    @Override
    public int tryFindToken(String input, int start, Queue<Token> output) {
        int length = 0;

        while (start + length < input.length())
            if (Character.isDigit(input.charAt(start + length)) || input.charAt(start + length) == '.')
                length++;
            else
                break;

        if (length == 0)
            return 0;

        try {
            output.add(new NumberToken(Double.parseDouble(input.substring(start, start + length))));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Number '" + input.substring(start, start + length) + "' is not valid.");
        }
        return length;
    }
}
