package ranger.sheet.cell;

/**
 * Enum representing a cell error.
 */
public enum CellError {
    /**
     * Indicates that the cell has an invalid expression, that failed to parse.
     */
    INVALID_EXPRESSION("#EXPR"),

    /**
     * Indicates that the cell has a dependency that has not been evaluated.
     */
    DEPENDENCY_NOT_EVALUATED("#DEP"),

    /**
     * Indicates that the cell failed to evaluate.
     */
    FAILED_TO_EVALUATE("#EVAL"),

    /**
     * Indicates that the cell has an arithmetic error.
     */
    ARITHMETIC_ERROR("#ARITH"),

    /**
     * Indicates that the cell has an argument error.
     */
    ARGUMENT_ERROR("#ARG"),

    /**
     * Indicates that the cell has a dependency cycle.
     */
    DEPENDENCY_CYCLE("#CYCLE"),

    ;

    /**
     * The text of the cell error.
     */
    private String text;

    /**
     * Constructs a new cell error.
     * 
     * @param text The text of the cell error.
     */
    private CellError(String text) {
        this.text = text;
    }

    /**
     * Returns the text of the cell error.
     * 
     * @return The text of the cell error.
     */
    @Override
    public String toString() {
        return text;
    }
}
