package ranger.sheet.action;

import ranger.sheet.Area;
import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellStorage;

/**
 * Class representing a fill area action.
 * The action is responsible for filling an area with a specified content.
 */
public class FillAreaAction implements SheetAction {
    /**
     * The top left coordinates of the area.
     */
    private CellCoordinates coordinates;

    /**
     * The width of the area.
     */
    private int width;

    /**
     * The height of the area.
     */
    private int height;

    /**
     * The content to fill the area with.
     */
    private CellContent content;

    /**
     * The old area.
     */
    private Area oldArea;

    /**
     * Constructs a new fill area action.
     * 
     * @param coordinates The top left coordinates of the area.
     * @param width       The width of the area.
     * @param height      The height of the area.
     * @param content     The content to fill the area with.
     */
    public FillAreaAction(CellCoordinates coordinates, int width, int height, CellContent content) {
        this.coordinates = coordinates;

        this.width = width;
        this.height = height;

        this.content = content;
    }

    /**
     * Clones the action.
     * 
     * @return The cloned action.
     */
    @Override
    public SheetAction clone() {
        return new FillAreaAction(coordinates, width, height, content);
    }

    /**
     * Does the action.
     * 
     * @param storage The storage in which to do the action.
     */
    @Override
    public void doAction(CellStorage storage) {
        oldArea = storage.getArea(coordinates, width, height);
        storage.fillArea(coordinates, width, height, content);
    }

    /**
     * Undoes the action.
     * 
     * @param storage The storage in which to undo the action.
     */
    @Override
    public void undoAction(CellStorage storage) {
        storage.setArea(coordinates, oldArea);
    }
}
