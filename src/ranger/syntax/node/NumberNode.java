package ranger.syntax.node;

import ranger.syntax.EvaluationContext;
import ranger.syntax.token.NumberToken;

/**
 * Class representing a number node.
 */
public class NumberNode extends SyntaxNode {
    /**
     * The value of the number.
     */
    private final double value;

    /**
     * Constructs a new number node.
     * 
     * @param value The value of the number.
     */
    public NumberNode(double value) {
        super(true);
        this.value = value;
    }

    /**
     * Constructs a new number node.
     * 
     * @param token The token representing the number.
     */
    public NumberNode(NumberToken token) {
        this(token.getValue());
    }

    /**
     * Returns the value of the number.
     * 
     * @return The value of the number.
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns the string representation of this number node.
     * 
     * @return The string representation of this number node.
     */
    @Override
    public String toString() {
        return Double.toString(value);
    }

    /**
     * Evaluates the number node.
     * 
     * @param context The evaluation context.
     * @return The result of the evaluation.
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return value;
    }
}
