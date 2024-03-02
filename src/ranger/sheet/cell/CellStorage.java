package ranger.sheet.cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import ranger.data.Coordinates;
import ranger.data.Tree;
import ranger.function.FunctionRegistrar;
import ranger.sheet.Area;
import ranger.syntax.parser.ExpressionParser;

/**
 * Class representing a cell storage.
 */
public class CellStorage {
    /**
     * The function registrar used to evaluate functions.
     */
    private FunctionRegistrar functionRegistrar;

    /**
     * The expression parser used to parse expressions.
     */
    private ExpressionParser expressionParser;

    /**
     * The tree containing the cells of the storage.
     */
    private Tree<Cell> cells;

    /**
     * The dependencies of the cells.
     */
    private Map<CellCoordinates, Set<CellCoordinates>> dependents;

    /**
     * The listeners of the storage.
     */
    private List<CellStorageListener> listeners;

    /**
     * Constructs a new cell storage.
     * 
     * @param functionRegistrar The function registrar used to evaluate functions.
     * @param expressionParser  The expression parser used to parse expressions.
     */
    public CellStorage(FunctionRegistrar functionRegistrar, ExpressionParser expressionParser) {
        this.functionRegistrar = functionRegistrar;
        this.expressionParser = expressionParser;

        cells = new Tree<Cell>(2);

        dependents = new HashMap<CellCoordinates, Set<CellCoordinates>>();

        listeners = new ArrayList<CellStorageListener>();
    }

    /**
     * Adds a listener to the storage.
     * 
     * @param listener The listener to add.
     */
    public void addListener(CellStorageListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from the storage.
     * 
     * @param listener The listener to remove.
     */
    public void removeListener(CellStorageListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns the function registrar used to evaluate functions.
     * 
     * @return The function registrar used to evaluate functions.
     */
    public FunctionRegistrar getFunctionRegistrar() {
        return functionRegistrar;
    }

    /**
     * Sets the function registrar used to evaluate functions.
     * 
     * @param functionRegistrar The function registrar used to evaluate functions.
     */
    public void setFunctionRegistrar(FunctionRegistrar functionRegistrar) {
        this.functionRegistrar = functionRegistrar;

        reevaluateAll();
    }

    /**
     * Returns the expression parser used to parse expressions.
     * 
     * @return The expression parser used to parse expressions.
     */
    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }

    /**
     * Sets the expression parser used to parse expressions.
     * 
     * @param expressionParser The expression parser used to parse expressions.
     */
    public void setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;

        reparseAll();
    }

    /**
     * Returns the content at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     * @return The content at the specified coordinates.
     */
    public CellContent getContent(CellCoordinates coordinates) {
        Cell cell = cells.get(coordinates);
        if (cell == null)
            return null;

        return cell.getContent();
    }

    /**
     * Returns the contents of the cells in the specified range.
     * 
     * @param start The start coordinates.
     * @param end   The end coordinates.
     * @return The contents of the cells in the specified range.
     */
    public Map<CellCoordinates, CellContent> getContents(CellCoordinates start, CellCoordinates end) {
        Map<CellCoordinates, CellContent> contents = new HashMap<CellCoordinates, CellContent>();

        for (Entry<Coordinates, Cell> entry : cells.getRange(start, end).entrySet()) {
            CellCoordinates coordinates = new CellCoordinates(entry.getKey());
            Cell cell = entry.getValue();

            contents.put(coordinates, cell.getContent());
        }

        return contents;
    }

    /**
     * Sets the content at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     * @param content     The content.
     */
    public void setContent(CellCoordinates coordinates, CellContent content) {
        passiveSetContent(coordinates, content);

        evaluate(coordinates);

        for (CellStorageListener listener : listeners)
            listener.contentChanged(this);
    }

    /**
     * Sets the contents of the cells with their matching coordinates.
     * 
     * @param contents The pairs of coordinates and contents.
     */
    public void setContents(Map<CellCoordinates, CellContent> contents) {
        boolean contentChanged = false;
        for (Entry<CellCoordinates, CellContent> entry : contents.entrySet())
            if (!contentEquals(entry.getKey(), entry.getValue())) {
                contentChanged = true;
                break;
            }

        if (!contentChanged)
            return;

        for (Entry<CellCoordinates, CellContent> entry : contents.entrySet())
            passiveSetContent(entry.getKey(), entry.getValue());

        for (Entry<CellCoordinates, CellContent> entry : contents.entrySet())
            evaluate(entry.getKey());

        for (CellStorageListener listener : listeners)
            listener.contentChanged(this);
    }

