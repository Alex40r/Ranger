package ranger.ui.window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * Class representing a window drag controller.
 * Surfaces can be registered to this controller, which will allow them to be
 * dragged around to move the window.
 */
public class WindowDragController implements MouseListener, MouseMotionListener {
    /**
     * The double click delay for maximizing and restoring the window.
     */
    public static final long DOUBLE_CLICK_DELAY = 400;

    /**
     * The window to control.
     */
    private Window window;

    /**
     * The offset x for dragging.
     */
    private int offsetX;

    /**
     * The offset y for dragging.
     */
    private int offsetY;

    /**
     * Whether or not the window is being dragged.
     */
    private boolean dragging;

    /**
     * The last click time.
     */
    private long lastClick;

    /**
     * The current click time.
     */
    private long currentClick;

    /**
     * Constructs a new window drag controller.
     * 
     * @param window The window to control.
     */
    public WindowDragController(Window window) {
        this.window = window;
    }

    /**
     * Registers a surface to this controller.
     * 
     * @param surface The surface.
     */
    public void registerSurface(JComponent surface) {
        surface.addMouseListener(this);
        surface.addMouseMotionListener(this);
    }

    /**
     * Unregisters a surface from this controller.
     * 
     * @param surface The surface.
     */
    public void unregisterSurface(JComponent surface) {
        surface.removeMouseListener(this);
        surface.removeMouseMotionListener(this);
    }

    /**
     * Handles a mouse drag event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (!dragging)
            return;

        if (window.isMaximized()) {
            window.restore();
            offsetX = window.getBounds().width / 2;
            offsetY = 0;
        }

        int x = e.getXOnScreen() - offsetX;
        int y = e.getYOnScreen() - offsetY;

        window.setLocation(x, y);

        currentClick = 0;
        lastClick = 0;
    }

    /**
     * Handles a mouse move event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Handles a mouse click event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Handles a mouse press event.
     * 
     * @param e The event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        offsetX = e.getXOnScreen() - window.getBounds().x;
        offsetY = e.getYOnScreen() - window.getBounds().y;

        dragging = true;

        currentClick = System.currentTimeMillis();
    }

    /**
     * Handles a mouse release event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        long time = System.currentTimeMillis();

        if (time - lastClick < DOUBLE_CLICK_DELAY) {
            if (window.isMaximized())
                window.restore();
            else
                window.maximize();

            lastClick = 0;
        } else
            lastClick = currentClick;

        dragging = false;
    }

    /**
     * Handles a mouse enter event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Handles a mouse exit event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
