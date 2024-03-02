package ranger.ui.window;

/**
 * Interface for listening to window state changes.
 */
public interface WindowStateListener {

    /**
     * Called when the window is maximized.
     * 
     * @param window The window.
     */
    public void maximized(Window window);

    /**
     * Called when the window is restored.
     * 
     * @param window The window.
     */
    public void restored(Window window);

    /**
     * Called when the window is closed.
     * 
     * @param window The window.
     */
    public void closed(Window window);

    /**
     * Called when the window is resized.
     * 
     * @param window The window.
     */
    public void resized(Window window);
}
