package ranger.ui.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JComponent;

import ranger.ui.component.layout.Direction;
import ranger.ui.component.layout.DirectionalLayout;
import ranger.ui.component.layout.Padding;
import ranger.ui.component.layout.DirectionalLayout.Behavior;

/**
 * Class representing a container.
 */
public class Container extends JComponent {
    /**
     * Constructs a new container.
     * 
     * @param layout The layout.
     */
    public Container(LayoutManager layout) {
        setLayout(layout);

        setOpaque(false);
    }

    /**
     * Constructs a new container. The default layout is a directional layout with
     * the direction set to east.
     */
    public Container() {
        this(new DirectionalLayout(Direction.EAST));
    }

    /**
     * Constructs a new container with the specified padding.
     * 
     * @param padding The padding.
     */
    public Container(Padding padding) {
        this(new DirectionalLayout(Direction.EAST, padding));
    }

    /**
     * Constructs a new container with the specified direction, behavior, and
     * padding.
     *
     * @param direction The direction.
     * @param behavior  The behavior.
     * @param padding   The padding.
     */
    public Container(Direction direction, Behavior behavior, Padding padding) {
        this(new DirectionalLayout(direction, behavior, padding));
    }

    /**
     * Constructs a new container with the specified direction and behavior.
     *
     * @param direction The direction.
     * @param behavior  The behavior.
     */
    public Container(Direction direction, Behavior behavior) {
        this(new DirectionalLayout(direction, behavior));
    }

    /**
     * Constructs a new container with the specified direction and padding.
     *
     * @param direction The direction.
     * @param padding   The padding.
     */
    public Container(Direction direction, Padding padding) {
        this(new DirectionalLayout(direction, padding));
    }

    /**
     * Constructs a new container with the specified direction.
     *
     * @param direction The direction.
     */
    public Container(Direction direction) {
        this(new DirectionalLayout(direction));
    }

    /**
     * Sets the preferred size of this container.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setPreferredSize(int width, int height) {
        super.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Returns the preferred width of this container.
     * 
     * @return The preferred width of this container.
     */
    public int getPreferredWidth() {
        return getPreferredSize().width;
    }

    /**
     * Returns the preferred height of this container.
     * 
     * @return The preferred height of this container.
     */
    public int getPreferredHeight() {
        return getPreferredSize().height;
    }

    /**
     * Sets the minimum size of this container.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMinimumSize(int width, int height) {
        super.setMinimumSize(new Dimension(width, height));
    }

    /**
     * Returns the minimum width of this container.
     * 
     * @return The minimum width of this container.
     */
    public int getMinimumWidth() {
        return getMinimumSize().width;
    }

    /**
     * Returns the minimum height of this container.
     * 
     * @return The minimum height of this container.
     */
    public int getMinimumHeight() {
        return getMinimumSize().height;
    }

    /**
     * Sets the maximum size of this container.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMaximumSize(int width, int height) {
        super.setMaximumSize(new Dimension(width, height));
    }

    /**
     * Returns the maximum width of this container.
     * 
     * @return The maximum width of this container.
     */
    public int getMaximumWidth() {
        return getMaximumSize().width;
    }

    /**
     * Returns the maximum height of this container.
     * 
     * @return The maximum height of this container.
     */
    public int getMaximumHeight() {
        return getMaximumSize().height;
    }

    /**
     * Paints this component.
     * 
     * @param g The graphics context.
     */
    @Override
    public void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        super.paintComponent(g);
    }
}
