package ranger.ui.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.GlyphVector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.sheet.Area;
import ranger.sheet.Sheet;
import ranger.sheet.SheetListener;
import ranger.sheet.SheetRequestListener;
import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellHorizontalAlignment;
import ranger.sheet.cell.CellValue;
import ranger.sheet.cell.CellVerticalAlignment;
import ranger.ui.component.Container;

/**
 * Class representing a sheet view.
 */
public class View extends Container
        implements MouseListener, MouseMotionListener, MouseWheelListener, SheetListener, KeyListener, FocusListener {
    /**
     * The speed at which the view moves when scrolling.
     */
    private static final double MOVE_SPEED = 50.0;

    /**
     * The speed at which the view zooms when scrolling.
     */
    private static final double ZOOM_SPEED = 1.1;

    /**
     * The threshold at which the border of a row or column can be resized.
     */
    private static final int RESIZE_THRESHOLD = 8;

    /**
     * The settings registrar containing the settings to use.
     */
    private SettingsRegistrar settings;

    /**
     * The sheet to display.
     */
    private Sheet sheet;

    /**
     * The zoom level.
     */
    private double zoom;

    /**
     * Whether a row is currently being resized.
     */
    private boolean isResizingRow;

    /**
     * The row that is being resized.
     */
    private int resizeTargetRow;

    /**
     * Whether a column is currently being resized.
     */
    private boolean isResizingColumn;

    /**
     * The column that is being resized.
     */
    private int resizeTargetColumn;

    /**
     * Whether the view is currently being dragged.
     */
    private boolean isDragging;

    /**
     * The x coordinate of the drag start.
     */
    private int dragX;

    /**
     * The y coordinate of the drag start.
     */
    private int dragY;

    /**
     * Whether the view is currently selecting.
     */
    private boolean isSelecting;

    /**
     * The selection, which may be null if no selection is active.
     */
    private Selection selection;

    /**
     * The listeners that are registered to this view.
     */
    private List<ViewListener> viewListeners;

    /**
     * The request listener that handles sheet requests.
     */
    private SheetRequestListener sheetRequestListener;

    /**
     * Whether the view is currently passing focus to the input bar.
     */
    private boolean isPassingFocus;

    /**
     * The cached column widths.
     */
    private int[] cachedColumnWidths;

    /**
     * The cached row heights.
     */
    private int[] cachedRowHeights;

    /**
     * The cached column offsets in the component.
     */
    private int[] cachedColumnOffsets;

    /**
     * The cached row offsets in the component.
     */
    private int[] cachedRowOffsets;

    /**
     * The width of a character at the current zoom level.
     */
    private int characterWidth;

    /**
     * The height of a character at the current zoom level.
     */
    private int characterHeight;

    /**
     * The width of the row labels.
     */
    private int labelWidth;

    /**
     * The height of the column labels.
     */
    private int labelHeight;

    /**
     * The current y coordinate of the start of the visible range.
     */
    private int visibleX;

    /**
     * The current y coordinate of the start of the visible range.
     */
    private int visibleY;

    /**
     * The current width of the visible range.
     */
    private int visibleWidth;

    /**
     * The current height of the visible range.
     */
    private int visibleHeight;

    /**
     * The current x offset of the visible range in the component.
     */
    private int visibleOffsetX;

    /**
     * The current y offset of the visible range in the component.
     */
    private int visibleOffsetY;

    /**
     * Constructs a new view.
     * 
     * @param settings The settings registrar containing the settings to use.
     * @param sheet    The sheet to display.
     */
    public View(SettingsRegistrar settings, Sheet sheet) {
        this.settings = settings;
        this.sheet = sheet;
        this.sheet.addListener(this);

        this.viewListeners = new ArrayList<ViewListener>();

        zoom = 1.0;

        /* ---- ---- */

        setPreferredSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

        setOpaque(true);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        addFocusListener(this);
    }

    /**
     * Adds a view listener to this view.
     * 
     * @param listener The listener to add.
     */
    public void addViewListener(ViewListener listener) {
        viewListeners.add(listener);
    }

    /**
     * Removes a view listener from this view.
     * 
     * @param listener The listener to remove.
     */
    public void removeViewListener(ViewListener listener) {
        viewListeners.remove(listener);
    }

    /**
     * Sets the sheet request listener.
     * 
     * @param listener The listener to set.
     */
    public void setSheetRequestListener(SheetRequestListener listener) {
        sheetRequestListener = listener;
    }

    /**
     * Returns the sheet that is being displayed.
     * 
     * @return The sheet that is being displayed.
     */
    public Sheet getSheet() {
        return sheet;
    }

    /**
     * Returns the zoom level.
     * 
     * @return The zoom level.
     */
    public double getZoom() {
        return zoom;
    }

    /**
     * Sets the zoom level.
     * 
     * @param zoom The zoom level.
     */
    public void setZoom(double zoom) {
        if (this.zoom == zoom)
            return;

        double max = settings.get(Setting.MAXIMUM_ZOOM, Double.class);
        double min = settings.get(Setting.MINIMUM_ZOOM, Double.class);
        if (zoom > max)
            zoom = max;
        if (zoom < min)
            zoom = min;

        double visibleOffsetX = this.visibleOffsetX / this.zoom;
        double visibleOffsetY = this.visibleOffsetY / this.zoom;

        this.zoom = zoom;

        this.visibleOffsetX = (int) Math.round(visibleOffsetX * zoom);
        this.visibleOffsetY = (int) Math.round(visibleOffsetY * zoom);

        for (ViewListener listener : viewListeners)
            listener.zoomChanged(this, zoom);
    }

    /**
     * Calculates the width of a column at the current zoom level.
     * 
     * @param column The column.
     * @return The width of the column.
     */
    private int calculateColumnWidth(int column) {
        return Math.max((int) Math.round(sheet.getColumnWidth(column) * zoom), 1);
    }

    /**
     * Calculates the height of a row at the current zoom level.
     * 
     * @param row The row.
     * @return The height of the row.
     */
    private int calculateRowHeight(int row) {
        return Math.max((int) Math.round(sheet.getRowHeight(row) * zoom), 1);
    }

    /**
     * Returns the width of a column at the current zoom level.
     * 
     * @param column The column.
     * @return The width of the column.
     */
    private int getColumnWidth(int column) {
        if (cachedColumnWidths == null || !isColumnVisible(column))
            return calculateColumnWidth(column);

        return cachedColumnWidths[column - visibleX];
    }

    /**
     * Returns the height of a row at the current zoom level.
     * 
     * @param row The row.
     * @return The height of the row.
     */
    private int getRowHeight(int row) {
        if (cachedRowHeights == null || !isRowVisible(row))
            return calculateRowHeight(row);

        return cachedRowHeights[row - visibleY];
    }

    /**
     * Returns the x offset of a column at the current zoom level.
     * 
     * @param column The column.
     * @return The x offset of the column.
     * @throws RuntimeException If the offset of this column is not cached, meaning
     *                          its not visible.
     */
    private int getColumnOffset(int column) {
        if (cachedColumnOffsets == null || !isColumnVisible(column))
            throw new RuntimeException("Column offset not cached. Please call generateCellMetrics() first.");

        return cachedColumnOffsets[column - visibleX];
    }

    /**
     * Returns the y offset of a row at the current zoom level.
     * 
     * @param row The row.
     * @return The y offset of the row.
     * @throws RuntimeException If the offset of this row is not cached, meaning
     *                          its not visible.
     */
    private int getRowOffset(int row) {
        if (cachedRowOffsets == null || !isRowVisible(row))
            throw new RuntimeException("Row offset not cached. Please call generateCellMetrics() first.");

        return cachedRowOffsets[row - visibleY];
    }

    /**
     * Returns whether a column is visible.
     * 
     * @param column The column.
     * @return Whether the column is visible.
     */
    public boolean isColumnVisible(int column) {
        return column >= visibleX && column < visibleX + visibleWidth;
    }

    /**
     * Returns whether a row is visible.
     * 
     * @param row The row.
     * @return Whether the row is visible.
     */
    public boolean isRowVisible(int row) {
        return row >= visibleY && row < visibleY + visibleHeight;
    }

    /**
     * Returns whether a cell is visible.
     * 
     * @param column The column.
     * @param row    The row.
     * @return Whether the cell is visible.
     */
    public boolean isCellVisible(int column, int row) {
        return isColumnVisible(column) && isRowVisible(row);
    }

    /**
     * Returns whether a cell is visible.
     * 
     * @param coordinates The coordinates of the cell.
     * @return Whether the cell is visible.
     */
    public boolean isCellVisible(CellCoordinates coordinates) {
        return isCellVisible(coordinates.getColumn(), coordinates.getRow());
    }

    /**
     * Applies a offset in pixel space.
     * 
     * @param x The x offset.
     * @param y The y offset.
     */
    private void applyOffset(int x, int y) {
        visibleOffsetX += x;
        visibleOffsetY += y;

        trimOffset();
    }

    /**
     * Brings the specified cell into view.
     * 
     * @param column The column.
     * @param row    The row.
     */
    private void bringIntoView(int column, int row) {
        int oldEndColumn = visibleX + visibleWidth - 1;
        int oldEndRow = visibleY + visibleHeight - 1;

        boolean columnInView = column > visibleX && column < visibleX + visibleWidth - 1;
        boolean rowInView = row > visibleY && row < visibleY + visibleHeight - 1;

        if (columnInView && rowInView)
            return;

        if (!columnInView) {
            visibleX = column;
            visibleOffsetX = 0;
        }

        if (!rowInView) {
            visibleY = row;
            visibleOffsetY = 0;
        }

        generateCellMetrics();

        if (!rowInView && row >= oldEndRow)
            applyOffset(0, -(getHeight() - labelHeight - 1) + getRowHeight(row));

        if (!columnInView && column >= oldEndColumn)
            applyOffset(-(getWidth() - labelWidth - 1) + getColumnWidth(column), 0);
    }

    /**
     * Trims the offsets to ensure that the visible range is valid.
     */
    private void trimOffset() {
        int initialVisibleX = visibleX;
        int initialVisibleY = visibleY;

        while (true) {
            int width = calculateColumnWidth(visibleX);

            if (visibleOffsetX < 0) {
                visibleOffsetX += calculateColumnWidth(visibleX - 1) + 1; // 1 pixel for the column border
                visibleX--;
            } else if (visibleOffsetX > width) {
                visibleOffsetX -= width + 1; // 1 pixel for the column border
                visibleX++;
            } else
                break;
        }

        if (visibleX < 0) {
            visibleX = 0;
            visibleOffsetX = 0;
        }

        while (true) {
            int height = calculateRowHeight(visibleY);

            if (visibleOffsetY < 0) {
                visibleOffsetY += calculateRowHeight(visibleY - 1) + 1; // 1 pixel for the row border
                visibleY--;
            } else if (visibleOffsetY > height) {
                visibleOffsetY -= height + 1; // 1 pixel for the row border
                visibleY++;
            } else
                break;
        }

        if (visibleY < 0) {
            visibleY = 0;
            visibleOffsetY = 0;
        }

        if (visibleX != initialVisibleX || visibleY != initialVisibleY)
            generateCellMetrics();
    }

    /**
     * Generates the character metrics.
     * 
     * @param g2d The graphics context.
     */
    private void generateCharacterMetrics(Graphics2D g2d) {
        GlyphVector vector = g2d.getFont().createGlyphVector(g2d.getFontRenderContext(), "0");

        characterWidth = g2d.getFontMetrics().getMaxAdvance();
        characterHeight = (int) vector.getVisualBounds().getHeight();
    }

    /**
     * Generates the cell metrics.
     */
    private void generateCellMetrics() {
        trimOffset();

        cachedColumnWidths = null;
        cachedRowHeights = null;

        List<Integer> columnWidths = new ArrayList<Integer>();
        List<Integer> rowHeights = new ArrayList<Integer>();

        List<Integer> columnOffsets = new ArrayList<Integer>();
        List<Integer> rowOffsets = new ArrayList<Integer>();

        labelHeight = (int) Math.round(Sheet.INITIAL_ROW_HEIGHT * zoom);

        int verticalSpace = getHeight() - labelHeight - 1; // 1 pixel for the top border
        int verticalOffset = -visibleOffsetY;

        visibleHeight = 0;
        do {
            int rowHeight = getRowHeight(visibleY + visibleHeight);

            rowHeights.add(rowHeight);
            rowOffsets.add(verticalOffset);

            verticalOffset += rowHeight + 1; // 1 pixel for the row border
            visibleHeight++;
        } while (verticalOffset < verticalSpace);

        labelWidth = (int) Math.round(16 +
                characterWidth * Math.max(3, Integer.toString(visibleY + visibleHeight).length()));

        int horizontalSpace = getWidth() - labelWidth - 1; // 1 pixel for the left border
        int horizontalOffset = -visibleOffsetX;

        visibleWidth = 0;
        do {
            int columnWidth = getColumnWidth(visibleX + visibleWidth);

            columnWidths.add(columnWidth);
            columnOffsets.add(horizontalOffset);

            horizontalOffset += columnWidth + 1; // 1 pixel for the column border
            visibleWidth++;
        } while (horizontalOffset < horizontalSpace);

        // Update the cache
        cachedColumnWidths = new int[columnWidths.size()];
        for (int i = 0; i < columnWidths.size(); i++)
            cachedColumnWidths[i] = columnWidths.get(i);

        cachedRowHeights = new int[rowHeights.size()];
        for (int i = 0; i < rowHeights.size(); i++)
            cachedRowHeights[i] = rowHeights.get(i);

        cachedColumnOffsets = new int[columnOffsets.size()];
        for (int i = 0; i < columnOffsets.size(); i++)
            cachedColumnOffsets[i] = columnOffsets.get(i);

        cachedRowOffsets = new int[rowOffsets.size()];
        for (int i = 0; i < rowOffsets.size(); i++)
            cachedRowOffsets[i] = rowOffsets.get(i);
    }

    /**
     * Returns the cell coordinates from the specified pixel coordinates.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The cell coordinates that the specified pixel coordinates are in, or
     *         null if the coordinates are not in a cell.
     */
    private CellCoordinates getCellCoordinates(int x, int y) {
        if (x < labelWidth + 1 || y < labelHeight + 1)
            return null;

        int column;
        for (column = 0; column < visibleWidth; column++)
            if (x < getColumnOffset(visibleX + column) + labelWidth + 1 + getColumnWidth(visibleX + column))
                break;

        int row;
        for (row = 0; row < visibleHeight; row++)
            if (y < getRowOffset(visibleY + row) + labelHeight + 1 + getRowHeight(visibleY + row))
                break;

        return new CellCoordinates(visibleX + column, visibleY + row);
    }

    /**
     * Returns the selection.
     * 
     * @return The selection.
     */
    public Selection getSelection() {
        return selection;
    }

    /**
     * Moves the selection cursor to a cell.
     * 
     * @param coordinates The coordinates of the cell to move the cursor to.
     */
    public void moveCursor(CellCoordinates coordinates) {
        if (coordinates == null) {
            selection = null;

            for (ViewListener listener : viewListeners)
                listener.selectionChanged(this, selection);

            return;
        }

        if (selection == null || !selection.isCellSelected(coordinates))
            selection = new Selection(coordinates, coordinates, coordinates);
        else
            selection = new Selection(coordinates, selection.getStart(), selection.getEnd());

        bringIntoView(coordinates.getColumn(), coordinates.getRow());

        for (ViewListener listener : viewListeners)
            listener.selectionChanged(this, selection);
    }

    /**
     * Moves the selection cursor in the specified direction.
     * 
     * @param direction The direction to move the cursor in.
     * @param clamped   Whether the cursor should be clamped to the selection range.
     */
    public void moveCursor(SelectionDirection direction, boolean clamped) {
        if (selection == null)
            return;

        selection = selection.moveCursor(direction, clamped);

        bringIntoView(selection.getCursor().getColumn(), selection.getCursor().getRow());

        for (ViewListener listener : viewListeners)
            listener.selectionChanged(this, selection);
    }

    /**
     * Moves the selection range.
     * 
     * @param x The x movement.
     * @param y The y movement.
     */
    public void moveSelection(int x, int y) {
        if (selection == null)
            return;

        selection = selection.move(x, y);
    }

    /**
     * Paints the column labels.
     * 
     * @param g2d The graphics context.
     */
    private void paintColumnLabels(Graphics2D g2d) {
        Color labelBackground = settings.get(Setting.SHEET_LABEL_BACKGROUND, Color.class);
        Color labelForeground = settings.get(Setting.SHEET_LABEL_FOREGROUND, Color.class);

        Color selectedLabelForeground = settings.get(Setting.SHEET_SELECTED_LABEL_FOREGROUND, Color.class);
        Color selectedLabelBackground = settings.get(Setting.SHEET_SELECTED_LABEL_BACKGROUND, Color.class);

        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        /* ---- ---- */

        for (int column = visibleX; column < visibleX + visibleWidth; column++) {
            int width = getColumnWidth(column);
            int offset = getColumnOffset(column) + labelWidth + 1; // 1 pixel for the left border

            String text = CellCoordinates.getColumnString(column);
            int textWidth = text.length() * characterWidth;

            boolean selected = selection != null && selection.isColumnSelected(column);

            g2d.setColor(selected ? selectedLabelBackground : labelBackground);
            g2d.fillRect(offset, 0, width, labelHeight);

            Graphics2D g2dColumn = (Graphics2D) g2d.create(offset, 0, width, labelHeight);

            g2dColumn.setColor(selected ? selectedLabelForeground : labelForeground);
            g2dColumn.drawString(text, (width - textWidth) / 2, (labelHeight + characterHeight) / 2);

            g2dColumn.dispose();

            if (selected) { // Selected effect
                g2d.setColor(selectedLabelForeground);
                g2d.fillRect(offset - 1, labelHeight - 1, width + 2, 2);
            }

            g2d.setColor(border);
            g2d.fillRect(offset + width, 0, 1, labelHeight);
        }
    }

    /**
     * Paints the row labels.
     * 
     * @param g2d The graphics context.
     */
    private void paintRowLabels(Graphics2D g2d) {
        Color labelBackground = settings.get(Setting.SHEET_LABEL_BACKGROUND, Color.class);
        Color labelForeground = settings.get(Setting.SHEET_LABEL_FOREGROUND, Color.class);

        Color selectedLabelForeground = settings.get(Setting.SHEET_SELECTED_LABEL_FOREGROUND, Color.class);
        Color selectedLabelBackground = settings.get(Setting.SHEET_SELECTED_LABEL_BACKGROUND, Color.class);

        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        /* ---- ---- */

        for (int row = visibleY; row < visibleY + visibleHeight; row++) {
            int height = getRowHeight(row);
            int offset = getRowOffset(row) + labelHeight + 1; // 1 pixel for the top border

            String text = CellCoordinates.getRowString(row);
            int textWidth = text.length() * characterWidth;

            boolean selected = selection != null && selection.isRowSelected(row);

            g2d.setColor(selected ? selectedLabelBackground : labelBackground);
            g2d.fillRect(0, offset, labelWidth, height);

            Graphics2D g2dRow = (Graphics2D) g2d.create(0, offset, labelWidth, height);

            g2dRow.setColor(selected ? selectedLabelForeground : labelForeground);
            g2dRow.drawString(text, (labelWidth - textWidth) / 2, (height + characterHeight) / 2);

            g2dRow.dispose();

            if (selected) { // Selected effect
                g2d.setColor(selectedLabelForeground);
                g2d.fillRect(labelWidth - 1, offset - 1, 2, height + 2);
            }

            g2d.setColor(border);
            g2d.fillRect(0, offset + height, labelWidth, 1);
        }
    }

    /**
     * Paints the borders.
     * 
     * @param g2d The graphics context.
     */
    private void paintBorders(Graphics2D g2d) {
        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        /* ---- ---- */

        g2d.setColor(border);
        g2d.fillRect(0, labelHeight, getWidth(), 1);
        g2d.fillRect(labelWidth, 0, 1, getHeight());

        g2d.fillRect(0, 0, 1, getHeight());
        g2d.fillRect(getWidth() - 1, 0, 1, getHeight());
    }

    /**
     * Paints the overlapping section of the row and column labels.
     * 
     * @param g2d The graphics context.
     */
    private void paintOverlap(Graphics2D g2d) {
        Color labelBackground = settings.get(Setting.SHEET_LABEL_BACKGROUND, Color.class);

        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        /* ---- ---- */

        g2d.setColor(labelBackground);
        g2d.fillRect(0, 0, labelWidth, labelHeight);

        g2d.setColor(border);
        g2d.fillRect(0, labelHeight, labelWidth, 1);
        g2d.fillRect(labelWidth, 0, 1, labelHeight);
    }

    /**
     * Paints the grid.
     * 
     * @param g2d The graphics context.
     */
    private void paintGrid(Graphics2D g2d) {
        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        /* ---- ---- */

        int offset = -visibleOffsetY + labelHeight + 1; // 1 pixel for the top border
        for (int row = visibleY; row < visibleY + visibleHeight; row++) {
            int rowHeight = getRowHeight(row);

            g2d.setColor(border);
            g2d.fillRect(labelWidth, offset + rowHeight, getWidth() - labelWidth, 1);

            offset += rowHeight + 1; // 1 pixel for the row border
        }

        int horizontalOffset = -visibleOffsetX + labelWidth + 1; // 1 pixel for the left border
        for (int column = visibleX; column < visibleX + visibleWidth; column++) {
            int columnWidth = getColumnWidth(column);

            g2d.setColor(border);
            g2d.fillRect(horizontalOffset + columnWidth, labelHeight, 1, getHeight() - labelHeight);

            horizontalOffset += columnWidth + 1; // 1 pixel for the column border
        }
    }

    /**
     * Paints the cells.
     * 
     * @param g2d The graphics context.
     */
    private void paintCells(Graphics2D g2d) {
        Color foreground = settings.get(Setting.SHEET_FOREGROUND, Color.class);

        /* ---- ---- */

        Map<CellCoordinates, CellValue> values = sheet.getValuesInRange(
                new CellCoordinates(visibleX, visibleY),
                new CellCoordinates(visibleX + visibleWidth - 1, visibleY + visibleHeight - 1));

        Graphics2D g2dCell = (Graphics2D) g2d.create();
        for (CellCoordinates coordinates : values.keySet()) {
            int row = coordinates.getRow();
            int column = coordinates.getColumn();

            int width = getColumnWidth(column);
            int height = getRowHeight(row);

            int x = getColumnOffset(column) + labelWidth + 1; // 1 pixel for the left border
            int y = getRowOffset(row) + labelHeight + 1; // 1 pixel for the top border

            CellValue value = values.get(coordinates);

            String displayText = value.getDisplayText();
            boolean isEvaluated = value.hasValue();

            CellContent content = value.getContent();
            Color cellBackground = content == null ? null : content.getBackground();
            Color cellForeground = content == null ? null : content.getForeground();

            CellHorizontalAlignment horizontalAlignment = content == null ? null : content.getHorizontalAlignment();
            CellVerticalAlignment verticalAlignment = content == null ? null : content.getVerticalAlignment();

            if (displayText != null || cellBackground != null) {

                if (cellBackground != null) {
                    g2d.setColor(cellBackground);
                    g2d.fillRect(x, y, width, height);
                }

                if (displayText != null) {
                    g2dCell.setClip(x, y, width, height);

                    int textHeight = characterHeight;

                    int textX;
                    if (horizontalAlignment == CellHorizontalAlignment.LEFT)
                        textX = (int) Math.round(2.0 * zoom);
                    else if (horizontalAlignment == CellHorizontalAlignment.CENTER)
                        textX = (width - g2dCell.getFontMetrics().stringWidth(displayText)) / 2;
                    else if (horizontalAlignment == CellHorizontalAlignment.RIGHT)
                        textX = width - g2dCell.getFontMetrics().stringWidth(displayText)
                                - (int) Math.round(2.0 * zoom);
                    else if (isEvaluated)
                        textX = width - g2dCell.getFontMetrics().stringWidth(displayText)
                                - (int) Math.round(2.0 * zoom);
                    else
                        textX = (int) Math.round(2.0 * zoom);

                    int textY;
                    if (verticalAlignment == CellVerticalAlignment.TOP)
                        textY = textHeight + (int) Math.round(4.0 * zoom);
                    else if (verticalAlignment == CellVerticalAlignment.BOTTOM)
                        textY = height - (int) Math.round(4.0 * zoom);
                    else
                        textY = (height + textHeight) / 2;

                    g2dCell.setColor(cellForeground != null ? cellForeground : foreground);
                    g2dCell.drawString(displayText, x + textX, y + textY);
                    g2dCell.setClip(null);
                }
            }
        }
        g2dCell.dispose();
    }

    /**
     * Paints the selection.
     * 
     * @param g2d The graphics context.
     */
    private void paintSelection(Graphics2D g2d) {
        Color selectionBorderColor = settings.get(Setting.SHEET_SELECTED_BORDER_COLOR, Color.class);
        Color selectionColor = settings.get(Setting.SHEET_SELECTED_CELL_BACKGROUND, Color.class);

        /* ---- ---- */

        if (selection == null)
            return;

        int sx = selection.getLeftColumn();
        int sy = selection.getTopRow();

        int ex = selection.getRightColumn();
        int ey = selection.getBottomRow();

        // Check if the start is after the end of the visible range
        if (sx > visibleX + visibleWidth - 1 || sy > visibleY + visibleHeight - 1)
            return;
        if (ex < visibleX || ey < visibleY)
            return;

        int startX;
        if (!isColumnVisible(sx))
            startX = 0 - 8; // Safety margin
        else
            startX = getColumnOffset(sx) + labelWidth + 1;

        int startY;
        if (!isRowVisible(sy))
            startY = 0 - 8; // Safety margin
        else
            startY = getRowOffset(sy) + labelHeight + 1;

        int endX;
        if (!isColumnVisible(ex))
            endX = getWidth() + 8; // Safety margin
        else
            endX = getColumnOffset(ex) + labelWidth + 1 + getColumnWidth(ex);

        int endY;
        if (!isRowVisible(ey))
            endY = getHeight() + 8; // Safety margin
        else
            endY = getRowOffset(ey) + labelHeight + 1 + getRowHeight(ey);

        g2d.setColor(selectionColor);

        if (isCellVisible(selection.getCursor())) {
            int selectedWidth = getColumnWidth(selection.getCursor().getColumn());
            int selectedHeight = getRowHeight(selection.getCursor().getRow());

            int selectedOffsetX = getColumnOffset(selection.getCursor().getColumn()) + labelWidth + 1;
            int selectedOffsetY = getRowOffset(selection.getCursor().getRow()) + labelHeight + 1;

            g2d.fillRect(startX, startY, selectedOffsetX - startX, endY - startY);
            g2d.fillRect(selectedOffsetX, startY, selectedWidth, selectedOffsetY - startY);
            g2d.fillRect(selectedOffsetX, selectedOffsetY + selectedHeight, selectedWidth, endY
                    - selectedOffsetY - selectedHeight);
            g2d.fillRect(selectedOffsetX + selectedWidth, startY, endX - selectedOffsetX - selectedWidth, endY
                    - startY);
        } else
            g2d.fillRect(startX, startY, endX - startX, endY - startY);

        g2d.setColor(selectionBorderColor);
        g2d.drawRect(startX, startY, endX - startX - 1, endY - startY - 1);
        g2d.drawRect(startX - 1, startY - 1, endX - startX + 1, endY - startY + 1);
        g2d.drawRect(startX - 2, startY - 2, endX - startX + 3, endY - startY + 3);
    }

    /**
     * Paints the view.
     * 
     * @param g The graphics context.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color background = settings.get(Setting.SHEET_BACKGROUND, Color.class);

        Font font = settings.get(Setting.FONT, Font.class);

        /* ---- ---- */

        g2d.setFont(font.deriveFont((float) (font.getSize() * zoom)));

        generateCharacterMetrics(g2d);
        generateCellMetrics();

        /* ---- ---- */

        if (isOpaque()) {
            g2d.setColor(background);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        paintGrid(g2d);
        paintCells(g2d);
        paintSelection(g2d);

        paintBorders(g2d);
        paintColumnLabels(g2d);
        paintRowLabels(g2d);
        paintOverlap(g2d);

        g2d.dispose();

        for (ViewListener listener : viewListeners)
            listener.repainted(this);
    }

    /**
     * Handles a mouse wheel event.
     * 
     * @param e The mouse wheel event.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double rotation = e.getPreciseWheelRotation();

        if (e.isControlDown()) {
            double oldZoom = zoom;
            double newZoom;
            if (rotation < 0)
                newZoom = zoom * Math.pow(ZOOM_SPEED, -rotation);
            else if (rotation > 0)
                newZoom = zoom / Math.pow(ZOOM_SPEED, rotation);
            else
                return;

            setZoom(newZoom);
            newZoom = zoom;

            applyOffset((int) Math.round(e.getX() / oldZoom * newZoom - e.getX()),
                    (int) Math.round(e.getY() / oldZoom * newZoom - e.getY()));

        } else if (e.isShiftDown()) {
            applyOffset((int) (rotation * MOVE_SPEED), 0);
        } else {
            applyOffset(0, (int) (rotation * MOVE_SPEED));
        }

        repaint();
    }

    /**
     * Handles a mouse drag event.
     * 
     * @param e The mouse drag event.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isResizingRow) {
            int offset = getRowOffset(resizeTargetRow) + labelHeight + 1;
            int size = e.getY() - offset + 1; // 1 pixel for the row border

            if (size < 0)
                size = 0;

            sheetRequestListener.setRowHeight(resizeTargetRow, (int) Math.round(size / zoom));
        } else if (isResizingColumn) {
            int offset = getColumnOffset(resizeTargetColumn) + labelWidth + 1;
            int size = e.getX() - offset + 1; // 1 pixel for the column border

            if (size < 0)
                size = 0;

            sheetRequestListener.setColumnWidth(resizeTargetColumn, (int) Math.round(size / zoom));
        } else if (isSelecting) {
            int x = e.getX();
            int y = e.getY();

            if (x < labelWidth + 1) // 1 pixel for the left border
                x = labelWidth + 1;
            if (y < labelHeight + 1) // 1 pixel for the top border
                y = labelHeight + 1;

            CellCoordinates coordinates = getCellCoordinates(x, y);
            if (coordinates == null)
                return;

            selection = new Selection(selection.getCursor(), selection.getStart(), coordinates);

            for (ViewListener listener : viewListeners)
                listener.selectionChanged(this, selection);

            repaint();
        } else if (isDragging) {
            applyOffset(dragX - e.getX(), dragY - e.getY());

            dragX = e.getX();
            dragY = e.getY();

            repaint();
        }
    }

    /**
     * Handles a mouse move event.
     * 
     * @param e The mouse move event.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getX() < labelWidth + 1 && e.getY() > labelHeight + 1) {// 1 pixel for the left border
            int row;
            for (row = visibleY; row < visibleY + visibleHeight; row++)
                if (e.getY() < getRowOffset(row) + labelHeight + 1 + getRowHeight(row))
                    break;

            int offset = getRowOffset(row) + labelHeight + 1;
            int delta = e.getY() - offset;

            if ((delta < RESIZE_THRESHOLD && row > 0) || (delta > getRowHeight(row) - RESIZE_THRESHOLD))
                setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
            else
                setCursor(Cursor.getDefaultCursor());
        } else if (e.getY() < labelHeight + 1 && e.getX() > labelWidth + 1) {// 1 pixel for the top border
            int column;
            for (column = visibleX; column < visibleX + visibleWidth; column++)
                if (e.getX() < getColumnOffset(column) + labelWidth + 1 + getColumnWidth(column))
                    break;

            int offset = getColumnOffset(column) + labelWidth + 1;
            int delta = e.getX() - offset;

            if ((delta < RESIZE_THRESHOLD && column > 0) || (delta > getColumnWidth(column) - RESIZE_THRESHOLD))
                setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
            else
                setCursor(Cursor.getDefaultCursor());
        } else
            setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Handles a mouse click event.
     * 
     * @param e The mouse click event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Handles a mouse press event.
     * 
     * @param e The mouse press event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        requestFocusInWindow();

        CellCoordinates coordinates = getCellCoordinates(e.getX(), e.getY());
        if (coordinates == null && e.getButton() == MouseEvent.BUTTON1) {
            if (e.getX() < labelWidth + 1 && e.getY() > labelHeight + 1) {// 1 pixel for the left border
                int row;
                for (row = visibleY; row < visibleY + visibleHeight; row++)
                    if (e.getY() < getRowOffset(row) + labelHeight + 1 + getRowHeight(row))
                        break;

                int offset = getRowOffset(row) + labelHeight + 1;
                int delta = e.getY() - offset;

                if (delta < RESIZE_THRESHOLD && row > 0) {
                    isResizingRow = true;
                    resizeTargetRow = row - 1;
                } else if (delta > getRowHeight(row) - RESIZE_THRESHOLD) {
                    isResizingRow = true;
                    resizeTargetRow = row;
                }
            } else if (e.getY() < labelHeight + 1 && e.getX() > labelWidth + 1) {// 1 pixel for the top border
                int column;
                for (column = visibleX; column < visibleX + visibleWidth; column++)
                    if (e.getX() < getColumnOffset(column) + labelWidth + 1 + getColumnWidth(column))
                        break;

                int offset = getColumnOffset(column) + labelWidth + 1;
                int delta = e.getX() - offset;

                if (delta < RESIZE_THRESHOLD && column > 0) {
                    isResizingColumn = true;
                    resizeTargetColumn = column - 1;
                } else if (delta > getColumnWidth(column) - RESIZE_THRESHOLD) {
                    isResizingColumn = true;
                    resizeTargetColumn = column;
                }
            }
        } else if (coordinates != null && e.getButton() == MouseEvent.BUTTON1) {
            isSelecting = true;

            if (e.isShiftDown() && selection != null && selection.getStart().equals(selection.getCursor()))
                selection = new Selection(selection.getCursor(), selection.getStart(), coordinates);
            else
                selection = new Selection(coordinates, coordinates, coordinates);

            for (ViewListener listener : viewListeners)
                listener.selectionChanged(this, selection);

            repaint();
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            isDragging = true;

            dragX = e.getX();
            dragY = e.getY();
        }
    }

    /**
     * Handles a mouse release event.
     * 
     * @param e The mouse release event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        isResizingColumn = false;
        isResizingRow = false;

        isSelecting = false;
        isDragging = false;
    }

    /**
     * Handles a mouse enter event.
     * 
     * @param e The mouse enter event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Handles a mouse exit event.
     * 
     * @param e The mouse exit event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Handles a sheet content change event.
     * 
     * @param sheet The sheet that changed.
     */
    @Override
    public void contentChanged(Sheet sheet) {
        repaint();
    }

    /**
     * Handles a sheet action event.
     * 
     * @param sheet The sheet on which the action was performed.
     */
    @Override
    public void actionPerformed(Sheet sheet) {
    }

    /**
     * Handles a sheet rename event.
     * 
     * @param sheet The sheet that was renamed.
     */
    @Override
    public void nameChanged(Sheet sheet, String name) {
    }

    /**
     * Handles a row resize event.
     * 
     * @param sheet  The sheet on which the row was resized.
     * @param row    The row that was resized.
     * @param height The new height of the row.
     */
    @Override
    public void rowResized(Sheet sheet, int row, int height) {
        repaint();
    }

    /**
     * Handles a column resize event.
     * 
     * @param sheet  The sheet on which the column was resized.
     * @param column The column that was resized.
     * @param width  The new width of the column.
     */
    @Override
    public void columnResized(Sheet sheet, int column, int width) {
        repaint();
    }

    /**
     * Handles a key typed event.
     * This component will does not care for such events, and will pass them to the
     * listeners.
     * 
     * @param e The key typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (isPassingFocus)
            for (ViewListener listener : viewListeners)
                listener.keyEmitted(this, e);
    }

    /**
     * Handles a key pressed event.
     * 
     * @param e The key pressed event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_ALT
                || e.getKeyCode() == KeyEvent.VK_META || e.getKeyCode() == KeyEvent.VK_SHIFT)
            return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                moveCursor(null);
                return;

            case KeyEvent.VK_UP:
                if (e.isShiftDown())
                    moveSelection(0, -1);
                else
                    moveCursor(SelectionDirection.UP, false);
                return;
            case KeyEvent.VK_DOWN:
                if (e.isShiftDown())
                    moveSelection(0, 1);
                else
                    moveCursor(SelectionDirection.DOWN, false);
                return;
            case KeyEvent.VK_LEFT:
                if (e.isShiftDown())
                    moveSelection(-1, 0);
                else
                    moveCursor(SelectionDirection.LEFT, false);
                return;
            case KeyEvent.VK_RIGHT:
                if (e.isShiftDown())
                    moveSelection(1, 0);
                else
                    moveCursor(SelectionDirection.RIGHT, false);
                return;

            case KeyEvent.VK_ENTER:
                if (e.isShiftDown())
                    moveCursor(SelectionDirection.UP, true);
                else
                    moveCursor(SelectionDirection.DOWN, true);
                return;
            case KeyEvent.VK_TAB:
                if (e.isShiftDown())
                    moveCursor(SelectionDirection.LEFT, true);
                else
                    moveCursor(SelectionDirection.RIGHT, true);
                return;

            case KeyEvent.VK_DELETE:
                sheetRequestListener.clearArea(selection.getTopLeft(), selection.getWidth(), selection.getHeight());
                return;

            case KeyEvent.VK_C:
                if (e.isControlDown())
                    sheetRequestListener.copy(selection.getTopLeft(), selection.getWidth(), selection.getHeight());
                else
                    break;
                return;

            case KeyEvent.VK_X:
                if (e.isControlDown())
                    sheetRequestListener.cut(selection.getTopLeft(), selection.getWidth(), selection.getHeight());
                else
                    break;
                return;

            case KeyEvent.VK_V:
                if (e.isControlDown()) {
                    Area clipboard = sheetRequestListener.getClipboard();
                    if (clipboard == null)
                        return;

                    sheetRequestListener.paste(selection.getTopLeft(), selection.getWidth(), selection.getHeight());

                    if (clipboard.getWidth() == 1 && clipboard.getHeight() == 1)
                        return;

                    selection = new Selection(selection.getTopLeft(), selection.getTopLeft(),
                            new CellCoordinates(selection.getTopLeft().getColumn() + clipboard.getWidth() - 1,
                                    selection.getTopLeft().getRow() + clipboard.getHeight() - 1));

                    for (ViewListener listener : viewListeners)
                        listener.selectionChanged(this, selection);
                } else
                    break;
                return;

            case KeyEvent.VK_Z:
                if (e.isControlDown())
                    sheetRequestListener.undo();
                else
                    break;
                return;

            case KeyEvent.VK_Y:
                if (e.isControlDown())
                    sheetRequestListener.redo();
                else
                    break;
                return;
        }

        isPassingFocus = true;

        this.setFocusable(false);
        this.setFocusable(true);

        for (ViewListener listener : viewListeners)
            listener.keyEmitted(this, e);
    }

    /**
     * Handles a key released event.
     * 
     * @param e The key released event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Handles a focus gained event.
     * 
     * @param e The focus gained event.
     */
    @Override
    public void focusGained(FocusEvent e) {
    }

    /**
     * Handles a focus lost event.
     * 
     * @param e The focus lost event.
     */
    @Override
    public void focusLost(FocusEvent e) {
        isPassingFocus = false;
    }
}
