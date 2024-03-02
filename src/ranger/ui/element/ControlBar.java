package ranger.ui.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.sheet.SheetRequestListener;
import ranger.sheet.StorageRequestListener;
import ranger.ui.component.Button;
import ranger.ui.component.ColoredIcon;
import ranger.ui.component.Container;
import ranger.ui.component.Label;
import ranger.ui.component.Spacer;
import ranger.ui.component.layout.Orientation;
import ranger.ui.window.WindowRequestListener;

/**
 * Class representing the control bar.
 */
public class ControlBar extends Container implements ActionListener {
    /**
     * The storage save action.
     */
    private static final String STORAGE_SAVE_ACTION = "STORAGE_SAVE_ACTION";

    /**
     * The sheet undo action.
     */
    private static final String SHEET_UNDO_ACTION = "SHEET_UNDO_ACTION";

    /**
     * The sheet redo action.
     */
    private static final String SHEET_REDO_ACTION = "SHEET_REDO_ACTION";

    /**
     * The window iconify action.
     */
    private static final String WINDOW_ICONIFY_ACTION = "WINDOW_ICONIFY_ACTION";

    /**
     * The window maximize action.
     */
    private static final String WINDOW_MAXIMIZE_ACTION = "WINDOW_MAXIMIZE_ACTION";

    /**
     * The window restore action.
     */
    private static final String WINDOW_RESTORE_ACTION = "WINDOW_RESTORE_ACTION";

    /**
     * The window close action.
     */
    private static final String WINDOW_CLOSE_ACTION = "WINDOW_CLOSE_ACTION";

    /**
     * The settings registrar that contains the settings to use.
     */
    private SettingsRegistrar settings;

    /**
     * The window request listener.
     */
    private WindowRequestListener windowRequestListener;

    /**
     * The storage request listener.
     */
    private StorageRequestListener storageRequestListener;

    /**
     * The sheet request listener.
     */
    private SheetRequestListener sheetRequestListener;

    /**
     * The save button.
     */
    private Button saveButton;

    /**
     * The undo button.
     */
    private Button undoButton;

    /**
     * The redo button.
     */
    private Button redoButton;

    /**
     * The title label.
     */
    private Label titleLabel;

    /**
     * The iconify button.
     */
    private Button iconifyButton;

    /**
     * The toggle maximize button.
     */
    private Button toggleMaximizeButton;

    /**
     * The close button.
     */
    private Button closeButton;

    /**
     * Constructs a new control bar.
     * 
     * @param settings    The settings to use.
     * @param isMaximized Whether the window is maximized.
     */
    public ControlBar(SettingsRegistrar settings, boolean isMaximized) {
        this.settings = settings;

        Color background = settings.get(Setting.HEADER_BACKGROUND, Color.class);
        Color foreground = settings.get(Setting.HEADER_FOREGROUND, Color.class);
        Font font = settings.get(Setting.FONT, Font.class);

        /* ---- ---- */

        setPreferredSize(Integer.MAX_VALUE, 32);

        setOpaque(true);
        setBackground(background);

        /* ---- ---- */

        Container leftContainer = new Container();
        leftContainer.setPreferredSize(Integer.MAX_VALUE, 32);
        leftContainer.setMinimumSize(0, 0);

        saveButton = new Button(new ColoredIcon("res/img/sheet/save.png", foreground, 19, 19));
        saveButton.setPreferredSize(48, 32);
        saveButton.setActionCommand(STORAGE_SAVE_ACTION);
        saveButton.addActionListener(this);
        leftContainer.add(saveButton);

        undoButton = new Button(new ColoredIcon("res/img/sheet/undo.png", foreground, 19, 19));
        undoButton.setPreferredSize(32, 32);
        undoButton.setActionCommand(SHEET_UNDO_ACTION);
        undoButton.addActionListener(this);
        leftContainer.add(undoButton);

        redoButton = new Button(new ColoredIcon("res/img/sheet/redo.png", foreground, 19, 19));
        redoButton.setPreferredSize(32, 32);
        redoButton.setActionCommand(SHEET_REDO_ACTION);
        redoButton.addActionListener(this);
        leftContainer.add(redoButton);

        add(leftContainer);

        /* ---- ---- */

        titleLabel = new Label("Ranger");
        titleLabel.setForeground(foreground);
        titleLabel.setFont(font);
        add(titleLabel);

        /* ---- ---- */

        Container rightContainer = new Container();
        rightContainer.setPreferredSize(Integer.MAX_VALUE, 32);
        rightContainer.setMinimumSize(0, 0);

        rightContainer.add(new Spacer(Orientation.HORIZONTAL));

        iconifyButton = new Button(new ColoredIcon("res/img/window/iconify.png", foreground, 19, 19));
        iconifyButton.setPreferredSize(48, 32);
        iconifyButton.setActionCommand(WINDOW_ICONIFY_ACTION);
        iconifyButton.addActionListener(this);
        rightContainer.add(iconifyButton);

        toggleMaximizeButton = new Button(
                new ColoredIcon(
                        isMaximized ? "res/img/window/restore.png" : "res/img/window/maximize.png",
                        foreground, 19, 19));
        toggleMaximizeButton.setPreferredSize(48, 32);
        toggleMaximizeButton.setActionCommand(isMaximized ? WINDOW_RESTORE_ACTION : WINDOW_MAXIMIZE_ACTION);
        toggleMaximizeButton.addActionListener(this);
        rightContainer.add(toggleMaximizeButton);

        closeButton = new Button(new ColoredIcon("res/img/window/close.png", foreground, 19, 19));
        closeButton.setPreferredSize(48, 32);
        closeButton.setActionCommand(WINDOW_CLOSE_ACTION);
        closeButton.addActionListener(this);
        closeButton.setHoveredFilter(Color.RED);
        rightContainer.add(closeButton);

        add(rightContainer);
    }