    /**
     * Sets the contents of a cell, but does not evaluate the cell.
     * 
     * @param coordinates The coordinates.
     * @param content     The content.
     */
    private void passiveSetContent(CellCoordinates coordinates, CellContent content) {
        if (content == null)
            content = new CellContent();

        CellContent currentContent = getContent(coordinates);

        boolean contentChanged = currentContent == null ? content != null : !currentContent.equals(content);
        if (!contentChanged)
            return;

        String currentExpression = currentContent == null ? null : currentContent.getExpression();
        String expression = content == null ? null : content.getExpression();

        boolean expressionChanged = currentExpression == null ? expression != null
                : !currentExpression.equals(expression);

        if (expressionChanged) {
            deevaluate(coordinates);
            clearDependencies(coordinates);
        }

        Cell cell = cells.get(coordinates);
        if (cell == null) {
            if (content.isEmpty())
                return;

            cell = new Cell(this, coordinates);
            cells.set(coordinates, cell);
        }

        cell.setContent(content);

        if (!cell.hasContent())
            cells.remove(coordinates);

        if (expressionChanged)
            updateDependencies(coordinates);
    }

    /**
     * Returns the area at the specified coordinates.
     * 
     * @param start  The start coordinates.
     * @param width  The width of the area.
     * @param height The height of the area.
     * @return The area at the specified coordinates.
     */
    public Area getArea(CellCoordinates start, int width, int height) {
        Map<CellCoordinates, CellContent> contents = getContents(start,
                new CellCoordinates(start.getX() + width - 1, start.getY() + height - 1));

        return new Area(start, width, height, contents);
    }

    /**
     * Sets the area at the specified coordinates.
     * 
     * @param start The start coordinates.
     * @param area  The area.
     */
    public void setArea(CellCoordinates start, Area area) {
        Map<Coordinates, Cell> cells = this.cells.getRange(start,
                new CellCoordinates(start.getX() + area.getWidth() - 1, start.getY() + area.getHeight() - 1));

        for (Coordinates coordinates : cells.keySet())
            passiveSetContent(new CellCoordinates(coordinates), null);

        for (Coordinates coordinates : cells.keySet())
            evaluate(new CellCoordinates(coordinates));

        for (Entry<CellCoordinates, CellContent> entry : area) {
            CellCoordinates coordinates = new CellCoordinates(
                    start.getX() + entry.getKey().getX() - area.getOrigin().getX(),
                    start.getY() + entry.getKey().getY() - area.getOrigin().getY());
            CellContent content = entry.getValue();

            passiveSetContent(coordinates, content);
        }

        for (Entry<CellCoordinates, CellContent> entry : area) {
            CellCoordinates coordinates = new CellCoordinates(
                    start.getX() + entry.getKey().getX() - area.getOrigin().getX(),
                    start.getY() + entry.getKey().getY() - area.getOrigin().getY());

            evaluate(coordinates);
        }

        for (CellStorageListener listener : listeners)
            listener.contentChanged(this);
    }

    /**
     * Fills the area at the specified coordinates with the specified content.
     * 
     * @param coordinates The coordinates.
     * @param width       The width of the area.
     * @param height      The height of the area.
     * @param content     The content.
     */
    public void fillArea(CellCoordinates coordinates, int width, int height, CellContent content) {
        for (int x = coordinates.getX(); x < coordinates.getX() + width; x++)
            for (int y = coordinates.getY(); y < coordinates.getY() + height; y++)
                passiveSetContent(new CellCoordinates(x, y), content);

        for (int x = coordinates.getX(); x < coordinates.getX() + width; x++)
            for (int y = coordinates.getY(); y < coordinates.getY() + height; y++)
                evaluate(new CellCoordinates(x, y));

        for (CellStorageListener listener : listeners)
            listener.contentChanged(this);
    }

    /**
     * Returns whether the content at the specific coordinates equals the specified
     * content.
     * 
     * @param coordinates The coordinates.
     * @param content     The content.
     * @return Whether the content at the specific coordinates equals the specified
     *         content.
     */
    private boolean contentEquals(CellCoordinates coordinates, CellContent content) {
        CellContent currentContent = getContent(coordinates);
        if (currentContent == null)
            return content == null;

        return currentContent.equals(content);
    }

    /**
     * Reparses the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     */
    private void reparse(CellCoordinates coordinates) {
        Cell cell = cells.get(coordinates);
        if (cell == null)
            return;

        deevaluate(coordinates);
        clearDependencies(coordinates);

        cell.reparse();

        updateDependencies(coordinates);
    }

    /**
     * Evaluates the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     */
    private void evaluate(CellCoordinates coordinates) {
        Cell cell = cells.get(coordinates);
        if (cell == null)
            evaluateDependents(coordinates);
        else if (cell.evaluate())
            evaluateDependents(coordinates);
    }

