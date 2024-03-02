package ranger.sheet.action;

import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellStorage;

/**
 * Class representing a set expression action.
 * The action is responsible for setting a cell's expression.
 */
public class SetExpressionAction implements SheetAction {
    /**
     * The coordinates of the cell.
     */
    private CellCoordinates coordinates;

    /**
     * The expression to set.
     */
    private String expression;

    /**
     * The old expression.
     */
    private String oldExpression;

    /**
     * Constructs a new set expression action.
     * 
     * @param coordinates The coordinates of the cell.
     * @param expression  The expression to set.
     */
    public SetExpressionAction(CellCoordinates coordinates, String expression) {
        this.coordinates = coordinates;

        this.expression = expression;
    }

    /**
     * Clones the action.
     * 
     * @return The cloned action.
     */
    public SheetAction clone() {
        return new SetExpressionAction(coordinates, expression);
    }

    /**
     * Does the action.
     * 
     * @param storage The storage in which to do the action.
     */
    @Override
    public void doAction(CellStorage storage) {
        CellContent content = storage.getContent(coordinates);
        if (content == null)
            content = new CellContent();

        oldExpression = content.getExpression();
        content.setExpression(expression);
        storage.setContent(coordinates, content);
    }

    /**
     * Undoes the action.
     * 
     * @param storage The storage in which to undo the action.
     */
    @Override
    public void undoAction(CellStorage storage) {
        CellContent content = storage.getContent(coordinates);
        if (content == null)
            content = new CellContent();

        content.setExpression(oldExpression);
        storage.setContent(coordinates, content);
    }
}
