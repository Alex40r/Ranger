package ranger.ui.view;

import java.awt.event.KeyEvent;

/**
 * Interface for listening to view events.
 */
public interface ViewListener {
    /**
     * Called when the zoom level changes.
     * 
     * @param view The view.
     * @param zoom The new zoom level.
     */
    public void zoomChanged(View view, double zoom);

    /**
     * Called when the selection changes.
     * 
     * @param view      The view.
     * @param selection The new selection.
     */
    public void selectionChanged(View view, Selection selection);

    /**
     * Called when the view is repainted.
     * 
     * @param view The view.
     */
    public void repainted(View view);

    /**
     * Called when a key is emitted.
     * 
     * @param view The view.
     * @param e    The key event.
     */
    public void keyEmitted(View view, KeyEvent e);
}
