package ranger.syntax.lexer.sublexers;

import java.util.Queue;

import ranger.syntax.Delimiter;
import ranger.syntax.token.DelimiterToken;
import ranger.syntax.token.Token;

/**
 * Class reprensenting a delimiter sublexer.
 */
public class DelimiterSublexer implements Sublexer {

    /**
     * Constructs a new delimiter sublexer.
     */
    public DelimiterSublexer() {
    }

    /**
     * Tries to find a delimiter token in the input.
     * 
     * @param input  The input to parse.
     * @param start  The index to start parsing at.
     * @param output The output queue to add the token to.
     * @return The number of characters consumed, or 0 if no token was found.
     */
    @Override
    public int tryFindToken(String input, int start, Queue<Token> output) {
        for (int i = Math.min(Delimiter.MAXIMUM_LENGTH, input.length() - start); i > 0; i--) {
            Delimiter delimiter = Delimiter.getDelimiter(input.substring(start, start + i));

            if (delimiter != null) {
                output.add(new DelimiterToken(delimiter));
                return i;
            }
        }

        return 0;
    }
}
