package ranger.ui.view;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ranger.setting.SettingsRegistrar;
import ranger.sheet.Sheet;
import ranger.sheet.Storage;
import ranger.sheet.StorageListener;

/**
 * Class representing a view storage.
 */
public class ViewStorage implements StorageListener, Iterable<View>, ViewListener {
    /**
     * The settings registrar to use for the views.
     */
    private SettingsRegistrar settings;

    /**
     * The sheet storage.
     */
    private Storage storage;

    /**
     * The views, mapped by the sheet they represent.
     */
    private Map<Sheet, View> views;

    /**
     * The view listeners.
     */
    private List<ViewListener> listeners;

    /**
     * Constructs a new view storage.
     * 
     * @param settings The settings registrar to use for the views.
     * @param storage  The sheet storage.
     */
    public ViewStorage(SettingsRegistrar settings, Storage storage) {
        this.settings = settings;
        this.storage = storage;
        this.storage.addListener(this);

        this.views = new HashMap<Sheet, View>();

        this.listeners = new ArrayList<ViewListener>();

        for (Sheet sheet : storage.getSheets())
            sheetAdded(storage, sheet);
    }

    /**
     * Adds a view listener.
     * 
     * @param listener The listener.
     */
    public void addListener(ViewListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a view listener.
     * 
     * @param listener The listener.
     */
    public void removeListener(ViewListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns the sheet storage.
     * 
     * @return The sheet storage.
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Returns the view for the specified sheet.
     * 
     * @param sheet The sheet.
     * @return The view for the specified sheet.
     */
    public View getView(Sheet sheet) {
        if (sheet == null)
            return null;

        return views.get(sheet);
    }

    /**
     * Returns the selected view.
     * 
     * @return The selected view.
     */
    public View getSelectedView() {
        return getView(storage.getSelectedSheet());
    }

    /**
     * Handles a sheet addition event.
     * 
     * @param storage The storage that the sheet was added to.
     * @param sheet   The sheet.
     */
    @Override
    public void sheetAdded(Storage storage, Sheet sheet) {
        if (!storage.contains(sheet))
            throw new IllegalArgumentException("Cannot add a sheet that is not in the storage.");

        if (views.containsKey(sheet))
            return;

        View view = new View(settings, sheet);
        view.addViewListener(this);
        views.put(sheet, view);
    }

    /**
     * Handles a sheet removal event.
     * 
     * @param storage The storage that the sheet was removed from.
     * @param sheet   The sheet.
     */
    @Override
    public void sheetRemoved(Storage storage, Sheet sheet) {
        if (storage.contains(sheet))
            throw new IllegalArgumentException("Cannot remove a sheet that is still in the storage.");

        if (!views.containsKey(sheet))
            return;

        View view = views.get(sheet);
        view.removeViewListener(this);
        views.remove(sheet);
    }

    /**
     * Handles a sheet selection event.
     * 
     * @param storage The storage that the sheet was selected in.
     * @param sheet   The sheet.
     */
    @Override
    public void sheetSelected(Storage storage, Sheet sheet) {
    }

    /**
     * Handles a sheet rename event.
     * 
     * @param storage The storage that the sheet was renamed in.
     * @param sheet   The sheet.
     * @param name    The new name.
     */
    @Override
    public void sheetRenamed(Storage storage, Sheet sheet, String name) {
    }

    /**
     * Handles a zoom change event.
     * 
     * @param view The view.
     * @param zoom The new zoom level.
     */
    @Override
    public void zoomChanged(View view, double zoom) {
        for (ViewListener listener : listeners)
            listener.zoomChanged(view, zoom);
    }

    /**
     * Handles a selection change event.
     * 
     * @param view      The view.
     * @param selection The new selection.
     */
    @Override
    public void selectionChanged(View view, Selection selection) {
        for (ViewListener listener : listeners)
            listener.selectionChanged(view, selection);
    }

    /**
     * Handles a sheet content change event.
     * 
     * @param storage The storage that the sheet content was changed in.
     * @param sheet   The sheet.
     */
    @Override
    public void sheetContentChanged(Storage storage, Sheet sheet) {
    }

    /**
     * Handles a sheet action event.
     * 
     * @param storage The storage that the sheet action was performed in.
     * @param sheet   The sheet.
     */
    @Override
    public void sheetActionPerformed(Storage storage, Sheet sheet) {
    }

    /**
     * Returns an iterator over the views.
     * 
     * @return An iterator over the views.
     */
    @Override
    public Iterator<View> iterator() {
        return views.values().iterator();
    }

    /**
     * Handles a view repaint event.
     * 
     * @param view The view.
     */
    @Override
    public void repainted(View view) {
        for (ViewListener listener : listeners)
            listener.repainted(view);
    }

    /**
     * Handles a key emit event.
     * 
     * @param view The view.
     * @param e    The key event.
     */
    @Override
    public void keyEmitted(View view, KeyEvent e) {
        for (ViewListener listener : listeners)
            listener.keyEmitted(view, e);
    }
}