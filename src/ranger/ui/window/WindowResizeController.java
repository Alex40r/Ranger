package ranger.ui.window;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * Class representing a window resize controller.
 * Surfaces can be registered to this controller, which will allow them to be
 * dragged around to resize the window.
 */
public class WindowResizeController implements MouseListener, MouseMotionListener {
    /**
     * The resize border size.
     */
    public static final int RESIZE_BORDER_SIZE = 8;

    /**
     * Enum representing which border is being dragged.
     */
    private enum Border {
        /**
         * The north border.
         */
        NORTH,

        /**
         * The south border.
         */
        SOUTH,

        /**
         * The east border.
         */
        EAST,

        /**
         * The west border.
         */
        WEST,

        /**
         * The north east border.
         */
        NORTH_EAST,

        /**
         * The north west border.
         */
        NORTH_WEST,

        /**
         * The south east border.
         */
        SOUTH_EAST,

        /**
         * The south west border.
         */
        SOUTH_WEST
    }

    /**
     * The window to control.
     */
    private Window window;

    /**
     * The border selected at the start of the drag.
     */
    private Border start;

    /**
     * The border currently selected.
     */
    private Border current;

    /**
     * The start x coordinate.
     */
    private int startX;

    /**
     * The start y coordinate.
     */
    private int startY;

    /**
     * The bounds of the window at the start of the drag.
     */
    private Rectangle startBounds;

    /**
     * Constructs a new window resize controller.
     * 
     * @param window The window to control.
     */
    public WindowResizeController(Window window) {
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
     * Returns the border at the specified screen coordinates.
     * 
     * @param screenX The screen x coordinate.
     * @param screenY The screen y coordinate.
     * @return The border at the specified screen coordinates.
     */
    private Border getBorder(int screenX, int screenY) {
        int x = window.getX();
        int y = window.getY();
        int width = window.getWidth();
        int height = window.getHeight();

        int vertical;
        if (screenY < y + RESIZE_BORDER_SIZE)
            vertical = -1;
        else if (screenY > y + height - RESIZE_BORDER_SIZE)
            vertical = 1;
        else
            vertical = 0;

        int horizontal;
        if (screenX < x + RESIZE_BORDER_SIZE)
            horizontal = -1;
        else if (screenX > x + width - RESIZE_BORDER_SIZE)
            horizontal = 1;
        else
            horizontal = 0;

        switch (vertical) {
            case -1:
                switch (horizontal) {
                    case -1:
                        return Border.NORTH_WEST;
                    case 0:
                        return Border.NORTH;
                    case 1:
                        return Border.NORTH_EAST;
                }

            case 0:
                switch (horizontal) {
                    case -1:
                        return Border.WEST;
                    case 0:
                        return null;
                    case 1:
                        return Border.EAST;
                }

            case 1:
                switch (horizontal) {
                    case -1:
                        return Border.SOUTH_WEST;
                    case 0:
                        return Border.SOUTH;
                    case 1:
                        return Border.SOUTH_EAST;
                }
        }

        return null;
    }

    /**
     * Sets the cursor to match the specified border.
     * 
     * @param border The border.
     */
    private void setCursor(Border border) {
        if (border == null) {
            window.setCursor(Cursor.getDefaultCursor());
            return;
        }

        switch (border) {
            case NORTH:
                window.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                break;

            case SOUTH:
                window.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                break;

            case EAST:
                window.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                break;

            case WEST:
                window.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                break;

            case NORTH_EAST:
                window.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                break;

            case NORTH_WEST:
                window.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                break;

            case SOUTH_EAST:
                window.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                break;

            case SOUTH_WEST:
                window.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                break;
        }
    }

    /**
     * Handles a mouse drag event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (start == null)
            return;

        int dx = e.getXOnScreen() - startX;
        int dy = e.getYOnScreen() - startY;

        Dimension minimum = window.getMinimumSize();

        switch (start) {
            case NORTH:
                window.setBounds(
                        startBounds.x,
                        startBounds.y + Math.min(dy, startBounds.height - minimum.height),
                        startBounds.width,
                        Math.max(startBounds.height - dy, minimum.height));
                break;
            case SOUTH:
                window.setBounds(
                        startBounds.x,
                        startBounds.y,
                        startBounds.width,
                        Math.max(startBounds.height + dy, minimum.height));
                break;
            case EAST:
                window.setBounds(
                        startBounds.x,
                        startBounds.y,
                        Math.max(startBounds.width + dx, minimum.width),
                        startBounds.height);
                break;
            case WEST:
                window.setBounds(
                        startBounds.x + Math.min(dx, startBounds.width - minimum.width),
                        startBounds.y,
                        Math.max(startBounds.width - dx, minimum.width),
                        startBounds.height);
                break;
            case NORTH_EAST:
                window.setBounds(
                        startBounds.x,
                        startBounds.y + Math.min(dy, startBounds.height - minimum.height),
                        Math.max(startBounds.width + dx, minimum.width),
                        Math.max(startBounds.height - dy, minimum.height));
                break;
            case NORTH_WEST:
                window.setBounds(
                        startBounds.x + Math.min(dx, startBounds.width - minimum.width),
                        startBounds.y + Math.min(dy, startBounds.height - minimum.height),
                        Math.max(startBounds.width - dx, minimum.width),
                        Math.max(startBounds.height - dy, minimum.height));
                break;
            case SOUTH_EAST:
                window.setBounds(
                        startBounds.x,
                        startBounds.y,
                        Math.max(startBounds.width + dx, minimum.width),
                        Math.max(startBounds.height + dy, minimum.height));
                break;
            case SOUTH_WEST:
                window.setBounds(
                        startBounds.x + Math.min(dx, startBounds.width - minimum.width),
                        startBounds.y,
                        Math.max(startBounds.width - dx, minimum.width),
                        Math.max(startBounds.height + dy, minimum.height));
                break;
            default:
                break;
        }

        window.revalidate();
        window.repaint();
    }

    /**
     * Handles a mouse move event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        Border old = current;

        current = getBorder(e.getXOnScreen(), e.getYOnScreen());

        if (start == null && old != current)
            setCursor(current);
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
        current = getBorder(e.getXOnScreen(), e.getYOnScreen());
        start = current;

        startX = e.getXOnScreen();
        startY = e.getYOnScreen();

        startBounds = window.getBounds();

        setCursor(current);
    }

    /**
     * Handles a mouse release event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        start = null;

        setCursor(current);
    }

    /**
     * Handles a mouse enter event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        current = getBorder(e.getXOnScreen(), e.getYOnScreen());

        if (start == null)
            setCursor(current);
    }

    /**
     * Handles a mouse exit event.
     * 
     * @param e The event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        current = null;

        if (start == null)
            setCursor(null);
    }
}
