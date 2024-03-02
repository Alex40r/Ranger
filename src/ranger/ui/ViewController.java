package ranger.ui;

import ranger.sheet.cell.CellCoordinates;
import ranger.ui.view.SelectionDirection;
import ranger.ui.view.View;
import ranger.ui.view.ViewRequestListener;
import ranger.ui.view.ViewStorage;

/**
 * Class for controlling views.
 * This controller is responsible for handling view requests.
 */
public class ViewController implements ViewRequestListener {
    /**
     * The view storage containing the views to control.
     */
    private ViewStorage viewStorage;

    /**
     * Constructs a new view controller.
     * 
     * @param viewStorage The view storage containing the views to control.
     */
    public ViewController(ViewStorage viewStorage) {
        this.viewStorage = viewStorage;
    }

    /**
     * Sets the zoom of the view.
     * 
     * @param zoom The zoom.
     */
    @Override
    public void setZoom(double zoom) {
        View view = viewStorage.getSelectedView();
        if (view == null)
            return;

        view.setZoom(zoom);
        view.repaint();
    }

    /**
     * Moves the cursor to the specified coordinates.
     * 
     * @param coordinates The coordinates.
     */
    @Override
    public void moveCursor(CellCoordinates coordinates) {
        View view = viewStorage.getSelectedView();
        if (view == null)
            return;

        view.moveCursor(coordinates);
        view.repaint();
    }

    /**
     * Moves the cursor in the specified direction.
     * 
     * @param direction The direction.
     * @param clamped   Whether the cursor should be clamped to the selection.
     */
    @Override
    public void moveCursor(SelectionDirection direction, boolean clamped) {
        View view = viewStorage.getSelectedView();
        if (view == null)
            return;

        view.moveCursor(direction, clamped);
        view.repaint();
    }

    /**
     * Acquires focus for the view.
     */
    @Override
    public void acquireFocus() {
        View view = viewStorage.getSelectedView();
        if (view == null)
            return;

        view.requestFocusInWindow();
    }
}