    /**
     * Sets the window request listener.
     * 
     * @param windowRequestListener The window request listener.
     */
    public void setWindowRequestListener(WindowRequestListener windowRequestListener) {
        this.windowRequestListener = windowRequestListener;
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
     * Sets the sheet request listener.
     * 
     * @param sheetRequestListener The sheet request listener.
     */
    public void setSheetRequestListener(SheetRequestListener sheetRequestListener) {
        this.sheetRequestListener = sheetRequestListener;
    }

    /**
     * Sets the window title.
     * 
     * @param title The window title.
     */
    public void setWindowTitle(String title) {
        titleLabel.setText(title);
    }

    /**
     * Sets whether the save button is enabled.
     * 
     * @param isEnabled Whether the save button is enabled.
     */
    public void setSaveEnabled(boolean isEnabled) {
        saveButton.setEnabled(isEnabled);
        saveButton.repaint();
    }

    /**
     * Sets whether the undo button is enabled.
     * 
     * @param isEnabled Whether the undo button is enabled.
     */
    public void setUndoEnabled(boolean isEnabled) {
        undoButton.setEnabled(isEnabled);
        undoButton.repaint();
    }

    /**
     * Sets whether the redo button is enabled.
     * 
     * @param isEnabled Whether the redo button is enabled.
     */
    public void setRedoEnabled(boolean isEnabled) {
        redoButton.setEnabled(isEnabled);
        redoButton.repaint();
    }

    /**
     * Sets whether the window is maximized. This will change the icon of the toggle
     * maximize button.
     * 
     * @param isMaximized Whether the window is maximized.
     */
    public void setWindowMaximized(boolean isMaximized) {
        toggleMaximizeButton.setIcon(
                new ColoredIcon(
                        isMaximized ? "res/img/window/restore.png" : "res/img/window/maximize.png",
                        settings.get(Setting.HEADER_FOREGROUND, Color.class), 19, 19));
        toggleMaximizeButton.setActionCommand(isMaximized ? WINDOW_RESTORE_ACTION : WINDOW_MAXIMIZE_ACTION);
    }

    /**
     * Handles an action event.
     * 
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case STORAGE_SAVE_ACTION:
                storageRequestListener.save();
                break;

            case SHEET_UNDO_ACTION:
                sheetRequestListener.undo();
                break;

            case SHEET_REDO_ACTION:
                sheetRequestListener.redo();
                break;

            case WINDOW_ICONIFY_ACTION:
                windowRequestListener.iconify();
                break;

            case WINDOW_MAXIMIZE_ACTION:
                windowRequestListener.maximize();
                break;

            case WINDOW_RESTORE_ACTION:
                windowRequestListener.restore();
                break;

            case WINDOW_CLOSE_ACTION:
                windowRequestListener.close();
                break;
        }
    }
}
