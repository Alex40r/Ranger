package ranger.function.standard;

import ranger.function.Function;

/**
 * Class representing the pow function.
 */
public class PowFunction extends Function {
    /**
     * Constructs a new pow function.
     */
    public PowFunction() {
        super("pow");
    }

    /**
     * Evaluates the function.
     * 
     * @param args The arguments to the function.
     * @return The result of the evaluation.
     * @throws IllegalArgumentException If the number of arguments is not two.
     */
    @Override
    public double evaluate(double[] args) {
        if (args.length != 2)
            throw new IllegalArgumentException("Function 'pow(x,y)' takes exactly two arguments.");

        return Math.pow(args[0], args[1]);
    }
}
