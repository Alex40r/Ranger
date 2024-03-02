package ranger.syntax.lexer.sublexers;

import java.util.Queue;

import ranger.Utils;
import ranger.function.Function;
import ranger.syntax.token.FunctionToken;
import ranger.syntax.token.Token;

/**
 * Class reprensenting a function sublexer.
 */
public class FunctionSublexer implements Sublexer {

    /**
     * Constructs a new function sublexer.
     */
    public FunctionSublexer() {
    }

    /**
     * Tries to find a function token in the input.
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
            if (Function.isSupportedCharacter(input.charAt(start + length)))
                length++;
            else
                break;

        if (length == 0)
            return 0;

        if (Utils.getNextChar(input, start + length) != '(')
            return 0;

        output.add(new FunctionToken(input.substring(start, start + length)));
        return length;
    }
}
