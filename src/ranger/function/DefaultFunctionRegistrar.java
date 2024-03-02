package ranger.function;

import ranger.function.standard.*;

/**
 * Class representing a function registrar, with the standard mathematical
 * functions registered.
 */
public class DefaultFunctionRegistrar extends FunctionRegistrar {

    /**
     * Constructs a new function registrar, and registers the standard
     * mathematical functions.
     */
    public DefaultFunctionRegistrar() {
        super(null, new Function[] {
                new AbsFunction(),
                new SqrtFunction(),
                new SumFunction(),
                new NowFunction(),
                new PowFunction(),
                new CosFunction(),
        });
    }
}
