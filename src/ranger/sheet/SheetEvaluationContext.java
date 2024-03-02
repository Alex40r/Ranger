package ranger.sheet;

import ranger.function.Function;
import ranger.function.FunctionRegistrar;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellStorage;
import ranger.syntax.EvaluationContext;

/**
 * Class representing a sheet evaluation context.
 */
public class SheetEvaluationContext implements EvaluationContext {
    /**
     * The function registrar.
     */
    private FunctionRegistrar functionRegistrar;
    /**
     * The cell storage.
     */
    private CellStorage cellStorage;

    /**
     * Constructs a new sheet evaluation context.
     * 
     * @param functionRegistrar The function registrar.
     * @param cellStorage       The cell storage.
     */
    public SheetEvaluationContext(FunctionRegistrar functionRegistrar, CellStorage cellStorage) {
        this.functionRegistrar = functionRegistrar;
        this.cellStorage = cellStorage;
    }

    /**
     * Returns the function with the given name.
     * 
     * @param name The name of the function.
     * @return The function with the given name.
     */
    @Override
    public Function getFunction(String name) {
        return functionRegistrar.get(name);
    }

    /**
     * Returns the value of the cell at the given coordinates.
     * 
     * @param coordinates The coordinates of the cell.
     * @return The value of the cell at the given coordinates.
     */
    @Override
    public double getValue(CellCoordinates coordinates) {
        return cellStorage.getValue(coordinates);
    }
}
