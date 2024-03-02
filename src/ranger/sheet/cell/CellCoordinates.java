package ranger.sheet.cell;

import ranger.data.Coordinates;

/**
 * Class representing cell coordinates in a sheet.
 */
public class CellCoordinates extends Coordinates {
    /**
     * The x coordinate.
     */
    private final int x;

    /**
     * The y coordinate.
     */
    private final int y;

    /**
     * Constructs a new cell coordinates.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public CellCoordinates(int x, int y) {
        super(new int[] { x, y });

        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Invalid cell coordinates (" + x + ", " + y + ")");

        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new cell coordinates from other cell coordinates.
     * 
     * @param other The other coordinates.
     */
    public CellCoordinates(CellCoordinates other) {
        super(new int[] { other.x, other.y });

        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Constructs a new cell coordinates from coordinates. The coordinates must have
     * exactly two dimensions.
     * 
     * @param other The other coordinates.
     */
    public CellCoordinates(Coordinates other) {
        super(new int[] { other.getCoordinate(0), other.getCoordinate(1) });

        if (other.getDimensions() != 2)
            throw new IllegalArgumentException("Too many dimensions in the coordinates.");

        this.x = other.getCoordinate(0);
        this.y = other.getCoordinate(1);
    }

    /**
     * Constructs a new cell coordinates from a string.
     * 
     * @param coordinates The coordinates string.
     */
    public CellCoordinates(String coordinates) {
        this(parse(coordinates));
    }

    /**
     * Returns the x coordinate.
     * 
     * @return The x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the column.
     * 
     * @return The column.
     */
    public int getColumn() {
        return x;
    }

    /**
     * Returns the column's string representation.
     * 
     * @param x The column.
     * @return The column's string representation.
     */
    public static String getColumnString(int x) {
        int value = x + 1;

        StringBuilder builder = new StringBuilder();

        while (value > 0) {
            value--;
            builder.insert(0, (char) ('A' + value % 26));
            value /= 26;
        }

        return builder.toString();
    }

    /**
     * Returns the column from its string representation.
     * 
     * @param column The column's string representation.
     * @return The column.
     * @throws NumberFormatException If the column is invalid.
     */
    public static int getColumnFromString(String column) {
        int result = 0;

        for (int i = 0; i < column.length(); i++) {
            if (result > Integer.MAX_VALUE / 26)
                throw new NumberFormatException(
                        "Invalid cell coordinates '" + column + "' (column too large). Format: [A-Z]+[0-9]+");

            result = result * 26 + (column.charAt(i) - 'A' + 1);
        }

        return result - 1;
    }

    /**
     * Returns the y coordinate.
     * 
     * @return The y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the row.
     * 
     * @return The row.
     */
    public int getRow() {
        return y;
    }

    /**
     * Returns the row's string representation.
     * 
     * @param y The row.
     * @return The row's string representation.
     * @throws NumberFormatException If the row is invalid.
     */
    public static String getRowString(int y) {
        return Integer.toString(y + 1);
    }

    /**
     * Returns the row from its string representation.
     * 
     * @param row The row's string representation.
     * @return The row.
     */
    public static int getRowFromString(String row) {
        return Integer.parseInt(row) - 1;
    }

    /**
     * Returns whether the character is supported for column and row values.
     * 
     * @param character The character.
     * @return Whether the character is supported for column and row values.
     */
    public static boolean isSupportedCharacter(char character) {
        return Character.isUpperCase(character) || Character.isDigit(character);
    }

    /**
     * Returns the string representation of the cell coordinates.
     * 
     * @return The string representation of the cell coordinates.
     */
    @Override
    public String toString() {
        return getColumnString(x) + getRowString(y);
    }

    /**
     * Parses the string representation of the cell coordinates.
     * 
     * @param coordinates The string representation of the cell coordinates.
     * @return The cell coordinates.
     * @throws NumberFormatException If the coordinates are invalid.
     */
    public static CellCoordinates parse(String coordinates) {
        int columnLength = 0;

        while (columnLength < coordinates.length())
            if (Character.isUpperCase(coordinates.charAt(columnLength)))
                columnLength++;
            else
                break;

        if (columnLength == 0)
            throw new NumberFormatException(
                    "Invalid cell coordinates '" + coordinates + "' (missing column). Format: [A-Z]+[0-9]+");

        int column = getColumnFromString(coordinates.substring(0, columnLength));

        int rowLength = 0;

        while (columnLength + rowLength < coordinates.length())
            if (Character.isDigit(coordinates.charAt(columnLength + rowLength)))
                rowLength++;
            else
                break;

        if (rowLength == 0)
            throw new NumberFormatException(
                    "Invalid cell coordinates '" + coordinates + "' (missing row). Format: [A-Z]+[0-9]+");

        int row = getRowFromString(coordinates.substring(columnLength, columnLength + rowLength));

        if (columnLength + rowLength < coordinates.length())
            throw new NumberFormatException(
                    "Invalid cell coordinates '" + coordinates + "' (additional characters). Format: [A-Z]+[0-9]+");

        return new CellCoordinates(column, row);
    }
}
