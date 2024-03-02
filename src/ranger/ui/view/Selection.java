package ranger.ui.view;

import ranger.sheet.cell.CellCoordinates;

/**
 * Class representing a selection.
 */
public class Selection {
    /**
     * Coordinates of the cursor within the selection.
     */
    private CellCoordinates cursor;

    /**
     * Coordinates of the start of the selection.
     */
    private CellCoordinates start;

    /**
     * Coordinates of the end of the selection.
     */
    private CellCoordinates end;

    /**
     * Constructs a new selection.
     * 
     * @param cursor Coordinates of the cursor within the selection.
     * @param start  Coordinates of the start of the selection.
     * @param end    Coordinates of the end of the selection.
     */
    public Selection(CellCoordinates cursor, CellCoordinates start, CellCoordinates end) {
        this.cursor = cursor;

        this.start = start;
        this.end = end;

        if (!isCellSelected(cursor))
            throw new IllegalArgumentException("Cursor is not within selection");
    }

    /**
     * Returns the coordinates of the cursor within the selection.
     * 
     * @return The coordinates of the cursor within the selection.
     */
    public CellCoordinates getCursor() {
        return cursor;
    }

    /**
     * Returns the coordinates of the start of the selection.
     * 
     * @return The coordinates of the start of the selection.
     */
    public CellCoordinates getStart() {
        return start;
    }

    /**
     * Returns the coordinates of the end of the selection.
     * 
     * @return The coordinates of the end of the selection.
     */
    public CellCoordinates getEnd() {
        return end;
    }

    /**
     * Returns the coordinates of the top left corner of the selection.
     * 
     * @return The coordinates of the top left corner of the selection.
     */
    public CellCoordinates getTopLeft() {
        return new CellCoordinates(getLeftColumn(), getTopRow());
    }

    /**
     * Returns the coordinates of the bottom right corner of the selection.
     * 
     * @return The coordinates of the bottom right corner of the selection.
     */
    public CellCoordinates getBottomRight() {
        return new CellCoordinates(getRightColumn(), getBottomRow());
    }

    /**
     * Returns the index of the top row of the selection.
     * 
     * @return The index of the top row of the selection.
     */
    public int getTopRow() {
        return Math.min(start.getRow(), end.getRow());
    }

    /**
     * Returns the index of the bottom row of the selection.
     * 
     * @return The index of the bottom row of the selection.
     */
    public int getBottomRow() {
        return Math.max(start.getRow(), end.getRow());
    }

    /**
     * Returns the index of the left column of the selection.
     * 
     * @return The index of the left column of the selection.
     */
    public int getLeftColumn() {
        return Math.min(start.getColumn(), end.getColumn());
    }

    /**
     * Returns the index of the right column of the selection.
     * 
     * @return The index of the right column of the selection.
     */
    public int getRightColumn() {
        return Math.max(start.getColumn(), end.getColumn());
    }

    /**
     * Returns the width of the selection.
     * 
     * @return The width of the selection.
     */
    public int getWidth() {
        return getRightColumn() - getLeftColumn() + 1;
    }

    /**
     * Returns the height of the selection.
     * 
     * @return The height of the selection.
     */
    public int getHeight() {
        return getBottomRow() - getTopRow() + 1;
    }

    /**
     * Returns whether the specified row is selected.
     * 
     * @param row The row.
     * @return Whether the specified row is selected.
     */
    public boolean isRowSelected(int row) {
        return row >= getTopRow() && row <= getBottomRow();
    }

    /**
     * Returns whether the specified column is selected.
     * 
     * @param column The column.
     * @return Whether the specified column is selected.
     */
    public boolean isColumnSelected(int column) {
        return column >= getLeftColumn() && column <= getRightColumn();
    }

    /**
     * Returns whether the specified cell is selected.
     * 
     * @param coordinates The coordinates.
     * @return Whether the specified cell is selected.
     */
    public boolean isCellSelected(CellCoordinates coordinates) {
        return isRowSelected(coordinates.getRow()) && isColumnSelected(coordinates.getColumn());
    }

    /**
     * Moves the cursor in the specified direction.
     * 
     * @param direction The direction.
     * @param clamped   Whether the cursor should be clamped to the selection.
     * @return The new selection.
     */
    public Selection moveCursor(SelectionDirection direction, boolean clamped) {
        if (!clamped) {
            int cx = cursor.getX();
            int cy = cursor.getY();

            switch (direction) {
                case UP:
                    cy--;
                    break;

                case DOWN:
                    cy++;
                    break;

                case LEFT:
                    cx--;
                    break;

                case RIGHT:
                    cx++;
                    break;
            }

            if (cx < 0)
                cx = 0;
            if (cy < 0)
                cy = 0;

            if (isColumnSelected(cx) && isRowSelected(cy))
                return new Selection(
                        new CellCoordinates(cx, cy),
                        start, end);
            else
                return new Selection(
                        new CellCoordinates(cx, cy),
                        new CellCoordinates(cx, cy), new CellCoordinates(cx, cy));
        } else {

            int sx = getLeftColumn();
            int sy = getTopRow();

            int ex = getRightColumn();
            int ey = getBottomRow();

            if (sx == ex && sy == ey) {
                int nx = sx;
                int ny = sy;

                switch (direction) {
                    case UP:
                        nx = sx;
                        ny = sy - 1;
                        break;

                    case DOWN:
                        nx = sx;
                        ny = sy + 1;
                        break;

                    case LEFT:
                        nx = sx - 1;
                        ny = sy;
                        break;

                    case RIGHT:
                        nx = sx + 1;
                        ny = sy;
                        break;
                }

                if (nx < 0)
                    nx = 0;
                if (ny < 0)
                    ny = 0;

                return new Selection(
                        new CellCoordinates(nx, ny),
                        new CellCoordinates(nx, ny), new CellCoordinates(nx, ny));
            } else {
                int nx = cursor.getX();
                int ny = cursor.getY();

                switch (direction) {
                    case UP:
                        ny--;
                        if (ny < sy) {
                            ny = ey;
                            nx--;

                            if (nx < sx)
                                nx = ex;
                        }
                        break;

                    case DOWN:
                        ny++;
                        if (ny > ey) {
                            ny = sy;
                            nx++;

                            if (nx > ex)
                                nx = sx;
                        }
                        break;

                    case LEFT:
                        nx--;
                        if (nx < sx) {
                            nx = ex;
                            ny--;

                            if (ny < sy)
                                ny = ey;
                        }
                        break;

                    case RIGHT:
                        nx++;
                        if (nx > ex) {
                            nx = sx;
                            ny++;

                            if (ny > ey)
                                ny = sy;
                        }
                        break;
                }

                return new Selection(
                        new CellCoordinates(nx, ny),
                        start, end);
            }
        }
    }

    /**
     * Moves the selection range by the specified amount.
     * 
     * @param x The x amount.
     * @param y The y amount.
     * @return The new selection.
     */
    public Selection move(int x, int y) {
        int sx = start.getX();
        int sy = start.getY();

        int ex = end.getX();
        int ey = end.getY();

        int cx = cursor.getX();
        int cy = cursor.getY();

        if (cx == sx)
            ex += x;
        else if (cx == ex)
            sx += x;

        if (cy == sy)
            ey += y;
        else if (cy == ey)
            sy += y;

        if (sx < 0)
            sx = 0;
        if (sy < 0)
            sy = 0;

        if (ex < 0)
            ex = 0;
        if (ey < 0)
            ey = 0;

        return new Selection(
                getCursor(),
                new CellCoordinates(sx, sy), new CellCoordinates(ex, ey));
    }
}
