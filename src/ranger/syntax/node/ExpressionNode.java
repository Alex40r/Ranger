package ranger.syntax.node;

import ranger.syntax.EvaluationContext;

/**
 * Class representing an expression node.
 */
public class ExpressionNode extends SyntaxNode {
    /**
     * Constructs a new expression node.
     */
    public ExpressionNode() {
        super(false);
    }

    /**
     * Returns the string representation of this expression node.
     * 
     * @return The string representation of this expression node.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("(");

        for (int i = 0; i < getChildrenCount(); i++)
            builder.append(getChild(i).toString());

        builder.append(")");

        return builder.toString();
    }

    /**
     * Evaluates the expression node.
     * 
     * @param context The evaluation context.
     * @return The result of the evaluation.
     */
    @Override
    public double evaluate(EvaluationContext context) {
        if (getChildrenCount() != 1)
            throw new IllegalStateException("Expression node must have exactly one child. This is a bug.");

        return getChild(0).evaluate(context);
    }
}
