package ranger.sheet.action;

import ranger.sheet.Area;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellStorage;

/**
 * Class representing a set area action.
 * The action is responsible for setting an area's content.
 */
public class SetAreaAction implements SheetAction {
    /**
     * The top left coordinates of the area.
     */
    private CellCoordinates coordinates;

    /**
     * The area to set.
     */
    private Area area;

    /**
     * The old area.
     */
    private Area oldArea;

    /**
     * Constructs a new set area action.
     * 
     * @param coordinates The top left coordinates of the area.
     * @param area        The area to set.
     */
    public SetAreaAction(CellCoordinates coordinates, Area area) {
        this.coordinates = coordinates;

        this.area = new Area(area);
    }

    /**
     * Clones the action.
     * 
     * @return The cloned action.
     */
    @Override
    public SheetAction clone() {
        return new SetAreaAction(coordinates, area);
    }

    /**
     * Does the action.
     * 
     * @param storage The storage in which to do the action.
     */
    @Override
    public void doAction(CellStorage storage) {
        oldArea = storage.getArea(coordinates, area.getWidth(), area.getHeight());
        storage.setArea(coordinates, area);
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
