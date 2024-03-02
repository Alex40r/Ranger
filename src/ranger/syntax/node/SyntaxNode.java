package ranger.syntax.node;

import java.util.LinkedList;
import java.util.List;

import ranger.syntax.EvaluationContext;

/**
 * Abstract class representing a syntax node.
 */
public abstract class SyntaxNode {
    /**
     * The children of this node.
     */
    private final List<SyntaxNode> children;

    /**
     * Constructs a new syntax node.
     * 
     * @param isLeaf Whether this node is a leaf node.
     */
    public SyntaxNode(boolean isLeaf) {
        this.children = isLeaf ? null : new LinkedList<SyntaxNode>();
    }

    /**
     * Returns whether this node is a leaf node.
     * 
     * @return Whether this node is a leaf node.
     */
    public boolean isLeaf() {
        return this.children == null;
    }

    /**
     * Returns the number of children of this node.
     * 
     * @return The number of children of this node.
     */
    public int getChildrenCount() {
        return this.children.size();
    }

    /**
     * Returns the child at the specified index.
     * 
     * @param index The index of the child.
     * @return The child at the specified index.
     */
    public SyntaxNode getChild(int index) {
        return this.children.get(index);
    }

    /**
     * Adds a child to this node.
     * 
     * @param child The child to add.
     */
    public void addChild(SyntaxNode child) {
        this.children.add(child);
    }

    /**
     * Removes the child at the specified index.
     * 
     * @param index The index of the child to remove.
     */
    public void removeChild(int index) {
        this.children.remove(index);
    }

    /**
     * Evaluates the syntax node.
     *
     * @param context The evaluation context.
     * @return The result of the evaluation. 
     */
    public abstract double evaluate(EvaluationContext context);
}
