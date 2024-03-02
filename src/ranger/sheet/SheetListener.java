package ranger.sheet;

/**
 * Interface for listening to changes in a sheet.
 */
public interface SheetListener {
    /**
     * Called when the content of a sheet has changed.
     * 
     * @param sheet The sheet.
     */
    public void contentChanged(Sheet sheet);

    /**
     * Called when an action was performed on a sheet.
     * 
     * @param sheet The sheet.
     */
    public void actionPerformed(Sheet sheet);

    /**
     * Called when the name of a sheet has changed.
     * 
     * @param sheet The sheet.
     * @param name  The new name.
     */
    public void nameChanged(Sheet sheet, String name);

    /**
     * Called when a row has been resized.
     * 
     * @param sheet  The sheet.
     * @param row    The row.
     * @param height The new height.
     */
    public void rowResized(Sheet sheet, int row, int height);

    /**
     * Called when a column has been resized.
     * 
     * @param sheet  The sheet.
     * @param column The column.
     * @param width  The new width.
     */
    public void columnResized(Sheet sheet, int column, int width);
}
