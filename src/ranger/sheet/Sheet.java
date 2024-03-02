package ranger.sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import ranger.function.FunctionRegistrar;
import ranger.sheet.action.SheetAction;
import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.sheet.cell.CellStorage;
import ranger.sheet.cell.CellStorageListener;
import ranger.sheet.cell.CellValue;
import ranger.syntax.SyntaxTree;
import ranger.syntax.block.ExpressionBlock;
import ranger.syntax.lexer.Lexer;
import ranger.syntax.parser.ExpressionParser;

/**
 * Class representing a sheet.
 */
public class Sheet implements CellStorageListener {
    /**
     * The initial row height.
     */
    public static final int INITIAL_ROW_HEIGHT = 20;

    /**
     * The initial column width.
     */
    public static final int INITIAL_COLUMN_WIDTH = 120;

    /**
     * The name of the sheet.
     */
    private String name;

    /**
     * The storage where the cells are stored.
     */
    private CellStorage storage;

    /**
     * The stack containing the actions that can be undone.
     */
    private Stack<SheetAction> undoStack;

    /**
     * The stack containing the actions that can be redone.
     */
    private Stack<SheetAction> redoStack;

    /**
     * The row heights.
     */
    private Map<Integer, Integer> rowHeights;

    /**
     * The column widths.
     */
    private Map<Integer, Integer> columnWidths;

    /**
     * The sheet listeners.
     */
    private List<SheetListener> listeners;

    /**
     * Constructs a new sheet.
     * 
     * @param name              The name of the sheet.
     * @param functionRegistrar The function registrar.
     * @param expressionParser  The expression parser.
     */
    public Sheet(String name, FunctionRegistrar functionRegistrar, ExpressionParser expressionParser) {
        this.name = name;

        storage = new CellStorage(functionRegistrar, expressionParser);
        storage.addListener(this);

        undoStack = new Stack<SheetAction>();
        redoStack = new Stack<SheetAction>();

        rowHeights = new HashMap<Integer, Integer>();
        columnWidths = new HashMap<Integer, Integer>();

        listeners = new ArrayList<SheetListener>();
    }

