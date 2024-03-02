package ranger.sheet.cell;

import java.awt.Color;

/**
 * Class representing a cell's content.
 */
public class CellContent {
    /**
     * The expression of the cell.
     */
    private String expression;

    /**
     * The background color of the cell.
     */
    private Color background;

    /**
     * The foreground color of the cell.
     */
    private Color foreground;

    /**
     * The format of the cell.
     */
    private String format;

    /**
     * The horizontal alignment of the cell.
     */
    private CellHorizontalAlignment horizontalAlignment;

    /**
     * The vertical alignment of the cell.
     */
    private CellVerticalAlignment verticalAlignment;

    /**
     * Constructs a new empty cell content.
     */
    public CellContent() {
        expression = null;

        background = null;
        foreground = null;

        format = null;

        horizontalAlignment = null;
        verticalAlignment = null;
    }

    /**
     * Constructs a new cell content from the specified cell content.
     * 
     * @param other The other cell content.
     */
    public CellContent(CellContent other) {
        expression = other.expression;

        background = other.background;
        foreground = other.foreground;

        format = other.format;

        horizontalAlignment = other.horizontalAlignment;
        verticalAlignment = other.verticalAlignment;
    }

    /**
     * Checks whether the cell content is empty.
     * 
     * @return Whether the cell content is empty.
     */
    public boolean isEmpty() {
        return expression == null
                && background == null
                && foreground == null
                && format == null
                && horizontalAlignment == null
                && verticalAlignment == null;
    }

    /**
     * Returns the expression of the cell.
     * 
     * @return The expression of the cell.
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Sets the expression of the cell.
     * 
     * @param expression The expression of the cell.
     */
    public void setExpression(String expression) {
        if (expression != null && expression.isEmpty())
            expression = null;

        this.expression = expression;
    }

    /**
     * Returns the background color of the cell.
     * 
     * @return The background color of the cell.
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Sets the background color of the cell.
     * 
     * @param background The background color of the cell.
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * Returns the foreground color of the cell.
     * 
     * @return The foreground color of the cell.
     */
    public Color getForeground() {
        return foreground;
    }

    /**
     * Sets the foreground color of the cell.
     * 
     * @param foreground The foreground color of the cell.
     */
    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    /**
     * Returns the format of the cell.
     * 
     * @return The format of the cell.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the format of the cell.
     * 
     * @param format The format of the cell.
     */
    public void setFormat(String format) {
        if (format != null && format.isEmpty())
            format = null;

        this.format = format;
    }

    /**
     * Returns the horizontal alignment of the cell.
     * 
     * @return The horizontal alignment of the cell.
     */
    public CellHorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * Sets the horizontal alignment of the cell.
     * 
     * @param horizontalAlignment The horizontal alignment of the cell.
     */
    public void setHorizontalAlignment(CellHorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * Returns the vertical alignment of the cell.
     * 
     * @return The vertical alignment of the cell.
     */
    public CellVerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    /**
     * Sets the vertical alignment of the cell.
     * 
     * @param verticalAlignment The vertical alignment of the cell.
     */
    public void setVerticalAlignment(CellVerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    /**
     * Returns whether the cell content is equal to the specified object.
     * 
     * @param other The other object.
     * @return Whether the cell content is equal to the specified object.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;

        if (!(other instanceof CellContent))
            return false;

        CellContent otherContent = (CellContent) other;

        if (!(expression == null ? otherContent.expression == null : expression.equals(otherContent.expression)))
            return false;

        if (!(background == null ? otherContent.background == null : background.equals(otherContent.background)))
            return false;

        if (!(foreground == null ? otherContent.foreground == null : foreground.equals(otherContent.foreground)))
            return false;

        if (!(format == null ? otherContent.format == null : format.equals(otherContent.format)))
            return false;

        if (!(horizontalAlignment == null ? otherContent.horizontalAlignment == null
                : horizontalAlignment.equals(otherContent.horizontalAlignment)))
            return false;

        if (!(verticalAlignment == null ? otherContent.verticalAlignment == null
                : verticalAlignment.equals(otherContent.verticalAlignment)))
            return false;

        return true;
    }
}
