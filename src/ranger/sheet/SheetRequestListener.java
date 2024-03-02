package ranger.sheet;

import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellHorizontalAlignment;
import ranger.sheet.cell.CellVerticalAlignment;

/**
 * Interface for listening to requests to change a sheet.
 */
public interface SheetRequestListener {
    /**
     * Called when the height of a row should be changed.
     * 
     * @param row    The row.
     * @param height The new height.
     */
    public void setRowHeight(int row, int height);

    /**
     * Called when the width of a column should be changed.
     * 
     * @param column The column.
     * @param width  The new width.
     */
    public void setColumnWidth(int column, int width);

    /**
     * Called when the expression of a cell should be changed.
     * 
     * @param coordinates The coordinates of the cell.
     * @param expression  The new expression.
     */
    public void setExpression(CellCoordinates coordinates, String expression);

    /**
     * Called when the format of a cell should be changed.
     * 
     * @param coordinates The coordinates of the cell.
     * @param text        The new format.
     */
    public void setFormat(CellCoordinates coordinates, String text);

    /**
     * Called when the horizontal alignment of a cell should be changed.
     * 
     * @param start               The start coordinates.
     * @param end                 The end coordinates.
     * @param horizontalAlignment The new horizontal alignment.
     */
    public void setHorizontalAlignments(CellCoordinates start, CellCoordinates end,
            CellHorizontalAlignment horizontalAlignment);

    /**
     * Called when the vertical alignment of a cell should be changed.
     * 
     * @param start             The start coordinates.
     * @param end               The end coordinates.
     * @param verticalAlignment The new vertical alignment.
     */
    public void setVerticalAlignments(CellCoordinates start, CellCoordinates end,
            CellVerticalAlignment verticalAlignment);

    /**
     * Called when the previous action should be undone.
     */
    public void undo();

    /**
     * Called when the previously undone action should be redone.
     */
    public void redo();

    /**
     * Called when the sheet's content should be copied to the clipboard.
     * 
     * @param coordinates The coordinates of the top left cell.
     * @param width       The width of the area.
     * @param height      The height of the area.
     */
    public void copy(CellCoordinates coordinates, int width, int height);

    /**
     * Called when the sheet's content should be cut to the clipboard.
     * 
     * @param coordinates The coordinates of the top left cell.
     * @param width       The width of the area.
     * @param height      The height of the area.
     */
    public void cut(CellCoordinates coordinates, int width, int height);

    /**
     * Called when the sheet's content should be pasted from the clipboard.
     * 
     * @param coordinates The coordinates of the top left cell.
     * @param width       The width of the area.
     * @param height      The height of the area.
     */
    public void paste(CellCoordinates coordinates, int width, int height);

    /**
     * Called to request the clipboard's content.
     * 
     * @return The clipboard's content.
     */
    public Area getClipboard();

    /**
     * Called when an area of the sheet should be cleared.
     * 
     * @param topLeft The top left coordinates.
     * @param width   The width of the area.
     * @param height  The height of the area.
     */
    public void clearArea(CellCoordinates topLeft, int width, int height);
}