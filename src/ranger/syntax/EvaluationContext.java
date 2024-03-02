package ranger.syntax;

import ranger.function.Function;
import ranger.sheet.cell.CellCoordinates;

/**
 * Interface representing an evaluation context.
 */
public interface EvaluationContext {
    /**
     * Returns the function with the specified name.
     * 
     * @param name The name.
     * @return The function.
     */
    public Function getFunction(String name);

    /**
     * Returns the value of the cell with the specified coordinates.
     * 
     * @param coordinates The coordinates.
     * @return The value.
     */
    public double getValue(CellCoordinates coordinates);
}
