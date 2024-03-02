package ranger.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class representing a sparse tree of arbitrary dimensions.
 * 
 * @param <T> The type of the values of the tree.
 */
public class Tree<T> implements Iterable<Entry<Coordinates, T>> {
    /**
     * The maximum size of a axis of the tree. Due to Java's only-signed integers,
     * this is 2^31 - 1 instead of 2^32 - 1.
     */
    public static final int MAX_TREE_SIZE = (1 << 31) - 1;

    /**
     * The height of the tree in levels. A height of 32 provides a maximum size of
     * 2^32 - 1.
     */
    private static final int TREE_HEIGHT = 32;

    /**
     * The height of each node in levels. A height of 2 means that each node has 4
     * children per axis.
     */
    private static final int NODE_HEIGHT = 2;

    /**
     * The number of children per node per axis.
     */
    private static final int NODE_SIZE = 1 << NODE_HEIGHT;

    /**
     * The mask used to extract the index of a child from a coordinate.
     */
    private static final int NODE_MASK = NODE_SIZE - 1;

    /**
     * The root node of the tree. This is null if the tree is empty.
     */
    private TreeNode<T> root;

    /**
     * The number of dimensions of the tree.
     */
    private int dimensions;

    /**
     * The number of children per node, which is equal to NODE_SIZE^dimensions.
     */
    private int children;

    /**
     * Constructs a new tree with the specified number of dimensions.
     * 
     * @param dimensions The number of dimensions.
     */
    public Tree(int dimensions) {
        this.dimensions = dimensions;

        this.children = 1;
        for (int i = 0; i < dimensions; i++)
            this.children *= NODE_SIZE;

        this.root = null;
    }

    /**
     * Returns the number of dimensions of the tree.
     * 
     * @return The number of dimensions of the tree.
     */
    public int getDimensions() {
        return dimensions;
    }

    /**
     * Returns whether the tree is empty.
     * 
     * @return Whether the tree is empty.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the value at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     * @return The value at the specified coordinates, or null if there is no value
     *         at the specified coordinates.
     * @throws IllegalArgumentException If the coordinates do not have the correct
     *                                  number of dimensions, or are out of bounds.
     */
    public T get(Coordinates coordinates) {
        int[] rawCoordinates = coordinates.getCoordinates();

        if (rawCoordinates.length != dimensions)
            throw new IllegalArgumentException("Coordinates must have " + dimensions + " dimensions.");

        for (int i = 0; i < dimensions; i++)
            if (rawCoordinates[i] < 0 || rawCoordinates[i] > MAX_TREE_SIZE)
                throw new IndexOutOfBoundsException(
                        "Coordinate number " + i + " (" + rawCoordinates[i] + ") is out of bounds.");

        if (root == null)
            return null;

        TreeNode<T> node = root;

        for (int depth = 0; depth < TREE_HEIGHT; depth += NODE_HEIGHT) {
            if (!node.hasActiveChildren())
                break;

            TreeNode<T> child = node.getChild(getChildIndex(rawCoordinates, depth));
            if (child == null)
                break;
            node = child;

            if (isLeafDepth(depth))
                return node.getValue();
        }

        return null;
    }

    /**
     * Sets the value at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     * @param value       The value.
     * @throws IllegalArgumentException If the coordinates do not have the correct
     *                                  number of dimensions, or are out of bounds.
     */
    public void set(Coordinates coordinates, T value) {
        if (value == null) {
            remove(coordinates);
            return;
        }

        int[] rawCoordinates = coordinates.getCoordinates();

        if (rawCoordinates.length != dimensions)
            throw new IllegalArgumentException("Coordinates must have " + dimensions + " dimensions.");

        for (int i = 0; i < dimensions; i++)
            if (rawCoordinates[i] < 0 || rawCoordinates[i] > MAX_TREE_SIZE)
                throw new IndexOutOfBoundsException(
                        "Coordinate number " + i + " (" + rawCoordinates[i] + ") is out of bounds.");

        if (root == null)
            root = new TreeNode<T>(children);

        TreeNode<T> node = root;

        for (int depth = 0; depth < TREE_HEIGHT; depth += NODE_HEIGHT) {
            int index = getChildIndex(rawCoordinates, depth);
            TreeNode<T> child = node.getChild(index);

            if (child == null) {
                child = new TreeNode<T>(isLeafDepth(depth) ? 0 : children);
                node.setChild(index, child);
            }

            node = child;

            if (isLeafDepth(depth)) {
                node.setValue(value);
                return;
            }
        }
    }

