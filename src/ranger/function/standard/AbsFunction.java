package ranger.function.standard;

import ranger.function.Function;

/**
 * Class representing the absolute value function.
 */
public class AbsFunction extends Function {
    /**
     * Constructs a new absolute value function.
     */
    public AbsFunction() {
        super("abs");
    }

    /**
     * Evaluates the function.
     * 
     * @param args The arguments to the function.
     * @return The result of the evaluation.
     * @throws IllegalArgumentException If the number of arguments is not one.
     */
    @Override
    public double evaluate(double[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("Function 'abs(x)' takes exactly one argument.");

        return Math.abs(args[0]);
    }
}