    /**
     * Reparses all cells.
     */
    private void reparseAll() {
        for (Entry<Coordinates, Cell> entry : cells)
            reparse(new CellCoordinates(entry.getKey()));

        for (Entry<Coordinates, Cell> entry : cells)
            evaluate(new CellCoordinates(entry.getKey()));
    }

    /**
     * Evaluates all cells.
     */
    private void reevaluateAll() {
        for (Entry<Coordinates, Cell> entry : cells)
            deevaluate(new CellCoordinates(entry.getKey()));

        for (Entry<Coordinates, Cell> entry : cells)
            evaluate(new CellCoordinates(entry.getKey()));
    }

    /**
     * Tries to evaluate the dependents of the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     */
    private void evaluateDependents(CellCoordinates coordinates) {
        Set<CellCoordinates> dependents = this.dependents.get(coordinates);
        if (dependents == null)
            return;

        for (CellCoordinates dependent : dependents)
            evaluate(dependent);
    }

    /**
     * Deevaluates the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     */
    private void deevaluate(CellCoordinates coordinates) {
        Cell cell = cells.get(coordinates);
        if (cell == null)
            deevaluateDependents(coordinates);
        else if (cell.deevaluate())
            deevaluateDependents(coordinates);
    }

    /**
     * Tries to deevaluate the dependents of the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     */
    private void deevaluateDependents(CellCoordinates coordinates) {
        Set<CellCoordinates> dependents = this.dependents.get(coordinates);
        if (dependents == null)
            return;

        for (CellCoordinates dependent : dependents)
            deevaluate(dependent);
    }

    /**
     * Returns the dependencies of the cell at the specified coordinates.
     * 
     * @param current The coordinates.
     * @return The dependencies of the cell at the specified coordinates.
     */
    public Set<CellCoordinates> getDependencies(CellCoordinates current) {
        Cell cell = cells.get(current);
        if (cell == null)
            return null;

        return cell.getDependencies();
    }

    /**
     * Clears the dependencies of the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     */
    private void clearDependencies(CellCoordinates coordinates) {
        Set<CellCoordinates> dependencies = this.dependents.get(coordinates);
        if (dependencies == null)
            return;

        dependencies = new HashSet<CellCoordinates>(dependencies); // Copy to avoid concurrent modification

        for (CellCoordinates dependency : dependencies) {
            Set<CellCoordinates> dependentsOnCell = this.dependents.get(dependency);
            if (dependentsOnCell != null)
                dependentsOnCell.remove(coordinates);
        }
    }

    /**
     * Updates the dependencies of the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     */
    private void updateDependencies(CellCoordinates coordinates) {
        Cell cell = cells.get(coordinates);
        if (cell == null)
            return;

        Set<CellCoordinates> dependencies = cell.getDependencies();
        if (dependencies == null)
            return;

        for (CellCoordinates dependency : dependencies) {
            Set<CellCoordinates> dependents = this.dependents.get(dependency);

            if (dependents == null) {
                dependents = new HashSet<CellCoordinates>();
                this.dependents.put(dependency, dependents);
            }

            dependents.add(coordinates);
        }
    }

    /**
     * Returns whether the cell at the specified coordinates is evaluated.
     * 
     * @param coordinates The coordinates.
     * @return Whether the cell at the specified coordinates is evaluated.
     */
    public boolean isEvaluated(CellCoordinates coordinates) {
        Cell cell = cells.get(coordinates);
        if (cell == null)
            return true;

        return cell.isEvaluated();
    }

    /**
     * Returns the raw value of the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     * @return The raw value of the cell at the specified coordinates.
     */
    public double getValue(CellCoordinates coordinates) {
        Cell cell = cells.get(coordinates);
        if (cell == null)
            return 0;

        return cell.getValue();
    }

    /**
     * Returns the cell value of the cell at the specified coordinates.
     * 
     * @param coordinates The coordinates.
     * @return The cell value of the cell at the specified coordinates.
     */
    public CellValue getCellValue(CellCoordinates coordinates) {
        Cell cell = cells.get(coordinates);
        if (cell == null)
            return null;

        return new CellValue(cell);
    }

    /**
     * Returns the cell values of the cells in the specified range.
     * 
     * @param start The start coordinates.
     * @param end   The end coordinates.
     * @return The cell values of the cells in the specified range.
     */
    public Map<CellCoordinates, CellValue> getCellValuesInRange(CellCoordinates start, CellCoordinates end) {
        Map<CellCoordinates, CellValue> values = new HashMap<CellCoordinates, CellValue>();

        for (Entry<Coordinates, Cell> entry : cells.getRange(start, end).entrySet())
            values.put(new CellCoordinates(entry.getKey()), new CellValue(entry.getValue()));

        return values;
    }

    /**
     * Returns the string reprensation of the cell storage.
     * 
     * @return The string reprensation of the cell storage.
     */
    @Override
    public String toString() {
        return cells.toString();
    }
}
