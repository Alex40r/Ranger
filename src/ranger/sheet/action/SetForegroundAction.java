package ranger.sheet.action;

import java.awt.Color;

import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellStorage;

/**
 * Class representing a set foreground action.
 * The action is responsible for setting a cell's foreground.
 */
public class SetForegroundAction implements SheetAction {
    /**
     * The coordinates of the cell.
     */
    private CellCoordinates coordinates;

    /**
     * The foreground to set.
     */
    private Color foreground;

    /**
     * The old foreground.
     */
    private Color oldForeground;

    /**
     * Constructs a new set foreground action.
     * 
     * @param coordinates The coordinates of the cell.
     * @param foreground  The foreground to set.
     */
    public SetForegroundAction(CellCoordinates coordinates, Color foreground) {
        this.coordinates = coordinates;

        this.foreground = foreground;
    }

    /**
     * Clones the action.
     * 
     * @return The cloned action.
     */
    @Override
    public SheetAction clone() {
        return new SetForegroundAction(coordinates, foreground);
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

        oldForeground = content.getForeground();
        content.setForeground(foreground);
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

        content.setForeground(oldForeground);
        storage.setContent(coordinates, content);
    }
}
