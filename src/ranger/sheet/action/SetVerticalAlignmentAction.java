package ranger.sheet.action;

import java.util.Map;

import ranger.sheet.Area;
import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellVerticalAlignment;
import ranger.sheet.cell.CellStorage;

/**
 * Class representing a set vertical alignment action.
 * The action is responsible for setting a range of cells' vertical alignment.
 */
public class SetVerticalAlignmentAction implements SheetAction {
    /**
     * The coordinates of the start cell.
     */
    private CellCoordinates start;

    /**
     * The coordinates of the end cell.
     */
    private CellCoordinates end;

    /**
     * The vertical alignment to set.
     */
    private CellVerticalAlignment verticalAlignment;

    /**
     * The old area's contents.
     */
    private Area oldArea;

    /**
     * Constructs a new set vertical alignment action.
     * 
     * @param start             The coordinates of the start cell.
     * @param end               The coordinates of the end cell.
     * @param verticalAlignment The vertical alignment to set.
     */
    public SetVerticalAlignmentAction(CellCoordinates start, CellCoordinates end,
            CellVerticalAlignment verticalAlignment) {
        this.start = start;
        this.end = end;

        this.verticalAlignment = verticalAlignment;
    }

    /**
     * Clones the action.
     * 
     * @return The cloned action.
     */
    public SheetAction clone() {
        return new SetVerticalAlignmentAction(start, end, verticalAlignment);
    }

    /**
     * Returns the width of the area.
     * 
     * @return The width of the area.
     */
    private int getWidth() {
        return end.getColumn() - start.getColumn() + 1;
    }

    /**
     * Returns the height of the area.
     * 
     * @return The height of the area.
     */
    private int getHeight() {
        return end.getRow() - start.getRow() + 1;
    }

    /**
     * Does the action.
     * 
     * @param storage The storage in which to do the action.
     */
    @Override
    public void doAction(CellStorage storage) {
        oldArea = storage.getArea(start, getWidth(), getHeight());

        Map<CellCoordinates, CellContent> newCells = storage.getContents(start, end);

        for (int row = start.getRow(); row <= end.getRow(); row++)
            for (int column = start.getColumn(); column <= end.getColumn(); column++) {
                CellCoordinates coordinates = new CellCoordinates(column, row);
                CellContent content = newCells.get(coordinates);
                if (content == null)
                    content = new CellContent();

                content.setVerticalAlignment(verticalAlignment);
                newCells.put(coordinates, content);
            }

        storage.setContents(newCells);
    }

    /**
     * Undoes the action.
     * 
     * @param storage The storage in which to undo the action.
     */
    @Override
    public void undoAction(CellStorage storage) {
        storage.setArea(start, oldArea);
    }
}
