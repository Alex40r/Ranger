package ranger.function;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a function registrar.
 */
public class FunctionRegistrar {
    /**
     * The base function registrar. Functions are first looked up in this
     * registrar, then in the base registrar if they are not found.
     */
    private FunctionRegistrar base;

    /**
     * The map of functions, indexed by name.
     */
    private Map<String, Function> functions;

    /**
     * Constructs a new function registrar.
     * 
     * @param base      The base function registrar. Functions are first looked up
     *                  in this registrar, then in the base registrar if they are
     *                  not found.
     * @param functions The functions to register.
     */
    public FunctionRegistrar(FunctionRegistrar base, Function... functions) {
        this.base = base;

        this.functions = new HashMap<String, Function>();

        for (Function function : functions)
            this.functions.put(function.getName(), function);
    }

    /**
     * Returns whether a function is registered.
     * 
     * @param name The name of the function.
     * @return Whether the function is registered.
     */
    public boolean isRegistered(String name) {
        if (functions.containsKey(name))
            return true;

        if (base != null)
            return base.isRegistered(name);

        return false;
    }

    /**
     * Returns a function by name.
     * 
     * @param name The name of the function.
     * @return The function, or null if no function with the specified name is
     *         registered.
     */
    public Function get(String name) {
        if (functions.containsKey(name))
            return functions.get(name);

        if (base != null)
            return base.get(name);

        return null;
    }
}
