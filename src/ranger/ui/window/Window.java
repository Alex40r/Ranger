package ranger.ui.window;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * Class representing a window.
 */
public class Window extends JFrame implements WindowListener, ComponentListener {
    /**
     * The bounds to restore to when restoring from maximized state.
     */
    private Rectangle restoreBounds;

    /**
     * The window state listeners.
     */
    private List<WindowStateListener> stateListeners;

    /**
     * Constructs a new window.
     */
    public Window() {
        super("Ranger");

        setSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(200, 200));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        setUndecorated(true);

        addWindowListener(this);

        stateListeners = new ArrayList<WindowStateListener>();
    }

    /**
     * Adds a window state listener.
     * 
     * @param windowStateListener The window state listener.
     */
    public void addWindowStateListener(WindowStateListener windowStateListener) {
        stateListeners.add(windowStateListener);
    }

    /**
     * Removes a window state listener.
     * 
     * @param windowStateListener The window state listener.
     */
    public void removeWindowStateListener(WindowStateListener windowStateListener) {
        stateListeners.remove(windowStateListener);
    }

    /**
     * Returns whether this window is iconified.
     * 
     * @return Whether this window is iconified.
     */
    public boolean isIconified() {
        return (getExtendedState() & ICONIFIED) == ICONIFIED;
    }

    /**
     * Iconifies this window.
     */
    public void iconify() {
        if (isIconified())
            return;

        setExtendedState(ICONIFIED);
    }

    /**
     * Returns whether this window is maximized.
     * 
     * @return Whether this window is maximized.
     */
    public boolean isMaximized() {
        return restoreBounds != null;
    }

    /**
     * Maximizes this window.
     */
    public void maximize() {
        if (isMaximized())
            return;

        restoreBounds = getBounds();

        Rectangle bounds = getGraphicsConfiguration().getDevice().getDefaultConfiguration().getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());

        setBounds(
                bounds.x + insets.left,
                bounds.y + insets.top,
                bounds.width - insets.left - insets.right,
                bounds.height - insets.top - insets.bottom);

        revalidate();
        repaint();

        for (WindowStateListener listener : stateListeners)
            listener.maximized(this);
    }

    /**
     * Restores this window.
     */
    public void restore() {
        if (isIconified())
            return;

        if (!isMaximized())
            return;

        setBounds(restoreBounds);

        restoreBounds = null;

        revalidate();
        repaint();

        for (WindowStateListener listener : stateListeners)
            listener.restored(this);
    }

    /**
     * Handles the window opened event.
     * 
     * @param e The event.
     */
    @Override
    public void windowOpened(WindowEvent e) {
    }

    /**
     * Handles the window closing event.
     * 
     * @param e The event.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        for (WindowStateListener listener : stateListeners)
            listener.closed(this);
    }

    /**
     * Handles the window closed event.
     * 
     * @param e The event.
     */
    @Override
    public void windowClosed(WindowEvent e) {
    }

    /**
     * Handles the window iconified event.
     * 
     * @param e The event.
     */
    @Override
    public void windowIconified(WindowEvent e) {
    }

    /**
     * Handles the window deiconified event.
     * 
     * @param e The event.
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    /**
     * Handles the window activated event.
     * 
     * @param e The event.
     */
    @Override
    public void windowActivated(WindowEvent e) {
    }

    /**
     * Handles the window deactivated event.
     * 
     * @param e The event.
     */
    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    /**
     * Handles the component resized event.
     * 
     * @param e The event.
     */
    @Override
    public void componentResized(ComponentEvent e) {
        for (WindowStateListener listener : stateListeners)
            listener.resized(this);
    }

    /**
     * Handles the component moved event.
     * 
     * @param e The event.
     */
    @Override
    public void componentMoved(ComponentEvent e) {
    }

    /**
     * Handles the component shown event.
     * 
     * @param e The event.
     */
    @Override
    public void componentShown(ComponentEvent e) {
    }

    /**
     * Handles the component hidden event.
     * 
     * @param e The event.
     */
    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
