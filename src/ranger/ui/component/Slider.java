package ranger.ui.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class representing a slider.
 */
public class Slider extends JComponent implements MouseListener, MouseMotionListener {
    /**
     * The width of the knob.
     */
    private static final int KNOB_WIDTH = 6;

    /**
     * The height of the knob.
     */
    private static final int KNOB_HEIGHT = 16;

    /**
     * The height of the track.
     */
    private static final int TRACK_HEIGHT = 4;

    /**
     * The minimum value.
     */
    private int minimum;

    /**
     * The maximum value.
     */
    private int maximum;

    /**
     * The current value.
     */
    private int value;

    /**
     * The change listeners.
     */
    private List<ChangeListener> changeListeners;

    /**
     * Constructs a new slider.
     * 
     * @param min   The minimum value.
     * @param max   The maximum value.
     * @param value The current value.
     */
    public Slider(int min, int max, int value) {
        this.minimum = min;
        this.maximum = max;

        changeListeners = new ArrayList<ChangeListener>();

        setMinimumSize(new Dimension(KNOB_WIDTH * 3, Math.max(KNOB_HEIGHT, TRACK_HEIGHT)));

        setFocusable(true);

        addMouseListener(this);
        addMouseMotionListener(this);

        setValue(value);
    }

    /**
     * Adds a change listener.
     * 
     * @param listener The change listener.
     */
    public void addChangeListener(ChangeListener listener) {
        changeListeners.add(listener);
    }

    /**
     * Removes a change listener.
     * 
     * @param listener The change listener.
     */
    public void removeChangeListener(ChangeListener listener) {
        changeListeners.remove(listener);
    }

    /**
     * Sets the value of this slider.
     * 
     * @param value The value.
     */
    public void setValue(int value) {
        if (value < minimum)
            value = minimum;
        else if (value > maximum)
            value = maximum;

        int old = this.value;
        this.value = value;

        if (old == value)
            return;

        repaint();

        for (ChangeListener listener : changeListeners)
            listener.stateChanged(new ChangeEvent(this));
    }

    /**
     * Returns the value of this slider.
     * 
     * @return The value of this slider.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the minimum value of this slider.
     * 
     * @param min The minimum value.
     */
    public void setMinimum(int min) {
        if (min > maximum)
            throw new IllegalArgumentException("Minimum must be less than maximum.");

        this.minimum = min;

        if (value < min)
            setValue(min);
    }

    /**
     * Returns the minimum value of this slider.
     * 
     * @return The minimum value of this slider.
     */
    public int getMinimum() {
        return minimum;
    }

    /**
     * Sets the maximum value of this slider.
     * 
     * @param max The maximum value.
     */
    public void setMaximum(int max) {
        if (max < minimum)
            throw new IllegalArgumentException("max must be greater than min");

        this.maximum = max;

        if (value > max)
            setValue(max);
    }

    /**
     * Returns the maximum value of this slider.
     * 
     * @return The maximum value of this slider.
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * Handles a mouse event.
     * 
     * @param e The mouse event.
     */
    private void handleEvent(MouseEvent e) {
        if (!isEnabled())
            return;

        int totalWidth = getWidth() - KNOB_WIDTH;
        int localX = e.getX() - KNOB_WIDTH / 2;

        if (localX < 0)
            localX = 0;
        else if (localX > totalWidth)
            localX = totalWidth;

        double ratio = (double) localX / totalWidth;

        setValue((int) Math.round(minimum + (maximum - minimum) * ratio));
    }

    /**
     * Sets the preferred size of this slider.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setPreferredSize(int width, int height) {
        super.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Returns the preferred width of this slider.
     * 
     * @return The preferred width of this slider.
     */
    public int getPreferredWidth() {
        return getPreferredSize().width;
    }

    /**
     * Returns the preferred height of this slider.
     * 
     * @return The preferred height of this slider.
     */
    public int getPreferredHeight() {
        return getPreferredSize().height;
    }

    /**
     * Sets the minimum size of this slider.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMinimumSize(int width, int height) {
        super.setMinimumSize(new Dimension(width, height));
    }

    /**
     * Returns the minimum width of this slider.
     * 
     * @return The minimum width of this slider.
     */
    public int getMinimumWidth() {
        return getMinimumSize().width;
    }

    /**
     * Returns the minimum height of this slider.
     * 
     * @return The minimum height of this slider.
     */
    public int getMinimumHeight() {
        return getMinimumSize().height;
    }

    /**
     * Sets the maximum size of this slider.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMaximumSize(int width, int height) {
        super.setMaximumSize(new Dimension(width, height));
    }

    /**
     * Returns the maximum width of this slider.
     * 
     * @return The maximum width of this slider.
     */
    public int getMaximumWidth() {
        return getMaximumSize().width;
    }

    /**
     * Returns the maximum height of this slider.
     * 
     * @return The maximum height of this slider.
     */
    public int getMaximumHeight() {
        return getMaximumSize().height;
    }

    /**
     * Paints this slider.
     * 
     * @param g The graphics context.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(getBackground());
        g2d.fillRect(0, getHeight() / 2 - 2, getWidth(), 4);

        int knobX = (int) Math.round((getWidth() - KNOB_WIDTH) * ((double) (value - minimum) / (maximum - minimum)));
        int knobY = getHeight() / 2 - KNOB_HEIGHT / 2;

        g2d.setColor(getForeground());
        g2d.fillRect(knobX, knobY, KNOB_WIDTH, KNOB_HEIGHT);

        g2d.dispose();
    }

    /**
     * Handles a mouse event. This method is called when the mouse is dragged.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        handleEvent(e);
    }

    /**
     * Handles a mouse event. This method is called when the mouse is moved.
     * This method is not used.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Handles a mouse event. This method is called when the mouse is clicked.
     * This method is not used.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Handles a mouse event. This method is called when the mouse is pressed.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        requestFocusInWindow();

        handleEvent(e);
    }

    /**
     * Handles a mouse event. This method is called when the mouse is released.
     * This method is not used.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Handles a mouse event. This method is called when the mouse enters the
     * component. This method is not used.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Handles a mouse event. This method is called when the mouse exits the
     * component. This method is not used.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
}