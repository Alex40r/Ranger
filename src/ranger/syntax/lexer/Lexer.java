package ranger.syntax.lexer;

import java.util.ArrayDeque;
import java.util.Queue;

import ranger.Utils;
import ranger.syntax.lexer.sublexers.*;
import ranger.syntax.token.Token;

/**
 * Class reprensenting a lexer.
 */
public class Lexer {

    /**
     * Constructs a new lexer.
     */
    public Lexer() {
    }

    /**
     * The sublexers to use to find tokens.
     */
    private static final Sublexer[] SUBLEXERS = new Sublexer[] {
            new NumberSublexer(),
            new OperatorSublexer(),
            new DelimiterSublexer(),
            new FunctionSublexer(),
            new ReferenceSublexer(),
    };

    /**
     * Returns the tokens in the given expression.
     * 
     * @param expression The expression to get the tokens in.
     * @return The tokens in the given expression.
     */
    public static Queue<Token> getTokens(String expression) {
        Queue<Token> tokens = new ArrayDeque<Token>();

        int consumed = 0;

        for (int offset = 0; offset < expression.length(); offset += consumed) {
            consumed = Utils.skipWhitespaces(expression, offset);
            if (consumed > 0)
                continue;

            for (Sublexer sublexer : SUBLEXERS)
                if ((consumed = sublexer.tryFindToken(expression, offset, tokens)) > 0)
                    break;

            if (consumed == 0)
                throw new IllegalArgumentException(
                        "Unexpected character '" + expression.charAt(offset) + "' at index " + offset);
        }

        return tokens;
    }
}
