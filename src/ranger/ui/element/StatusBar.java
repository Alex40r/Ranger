package ranger.ui.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.sheet.Sheet;
import ranger.sheet.Storage;
import ranger.sheet.StorageRequestListener;
import ranger.ui.component.Block;
import ranger.ui.component.Button;
import ranger.ui.component.ColoredIcon;
import ranger.ui.component.Container;
import ranger.ui.component.Label;
import ranger.ui.component.Slider;
import ranger.ui.component.Spacer;
import ranger.ui.component.WeightedAdapter;
import ranger.ui.component.layout.Direction;
import ranger.ui.component.layout.Orientation;
import ranger.ui.component.layout.Padding;
import ranger.ui.context.ContextMenu;
import ranger.ui.component.layout.DirectionalLayout.Behavior;
import ranger.ui.popup.PopupRequestListener;
import ranger.ui.view.View;
import ranger.ui.view.ViewRequestListener;
import ranger.ui.view.ViewStorage;

/**
 * Class representing the status bar.
 */
public class StatusBar extends Container implements ActionListener, ChangeListener {
    /**
     * The storage select action.
     */
    private static final String SELECT_ACTION = "SELECT_ACTION";

    /**
     * The storage unselect action.
     */
    private static final String UNSELECT_ACTION = "UNSELECT_ACTION";

    /**
     * The storage add action.
     */
    private static final String ADD_ACTION = "ADD_ACTION";

    /**
     * The storage remove action.
     */
    private static final String REMOVE_ACTION = "REMOVE_ACTION";

    /**
     * The storage rename action.
     */
    private static final String RENAME_ACTION = "RENAME_ACTION";

    /**
     * The view zoom out action.
     */
    private static final String ZOOM_OUT_ACTION = "ZOOM_OUT_ACTION";

    /**
     * The view zoom in action.
     */
    private static final String ZOOM_IN_ACTION = "ZOOM_IN_ACTION";

    /**
     * The settings registrar that contains the settings to use.
     */
    private SettingsRegistrar settings;

    /**
     * The view storage that contains the views and sheets to use.
     */
    private ViewStorage viewStorage;

    /**
     * The status label.
     */
    private Label statusLabel;

    /**
     * The entry container.
     */
    private Container entryContainer;

    /**
     * The add button.
     */
    private Button addButton;

    /**
     * The zoom container, containing the zoom label, zoom out button, zoom slider,
     * and zoom in button.
     */
    private Container zoomContainer;

    /**
     * The zoom label.
     */
    private Label zoomLabel;

    /**
     * The zoom out button.
     */
    private Button zoomOutButton;

    /**
     * The zoom slider.
     */
    private Slider zoomSlider;

    /**
     * The zoom in button.
     */
    private Button zoomInButton;

    /**
     * The popup request listener.
     */
    private PopupRequestListener popupRequestListener;

    /**
     * The storage request listener.
     */
    private StorageRequestListener storageRequestListener;

    /**
     * The view request listener.
     */
    private ViewRequestListener viewRequestListener;

