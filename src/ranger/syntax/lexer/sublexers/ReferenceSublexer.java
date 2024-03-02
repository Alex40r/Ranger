package ranger.syntax.lexer.sublexers;

import java.util.Queue;

import ranger.sheet.cell.CellCoordinates;
import ranger.syntax.token.ReferenceToken;
import ranger.syntax.token.Token;

/**
 * Class reprensenting a reference sublexer.
 */
public class ReferenceSublexer implements Sublexer {

    /**
     * Constructs a new reference sublexer.
     */
    public ReferenceSublexer() {
    }

    /**
     * Tries to find a reference token in the input.
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
            if (CellCoordinates.isSupportedCharacter(input.charAt(start + length)))
                length++;
            else
                break;

        if (length == 0)
            return 0;

        output.add(new ReferenceToken(new CellCoordinates(input.substring(start, start + length))));
        return length;
    }

}
