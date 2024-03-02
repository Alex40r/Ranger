package ranger.syntax.token;

import ranger.sheet.cell.CellCoordinates;

/**
 * Class representing a reference token.
 */
public class ReferenceToken implements Token {
    /**
     * The coordinates of the cell.
     */
    private final CellCoordinates coordinates;

    /**
     * Constructs a new reference token.
     * 
     * @param coordinates The coordinates of the cell.
     */
    public ReferenceToken(CellCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Returns the coordinates of the cell.
     * 
     * @return The coordinates of the cell.
     */
    public CellCoordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Returns the string representation of this reference token.
     * 
     * @return The string representation of this reference token.
     */
    @Override
    public String toString() {
        return coordinates.toString();
    }
}
