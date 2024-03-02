package ranger.syntax.node;

import ranger.function.Function;
import ranger.syntax.EvaluationContext;
import ranger.syntax.token.FunctionToken;

/**
 * Class representing a function node.
 */
public class FunctionNode extends SyntaxNode {
    /**
     * The name of the function.
     */
    private String name;

    /**
     * Constructs a new function node.
     * 
     * @param name The name of the function.
     */
    public FunctionNode(String name) {
        super(false);

        this.name = name;
    }

    /**
     * Constructs a new function node.
     * 
     * @param token The token representing the function.
     */
    public FunctionNode(FunctionToken token) {
        this(token.getName());
    }

    /**
     * Returns the name of the function.
     * 
     * @return The name of the function.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the string representation of this function node.
     * 
     * @return The string representation of this function node.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(name);
        builder.append("(");

        for (int i = 0; i < getChildrenCount(); i++) {
            builder.append(getChild(i).toString());
            if (i < getChildrenCount() - 1)
                builder.append(",");
        }

        builder.append(")");

        return builder.toString();
    }

    /**
     * Evaluates the function node.
     * 
     * @param context The evaluation context.
     * @return The result of the evaluation.
     */
    @Override
    public double evaluate(EvaluationContext context) {
        Function function = context.getFunction(name);
        if (function == null)
            throw new IllegalStateException("Function '" + name + "' is not registered.");

        double[] arguments = new double[getChildrenCount()];
        for (int i = 0; i < getChildrenCount(); i++)
            arguments[i] = getChild(i).evaluate(context);

        return function.evaluate(arguments);
    }
}
