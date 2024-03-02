package ranger.operator;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Enum representing a mathematical operator.
 */
public enum Operator {
    /**
     * The primary power operator. Eg. 2^3 = 8.
     */
    POWER_PRIMARY("^", OperatorPrecedence.EXPONENTIAL, OperatorUsage.BINARY),

    /**
     * The secondary power operator. Eg. 2**3 = 8.
     */
    POWER_SECONDARY("**", OperatorPrecedence.EXPONENTIAL, OperatorUsage.BINARY),

    /**
     * The factorial operator. Eg. 3! = 6.
     */
    FACTORIAL("!", OperatorPrecedence.EXPONENTIAL, OperatorUsage.RIGHT_UNARY),

    /**
     * The multiplication operator. Eg. 2*3 = 6.
     */
    MULTIPLICATION("*", OperatorPrecedence.MULTIPLICATIVE, OperatorUsage.BINARY),

    /**
     * The division operator. Eg. 6/3 = 2.
     */
    DIVISION("/", OperatorPrecedence.MULTIPLICATIVE, OperatorUsage.BINARY),

    /**
     * The modulo operator. Eg. 6%4 = 2.
     */
    MODULO("%", OperatorPrecedence.MULTIPLICATIVE, OperatorUsage.BINARY),

    /**
     * The addition operator. Eg. 2+3 = 5.
     */
    ADDITION("+", OperatorPrecedence.ADDITIVE, OperatorUsage.LEFT_UNARY, OperatorUsage.BINARY),

    /**
     * The subtraction operator. Eg. 5-3 = 2.
     */
    SUBTRACTION("-", OperatorPrecedence.ADDITIVE, OperatorUsage.LEFT_UNARY, OperatorUsage.BINARY),

    /**
     * The less than operator. Eg. 2&lt;3 = true (1).
     */
    LESS_THAN("<", OperatorPrecedence.RELATIONAL, OperatorUsage.BINARY),

    /**
     * The less than or equal to operator. Eg. 2&lt;=3 = true (1).
     */
    LESS_THAN_OR_EQUAL_TO("<=", OperatorPrecedence.RELATIONAL, OperatorUsage.BINARY),

    /**
     * The greater than operator. Eg. 2&gt;3 = false (0).
     */
    GREATER_THAN(">", OperatorPrecedence.RELATIONAL, OperatorUsage.BINARY),

    /**
     * The greater than or equal to operator. Eg. 2&gt;=3 = false (0).
     */
    GREATER_THAN_OR_EQUAL_TO(">=", OperatorPrecedence.RELATIONAL, OperatorUsage.BINARY),

    /**
     * The equal to operator. Eg. 2==3 = false (0).
     */
    EQUAL_TO("==", OperatorPrecedence.RELATIONAL, OperatorUsage.BINARY),

    /**
     * The not equal to operator. Eg. 2!=3 = true (1).
     */
    NOT_EQUAL_TO("!=", OperatorPrecedence.RELATIONAL, OperatorUsage.BINARY),

    /**
     * The logical and operator. Eg. true &amp;&amp; false = false (0).
     */
    LOGICAL_AND("&&", OperatorPrecedence.RELATIONAL, OperatorUsage.BINARY),

    /**
     * The logical or operator. Eg. true || false = true (1).
     */
    LOGICAL_OR("||", OperatorPrecedence.RELATIONAL, OperatorUsage.BINARY);

    /**
     * The set of characters that are used by operators symbols.
     */
    public static final Set<Character> CHARACTERS = getCharacters();

    /**
     * The maximum length of an operator symbol.
     */
    public static final int MAXIMUM_LENGTH = getMaximumLength();

    /**
     * The symbol of the operator.
     */
    private final String symbol;

    /**
     * The precedence of the operator.
     */
    private final OperatorPrecedence precedence;

    /**
     * The set of usages of the operator.
     */
    private final EnumSet<OperatorUsage> usage;

    /**
     * Constructs a new operator with the specified symbol, precedence and usages.
     * 
     * @param symbol     The symbol of the operator.
     * @param precedence The precedence of the operator.
     * @param usages     The usages of the operator.
     */
    private Operator(String symbol, OperatorPrecedence precedence, OperatorUsage... usages) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.usage = EnumSet.of(usages[0], usages);
    }

    /**
     * Returns the symbol of the operator.
     * 
     * @return The symbol of the operator.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the precedence of the operator.
     * 
     * @return The precedence of the operator.
     */
    public OperatorPrecedence getPrecedence() {
        return precedence;
    }

    /**
     * Returns whether the operator supports the specified usage.
     * 
     * @param usage The usage.
     * @return Whether the operator supports the specified usage.
     */
    public boolean supportsUsage(OperatorUsage usage) {
        return this.usage.contains(usage);
    }

    /**
     * Returns the operator with the specified symbol. This requires an exact match.
     * 
     * @param symbol The symbol.
     * @return The operator with the specified symbol, or null if no operator with
     *         the specified symbol exists.
     */
    public static Operator getOperator(String symbol) {
        for (Operator operator : Operator.values())
            if (operator.getSymbol().equals(symbol))
                return operator;

        return null;
    }

    /**
     * Returns the set of characters that are used by operators symbols. The result
     * should already be present in the CHARACTERS constant.
     * 
     * @return The set of characters that are used by operators symbols.
     */
    private static Set<Character> getCharacters() {
        Set<Character> characters = new HashSet<Character>();

        for (Operator operator : Operator.values())
            for (char character : operator.getSymbol().toCharArray())
                characters.add(character);

        return characters;
    }

    /**
     * Returns the maximum length of an operator symbol. The result should already
     * be present in the MAXIMUM_LENGTH constant.
     * 
     * @return The maximum length of an operator symbol.
     */
    private static int getMaximumLength() {
        int maximumLength = 0;

        for (Operator operator : Operator.values())
            if (operator.getSymbol().length() > maximumLength)
                maximumLength = operator.getSymbol().length();

        return maximumLength;
    }

    /**
     * Returns the string representation of the operator.
     * 
     * @return The string representation of the operator.
     */
    @Override
    public String toString() {
        return getSymbol();
    }
}