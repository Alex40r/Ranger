package ranger.ui.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.KeyStroke;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.sheet.Sheet;
import ranger.sheet.StorageRequestListener;
import ranger.ui.component.Button;
import ranger.ui.component.Container;
import ranger.ui.component.Input;
import ranger.ui.component.KeybindListener;
import ranger.ui.component.KeybindPassthrough;
import ranger.ui.component.Label;
import ranger.ui.component.Spacer;
import ranger.ui.component.layout.Direction;
import ranger.ui.component.layout.DirectionalLayout;
import ranger.ui.component.layout.DirectionalLayout.Behavior;
import ranger.ui.component.layout.Orientation;
import ranger.ui.component.layout.Padding;
import ranger.ui.popup.Popup;

/**
 * Class representing a popup for renaming a sheet.
 */
public class RenamePopup extends Popup implements ActionListener, KeybindListener {
    /**
     * The rename action.
     */
    private static final String RENAME_ACTION = "RENAME_ACTION";

    /**
     * The cancel action.
     */
    private static final String CANCEL_ACTION = "CANCEL_ACTION";

    /**
     * The sheet on which to operate.
     */
    private Sheet sheet;

    /**
     * The name input.
     */
    private Input nameInput;

    /**
     * The storage request listener.
     */
    private StorageRequestListener storageRequestListener;

    /**
     * Constructs a new rename popup.
     * 
     * @param settings The settings.
     * @param sheet    The sheet.
     */
    public RenamePopup(SettingsRegistrar settings, Sheet sheet) {
        super(settings);

        this.sheet = sheet;

        Color inputBackground = settings.get(Setting.INPUT_BACKGROUND, Color.class);
        Color inputForeground = settings.get(Setting.INPUT_FOREGROUND, Color.class);

        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        Font font = settings.get(Setting.FONT, Font.class);

        /* ---- ---- */

        setLayout(new DirectionalLayout(Direction.SOUTH, Behavior.FILL, new Padding(8, 8, 8, 8)));
        setTimeout(-1); // Indefinite
        setClickable(false);

        Label nameLabel = new Label("Please enter a new name:");
        nameLabel.setForeground(inputForeground);
        nameLabel.setFont(font);
        add(nameLabel);

        add(new Spacer(Orientation.VERTICAL, 8));

        Container nameContainer = new Container(new Padding(1, 1, 5, 5));
        nameContainer.setOpaque(true);
        nameContainer.setBackground(inputBackground);
        nameContainer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));

        nameInput = new Input();
        nameInput.setText(sheet.getName());
        nameInput.setPreferredSize(256, 24);
        nameInput.setForeground(inputForeground);
        nameInput.setFont(font);

        nameInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), RENAME_ACTION);
        nameInput.getActionMap().put(RENAME_ACTION, new KeybindPassthrough(RENAME_ACTION, this));

        nameInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), CANCEL_ACTION);
        nameInput.getActionMap().put(CANCEL_ACTION, new KeybindPassthrough(CANCEL_ACTION, this));

        nameContainer.add(nameInput);

        add(nameContainer);

        add(new Spacer(Orientation.VERTICAL, 8));

        Container buttonContainer = new Container(new DirectionalLayout(Direction.WEST));

        Container renameContainer = new Container(new Padding(1, 1, 1, 1));
        renameContainer.setOpaque(true);
        renameContainer.setBackground(inputBackground);
        renameContainer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));

        Button renameButton = new Button("Rename");
        renameButton.setPreferredSize(64, 24);
        renameButton.setForeground(inputForeground);
        renameButton.setFont(font);
        renameButton.setActionCommand(RENAME_ACTION);
        renameButton.addActionListener(this);
        renameContainer.add(renameButton);

        buttonContainer.add(renameContainer);

        buttonContainer.add(new Spacer(Orientation.HORIZONTAL, 8));

        Container cancelContainer = new Container(new Padding(1, 1, 1, 1));
        cancelContainer.setOpaque(true);
        cancelContainer.setBackground(inputBackground);
        cancelContainer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));

        Button cancelButton = new Button("Cancel");
        cancelButton.setPreferredSize(64, 24);
        cancelButton.setForeground(inputForeground);
        cancelButton.setFont(font);
        cancelButton.setActionCommand(CANCEL_ACTION);
        cancelButton.addActionListener(this);
        cancelContainer.add(cancelButton);

        buttonContainer.add(cancelContainer);

        add(buttonContainer);
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
     * Acquires focus.
     */
    public void acquireFocus() {
        nameInput.requestFocusInWindow();

        nameInput.selectAll();
    }

    /**
     * Returns the sheet on which this popup operates.
     * 
     * @return The sheet.
     */
    public Sheet getSheet() {
        return sheet;
    }

    /**
     * Renames the sheet.
     */
    public void rename() {
        storageRequestListener.renameSheet(sheet, nameInput.getText());

        closePopup();
    }

    /**
     * Cancels the rename.
     */
    public void cancel() {
        closePopup();
    }

    /**
     * Handles an action event.
     * 
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case RENAME_ACTION:
                rename();
                break;

            case CANCEL_ACTION:
                cancel();
                break;
        }
    }

    /**
     * Handles a keybind.
     * 
     * @param keybind   The keybind.
     * @param modifiers The modifiers.
     */
    @Override
    public void keybindPressed(String keybind, int modifiers) {
        switch (keybind) {
            case RENAME_ACTION:
                rename();
                break;

            case CANCEL_ACTION:
                cancel();
                break;
        }
    }
}
