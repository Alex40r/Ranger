package ranger.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Class representing a button.
 */
public class Button extends JButton {
    /**
     * The disabled filter.
     */
    private Color disabledFilter;

    /**
     * The selected filter.
     */
    private Color selectedFilter;

    /**
     * The pressed filter.
     */
    private Color pressedFilter;

    /**
     * The hovered filter.
     */
    private Color hoveredFilter;

    /**
     * The focus border color.
     */
    private Color focusBorderColor;

    /**
     * Constructs a new button.
     */
    public Button() {
        this(null, null);
    }

    /**
     * Constructs a new button.
     * 
     * @param text The text.
     */
    public Button(String text) {
        this(text, null);
    }

    /**
     * Constructs a new button.
     * 
     * @param icon The icon.
     */
    public Button(Icon icon) {
        this(null, icon);
    }

    /**
     * Constructs a new button.
     * 
     * @param text The text.
     * @param icon The icon.
     */
    public Button(String text, Icon icon) {
        super(text, icon);

        setContentAreaFilled(false); // Custom painting
        setFocusPainted(false); // Custom painting

        setOpaque(false);
        setBorder(null);

        disabledFilter = new Color(0, 0, 0, 24);
        selectedFilter = new Color(0, 0, 0, 96);
        pressedFilter = new Color(0, 0, 0, 64);
        hoveredFilter = new Color(0, 0, 0, 32);

        focusBorderColor = new Color(0, 0, 0, 24);
    }

    /**
     * Sets the preferred size of this button.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setPreferredSize(int width, int height) {
        super.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Returns the preferred width of this button.
     * 
     * @return The preferred width of this button.
     */
    public int getPreferredWidth() {
        return getPreferredSize().width;
    }

    /**
     * Returns the preferred height of this button.
     * 
     * @return The preferred height of this button.
     */
    public int getPreferredHeight() {
        return getPreferredSize().height;
    }

    /**
     * Sets the minimum size of this button.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMinimumSize(int width, int height) {
        super.setMinimumSize(new Dimension(width, height));
    }

    /**
     * Returns the minimum width of this button.
     * 
     * @return The minimum width of this button.
     */
    public int getMinimumWidth() {
        return getMinimumSize().width;
    }

    /**
     * Returns the minimum height of this button.
     * 
     * @return The minimum height of this button.
     */
    public int getMinimumHeight() {
        return getMinimumSize().height;
    }

    /**
     * Sets the maximum size of this button.
     * 
     * @param width  The width.
     * @param height The height.
     */
    public void setMaximumSize(int width, int height) {
        super.setMaximumSize(new Dimension(width, height));
    }

    /**
     * Returns the maximum width of this button.
     * 
     * @return The maximum width of this button.
     */
    public int getMaximumWidth() {
        return getMaximumSize().width;
    }

    /**
     * Returns the maximum height of this button.
     * 
     * @return The maximum height of this button.
     */
    public int getMaximumHeight() {
        return getMaximumSize().height;
    }

    /**
     * Returns the color of the disabled filter.
     * 
     * @return The color of the disabled filter.
     */
    public Color getDisabledFilter() {
        return disabledFilter;
    }

    /**
     * Sets the color of the disabled filter.
     * 
     * @param disabledFilter The color of the disabled filter.
     */
    public void setDisabledFilter(Color disabledFilter) {
        this.disabledFilter = disabledFilter;
    }

    /**
     * Returns the color of the selected filter.
     * 
     * @return The color of the selected filter.
     */
    public Color getSelectedFilter() {
        return selectedFilter;
    }

    /**
     * Sets the color of the selected filter.
     * 
     * @param selectedFilter The color of the selected filter.
     */
    public void setSelectedFilter(Color selectedFilter) {
        this.selectedFilter = selectedFilter;
    }

    /**
     * Returns the color of the pressed filter.
     * 
     * @return The color of the pressed filter.
     */
    public Color getPressedFilter() {
        return pressedFilter;
    }

    /**
     * Sets the color of the pressed filter.
     * 
     * @param pressedFilter The color of the pressed filter.
     */
    public void setPressedFilter(Color pressedFilter) {
        this.pressedFilter = pressedFilter;
    }

    /**
     * Returns the color of the hovered filter.
     * 
     * @return The color of the hovered filter.
     */
    public Color getHoveredFilter() {
        return hoveredFilter;
    }

    /**
     * Sets the color of the hovered filter.
     * 
     * @param hoveredFilter The color of the hovered filter.
     */
    public void setHoveredFilter(Color hoveredFilter) {
        this.hoveredFilter = hoveredFilter;
    }

    /**
     * Returns the color of the focus border.
     * 
     * @return The color of the focus border.
     */
    public Color getFocusBorderColor() {
        return focusBorderColor;
    }

    /**
     * Sets the color of the focus border.
     * 
     * @param focusBorderColor The color of the focus border.
     */
    public void setFocusBorderColor(Color focusBorderColor) {
        this.focusBorderColor = focusBorderColor;
    }

    /**
     * Paints this button.
     * 
     * @param g The graphics context.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (isFocusOwner()) {
            g2d.setColor(focusBorderColor);
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

        if (!getModel().isEnabled()) {
            g2d.setColor(disabledFilter);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else if (getModel().isSelected()) {
            g2d.setColor(selectedFilter);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else if (getModel().isPressed()) {
            g2d.setColor(pressedFilter);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else if (getModel().isRollover()) {
            g2d.setColor(hoveredFilter);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        super.paintComponent(g2d);

        g2d.dispose();
    }
}
