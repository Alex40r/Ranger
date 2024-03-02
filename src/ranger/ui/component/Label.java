package ranger.ui.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Class representing a label.
 */
public class Label extends JLabel {
    /**
     * Constructs a new label.
     * 
     * @param text The text.
     */
    public Label(String text) {
        super(text);
    }

    /**
     * Constructs a new label.
     * 
     * @param text                The text.
     * @param horizontalAlignment The horizontal alignment.
     */
    public Label(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    /**
     * Constructs a new label.
     * 
     * @param text                The text.
     * @param horizontalAlignment The horizontal alignment.
     * @param verticalAlignment   The vertical alignment.
     */
    public Label(String text, int horizontalAlignment, int verticalAlignment) {
        super(text, horizontalAlignment);

        setVerticalAlignment(verticalAlignment);
    }

    /**
     * Constructs a new label.
     * 
     * @param icon The icon.
     */
    public Label(Icon icon) {
        super(icon);
    }

    /**
     * Sets the preferred size of this label.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setPreferredSize(int width, int height) {
        super.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Returns the preferred width of this label.
     * 
     * @return The preferred width of this label.
     */
    public int getPreferredWidth() {
        return getPreferredSize().width;
    }

    /**
     * Returns the preferred height of this label.
     * 
     * @return The preferred height of this label.
     */
    public int getPreferredHeight() {
        return getPreferredSize().height;
    }

    /**
     * Sets the minimum size of this label.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMinimumSize(int width, int height) {
        super.setMinimumSize(new Dimension(width, height));
    }

    /**
     * Returns the minimum width of this label.
     * 
     * @return The minimum width of this label.
     */
    public int getMinimumWidth() {
        return getMinimumSize().width;
    }

    /**
     * Returns the minimum height of this label.
     * 
     * @return The minimum height of this label.
     */
    public int getMinimumHeight() {
        return getMinimumSize().height;
    }

    /**
     * Sets the maximum size of this label.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMaximumSize(int width, int height) {
        super.setMaximumSize(new Dimension(width, height));
    }

    /**
     * Returns the maximum width of this label.
     * 
     * @return The maximum width of this label.
     */
    public int getMaximumWidth() {
        return getMaximumSize().width;
    }

    /**
     * Returns the maximum height of this label.
     * 
     * @return The maximum height of this label.
     */
    public int getMaximumHeight() {
        return getMaximumSize().height;
    }

    /**
     * Paints this label.
     * 
     * @param g The graphics context.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        super.paintComponent(g2d);

        g2d.dispose();
    }
}
