package ranger.sheet.action;

import ranger.sheet.cell.CellStorage;

/**
 * Interface representing a sheet action.
 */
public interface SheetAction {
    /**
     * Clones the action.
     * 
     * @return The cloned action.
     */
    public SheetAction clone();

    /**
     * Does the action.
     * 
     * @param storage The storage in which to do the action.
     */
    public void doAction(CellStorage storage);

    /**
     * Undoes the action.
     * 
     * @param storage The storage in which to undo the action.
     */
    public void undoAction(CellStorage storage);
}
