package ranger.sheet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;

/**
 * Class representing an area of content.
 */
public class Area implements Iterable<Entry<CellCoordinates, CellContent>> {
    /**
     * The origin of the area.
     */
    private CellCoordinates origin;

    /**
     * The width of the area.
     */
    private int width;

    /**
     * The height of the area.
     */
    private int height;

    /**
     * The contents of the area.
     */
    private Map<CellCoordinates, CellContent> contents;

    /**
     * Constructs a new area.
     * 
     * @param origin   The origin.
     * @param width    The width.
     * @param height   The height.
     * @param contents The contents.
     */
    public Area(CellCoordinates origin, int width, int height, Map<CellCoordinates, CellContent> contents) {
        if (origin == null)
            origin = new CellCoordinates(0, 0);
        this.origin = origin;

        this.width = width;
        this.height = height;

        this.contents = new HashMap<CellCoordinates, CellContent>();
        if (contents != null)
            for (Entry<CellCoordinates, CellContent> entry : contents.entrySet())
                this.contents.put(entry.getKey(), new CellContent(entry.getValue()));
    }

    /**
     * Constructs a new area from the specified area.
     * 
     * @param other The other area.
     */
    public Area(Area other) {
        this(other.origin, other.width, other.height, other.contents);
    }

    /**
     * Returns the origin.
     * 
     * @return The origin.
     */
    public CellCoordinates getOrigin() {
        return origin;
    }

    /**
     * Returns the width.
     * 
     * @return The width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height.
     * 
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the content of the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     * @return The content.
     */
    public CellContent getCellContent(CellCoordinates coordinates) {
        return contents.get(coordinates);
    }

    /**
     * Sets the content of the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     * @param content     The content.
     */
    public void setCellContent(CellCoordinates coordinates, CellContent content) {
        if (content == null || content.isEmpty())
            contents.remove(coordinates);
        else
            contents.put(coordinates, content);
    }

    /**
     * Returns the iterator over the contents.
     * 
     * @return The iterator over the contents.
     */
    @Override
    public Iterator<Entry<CellCoordinates, CellContent>> iterator() {
        return contents.entrySet().iterator();
    }
}
