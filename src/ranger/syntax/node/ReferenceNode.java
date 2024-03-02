package ranger.syntax.node;

import ranger.sheet.cell.CellCoordinates;
import ranger.syntax.EvaluationContext;
import ranger.syntax.token.ReferenceToken;

/**
 * Class representing a reference node.
 */
public class ReferenceNode extends SyntaxNode {
    /**
     * The coordinates of the cell.
     */
    public final CellCoordinates coordinates;

    /**
     * Constructs a new reference node.
     * 
     * @param coordinates The coordinates of the cell.
     */
    public ReferenceNode(CellCoordinates coordinates) {
        super(true);

        this.coordinates = coordinates;
    }

    /**
     * Constructs a new reference node.
     * 
     * @param token The token representing the reference.
     */
    public ReferenceNode(ReferenceToken token) {
        this(token.getCoordinates());
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
     * Returns the string representation of this reference node.
     * 
     * @return The string representation of this reference node.
     */
    @Override
    public String toString() {
        return coordinates.toString();
    }

    /**
     * Evaluates the reference node.
     * 
     * @param context The evaluation context.
     * @return The result of the evaluation.
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return context.getValue(coordinates);
    }
}
