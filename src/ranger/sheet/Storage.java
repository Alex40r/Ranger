package ranger.sheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing a storage of sheets.
 */
public class Storage implements SheetListener, Iterable<Sheet> {
    /**
     * The sheets.
     */
    private List<Sheet> sheets;

    /**
     * The selected sheet.
     */
    private Sheet selectedSheet;

    /**
     * The storage listeners.
     */
    private List<StorageListener> listeners;

    /**
     * Constructs a new empty storage.
     */
    public Storage() {
        sheets = new ArrayList<Sheet>();

        selectedSheet = null;

        listeners = new ArrayList<StorageListener>();
    }

    /**
     * Adds the specified storage listener.
     * 
     * @param listener The storage listener.
     */
    public void addListener(StorageListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the specified storage listener.
     * 
     * @param listener The storage listener.
     */
    public void removeListener(StorageListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns the sheets as a copy.
     * 
     * @return The sheets.
     */
    public Sheet[] getSheets() {
        return sheets.toArray(new Sheet[sheets.size()]);
    }

    /**
     * Returns the sheet at the specified index.
     * 
     * @param i The index.
     * @return The sheet.
     */
    public Sheet getSheet(int i) {
        return sheets.get(i);
    }

    /**
     * Returns whether the sheet at the specified index is selected.
     * 
     * @param i The index.
     * @return Whether the sheet at the specified index is selected.
     */
    public boolean isSelected(int i) {
        return sheets.get(i) == selectedSheet;
    }

    /**
     * Returns the number of sheets.
     * 
     * @return The number of sheets.
     */
    public int getSheetCount() {
        return sheets.size();
    }

    /**
     * Adds a sheet.
     * 
     * @param sheet The sheet to add.
     */
    public void addSheet(Sheet sheet) {
        if (sheets.contains(sheet))
            throw new IllegalArgumentException("Cannot add a sheet that is already in the storage.");

        sheets.add(sheet);
        sheet.addListener(this);

        for (StorageListener listener : listeners)
            listener.sheetAdded(this, sheet);

        selectSheet(sheet);
    }

    /**
     * Removes a sheet.
     * 
     * @param sheet The sheet to remove.
     */
    public void removeSheet(Sheet sheet) {
        if (!sheets.contains(sheet))
            throw new IllegalArgumentException("Cannot remove a sheet that is not in the storage.");

        if (selectedSheet == sheet) {
            int index = sheets.indexOf(sheet);

            if (index == -1)
                selectSheet(null);
            else if (index < sheets.size() - 1)
                selectSheet(sheets.get(index + 1));
            else if (index > 0)
                selectSheet(sheets.get(index - 1));
            else
                selectSheet(null);
        }

        sheet.removeListener(this);
        sheets.remove(sheet);

        for (StorageListener listener : listeners)
            listener.sheetRemoved(this, sheet);
    }

    /**
     * Returns whether a sheet is selected.
     * 
     * @return Whether a sheet is selected.
     */
    public boolean hasSelectedSheet() {
        return selectedSheet != null;
    }

    /**
     * Returns the selected sheet.
     * 
     * @return The selected sheet.
     */
    public Sheet getSelectedSheet() {
        return selectedSheet;
    }

    /**
     * Selects a sheet.
     * 
     * @param sheet The sheet to select.
     */
    public void selectSheet(Sheet sheet) {
        if (selectedSheet == sheet)
            return;

        if (sheet != null && !sheets.contains(sheet))
            throw new IllegalArgumentException("Cannot select a sheet that is not in the storage.");

        selectedSheet = sheet;

        for (StorageListener listener : listeners)
            listener.sheetSelected(this, sheet);
    }

    /**
     * Returns whether the storage contains the specified sheet.
     * 
     * @param sheet The sheet.
     * @return Whether the storage contains the specified sheet.
     */
    public boolean contains(Sheet sheet) {
        return sheets.contains(sheet);
    }

    /**
     * Handles the change of the content of a sheet.
     * 
     * @param sheet The sheet whose content changed.
     */
    @Override
    public void contentChanged(Sheet sheet) {
        for (StorageListener listener : listeners)
            listener.sheetContentChanged(this, sheet);
    }

    /**
     * Handles the action performed in a sheet.
     * 
     * @param sheet The sheet in which the action was performed.
     */
    @Override
    public void actionPerformed(Sheet sheet) {
        for (StorageListener listener : listeners)
            listener.sheetActionPerformed(this, sheet);
    }

    /**
     * Handles the change of the name of a sheet.
     * 
     * @param sheet The sheet whose name changed.
     * @param name  The new name.
     */
    @Override
    public void nameChanged(Sheet sheet, String name) {
        for (StorageListener listener : listeners)
            listener.sheetRenamed(this, sheet, name);
    }

    /**
     * Handles the resizing of a row.
     * 
     * @param sheet  The sheet.
     * @param row    The row.
     * @param height The new height.
     */
    @Override
    public void rowResized(Sheet sheet, int row, int height) {
    }

    /**
     * Handles the resizing of a column.
     * 
     * @param sheet  The sheet.
     * @param column The column.
     * @param width  The new width.
     */
    @Override
    public void columnResized(Sheet sheet, int column, int width) {
    }

    /**
     * Returns an iterator over the sheets.
     * 
     * @return An iterator over the sheets.
     */
    @Override
    public Iterator<Sheet> iterator() {
        return sheets.iterator();
    }
}
