package ranger.sheet.action;

import java.awt.Color;

import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellStorage;

/**
 * Class representing a set background action.
 * The action is responsible for setting a cell's background.
 */
public class SetBackgroundAction implements SheetAction {
    /**
     * The coordinates of the cell.
     */
    private CellCoordinates coordinates;

    /**
     * The background to set.
     */
    private Color background;

    /**
     * The old background.
     */
    private Color oldBackground;

    /**
     * Constructs a new set background action.
     * 
     * @param coordinates The coordinates of the cell.
     * @param background  The background to set.
     */
    public SetBackgroundAction(CellCoordinates coordinates, Color background) {
        this.coordinates = coordinates;

        this.background = background;
    }

    /**
     * Clones the action.
     * 
     * @return The cloned action.
     */
    @Override
    public SheetAction clone() {
        return new SetBackgroundAction(coordinates, background);
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

        oldBackground = content.getBackground();
        content.setBackground(background);
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

        content.setBackground(oldBackground);
        storage.setContent(coordinates, content);
    }
}
