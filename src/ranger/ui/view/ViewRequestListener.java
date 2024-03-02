package ranger.ui.view;

import ranger.sheet.cell.CellCoordinates;

/**
 * Interface for listening to view requests.
 */
public interface ViewRequestListener {
    /**
     * Called when the zoom level is requested to change.
     * 
     * @param zoom The new zoom level.
     */
    public void setZoom(double zoom);

    /**
     * Called when the selection cursor is requested to move.
     * 
     * @param coordinates The new coordinates of the cursor.
     */
    public void moveCursor(CellCoordinates coordinates);

    /**
     * Called when the selection cursor is requested to move in a specific
     * direction.
     * 
     * @param direction The direction to move the cursor.
     * @param clamped   Whether the cursor should be clamped to the selection.
     */
    public void moveCursor(SelectionDirection direction, boolean clamped);

    /**
     * Called when the view is requested to acquire focus.
     */
    public void acquireFocus();
}
