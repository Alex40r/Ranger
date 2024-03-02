package ranger.function.standard;

import ranger.function.Function;

/**
 * Class representing the sum function.
 */
public class SumFunction extends Function {
    /**
     * Constructs a new sum function.
     */
    public SumFunction() {
        super("sum");
    }

    /**
     * Evaluates the function.
     * 
     * @param args The arguments to the function.
     * @return The result of the evaluation.
     * @throws IllegalArgumentException If the number of arguments is not at least
     *                                  one.
     */
    @Override
    public double evaluate(double[] args) {
        if (args.length == 0)
            throw new IllegalArgumentException("Function 'sum(x,...)' takes at least one argument.");

        double sum = 0;

        for (double arg : args)
            sum += arg;

        return sum;
    }
}