    /**
     * Adds a sheet listener.
     * 
     * @param listener The listener.
     */
    public void addListener(SheetListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a sheet listener.
     * 
     * @param listener The listener.
     */
    public void removeListener(SheetListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns the name of the sheet.
     * 
     * @return The name of the sheet.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the sheet.
     * 
     * @param name The name of the sheet.
     */
    public void setName(String name) {
        this.name = name;

        for (SheetListener listener : listeners)
            listener.nameChanged(this, name);
    }

    /**
     * Returns the width of a column.
     * 
     * @param column The column.
     * @return The width of the column.
     */
    public int getColumnWidth(int column) {
        if (columnWidths.containsKey(column))
            return columnWidths.get(column);

        return INITIAL_COLUMN_WIDTH;
    }

    /**
     * Sets the width of a column.
     * 
     * @param column The column.
     * @param width  The width.
     */
    public void setColumnWidth(int column, int width) {
        if (width == INITIAL_COLUMN_WIDTH || width < 0)
            columnWidths.remove(column);
        else
            columnWidths.put(column, width);

        for (SheetListener listener : listeners)
            listener.columnResized(this, column, width);
    }

    /**
     * Returns the height of a row.
     * 
     * @param row The row.
     * @return The height of the row.
     */
    public int getRowHeight(int row) {
        if (rowHeights.containsKey(row))
            return rowHeights.get(row);

        return INITIAL_ROW_HEIGHT;
    }

    /**
     * Sets the height of a row.
     * 
     * @param row    The row.
     * @param height The height.
     */
    public void setRowHeight(int row, int height) {
        if (height == INITIAL_ROW_HEIGHT || height < 0)
            rowHeights.remove(row);
        else
            rowHeights.put(row, height);

        for (SheetListener listener : listeners)
            listener.rowResized(this, row, height);
    }

    /**
     * Returns the function registrar.
     * 
     * @return The function registrar.
     */
    public FunctionRegistrar getFunctionRegistrar() {
        return storage.getFunctionRegistrar();
    }

    /**
     * Sets the function registrar.
     * 
     * @param functionRegistrar The function registrar.
     */
    public void setFunctionRegistrar(FunctionRegistrar functionRegistrar) {
        storage.setFunctionRegistrar(functionRegistrar);
    }

    /**
     * Returns the expression parser.
     * 
     * @return The expression parser.
     */
    public ExpressionParser getExpressionParser() {
        return storage.getExpressionParser();
    }

    /**
     * Sets the expression parser.
     * 
     * @param expressionParser The expression parser.
     */
    public void setExpressionParser(ExpressionParser expressionParser) {
        storage.setExpressionParser(expressionParser);
    }

    /**
     * Returns the value of a cell.
     * 
     * @param coordinates The coordinates of the cell.
     * @return The value of the cell.
     */
    public CellValue getValue(CellCoordinates coordinates) {
        return storage.getCellValue(coordinates);
    }

    /**
     * Returns the values of the cells in a range.
     * 
     * @param start The start coordinates.
     * @param end   The end coordinates.
     * @return The values of the cells in the range.
     */
    public Map<CellCoordinates, CellValue> getValuesInRange(CellCoordinates start, CellCoordinates end) {
        return storage.getCellValuesInRange(start, end);
    }

    /**
     * Applies an action to the sheet.
     * 
     * @param action The action.
     */
    public void applyAction(SheetAction action) {
        action = action.clone();

        action.doAction(storage);

        undoStack.push(action);
        redoStack.clear();

        for (SheetListener listener : listeners)
            listener.actionPerformed(this);
    }

    /**
     * Returns whether an action can be undone.
     * 
     * @return Whether an action can be undone.
     */
    public boolean canUndoAction() {
        return !undoStack.isEmpty();
    }

    /**
     * Undoes an action.
     */
    public void undoAction() {
        if (!canUndoAction())
            return;

        SheetAction action = undoStack.pop();

        action.undoAction(storage);

        redoStack.push(action);

        for (SheetListener listener : listeners)
            listener.actionPerformed(this);
    }

    /**
     * Returns whether an action can be redone.
     * 
     * @return Whether an action can be redone.
     */
    public boolean canRedoAction() {
        return !redoStack.isEmpty();
    }

    /**
     * Redoes an action.
     */
    public void redoAction() {
        if (!canRedoAction())
            return;

        SheetAction action = redoStack.pop();

        action.doAction(storage);

        undoStack.push(action);

        for (SheetListener listener : listeners)
            listener.actionPerformed(this);
    }

    /**
     * Returns the content of a cell.
     * 
     * @param coordinates The coordinates of the cell.
     * @return The content of the cell.
     */
    public CellContent getContent(CellCoordinates coordinates) {
        return storage.getContent(coordinates);
    }

    /**
     * Returns an area of the sheet.
     * 
     * @param start  The start coordinates.
     * @param width  The width.
     * @param height The height.
     * @return The area.
     */
    public Area getArea(CellCoordinates start, int width, int height) {
        return storage.getArea(start, width, height);
    }

    /**
     * Calulates the value of an expression, without storing it in the sheet.
     * 
     * @param expression The expression.
     * @return The value of the expression.
     */
    public double interactive(String expression) {
        SyntaxTree tree = new SyntaxTree(ExpressionBlock.getRoot(Lexer.getTokens(expression)),
                storage.getExpressionParser());

        return tree.evaluate(new SheetEvaluationContext(storage.getFunctionRegistrar(), storage));
    }

    /**
     * Handles when the content of a storage has changed.
     * 
     * @param storage The storage.
     */
    @Override
    public void contentChanged(CellStorage storage) {
        if (storage != this.storage)
            throw new IllegalArgumentException("The storage is not the same as the storage of this sheet.");

        for (SheetListener listener : listeners)
            listener.contentChanged(this);
    }

    /**
     * Returns a string representation of the sheet.
     * 
     * @return A string representation of the sheet.
     */
    @Override
    public String toString() {
        return name + ": (\n" + storage + ")";
    }
}