    /**
     * Removes the value at the specified coordinates, and trims the tree if
     * possible.
     * 
     * @param coordinates The coordinates of the value to remove.
     * @throws IllegalArgumentException If the coordinates do not have the correct
     *                                  number of dimensions, or are out of bounds.
     */
    public void remove(Coordinates coordinates) {
        int[] rawCoordinates = coordinates.getCoordinates();

        if (rawCoordinates.length != dimensions)
            throw new IllegalArgumentException("Coordinates must have " + dimensions + " dimensions.");

        for (int i = 0; i < dimensions; i++)
            if (rawCoordinates[i] < 0 || rawCoordinates[i] > MAX_TREE_SIZE)
                throw new IndexOutOfBoundsException(
                        "Coordinate number " + i + " (" + rawCoordinates[i] + ") is out of bounds.");

        if (root == null)
            return;

        List<TreeNode<T>> path = new ArrayList<TreeNode<T>>((TREE_HEIGHT + NODE_HEIGHT - 1) / NODE_HEIGHT);

        TreeNode<T> node = root;

        for (int depth = 0; depth < TREE_HEIGHT; depth += NODE_HEIGHT) {
            if (!node.hasActiveChildren())
                break;

            TreeNode<T> child = node.getChild(getChildIndex(rawCoordinates, depth));
            if (child == null)
                break;
            node = child;
            path.add(node);

            if (isLeafDepth(depth)) {
                node.setValue(null);
                break;
            }
        }

        for (int i = path.size() - 1; i >= 0; i--) {
            int index = getChildIndex(rawCoordinates, i * NODE_HEIGHT);
            node = path.get(i);

            if (node.hasActiveChildren())
                break;

            if (i == 0)
                root.setChild(index, null);
            else
                path.get(i - 1).setChild(index, null);
        }

        if (!root.hasActiveChildren())
            root = null;
    }

    /**
     * Returns the range of values between the specified coordinates.
     * 
     * @param start The start coordinates.
     * @param end   The end coordinates.
     * @return The range of values between the specified coordinates.
     * @throws IllegalArgumentException If the coordinates do not have the correct
     *                                  number of dimensions, or are out of bounds,
     *                                  or if the start coordinates are greater
     *                                  than the end coordinates.
     */
    public Map<Coordinates, T> getRange(Coordinates start, Coordinates end) {
        Map<Coordinates, T> output = new HashMap<Coordinates, T>();

        getRange(start, end, output);

        return output;
    }

    /**
     * Returns the range of values between the specified coordinates, and adds them
     * to the specified map.
     * 
     * @param start  The start coordinates.
     * @param end    The end coordinates.
     * @param output The map to add the values to.
     */
    public void getRange(Coordinates start, Coordinates end, Map<Coordinates, T> output) {
        int[] rawStart = start.getCoordinates();
        int[] rawEnd = end.getCoordinates();

        if (rawStart.length != dimensions)
            throw new IllegalArgumentException("Start coordinates must have " + dimensions
                    + " dimensions.");

        if (rawEnd.length != dimensions)
            throw new IllegalArgumentException("End coordinates must have " + dimensions
                    + " dimensions.");

        for (int i = 0; i < dimensions; i++) {
            if (rawStart[i] < 0 || rawStart[i] > MAX_TREE_SIZE)
                throw new IndexOutOfBoundsException(
                        "Start coordinate number " + i + " (" + rawStart[i] + ") is out of bounds.");

            if (rawEnd[i] < 0 || rawEnd[i] > MAX_TREE_SIZE)
                throw new IndexOutOfBoundsException(
                        "End coordinate number " + i + " (" + rawEnd[i] + ") is out of bounds.");

            if (rawStart[i] > rawEnd[i])
                throw new IllegalArgumentException("Start coordinate number " + i + " ("
                        + rawStart[i] + ") is greater than end coordinate number " + i + " ("
                        + rawEnd[i] + ").");
        }

        getRange(root, new int[dimensions], 0, rawStart, rawEnd, output);
    }

