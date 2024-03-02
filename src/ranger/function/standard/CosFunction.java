package ranger.function.standard;

import ranger.function.Function;

/**
 * Class representing the cosine function.
 */
public class CosFunction extends Function {
    /**
     * Constructs a new cosine function.
     */
    public CosFunction() {
        super("cos");
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
            throw new IllegalArgumentException("Function 'cos(x)' takes exactly one argument.");

        return Math.cos(args[0]);
    }
}
