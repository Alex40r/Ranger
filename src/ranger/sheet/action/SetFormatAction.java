package ranger.sheet.action;

import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellStorage;

/**
 * Class representing a set format action.
 * The action is responsible for setting a cell's format.
 */
public class SetFormatAction implements SheetAction {
    /**
     * The coordinates of the cell.
     */
    private CellCoordinates coordinates;

    /**
     * The format to set.
     */
    private String format;

    /**
     * The old format.
     */
    private String oldFormat;

    /**
     * Constructs a new set format action.
     * 
     * @param coordinates The coordinates of the cell.
     * @param format      The format to set.
     */
    public SetFormatAction(CellCoordinates coordinates, String format) {
        this.coordinates = coordinates;

        this.format = format;
    }

    /**
     * Clones the action.
     * 
     * @return The cloned action.
     */
    @Override
    public SheetAction clone() {
        return new SetFormatAction(coordinates, format);
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

        oldFormat = content.getFormat();
        content.setFormat(format);
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

        content.setFormat(oldFormat);
        storage.setContent(coordinates, content);
    }
}
