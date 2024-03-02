package ranger.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * Class representing a block, which is a rectangular component with a
 * background color.
 */
public class Block extends JComponent {
    /**
     * Constructs a new block.
     * 
     * @param color  The background color.
     * @param width  The width.
     * @param height The height.
     */
    public Block(Color color, int width, int height) {
        super();

        setBackground(color);
        setOpaque(true);

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
    }

    /**
     * Sets the preferred size of this block.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setPreferredSize(int width, int height) {
        super.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Returns the preferred width of this block.
     * 
     * @return The preferred width of this block.
     */
    public int getPreferredWidth() {
        return getPreferredSize().width;
    }

    /**
     * Returns the preferred height of this block.
     * 
     * @return The preferred height of this block.
     */
    public int getPreferredHeight() {
        return getPreferredSize().height;
    }

    /**
     * Sets the minimum size of this block.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMinimumSize(int width, int height) {
        super.setMinimumSize(new Dimension(width, height));
    }

    /**
     * Returns the minimum width of this block.
     * 
     * @return The minimum width of this block.
     */
    public int getMinimumWidth() {
        return getMinimumSize().width;
    }

    /**
     * Returns the minimum height of this block.
     * 
     * @return The minimum height of this block.
     */
    public int getMinimumHeight() {
        return getMinimumSize().height;
    }

    /**
     * Sets the maximum size of this block.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMaximumSize(int width, int height) {
        super.setMaximumSize(new Dimension(width, height));
    }

    /**
     * Returns the maximum width of this block.
     * 
     * @return The maximum width of this block.
     */
    public int getMaximumWidth() {
        return getMaximumSize().width;
    }

    /**
     * Returns the maximum height of this block.
     * 
     * @return The maximum height of this block.
     */
    public int getMaximumHeight() {
        return getMaximumSize().height;
    }

    /**
     * Paints this block.
     * 
     * @param g The graphics context.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        super.paintComponent(g2d);

        g2d.dispose();
    }
}
