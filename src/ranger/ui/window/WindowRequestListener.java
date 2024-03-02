
package ranger.ui.window;

/**
 * Interface for listening to window requests.
 */
public interface WindowRequestListener {
    /**
     * Called when the window is requested to be iconified.
     */
    public void iconify();

    /**
     * Called when the window is requested to be maximized.
     */
    public void maximize();

    /**
     * Called when the window is requested to be restored.
     */
    public void restore();

    /**
     * Called when the window is requested to be closed.
     */
    public void close();
}