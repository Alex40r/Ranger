package ranger;

import java.util.Queue;

import ranger.syntax.Delimiter;
import ranger.syntax.token.DelimiterToken;
import ranger.syntax.token.Token;

/**
 * Class containing utility methods.
 */
public class Utils {
    private Utils() {
    }

    /**
     * Returns the next character in the input string, skipping whitespaces.
     * 
     * @param input The input string.
     * @param start The index of the character to start at.
     * @return The next character in the input string, or '\0' if there are no more
     *         characters.
     */
    public static char getNextChar(String input, int start) {
        int length = skipWhitespaces(input, start);

        if (start + length >= input.length())
            return '\0';

        return input.charAt(start + length);
    }

    /**
     * Returns the index of the next character in the input string, skipping
     * whitespaces.
     * 
     * @param input The input string.
     * @param start The index of the character to start at.
     * @return The index of the next character in the input string, or -1 if there
     *         are no more characters.
     */
    public static int skipWhitespaces(String input, int start) {
        int length = 0;

        while (start + length < input.length()) {
            char character = input.charAt(start + length);

            if (character == ' ' || character == '\t' || character == '\n' || character == '\r')
                length++;
            else
                break;
        }

        return length;
    }

    /**
     * Returns whether the next token in the queue is a specific delimiter.
     * 
     * @param tokens    The queue of tokens.
     * @param delimiter The delimiter to check for.
     * @return Whether the next token in the queue is a specific delimiter.
     */
    public static boolean isNextDelimiter(Queue<Token> tokens, Delimiter delimiter) {
        if (tokens.isEmpty())
            return false;

        Token token = tokens.peek();
        if (!(token instanceof DelimiterToken))
            return false;

        return ((DelimiterToken) token).getDelimiter() == delimiter;
    }

    /**
     * Calculates the factorial of a value.
     * 
     * @param value The value.
     * @return The factorial of the value.
     */
    public static double factorial(double value) {
        double result = 1;

        for (int i = 2; i <= value; i++)
            result *= i;

        return result;
    }
}
