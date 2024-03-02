package ranger.operator;

/**
 * Enum representing the precedence of an operator. Operators with a higher
 * precedence are evaluated before operators with a lower precedence.
 */
public enum OperatorPrecedence {
    /**
     * The exponential precedence. Eg. 2^3 = 8.
     * Higher precedence than MULTIPLICATIVE.
     */
    EXPONENTIAL,

    /**
     * The multiplicative precedence. Eg. 2*3 = 6.
     * Higher precedence than ADDITIVE.
     */
    MULTIPLICATIVE,

    /**
     * The additive precedence. Eg. 2+3 = 5.
     * Higher precedence than RELATIONAL.
     */
    ADDITIVE,

    /**
     * The relational precedence. Eg. 2&lt;3 = true (1).
     */
    RELATIONAL,
}
