package ranger.sheet.cell;

import java.util.HashSet;
import java.util.Set;

import ranger.format.Formatter;
import ranger.sheet.SheetEvaluationContext;
import ranger.syntax.SyntaxTree;
import ranger.syntax.block.ExpressionBlock;
import ranger.syntax.lexer.Lexer;
import ranger.syntax.node.NumberNode;

/**
 * Class representing a cell.
 */
public class Cell {
    /**
     * The storage in which the cell is stored.
     */
    private CellStorage storage;

    /**
     * The coordinates of the cell.
     */
    private CellCoordinates coordinates;

    /**
     * The cell's content.
     */
    private CellContent content;

    /**
     * The cell's dependencies.
     */
    private Set<CellCoordinates> dependencies;

    /**
     * The cell's syntax tree.
     * This can be null if the cell failed to parse, or does not have a mathematical
     * expression.
     */
    private SyntaxTree tree;

    /**
     * The cell's value.
     * This can be null if the cell failed to evaluate or to parse.
     */
    private Double value;

    /**
     * The cell's formatted value.
     * This can be null if the cell failed to format the value or to evaluate.
     */
    private String formatted;

    /**
     * The cell's error.
     * This can be null if the cell does not have an error.
     */
    private CellError error;

    /**
     * Constructs a new cell.
     * 
     * @param storage     The storage in which the cell is stored.
     * @param coordinates The coordinates of the cell.
     */
    public Cell(CellStorage storage, CellCoordinates coordinates) {
        this.storage = storage;
        this.coordinates = coordinates;

        this.content = new CellContent();
    }

    /**
     * Returns whether the cell has an error.
     * 
     * @return Whether the cell has an error.
     */
    public boolean hasError() {
        return error != null;
    }

    /**
     * Returns the cell's error.
     * 
     * @return The cell's error.
     */
    public CellError getError() {
        return error;
    }

    /**
     * Returns the cell's dependencies, as a copy.
     * 
     * @return The cell's dependencies.
     */
    public Set<CellCoordinates> getDependencies() {
        if (dependencies != null)
            return new HashSet<CellCoordinates>(dependencies);

        return null;
    }

    /**
     * Returns whether the cell is evaluated.
     * 
     * @return Whether the cell is evaluated.
     */
    public boolean isEvaluated() {
        return value != null;
    }

    /**
     * Returns the cell's value.
     * 
     * @return The cell's value.
     */
    public double getValue() {
        if (!isEvaluated())
            throw new IllegalStateException(
                    "Cannot get value of the cell '" + coordinates + "' because it is not evaluated.");

        return value;
    }

    /**
     * Returns the cell's formatted value.
     * 
     * @return The cell's formatted value.
     */
    public String getFormattedValue() {
        if (!isEvaluated())
            throw new IllegalStateException(
                    "Cannot get formatted value of the cell '" + coordinates + "' because it is not evaluated.");

        return formatted;
    }

    /**
     * Parses the cell's expression again.
     */
    public void reparse() {
        String expression = content.getExpression();

        this.tree = null;
        this.dependencies = null;

        this.value = null;
        this.formatted = null;

        this.error = null;

        if (expression == null) {
            tree = new SyntaxTree(new NumberNode(0.0), storage.getExpressionParser());
            return;
        }

        try {
            tree = new SyntaxTree(new NumberNode(Double.parseDouble(expression)), storage.getExpressionParser());
            return;
        } catch (NumberFormatException e) {
        }

        if (expression.startsWith("=")) {
            try {
                tree = new SyntaxTree(ExpressionBlock.getRoot(Lexer.getTokens(expression.substring(1))),
                        storage.getExpressionParser());

                dependencies = tree.getReferences();
                return;
            } catch (Exception e) {
                error = CellError.INVALID_EXPRESSION;
            }

            return;
        }
    }

    /**
     * Formats the cell's value again.
     */
    public void reformat() {
        formatted = null;

        if (!isEvaluated())
            throw new IllegalStateException(
                    "Cannot reformat the cell '" + coordinates + "' because it is not evaluated.");

        try {
            if (content.getFormat() == null)
                formatted = value.toString();
            else
                formatted = Formatter.format(content.getFormat(), value);
        } catch (Exception e) {
            formatted = null;
        }
    }

    /**
     * Tries to evaluate the cell.
     * 
     * @return Whether the cell was evaluated.
     */
    public boolean evaluate() {
        if (isEvaluated())
            return false;

        if (tree == null)
            return false;

        if (dependencies != null)
            for (CellCoordinates coordinates : dependencies)
                if (!storage.isEvaluated(coordinates)) {
                    error = CellError.DEPENDENCY_NOT_EVALUATED;
                    return false;
                }

        try {
            value = tree.evaluate(new SheetEvaluationContext(storage.getFunctionRegistrar(), storage));
            error = null;

            reformat();

            return true;
        } catch (ArithmeticException e) {
            error = CellError.ARITHMETIC_ERROR;
            return false;
        } catch (IllegalArgumentException e) {
            error = CellError.ARGUMENT_ERROR;
            return false;
        } catch (Exception e) {
            error = CellError.FAILED_TO_EVALUATE;
            return false;
        }
    }

    /**
     * Tries to deevaluate the cell.
     * 
     * @return Whether the cell was deevaluated.
     */
    public boolean deevaluate() {
        if (!isEvaluated())
            return false;

        error = CellError.DEPENDENCY_NOT_EVALUATED;
        value = null;
        formatted = null;

        return true;
    }

    /**
     * Returns whether the cell has a cycle.
     * 
     * @return Whether the cell has a cycle.
     */
    public boolean checkForCycle() {
        return checkForCycle(coordinates, new HashSet<CellCoordinates>());
    }

    /**
     * Returns whether the cell has a cycle. This is a recursive method.
     * 
     * @param current The current cell.
     * @param visited The visited cells.
     * @return Whether the cell has a cycle.
     */
    private boolean checkForCycle(CellCoordinates current, Set<CellCoordinates> visited) {
        visited.add(current);

        Set<CellCoordinates> dependencies = storage.getDependencies(current);
        if (dependencies == null)
            return false;

        for (CellCoordinates dependency : dependencies) {
            if (dependency.equals(coordinates))
                return true;

            if (visited.contains(dependency))
                continue;

            if (checkForCycle(dependency, visited))
                return true;
        }

        return false;
    }

    /**
     * Returns the content of the cell.
     * 
     * @return The content of the cell.
     */
    public CellContent getContent() {
        return new CellContent(content);
    }

    /**
     * Sets the content of the cell.
     * 
     * @param content The content of the cell.
     */
    public void setContent(CellContent content) {
        String oldExpression = this.content.getExpression();
        String newExpression = content.getExpression();

        String oldFormat = this.content.getFormat();
        String newFormat = content.getFormat();

        this.content = new CellContent(content);

        if ((oldExpression == null && newExpression != null)
                || (oldExpression != null && !oldExpression.equals(newExpression)))
            reparse();

        if (isEvaluated())
            if ((oldFormat == null && newFormat != null) || (oldFormat != null && !oldFormat.equals(newFormat)))
                reformat();
    }

    /**
     * Returns whether the cell has content.
     * 
     * @return Whether the cell has content.
     */
    public boolean hasContent() {
        return !content.isEmpty();
    }

    /**
     * Returns the string representation of the cell.
     * 
     * @return The string representation of the cell.
     */
    @Override
    public String toString() {
        return coordinates + ": " + content.getExpression() + " = " + value;
    }
}
