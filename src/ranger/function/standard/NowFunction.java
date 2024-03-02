package ranger.function.standard;

import ranger.function.Function;

/**
 * Class representing the now function.
 */
public class NowFunction extends Function {
    /**
     * Constructs a new now function.
     */
    public NowFunction() {
        super("now");
    }

    /**
     * Evaluates the function.
     * 
     * @param args The arguments to the function.
     * @return The result of the evaluation.
     * @throws IllegalArgumentException If the number of arguments is not zero.
     */
    @Override
    public double evaluate(double[] args) {
        if (args.length != 0)
            throw new IllegalArgumentException("Function 'now()' takes no arguments.");

        return System.currentTimeMillis() / 1000.0;
    }
}
