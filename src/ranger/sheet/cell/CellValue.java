package ranger.sheet.cell;

/**
 * Class representing a cell's value.
 */
public class CellValue {
    /**
     * The cell.
     */
    private Cell cell;

    /**
     * Constructs a new cell value.
     * 
     * @param cell The cell.
     */
    public CellValue(Cell cell) {
        this.cell = cell;
    }

    /**
     * Returns whether the cell has a value.
     * 
     * @return Whether the cell has a value.
     */
    public boolean hasValue() {
        return cell.isEvaluated();
    }

    /**
     * Returns the cell's value.
     * 
     * @return The cell's value.
     */
    public double getValue() {
        return cell.getValue();
    }

    /**
     * Returns the cell's formatted value.
     * 
     * @return The cell's formatted value.
     */
    public String getFormattedValue() {
        return cell.getFormattedValue();
    }

    /**
     * Returns whether the cell has an error.
     * 
     * @return Whether the cell has an error.
     */
    public boolean hasError() {
        return cell.hasError();
    }

    /**
     * Returns the cell's error.
     * 
     * @return The cell's error.
     */
    public CellError getError() {
        CellError error = cell.getError();
        if (error == null)
            return null;

        if (error == CellError.DEPENDENCY_NOT_EVALUATED && isPartOfCycle())
            return CellError.DEPENDENCY_CYCLE;

        return error;
    }

    /**
     * Returns the cell's content.
     * 
     * @return The cell's content.
     */
    public CellContent getContent() {
        return cell.getContent();
    }

    /**
     * Returns whether the cell is part of a cycle.
     * 
     * @return Whether the cell is part of a cycle.
     */
    public boolean isPartOfCycle() {
        return cell.checkForCycle();
    }

    /**
     * Returns the cell's display text.
     * 
     * @return The cell's display text.
     */
    public String getDisplayText() {
        if (cell.hasError())
            return getError().toString();

        if (cell.isEvaluated()) {
            String formatted = getFormattedValue();
            if (formatted == null)
                return "#FORMAT";

            return formatted;
        }
        CellContent content = getContent();
        return content == null ? null : content.getExpression();
    }
}