    /**
     * Recursively explores the tree in a given range to find all non-null values.
     * 
     * @param node          The current node.
     * @param globalCurrent The current coordinates of the node (in global).
     * @param depth         The current depth.
     * @param globalStart   The start coordinates of the range (in global).
     * @param globalEnd     The end coordinates of the range (in global).
     * @param output        The map to add the values to.
     */
    private void getRange(TreeNode<T> node, int[] globalCurrent, int depth, int[] globalStart,
            int[] globalEnd, Map<Coordinates, T> output) {
        if (node == null)
            return;

        int scaling = (TREE_HEIGHT - depth - NODE_HEIGHT);

        int[] localStart = new int[dimensions];
        int[] localEnd = new int[dimensions];

        for (int i = 0; i < dimensions; i++) {
            localStart[i] = Math.min(Math.max(globalStart[i] - globalCurrent[i] >> scaling, 0), NODE_MASK);
            localEnd[i] = Math.min(Math.max(globalEnd[i] - globalCurrent[i] >> scaling, 0), NODE_MASK);
        }

        int[] childGlobalCurrent = new int[dimensions];

        boolean finished = false;
        for (int[] current = localStart.clone(); !finished; finished = !moveNext(current, localStart, localEnd)) {
            TreeNode<T> child = node.getChild(getLocalChildIndex(current, depth));

            if (child == null)
                continue;

            for (int i = 0; i < dimensions; i++)
                childGlobalCurrent[i] = (current[i] << scaling) + globalCurrent[i];

            if (child.getValue() != null)
                output.put(new Coordinates(childGlobalCurrent), child.getValue());
            else if (child.hasActiveChildren())
                getRange(child, childGlobalCurrent, depth + NODE_HEIGHT, globalStart, globalEnd, output);
        }
    }

    /**
     * Moves the current coordinates to the next set of coordinates. Equivalent to
     * iterating over all possible coordinates in the range.
     * 
     * @param current The current coordinates.
     * @param start   The start coordinates.
     * @param end     The end coordinates.
     * @return Whether the coordinates were successfully moved.
     */
    private boolean moveNext(int[] current, int[] start, int[] end) {
        for (int i = 0; i < dimensions; i++) {
            if (current[i] < end[i]) {
                current[i]++;
                break;
            }

            if (i == dimensions - 1)
                return false;

            current[i] = start[i];
        }

        return true;
    }

    /**
     * Returns the index of the child to reach the specified global target
     * coordinates.
     * 
     * @param coordinates The global target coordinates.
     * @param depth       The current depth of the node.
     * @return The index of the child within the node.
     * 
     */
    private int getChildIndex(int[] coordinates, int depth) {
        int index = 0;

        for (int i = 0; i < dimensions; i++)
            index |= ((coordinates[i] >> (TREE_HEIGHT - depth - NODE_HEIGHT)) & NODE_MASK) << (i
                    * NODE_HEIGHT);

        return index;
    }

    /**
     * Returns the index of the child to reach the specified local target
     * coordinates.
     * 
     * @param coordinates The local target coordinates.
     * @param depth       The current depth of the node.
     * @return The index of the child within the node.
     * 
     */
    private int getLocalChildIndex(int[] coordinates, int depth) {
        int index = 0;

        for (int i = 0; i < dimensions; i++)
            index |= (coordinates[i] & NODE_MASK) << (i * NODE_HEIGHT);

        return index;
    }

    /**
     * Returns whether the specified depth is a leaf depth (i.e. the depth at which
     * the tree has no more children, and can only contain values).
     * 
     * @param depth The depth.
     * @return Whether the specified depth is a leaf depth.
     */
    private boolean isLeafDepth(int depth) {
        return depth + NODE_HEIGHT >= TREE_HEIGHT;
    }

    /**
     * Returns an iterator over the entries of the tree.
     * 
     * @return An iterator over the entries of the tree.
     */
    @Override
    public Iterator<Entry<Coordinates, T>> iterator() {
        return new TreeIterator<T>(this);
    }

    /**
     * Returns the string representation of the tree.
     * 
     * @return The string representation of the tree.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Entry<Coordinates, T> entry : this)
            builder.append(entry.getKey()).append(" = ").append(entry.getValue()).append('\n');

        return builder.toString();
    }
}
