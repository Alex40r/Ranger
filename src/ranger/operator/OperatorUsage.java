package ranger.operator;

/**
 * Enum representing the usage of an operator.
 */
public enum OperatorUsage {
    /**
     * The operator is a left unary operator. Eg. -3.
     */
    LEFT_UNARY,

    /**
     * The operator is a right unary operator. Eg. 3!
     */
    RIGHT_UNARY,

    /**
     * The operator is a binary operator. Eg. 2+3.
     */
    BINARY,
}
