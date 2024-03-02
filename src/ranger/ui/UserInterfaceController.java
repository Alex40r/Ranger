package ranger.ui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import ranger.setting.SettingsRegistrar;
import ranger.sheet.Sheet;
import ranger.sheet.Storage;
import ranger.sheet.StorageListener;
import ranger.sheet.StorageRequestListener;
import ranger.ui.component.Container;
import ranger.ui.component.LayerContainer;
import ranger.ui.component.layout.Direction;
import ranger.ui.element.ControlBar;
import ranger.ui.element.InputBar;
import ranger.ui.element.MenuBar;
import ranger.ui.element.StatusBar;
import ranger.ui.menu.FileMenu;
import ranger.ui.menu.HomeMenu;
import ranger.ui.menu.Menu;
import ranger.ui.popup.Popup;
import ranger.ui.popup.PopupController;
import ranger.ui.popup.PopupLayer;
import ranger.ui.popup.PopupRequestListener;
import ranger.ui.view.Selection;
import ranger.ui.view.View;
import ranger.ui.view.ViewListener;
import ranger.ui.view.ViewStorage;
import ranger.ui.window.Window;
import ranger.ui.window.WindowRequestListener;
import ranger.ui.window.WindowDragController;
import ranger.ui.window.WindowStateListener;
import ranger.ui.window.WindowResizeController;
import ranger.ui.window.WindowResizeLayer;

/**
 * Class representing the user interface controller.
 * This class is responsible for managing the user interface and its
 * subcontrollers.
 */
