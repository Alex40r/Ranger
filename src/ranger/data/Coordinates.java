package ranger.data;

/**
 * Class representing a set of coordinates.
 */
public class Coordinates {
    /**
     * The array of coordinates.
     */
    private int[] coordinates;

    /**
     * Constructs a new set of coordinates.
     * 
     * @param coordinates The array of coordinates to use. The array is cloned.
     */
    public Coordinates(int[] coordinates) {
        this.coordinates = coordinates.clone();
    }

    /**
     * Returns the number of dimensions.
     * 
     * @return The number of dimensions.
     */
    public int getDimensions() {
        return coordinates.length;
    }

    /**
     * Returns the coordinate at the specified dimension.
     * 
     * @param dimension The dimension.
     * @return The coordinate at the specified dimension.
     */
    public int getCoordinate(int dimension) {
        return coordinates[dimension];
    }

    /**
     * Returns the coordinates as an array.
     * 
     * @return The coordinates as an array. The array is a clone.
     */
    public int[] getCoordinates() {
        return coordinates.clone();
    }

    /**
     * Returns the string representation of the coordinates.
     * 
     * @return The string representation of the coordinates.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append('[');

        for (int i = 0; i < coordinates.length; i++) {
            if (i > 0)
                builder.append(", ");

            builder.append(coordinates[i]);
        }

        builder.append(']');

        return builder.toString();
    }

    /**
     * Compares this object to another object.
     * 
     * @param obj The object to compare to.
     * @return Whether the objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Coordinates))
            return false;

        Coordinates other = (Coordinates) obj;

        if (coordinates.length != other.coordinates.length)
            return false;

        for (int i = 0; i < coordinates.length; i++)
            if (coordinates[i] != other.coordinates[i])
                return false;

        return true;
    }

    /**
     * Returns the hash code of the object. The hash code is not guaranteed at all to
     * be unique nor is it garanteed to be evenly distributed.
     * 
     * @return The hash code of the object.
     */
    @Override
    public int hashCode() {
        int hash = 0;

        for (int i = 0; i < coordinates.length; i++)
            hash ^= coordinates[i] * 0x9e3779b9 + (hash << 6) + (hash >> 2) + 1;

        return hash;
    }
}
