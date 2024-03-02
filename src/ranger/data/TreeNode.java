package ranger.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a tree's node.
 * 
 * @param <T> The type of the value of the node.
 */
public class TreeNode<T> {
    /**
     * The value of the node. This is null if the node is not a leaf.
     * Leaves are only at the bottom of the tree, and all leaves have the same
     * depth.
     */
    private T value;

    /**
     * The number of active children. Only non-null children are active.
     */
    private int activeChildren;

    /**
     * The list of children. This is null if the node is a leaf.
     */
    private List<TreeNode<T>> children;

    /**
     * Constructs a new tree node.
     * 
     * @param children The number of children the node has. If this is 0, the
     *                 node is a leaf.
     */
    public TreeNode(int children) {
        value = null;

        this.activeChildren = 0;
        this.children = null;
        if (children == 0)
            return;

        this.children = new ArrayList<TreeNode<T>>(children);
        for (int i = 0; i < children; i++)
            this.children.add(null);
    }

    /**
     * Returns the value of the node.
     * 
     * @return The value of the node.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value of the node.
     * 
     * @param value The value of the node.
     * @throws IllegalStateException If the node is not a leaf.
     */
    public void setValue(T value) {
        if (children != null)
            throw new IllegalStateException("Non-leaf nodes cannot have values.");

        this.value = value;
    }

    /**
     * Returns whether the node can have children.
     * 
     * @return Whether the node can have children.
     */
    public boolean canHaveChildren() {
        return children != null;
    }

    /**
     * Returns whether the node has active children (non-null children).
     * 
     * @return Whether the node has active children.
     */
    public boolean hasActiveChildren() {
        return activeChildren > 0;
    }

    /**
     * Returns the number of children the node can have.
     * 
     * @return The number of children the node can have.
     */
    public int getChildrenCount() {
        if (children == null)
            return 0;

        return children.size();
    }

    /**
     * Returns the number of active children (non-null children) the node has.
     * 
     * @return The number of active children the node has.
     */
    public int getActiveChildrenCount() {
        return activeChildren;
    }

    /**
     * Returns the child at the specified index. This will always return null
     * if the node is a leaf.
     * 
     * @param index The index of the child.
     * @return The child at the specified index.
     */
    public TreeNode<T> getChild(int index) {
        if (children == null)
            return null;

        return children.get(index);
    }

    /**
     * Sets the child at the specified index.
     * 
     * @param index The index of the child.
     * @param child The child to set.
     * @throws IllegalStateException If the node is a leaf.
     */
    public void setChild(int index, TreeNode<T> child) {
        if (children == null)
            throw new IllegalStateException("Tree node cannot have children.");

        if (children.get(index) != null)
            activeChildren--;

        children.set(index, child);

        if (child != null)
            activeChildren++;
    }
}
