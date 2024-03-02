package ranger.ui.element;

import java.awt.Color;
import java.awt.Graphics;

import ranger.sheet.Sheet;
import ranger.ui.component.Button;

/**
 * Class representing a sheet entry in the status bar.
 */
public class SheetEntry extends Button {
    /**
     * The sheet this entry represents.
     */
    private Sheet sheet;

    /**
     * The border color.
     */
    private Color borderColor;

    /**
     * The accent color.
     */
    private Color accentColor;

    /**
     * Constructs a new sheet entry.
     * 
     * @param sheet The sheet.
     */
    public SheetEntry(Sheet sheet) {
        super(sheet.getName());

        this.sheet = sheet;
    }

    /**
     * Returns the sheet this entry represents.
     * 
     * @return The sheet this entry represents.
     */
    public Sheet getSheet() {
        return sheet;
    }

    /**
     * Returns the border color.
     * 
     * @return The border color.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Sets the border color.
     * 
     * @param border The border color.
     */
    public void setBorderColor(Color border) {
        this.borderColor = border;
    }

    /**
     * Returns the accent color.
     * 
     * @return The accent color.
     */
    public Color getAccentColor() {
        return accentColor;
    }

    /**
     * Sets the accent color.
     * 
     * @param accent The accent color.
     */
    public void setAccentColor(Color accent) {
        this.accentColor = accent;
    }

    /**
     * Returns whether this entry is opaque.
     * 
     * @return Whether this entry is opaque.
     */
    @Override
    public boolean isOpaque() {
        return isSelected() || super.isOpaque();
    }

    /**
     * Returns the foreground.
     * 
     * @return The foreground.
     */
    @Override
    public Color getForeground() {
        return isSelected() ? accentColor : super.getForeground();
    }

    /**
     * Paints this component.
     * 
     * @param g The graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isSelected()) {
            g.setColor(borderColor);
            g.fillRect(0, 0, 1, getHeight());
            g.fillRect(getWidth() - 1, 0, 1, getHeight());

            g.setColor(accentColor);
            g.fillRect(0, getHeight() - 3, getWidth(), 3);
        }
    }
}
