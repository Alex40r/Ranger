package ranger.ui.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Class representing a text input.
 */
public class Input extends JTextField implements DocumentListener {
    /**
     * The change listeners.
     */
    private List<ChangeListener> changeListeners;

    /**
     * Constructs a new input.
     */
    public Input() {
        changeListeners = new ArrayList<ChangeListener>();

        setBorder(null);

        getDocument().addDocumentListener(this);
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
     * Sets the preferred size of this input.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setPreferredSize(int width, int height) {
        super.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Returns the preferred width of this input.
     * 
     * @return The preferred width of this input.
     */
    public int getPreferredWidth() {
        return getPreferredSize().width;
    }

    /**
     * Returns the preferred height of this input.
     * 
     * @return The preferred height of this input.
     */
    public int getPreferredHeight() {
        return getPreferredSize().height;
    }

    /**
     * Sets the minimum size of this input.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMinimumSize(int width, int height) {
        super.setMinimumSize(new Dimension(width, height));
    }

    /**
     * Returns the minimum width of this input.
     * 
     * @return The minimum width of this input.
     */
    public int getMinimumWidth() {
        return getMinimumSize().width;
    }

    /**
     * Returns the minimum height of this input.
     * 
     * @return The minimum height of this input.
     */
    public int getMinimumHeight() {
        return getMinimumSize().height;
    }

    /**
     * Sets the maximum size of this input.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMaximumSize(int width, int height) {
        super.setMaximumSize(new Dimension(width, height));
    }

    /**
     * Returns the maximum width of this input.
     * 
     * @return The maximum width of this input.
     */
    public int getMaximumWidth() {
        return getMaximumSize().width;
    }

    /**
     * Returns the maximum height of this input.
     * 
     * @return The maximum height of this input.
     */
    public int getMaximumHeight() {
        return getMaximumSize().height;
    }

    /**
     * Adds a keybind.
     * 
     * @param keyCode       The key code.
     * @param modifiers     The modifiers.
     * @param actionCommand The action command.
     * @param listener      The listener to call when the keybind is pressed.
     */
    public void setKeybind(int keyCode, int modifiers, String actionCommand, KeybindListener listener) {
        getInputMap().put(KeyStroke.getKeyStroke(keyCode, modifiers), actionCommand);
        getActionMap().put(actionCommand, new KeybindPassthrough(actionCommand, listener));
    }

    /**
     * Handles a change in the document.
     * 
     * @param e The document event.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        for (ChangeListener listener : changeListeners)
            listener.stateChanged(new ChangeEvent(this));
    }

    /**
     * Handles a change in the document.
     * 
     * @param e The document event.
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        for (ChangeListener listener : changeListeners)
            listener.stateChanged(new ChangeEvent(this));
    }

    /**
     * Handles a change in the document.
     * 
     * @param e The document event.
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    /**
     * Paints this component.
     * 
     * @param g The graphics context.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        super.paintComponent(g2d);

        g2d.dispose();
    }
}
