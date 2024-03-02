package ranger.ui.window;

import javax.swing.JComponent;

/**
 * Class representing a window resize layer.
 */
public class WindowResizeLayer extends JComponent {
    /**
     * Constructs a new window resize layer.
     */
    public WindowResizeLayer() {
    }

    /**
     * Returns whether this window resize layer contains the specified point.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return Whether this window resize layer contains the specified point.
     */
    @Override
    public boolean contains(int x, int y) {
        if (x < WindowResizeController.RESIZE_BORDER_SIZE ||
                x > getWidth() - WindowResizeController.RESIZE_BORDER_SIZE ||
                y < WindowResizeController.RESIZE_BORDER_SIZE ||
                y > getHeight() - WindowResizeController.RESIZE_BORDER_SIZE)
            return true;

        return false;
    }
}