public class UserInterfaceController
        implements WindowRequestListener, WindowStateListener, PopupRequestListener, StorageListener, ViewListener {

    /**
     * The settings registrar that contains the settings to use.
     */
    private SettingsRegistrar settings;

    /**
     * The storage containing the sheets to display.
     */
    private Storage storage;

    /**
     * The view storage containing the views used to display the sheets.
     */
    private ViewStorage viewStorage;

    /**
     * The sheet subcontroller that handles sheet requests.
     */
    private SheetController sheetSubcontroller;

    /**
     * The view subcontroller that handles view requests.
     */
    private ViewController viewSubcontroller;

    /**
     * The window resize subcontroller that handles resizing the window.
     */
    private WindowResizeController windowResizeSubcontroller;

    /**
     * The window drag subcontroller that handles dragging the window.
     */
    private WindowDragController windowDragSubcontroller;

    /**
     * The popup subcontroller that handles popups.
     */
    private PopupController popupSubcontroller;

    /**
     * The window containing the user interface.
     */
    private Window window;

    /**
     * The layer container containing the window layers.
     */
    private LayerContainer windowLayerContainer;

    /**
     * The window resize layer.
     */
    private WindowResizeLayer windowResizeLayer;

    /**
     * The window content layer.
     */
    private Container windowContentLayer;

    /**
     * The control bar.
     */
    private ControlBar controlBar;

    /**
     * The file menu.
     */
    private FileMenu fileMenu;

    /**
     * The home menu.
     */
    private HomeMenu homeMenu;

    /**
     * The menu bar that contains the menus.
     */
    private MenuBar menuBar;

    /**
     * The input bar.
     */
    private InputBar inputBar;

    /**
     * The layer container containing the content layers.
     */
    private LayerContainer contentLayerContainer;

    /**
     * The popup layer.
     */
    private PopupLayer popupLayer;

    /**
     * The container containing the view.
     */
    private Container viewContainer;

    /**
     * The status bar.
     */
    private StatusBar statusBar;

    /**
     * Constructs a new user interface controller.
     * 
     * @param settings The settings registrar that contains the settings to use.
     * @param storage  The storage containing the sheets to display.
     */
    public UserInterfaceController(SettingsRegistrar settings, Storage storage) {
        this.settings = settings;
        this.storage = storage;
        this.storage.addListener(this);

        viewStorage = new ViewStorage(settings, storage);
        viewStorage.addListener(this);

        sheetSubcontroller = new SheetController(storage);
        viewSubcontroller = new ViewController(viewStorage);

        window = new Window();
        window.addWindowStateListener(this);

        windowResizeSubcontroller = new WindowResizeController(window);
        windowDragSubcontroller = new WindowDragController(window);
        popupSubcontroller = new PopupController(settings);

        windowLayerContainer = new LayerContainer();
        window.add(windowLayerContainer);

        windowResizeLayer = new WindowResizeLayer();
        windowResizeSubcontroller.registerSurface(windowResizeLayer);
        windowLayerContainer.add(windowResizeLayer);

        windowContentLayer = new Container(Direction.SOUTH);

        /* ---- ---- */

        controlBar = new ControlBar(settings, window.isMaximized());
        controlBar.setWindowRequestListener(this);
        controlBar.setSheetRequestListener(sheetSubcontroller);
        windowDragSubcontroller.registerSurface(controlBar);
        windowContentLayer.add(controlBar);

        /* ---- ---- */

        generateMenus();

        menuBar = new MenuBar(settings);
        menuBar.setMenus(new Menu[] {
                fileMenu,
                homeMenu
        });
        menuBar.setSelectedMenu(homeMenu);
        windowContentLayer.add(menuBar);

        /* ---- ---- */

        inputBar = new InputBar(settings, storage);
        inputBar.setPopupRequestListener(this);
        inputBar.setSheetRequestListener(sheetSubcontroller);
        inputBar.setViewRequestListener(viewSubcontroller);
        windowContentLayer.add(inputBar);

        /* ---- ---- */

        contentLayerContainer = new LayerContainer();
        contentLayerContainer.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        popupLayer = new PopupLayer();
        contentLayerContainer.add(popupLayer);

        viewContainer = new Container();
        contentLayerContainer.add(viewContainer);

        windowContentLayer.add(contentLayerContainer);

        /* ---- ---- */

        statusBar = new StatusBar(settings, viewStorage);
        statusBar.setPopupRequestListener(this);
        statusBar.setViewRequestListener(viewSubcontroller);
        windowContentLayer.add(statusBar);

        /* ---- ---- */

        windowLayerContainer.add(windowContentLayer);

        window.setVisible(true);

        window.revalidate();
        window.repaint();
    }

    /**
     * Generates the menus.
     */
    private void generateMenus() {
        fileMenu = new FileMenu(settings);

        homeMenu = new HomeMenu(settings, storage, viewStorage);
        homeMenu.setSheetRequestListener(sheetSubcontroller);
    }

    /**
     * Sets the storage request listener.
     * 
     * @param storageRequestListener The storage request listener.
     */
    public void setStorageRequestListener(StorageRequestListener storageRequestListener) {
        controlBar.setStorageRequestListener(storageRequestListener);

        statusBar.setStorageRequestListener(storageRequestListener);
    }

    /**
     * Pushes the specified popup to the popup layer.
     * 
     * @param popup The popup to push.
     */
    @Override
    public void pushPopup(Popup popup) {
        popupSubcontroller.registerPopup(popup, popupLayer);

        popupLayer.revalidate();
        popupLayer.repaint();
    }

    /**
     * Pops the specified popup from the popup layer.
     * 
     * @param popup The popup to pop.
     */
    @Override
    public void popPopup(Popup popup) {
        popupSubcontroller.unregisterPopup(popup);

        popupLayer.revalidate();
        popupLayer.repaint();
    }

    /**
     * Iconifies the window.
     */
    @Override
    public void iconify() {
        window.iconify();
    }

    /**
     * Maximizes the window.
     */
    @Override
    public void maximize() {
        window.maximize();
    }

    /**
     * Restores the window.
     */
    @Override
    public void restore() {
        window.restore();
    }

    /**
     * Closes the window.
     */
    @Override
    public void close() {
        popupSubcontroller.dispose();
        window.dispose();
    }

    /**
     * Handles when the window is maximized.
     * 
     * @param window The window that was maximized.
     */
    @Override
    public void maximized(Window window) {
        if (window != this.window)
            return;

        windowResizeLayer.setVisible(false);

        controlBar.setWindowMaximized(true);
    }

    /**
     * Handles when the window is restored.
     * 
     * @param window The window that was restored.
     */
    @Override
    public void restored(Window window) {
        if (window != this.window)
            return;

        windowResizeLayer.setVisible(true);

        controlBar.setWindowMaximized(false);
    }

    /**
     * Handles when the window is closed.
     * 
     * @param window The window that was closed.
     */
    @Override
    public void closed(Window window) {
        if (window != this.window)
            return;

        popupSubcontroller.dispose();
    }

    /**
     * Handles when the window is resized.
     * 
     * @param window The window that was resized.
     */
    @Override
    public void resized(Window window) {
    }

    /**
     * Handles when a sheet is added to the storage.
     * 
     * @param storage The storage.
     * @param sheet   The sheet that was added.
     */
    @Override
    public void sheetAdded(Storage storage, Sheet sheet) {
        statusBar.update();
    }

    /**
     * Handles when a sheet is removed from the storage.
     * 
     * @param storage The storage.
     * @param sheet   The sheet that was removed.
     */
    @Override
    public void sheetRemoved(Storage storage, Sheet sheet) {
        statusBar.update();
    }

    /**
     * Handles when a sheet is selected in the storage.
     * 
     * @param storage The storage.
     * @param sheet   The sheet that was selected.
     */
    @Override
    public void sheetSelected(Storage storage, Sheet sheet) {
        viewContainer.removeAll();
        if (sheet != null) {
            View view = viewStorage.getView(sheet);
            view.setSheetRequestListener(sheetSubcontroller);
            viewContainer.add(view);
        }

        viewContainer.revalidate();
        viewContainer.repaint();

        if (sheet == null)
            controlBar.setWindowTitle("Ranger");
        else
            controlBar.setWindowTitle("Ranger - " + sheet.getName());

        controlBar.setUndoEnabled(sheet != null && sheet.canUndoAction());
        controlBar.setRedoEnabled(sheet != null && sheet.canRedoAction());

        View view = viewStorage.getView(sheet);
        Selection selection = view == null ? null : view.getSelection();
        inputBar.setCursor(selection == null ? null : selection.getCursor());

        statusBar.update();
    }

    /**
     * Handles when a sheet is renamed.
     * 
     * @param storage The storage.
     * @param sheet   The sheet that was renamed.
     * @param name    The new name.
     */
    @Override
    public void sheetRenamed(Storage storage, Sheet sheet, String name) {
        statusBar.update();
    }

    /**
     * Handles when the zoom level of a view is changed.
     * 
     * @param view The view.
     * @param zoom The new zoom level.
     */
    @Override
    public void zoomChanged(View view, double zoom) {
        statusBar.updateView();
    }

    /**
     * Handles when the selection of a view is changed.
     * 
     * @param view      The view.
     * @param selection The new selection.
     */
    @Override
    public void selectionChanged(View view, Selection selection) {
        inputBar.setCursor(selection == null ? null : selection.getCursor());

        homeMenu.update();
    }

    /**
     * Handles when the content of a sheet is changed.
     * 
     * @param storage The storage.
     * @param sheet   The sheet.
     */
    @Override
    public void sheetContentChanged(Storage storage, Sheet sheet) {
        inputBar.updateExpression();

        homeMenu.update();
    }

    /**
     * Handles when an action is performed on a sheet.
     * 
     * @param storage The storage.
     * @param sheet   The sheet.
     */
    @Override
    public void sheetActionPerformed(Storage storage, Sheet sheet) {
        if (sheet != storage.getSelectedSheet())
            return;

        controlBar.setUndoEnabled(sheet.canUndoAction());
        controlBar.setRedoEnabled(sheet.canRedoAction());
    }

    /**
     * Handles when a view is repainted.
     * 
     * @param view The view.
     */
    @Override
    public void repainted(View view) {
        popupLayer.repaint();
    }

    /**
     * Handles when a key is emitted from a view.
     * 
     * @param view The view.
     * @param e    The key event.
     */
    @Override
    public void keyEmitted(View view, KeyEvent e) {
        inputBar.emitKey(e);
    }
}
