package ranger.sheet;

/**
 * Interface for listening to storage requests.
 */
public interface StorageRequestListener {
    /**
     * Called when the storage should be saved.
     */
    public void save();

    /**
     * Called when a new sheet should be added.
     */
    public void addSheet();

    /**
     * Called when a sheet should be selected.
     * 
     * @param sheet The sheet.
     */
    public void selectSheet(Sheet sheet);

    /**
     * Called when a sheet should be removed.
     * 
     * @param sheet The sheet.
     */
    public void removeSheet(Sheet sheet);

    /**
     * Called when a sheet should be renamed.
     * 
     * @param sheet The sheet.
     * @param name  The new name.
     */
    public void renameSheet(Sheet sheet, String name);
}
