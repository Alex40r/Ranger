package ranger.data;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Class representing an iterator over all the values of a tree.
 * 
 * @param <T> The type of the values of the tree.
 */
public class TreeIterator<T> implements Iterator<Entry<Coordinates, T>> {
    /**
     * The iterator over the values of the tree.
     */
    private Iterator<Entry<Coordinates, T>> iterator;

    /**
     * Constructs a new tree iterator.
     * 
     * @param tree The tree to iterate over.
     */
    public TreeIterator(Tree<T> tree) {
        int[] start = new int[tree.getDimensions()];

        int[] end = new int[tree.getDimensions()];
        for (int i = 0; i < tree.getDimensions(); i++)
            end[i] = Tree.MAX_TREE_SIZE;

        iterator = tree.getRange(new Coordinates(start), new Coordinates(end)).entrySet().iterator();
    }

    /**
     * Returns whether there are more values to iterate over.
     * 
     * @return Whether there are more values to iterate over.
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the next value.
     * 
     * @return The next value.
     */
    @Override
    public Entry<Coordinates, T> next() {
        return iterator.next();
    }
}
