package ranger.sheet.cell;

/**
 * Interface for listening to changes in a cell storage.
 */
public interface CellStorageListener {
    /**
     * Called when the content of a cell has changed.
     * 
     * @param storage The cell storage.
     */
    public void contentChanged(CellStorage storage);
}
