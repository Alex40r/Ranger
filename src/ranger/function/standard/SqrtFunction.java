package ranger.function.standard;

import ranger.function.Function;

/**
 * Class representing the square root function.
 */
public class SqrtFunction extends Function {
    /**
     * Constructs a new square root function.
     */
    public SqrtFunction() {
        super("sqrt");
    }

    /**
     * Evaluates the function.
     * 
     * @param args The arguments to the function.
     * @return The result of the evaluation.
     * @throws IllegalArgumentException If the number of arguments is not one, or
     *                                  if the argument is negative.
     */
    @Override
    public double evaluate(double[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("Function 'sqrt(x)' takes exactly one argument.");

        if (args[0] < 0)
            throw new IllegalArgumentException("Function 'sqrt(x)' takes only positive arguments.");

        return Math.sqrt(args[0]);
    }
}
