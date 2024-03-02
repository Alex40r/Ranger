package ranger.syntax.lexer.sublexers;

import java.util.Queue;

import ranger.operator.Operator;
import ranger.syntax.token.OperatorToken;
import ranger.syntax.token.Token;

/**
 * Class reprensenting an operator sublexer.
 */
public class OperatorSublexer implements Sublexer {

    /**
     * Constructs a new operator sublexer.
     */
    public OperatorSublexer() {
    }

    /**
     * Tries to find an operator token in the input.
     * 
     * @param input  The input to parse.
     * @param start  The index to start parsing at.
     * @param output The output queue to add the token to.
     * @return The number of characters consumed, or 0 if no token was found.
     */
    @Override
    public int tryFindToken(String input, int start, Queue<Token> output) {
        for (int i = Math.min(Operator.MAXIMUM_LENGTH, input.length() - start); i > 0; i--) {
            Operator operator = Operator.getOperator(input.substring(start, start + i));

            if (operator != null) {
                output.add(new OperatorToken(operator));
                return i;
            }
        }

        return 0;
    }
}
