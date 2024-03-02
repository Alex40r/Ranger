package ranger.syntax;

import java.util.HashSet;
import java.util.Set;

import ranger.sheet.cell.CellCoordinates;
import ranger.syntax.node.ExpressionNode;
import ranger.syntax.node.ReferenceNode;
import ranger.syntax.node.SyntaxNode;
import ranger.syntax.parser.ExpressionParser;

/**
 * Class representing a syntax tree.
 */
public class SyntaxTree {
    /**
     * The root node of the syntax tree.
     */
    private SyntaxNode root;

    /**
     * Constructs a new syntax tree.
     * 
     * @param root             The root node of the syntax tree.
     * @param expressionParser The expression parser.
     */
    public SyntaxTree(SyntaxNode root, ExpressionParser expressionParser) {
        this.root = root;

        parse(root, expressionParser);
    }

    /**
     * Returns a set of cell coordinates that are referenced by this syntax tree.
     * 
     * @return A set of cell coordinates that are referenced by this syntax tree.
     */
    public Set<CellCoordinates> getReferences() {
        Set<CellCoordinates> references = new HashSet<CellCoordinates>();

        getReferences(root, references);

        return references;
    }

    /**
     * Adds the cell coordinates that are referenced by the specified node to the
     * specified set.
     * 
     * @param node       The node.
     * @param references The set of cell coordinates.
     */
    private void getReferences(SyntaxNode node, Set<CellCoordinates> references) {
        if (node instanceof ReferenceNode)
            references.add(((ReferenceNode) node).getCoordinates());

        if (node == null || node.isLeaf())
            return;

        for (int i = 0; i < node.getChildrenCount(); i++)
            getReferences(node.getChild(i), references);
    }

    /**
     * Parses the specified node using the specified expression parser.
     * This is a recursive method.
     * 
     * @param node             The node.
     * @param expressionParser The expression parser.
     */
    private void parse(SyntaxNode node, ExpressionParser expressionParser) {
        if (!node.isLeaf())
            for (int i = 0; i < node.getChildrenCount(); i++)
                parse(node.getChild(i), expressionParser);

        if (node instanceof ExpressionNode)
            expressionParser.parse((ExpressionNode) node);
    }

    /**
     * Evaluates the syntax tree using the specified evaluation context.
     * 
     * @param context The evaluation context.
     * @return The result of the evaluation.
     */
    public double evaluate(EvaluationContext context) {
        double result = root.evaluate(context);
        if (Double.isNaN(result))
            throw new ArithmeticException("Expression resulted in NaN.");
        if (Double.isInfinite(result))
            throw new ArithmeticException("Expression resulted in infinity.");
        return result;
    }
}
