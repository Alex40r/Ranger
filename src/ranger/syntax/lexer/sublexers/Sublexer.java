package ranger.syntax.lexer.sublexers;

import java.util.Queue;

import ranger.syntax.token.Token;

/**
 * Interface reprensenting a sublexer.
 */
public interface Sublexer {
    /**
     * Tries to find a token in the input.
     * 
     * @param input  The input to parse.
     * @param start  The index to start parsing at.
     * @param output The output queue to add the token to.
     * @return The number of characters consumed, or 0 if no token was found.
     */
    public int tryFindToken(String input, int start, Queue<Token> output);
}