    /**
     * Constructs a new status bar.
     * 
     * @param settings    The settings.
     * @param viewStorage The view storage.
     */
    public StatusBar(SettingsRegistrar settings, ViewStorage viewStorage) {
        super(Direction.EAST, Behavior.LEFT);

        this.settings = settings;
        this.viewStorage = viewStorage;

        Color background = settings.get(Setting.STATUS_BAR_BACKGROUND, Color.class);
        Color foreground = settings.get(Setting.STATUS_BAR_FOREGROUND, Color.class);
        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        Font font = settings.get(Setting.FONT, Font.class);

        int minimum = (int) (settings.get(Setting.MINIMUM_ZOOM, Double.class) * 100);
        int maximum = (int) (settings.get(Setting.MAXIMUM_ZOOM, Double.class) * 100);

        /* ---- ---- */

        setPreferredSize(Integer.MAX_VALUE, 33);

        setOpaque(true);
        setBackground(background);

        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, border));

        /* ---- ---- */

        Container statusContainer = new Container(new Padding(1, 0, 4, 4));
        statusContainer.setPreferredSize(64 + 4 + 4, 33);

        statusLabel = new Label("Ready");
        statusLabel.setForeground(foreground);
        statusLabel.setFont(font);
        statusContainer.add(statusLabel);

        add(statusContainer);

        /* ---- ---- */

        entryContainer = new Container();
        add(entryContainer);

        /* ---- ---- */

        Container addContainer = new Container(new Padding(1, 0, 4, 0));
        addContainer.setPreferredSize(24 + 4, 33);

        addButton = new Button(new ColoredIcon("res/img/sheet/add.png", foreground, 19, 19));
        addButton.setPreferredSize(24, 24);
        addButton.setActionCommand(ADD_ACTION);
        addButton.addActionListener(this);
        addContainer.add(addButton);

        add(addContainer);

        /* ---- ---- */

        add(new Spacer(-2, Orientation.HORIZONTAL));

        /* ---- ---- */

        zoomContainer = new Container(new Padding(1, 0, 0, 4));
        zoomContainer.setVisible(false);

        zoomContainer.add(new Spacer(Orientation.HORIZONTAL, 4));

        zoomLabel = new Label("100%");
        zoomLabel.setPreferredSize(32, 32);
        zoomLabel.setMinimumSize(32, 32);
        zoomLabel.setForeground(foreground);
        zoomLabel.setFont(font);
        zoomContainer.add(zoomLabel);

        zoomContainer.add(new Spacer(Orientation.HORIZONTAL, 4));

        zoomOutButton = new Button(new ColoredIcon("res/img/sheet/zoom-out.png", foreground, 19, 19));
        zoomOutButton.setPreferredSize(24, 24);
        zoomOutButton.setActionCommand(ZOOM_OUT_ACTION);
        zoomOutButton.addActionListener(this);
        zoomContainer.add(zoomOutButton);

        zoomContainer.add(new Spacer(Orientation.HORIZONTAL, 4));

        zoomSlider = new Slider(minimum, maximum, 100);
        zoomSlider.setPreferredSize(96, 24);
        zoomSlider.setBackground(border);
        zoomSlider.setForeground(foreground);
        zoomSlider.addChangeListener(this);
        zoomContainer.add(zoomSlider);

        zoomContainer.add(new Spacer(Orientation.HORIZONTAL, 4));

        zoomInButton = new Button(new ColoredIcon("res/img/sheet/zoom-in.png", foreground, 19, 19));
        zoomInButton.setPreferredSize(24, 24);
        zoomInButton.setActionCommand(ZOOM_IN_ACTION);
        zoomInButton.addActionListener(this);
        zoomContainer.add(zoomInButton);

        add(new WeightedAdapter(-1, zoomContainer));

        /* ---- ---- */

        update();
    }

    /**
     * Sets the popup request listener.
     * 
     * @param popupRequestListener The popup request listener.
     */
    public void setPopupRequestListener(PopupRequestListener popupRequestListener) {
        this.popupRequestListener = popupRequestListener;
    }

    /**
     * Sets the storage request listener.
     * 
     * @param storageRequestListener The storage request listener.
     */
    public void setStorageRequestListener(StorageRequestListener storageRequestListener) {
        this.storageRequestListener = storageRequestListener;
    }

    /**
     * Sets the view request listener.
     * 
     * @param viewRequestListener The view request listener.
     */
    public void setViewRequestListener(ViewRequestListener viewRequestListener) {
        this.viewRequestListener = viewRequestListener;
    }

    /**
     * Sets the status.
     * 
     * @param status The status.
     */
    public void setStatus(String status) {
        statusLabel.setText(status);

        statusLabel.revalidate();
        statusLabel.repaint();
    }

    /**
     * Updates the status bar.
     */
    public void update() {
        regenerateEntries();

        updateView();
    }

    /**
     * Updates the view section of the status bar.
     * If no view is selected, the zoom container is hidden.
     */
    public void updateView() {
        View selected = viewStorage.getSelectedView();

        zoomContainer.setVisible(selected != null);

        if (selected != null)
            zoomSlider.setValue((int) Math.round(selected.getZoom() * 100));
    }

    /**
     * Regenerates the entries.
     */
    private void regenerateEntries() {
        Color background = settings.get(Setting.SHEET_BACKGROUND, Color.class);
        Color foreground = settings.get(Setting.STATUS_BAR_FOREGROUND, Color.class);
        Color border = settings.get(Setting.BORDER_COLOR, Color.class);
        Color accent = settings.get(Setting.ACCENT_COLOR, Color.class);

        Font font = settings.get(Setting.FONT, Font.class);

        /* ---- ---- */

        entryContainer.removeAll();

        Storage storage = viewStorage.getStorage();

        for (int i = 0; i <= storage.getSheetCount(); i++) {
            if ((i == 0 || !storage.isSelected(i - 1)) && (i >= storage.getSheetCount() || !storage.isSelected(i)))
                entryContainer.add(new Block(border, 1, 16));

            if (i >= storage.getSheetCount())
                break;

            boolean selected = storage.isSelected(i);

            Sheet sheet = storage.getSheet(i);

            SheetEntry entry = new SheetEntry(sheet);
            entry.setPreferredSize(entry.getPreferredWidth() + 24 + (selected ? 2 : 0), 24);
            entry.setSelected(selected);
            entry.setBackground(background);
            entry.setForeground(foreground);
            entry.setBorderColor(border);
            entry.setAccentColor(accent);
            entry.setFont(font);
            entry.setActionCommand(selected ? UNSELECT_ACTION : SELECT_ACTION);
            entry.addActionListener(this);

            entry.setComponentPopupMenu(createContextMenu(sheet));

            entryContainer.add(entry);
        }

        entryContainer.revalidate();
        entryContainer.repaint();
    }

    /**
     * Creates a context menu for the given sheet.
     * 
     * @param sheet The sheet.
     * @return The context menu.
     */
    private ContextMenu createContextMenu(Sheet sheet) {
        ContextMenu contextMenu = new ContextMenu(settings);

        SheetContextEntry selectButton = new SheetContextEntry(contextMenu, sheet, "Select");
        selectButton.setActionCommand(SELECT_ACTION);
        selectButton.addActionListener(this);
        contextMenu.add(selectButton);

        SheetContextEntry renameButton = new SheetContextEntry(contextMenu, sheet, "Rename");
        renameButton.setActionCommand(RENAME_ACTION);
        renameButton.addActionListener(this);
        contextMenu.add(renameButton);

        SheetContextEntry removeButton = new SheetContextEntry(contextMenu, sheet, "Remove");
        removeButton.setActionCommand(REMOVE_ACTION);
        removeButton.addActionListener(this);
        contextMenu.add(removeButton);

        return contextMenu;
    }

    /**
     * Handles an action event.
     * 
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case SELECT_ACTION:
                Sheet sheet;

                if (e.getSource() instanceof SheetEntry) {
                    SheetEntry entry = (SheetEntry) e.getSource();
                    sheet = entry.getSheet();
                } else if (e.getSource() instanceof SheetContextEntry) {
                    SheetContextEntry entry = (SheetContextEntry) e.getSource();
                    sheet = entry.getSheet();
                    entry.getMenu().setVisible(false);
                } else
                    throw new IllegalArgumentException("Cannot select a sheet from an unknown source.");

                storageRequestListener.selectSheet(sheet);
                break;

            case UNSELECT_ACTION:
                storageRequestListener.selectSheet(null);
                break;

            case ADD_ACTION:
                storageRequestListener.addSheet();
                break;

            case REMOVE_ACTION:
                if (e.getSource() instanceof SheetContextEntry) {
                    SheetContextEntry entry = (SheetContextEntry) e.getSource();
                    sheet = entry.getSheet();
                    entry.getMenu().setVisible(false);
                } else
                    throw new IllegalArgumentException("Cannot remove a sheet from an unknown source.");

                storageRequestListener.removeSheet(sheet);
                break;

            case RENAME_ACTION:
                if (e.getSource() instanceof SheetContextEntry) {
                    SheetContextEntry entry = (SheetContextEntry) e.getSource();
                    sheet = entry.getSheet();
                    entry.getMenu().setVisible(false);
                } else
                    throw new IllegalArgumentException("Cannot rename a sheet from an unknown source.");

                RenamePopup renamePopup = new RenamePopup(settings, sheet);
                renamePopup.setStorageRequestListener(storageRequestListener);

                popupRequestListener.pushPopup(renamePopup);
                renamePopup.acquireFocus();
                break;

            case ZOOM_OUT_ACTION:
                zoomSlider.setValue(zoomSlider.getValue() - 5);
                break;

            case ZOOM_IN_ACTION:
                zoomSlider.setValue(zoomSlider.getValue() + 5);
                break;
        }
    }

    /**
     * Handles a change event.
     * 
     * @param e The change event.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == zoomSlider) {
            Slider slider = (Slider) e.getSource();
            zoomLabel.setText(slider.getValue() + "%");

            viewRequestListener.setZoom(slider.getValue() / 100.0);
        }
    }
}
