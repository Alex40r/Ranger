package ranger.ui;

import ranger.sheet.Area;
import ranger.sheet.Sheet;
import ranger.sheet.SheetRequestListener;
import ranger.sheet.Storage;
import ranger.sheet.action.FillAreaAction;
import ranger.sheet.action.SetAreaAction;
import ranger.sheet.action.SetExpressionAction;
import ranger.sheet.action.SetFormatAction;
import ranger.sheet.action.SetHorizontalAlignmentAction;
import ranger.sheet.action.SetVerticalAlignmentAction;
import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellHorizontalAlignment;
import ranger.sheet.cell.CellVerticalAlignment;

/**
 * Class representing the sheet controller.
 * This controller is responsible for handling sheet requests.
 */
public class SheetController implements SheetRequestListener {
    /**
     * The storage containing the sheets to control.
     */
    private Storage storage;

    /**
     * The clipboard.
     */
    private Area clipboard;

    /**
     * Constructs a new sheet controller.
     * 
     * @param storage The storage containing the sheets to control.
     */
    public SheetController(Storage storage) {
        this.storage = storage;
    }

    /**
     * Sets the height of the specified row.
     * 
     * @param row    The row.
     * @param height The height.
     */
    @Override
    public void setRowHeight(int row, int height) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        sheet.setRowHeight(row, height);
    }

    /**
     * Sets the width of the specified column.
     * 
     * @param column The column.
     * @param width  The width.
     */
    @Override
    public void setColumnWidth(int column, int width) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        sheet.setColumnWidth(column, width);
    }

    /**
     * Sets the expression of the specified cell, using an action.
     * 
     * @param coordinates The coordinates.
     * @param expression  The expression.
     */
    @Override
    public void setExpression(CellCoordinates coordinates, String expression) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        CellContent content = sheet.getContent(coordinates);
        String current = content == null ? null : content.getExpression();
        if (expression != null && expression.equals(""))
            expression = null;
        if (expression == null && current == null)
            return;
        if (expression != null && expression.equals(current))
            return;

        sheet.applyAction(new SetExpressionAction(coordinates, expression));
    }

    /**
     * Sets the format of the specified cell, using an action.
     * 
     * @param coordinates The coordinates.
     * @param format      The format.
     */
    @Override
    public void setFormat(CellCoordinates coordinates, String format) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        CellContent content = sheet.getContent(coordinates);
        String current = content == null ? null : content.getFormat();
        if (format != null && format.equals(""))
            format = null;
        if (format == null && current == null)
            return;
        if (format != null && format.equals(current))
            return;

        sheet.applyAction(new SetFormatAction(coordinates, format));
    }

    /**
     * Sets the horizontal alignments of the specified cell range, using an action.
     * 
     * @param start               The start coordinates.
     * @param end                 The end coordinates.
     * @param horizontalAlignment The horizontal alignment.
     */
    @Override
    public void setHorizontalAlignments(CellCoordinates start, CellCoordinates end,
            CellHorizontalAlignment horizontalAlignment) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        sheet.applyAction(new SetHorizontalAlignmentAction(start, end, horizontalAlignment));
    }

    /**
     * Sets the vertical alignments of the specified cell range, using an action.
     * 
     * @param start             The start coordinates.
     * @param end               The end coordinates.
     * @param verticalAlignment The vertical alignment.
     */
    @Override
    public void setVerticalAlignments(CellCoordinates start, CellCoordinates end,
            CellVerticalAlignment verticalAlignment) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        sheet.applyAction(new SetVerticalAlignmentAction(start, end, verticalAlignment));
    }

    /**
     * Undoes the last action.
     */
    @Override
    public void undo() {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        sheet.undoAction();
    }

    /**
     * Redoes the last undone action.
     */
    @Override
    public void redo() {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        sheet.redoAction();
    }

    /**
     * Copies the specified cell range to the clipboard.
     * 
     * @param coordinates The coordinates.
     * @param width       The width.
     * @param height      The height.
     */
    @Override
    public void copy(CellCoordinates coordinates, int width, int height) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        clipboard = sheet.getArea(coordinates, width, height);
    }

    /**
     * Cuts the specified cell range to the clipboard.
     * 
     * @param coordinates The coordinates.
     * @param width       The width.
     * @param height      The height.
     */
    @Override
    public void cut(CellCoordinates coordinates, int width, int height) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        clipboard = sheet.getArea(coordinates, width, height);
        sheet.applyAction(new SetAreaAction(coordinates, new Area(null, width, height, null)));
    }

    /**
     * Pastes the clipboard to the specified cell range.
     * 
     * @param coordinates The coordinates.
     * @param width       The width.
     * @param height      The height.
     */
    @Override
    public void paste(CellCoordinates coordinates, int width, int height) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null || clipboard == null)
            return;

        if (clipboard.getWidth() == 1 && clipboard.getHeight() == 1) {
            CellContent content = clipboard.getCellContent(clipboard.getOrigin());

            sheet.applyAction(new FillAreaAction(coordinates, width, height, content));
        } else
            sheet.applyAction(new SetAreaAction(coordinates, clipboard));
    }

    /**
     * Clears the specified cell range.
     * 
     * @param topLeft The top-left coordinates.
     * @param width   The width.
     * @param height  The height.
     */
    @Override
    public void clearArea(CellCoordinates topLeft, int width, int height) {
        Sheet sheet = storage.getSelectedSheet();
        if (sheet == null)
            return;

        sheet.applyAction(new SetAreaAction(topLeft, new Area(null, width, height, null)));
    }

    /**
     * Returns the stored clipboard.
     * 
     * @return The stored clipboard.
     */
    @Override
    public Area getClipboard() {
        if (clipboard == null)
            return null;

        return new Area(clipboard);
    }
}
