package ranger.sheet;

/**
 * Interface for listening to changes in a storage.
 */
public interface StorageListener {

    /**
     * Called when the content of a sheet has changed.
     * 
     * @param storage The storage.
     * @param sheet   The sheet.
     */
    public void sheetContentChanged(Storage storage, Sheet sheet);

    /**
     * Called when an action has been performed on a sheet.
     * 
     * @param storage The storage.
     * @param sheet   The sheet.
     */
    public void sheetActionPerformed(Storage storage, Sheet sheet);

    /**
     * Called when a sheet has been added.
     * 
     * @param storage The storage.
     * @param sheet   The sheet.
     */
    public void sheetAdded(Storage storage, Sheet sheet);

    /**
     * Called when a sheet has been removed.
     * 
     * @param storage The storage.
     * @param sheet   The sheet.
     */
    public void sheetRemoved(Storage storage, Sheet sheet);

    /**
     * Called when a sheet has been selected.
     * 
     * @param storage The storage.
     * @param sheet   The sheet.
     */
    public void sheetSelected(Storage storage, Sheet sheet);

    /**
     * Called when a sheet has been renamed.
     * 
     * @param storage The storage.
     * @param sheet   The sheet.
     * @param name    The new name of the sheet.
     */
    public void sheetRenamed(Storage storage, Sheet sheet, String name);
}