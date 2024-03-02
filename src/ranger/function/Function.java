package ranger.function;

/**
 * Abstract class representing a function. Functions have a name and can be
 * evaluated with a given set of arguments.
 */
public abstract class Function {
    /**
     * The name of the function.
     */
    private String name;

    /**
     * Constructs a new function with the specified name.
     * 
     * @param name The name of the function.
     */
    public Function(String name) {
        this.name = name;

        for (int i = 0; i < name.length(); i++)
            if (!isSupportedCharacter(name.charAt(i)))
                throw new IllegalArgumentException("Unsupported character in function name: " + name.charAt(i));
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
     * Evaluates the function with the specified arguments. This can throw all
     * kinds of exceptions, depending on the function.
     * 
     * @param args The arguments.
     * @return The result of the evaluation.
     */
    public abstract double evaluate(double[] args);

    /**
     * Returns whether a character is supported in a function name.
     * 
     * @param c The character.
     * @return Whether the character is supported in a function name.
     */
    public static boolean isSupportedCharacter(char c) {
        return Character.isUpperCase(c) || Character.isLowerCase(c) || Character.isDigit(c) || c == '_' || c == '-';
    }
}
