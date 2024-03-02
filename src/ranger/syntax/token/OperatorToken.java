package ranger.syntax.token;

import ranger.operator.Operator;

/**
 * Class representing an operator token.
 */
public class OperatorToken implements Token {
    /**
     * The operator.
     */
    private final Operator operator;

    /**
     * Constructs a new operator token.
     * 
     * @param operator The operator.
     */
    public OperatorToken(Operator operator) {
        this.operator = operator;
    }

    /**
     * Returns the operator.
     * 
     * @return The operator.
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Returns the string representation of this operator token.
     * 
     * @return The string representation of this operator token.
     */
    @Override
    public String toString() {
        return operator.getSymbol();
    }
}
