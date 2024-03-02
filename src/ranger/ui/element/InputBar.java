package ranger.ui.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.sheet.Sheet;
import ranger.sheet.SheetRequestListener;
import ranger.sheet.Storage;
import ranger.sheet.cell.CellContent;
import ranger.sheet.cell.CellCoordinates;
import ranger.ui.component.Button;
import ranger.ui.component.ColoredIcon;
import ranger.ui.component.Container;
import ranger.ui.component.Input;
import ranger.ui.component.KeybindListener;
import ranger.ui.component.Spacer;
import ranger.ui.component.layout.Orientation;
import ranger.ui.component.layout.Padding;
import ranger.ui.popup.PopupRequestListener;
import ranger.ui.popup.standard.WarningPopup;
import ranger.ui.view.SelectionDirection;
import ranger.ui.view.ViewRequestListener;

/**
 * Class representing the input bar.
 */
public class InputBar extends Container implements ChangeListener, ActionListener, KeybindListener {
    /**
     * The cancel action.
     */
    private static final String CANCEL_ACTION = "CANCEL_ACTION";

    /**
     * The confirm action.
     */
    private static final String CONFIRM_ACTION = "CONFIRM_ACTION";

    /**
     * The show warning action.
     */
    private static final String SHOW_WARNING_ACTION = "SHOW_WARNING_ACTION";

    /**
     * The cycle up action.
     */
    private static final String KEY_CYCLE_UP_ACTION = "KEY_CYCLE_UP_ACTION";

    /**
     * The cycle down action.
     */
    private static final String KEY_CYCLE_DOWN_ACTION = "KEY_CYCLE_DOWN_ACTION";

    /**
     * The cycle left action.
     */
    private static final String KEY_CYCLE_LEFT_ACTION = "KEY_CYCLE_LEFT_ACTION";

    /**
     * The cycle right action.
     */
    private static final String KEY_CYCLE_RIGHT_ACTION = "KEY_CYCLE_RIGHT_ACTION";

    /**
     * The undo action.
     */
    private static final String KEY_UNDO_ACTION = "KEY_UNDO_ACTION";

    /**
     * The redo action.
     */
    private static final String KEY_REDO_ACTION = "KEY_REDO_ACTION";

    /**
     * The settings registrar that contains the settings to use.
     */
    private SettingsRegistrar settings;

    /**
     * The storage that contains the sheets on which to operate.
     */
    private Storage storage;

    /**
     * The coordinates input.
     */
    private Input coordinatesInput;

    /**
     * The current coordinates.
     */
    private CellCoordinates coordinates;

    /**
     * Whether the coordinates input is locked.
     */
    private boolean coordinatesLock;

    /**
     * The cancel button.
     */
    private Button cancelButton;

    /**
     * The confirm button.
     */
    private Button confirmButton;

    /**
     * The expression input.
     */
    private Input expressionInput;

    /**
     * The initial expression.
     */
    private String initialExpression;

    /**
     * The warning container.
     */
    private Container warningContainer;

    /**
     * The warning button.
     */
    private Button warningButton;

    /**
     * The warning message.
     */
    private String warningMessage;

    /**
     * The popup request listener.
     */
    private PopupRequestListener popupRequestListener;

    /**
     * The sheet request listener.
     */
    private SheetRequestListener sheetRequestListener;

    /**
     * The view request listener.
     */
    private ViewRequestListener viewRequestListener;

    /**
     * Constructs a new input bar.
     * 
     * @param settings The settings to use.
     * @param storage  The storage to use.
     */
    public InputBar(SettingsRegistrar settings, Storage storage) {
        super(new Padding(0, 1, 0, 0));

        this.settings = settings;
        this.storage = storage;

        initialExpression = "";

        Color background = settings.get(Setting.INPUT_BAR_BACKGROUND, Color.class);
        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        Color inputBackground = settings.get(Setting.INPUT_BACKGROUND, Color.class);
        Color inputForeground = settings.get(Setting.INPUT_FOREGROUND, Color.class);
        Font font = settings.get(Setting.FONT, Font.class);

        /* ---- ---- */

        setPreferredSize(Integer.MAX_VALUE, 33);

        setOpaque(true);
        setBackground(background);

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, border));

        /* ---- ---- */

        add(new Spacer(Orientation.HORIZONTAL, 4));

        Container coordinatesContainer = new Container(new Padding(1, 1, 5, 5));
        coordinatesContainer.setOpaque(true);
        coordinatesContainer.setBackground(inputBackground);
        coordinatesContainer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));

        coordinatesInput = new Input();
        coordinatesInput.setPreferredSize(48, 24);
        coordinatesInput.setForeground(inputForeground);
        coordinatesInput.setFont(font);
        coordinatesInput.addChangeListener(this);
        coordinatesContainer.add(coordinatesInput);

        add(coordinatesContainer);

        /* ---- ---- */

        add(new Spacer(Orientation.HORIZONTAL, 4));

        Container buttonContainer = new Container(new Padding(1, 1, 1, 1));
        buttonContainer.setOpaque(true);
        buttonContainer.setBackground(inputBackground);
        buttonContainer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));

        cancelButton = new Button(new ColoredIcon("res/img/input/cancel.png", inputForeground, 19, 19));
        cancelButton.setPreferredSize(24, 24);
        cancelButton.setActionCommand(CANCEL_ACTION);
        cancelButton.addActionListener(this);
        cancelButton.setEnabled(false);
        buttonContainer.add(cancelButton);

        confirmButton = new Button(new ColoredIcon("res/img/input/confirm.png", inputForeground, 19, 19));
        confirmButton.setPreferredSize(24, 24);
        confirmButton.setActionCommand(CONFIRM_ACTION);
        confirmButton.addActionListener(this);
        confirmButton.setEnabled(false);
        buttonContainer.add(confirmButton);

        add(buttonContainer);

        /* ---- ---- */

        add(new Spacer(Orientation.HORIZONTAL, 4));

        Container expressionContainer = new Container(new Padding(1, 1, 5, 5));
        expressionContainer.setOpaque(true);
        expressionContainer.setBackground(inputBackground);
        expressionContainer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));

        expressionInput = new Input();
        expressionInput.setPreferredSize(Integer.MAX_VALUE, 24);
        expressionInput.setForeground(inputForeground);
        expressionInput.setFont(font);
        expressionInput.addChangeListener(this);
        expressionInput.setFocusTraversalKeysEnabled(false);

        expressionInput.setKeybind(KeyEvent.VK_ESCAPE, 0, CANCEL_ACTION, this);

        expressionInput.setKeybind(KeyEvent.VK_ENTER, 0, KEY_CYCLE_DOWN_ACTION, this);
        expressionInput.setKeybind(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK, KEY_CYCLE_UP_ACTION, this);
        expressionInput.setKeybind(KeyEvent.VK_TAB, 0, KEY_CYCLE_RIGHT_ACTION, this);
        expressionInput.setKeybind(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK, KEY_CYCLE_LEFT_ACTION, this);

        expressionInput.setKeybind(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK, KEY_UNDO_ACTION, this);
        expressionInput.setKeybind(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK, KEY_REDO_ACTION, this);

        expressionContainer.add(expressionInput);

        add(expressionContainer);

        /* ---- ---- */

        warningContainer = new Container(new Padding(0, 0, 4, 0));
        warningContainer.setVisible(false);

        warningButton = new Button(new ColoredIcon("res/img/input/warning.png", new Color(0xFF4000), 19, 19));
        warningButton.setPreferredSize(24, 24);
        warningButton.setActionCommand(SHOW_WARNING_ACTION);
        warningButton.addActionListener(this);
        warningContainer.add(warningButton);

        add(warningContainer);

        /* ---- ---- */

        add(new Spacer(Orientation.HORIZONTAL, 4));
    }

    /**
     * Sets the popup request listener.
     * 
     * @param listener The listener.
     */
    public void setPopupRequestListener(PopupRequestListener listener) {
        this.popupRequestListener = listener;
    }

    /**
     * Sets the sheet request listener.
     * 
     * @param listener The listener.
     */
    public void setSheetRequestListener(SheetRequestListener listener) {
        this.sheetRequestListener = listener;
    }

    /**
     * Sets the view request listener.
     * 
     * @param listener The listener.
     */
    public void setViewRequestListener(ViewRequestListener listener) {
        this.viewRequestListener = listener;
    }

    /**
     * Sets the cursor.
     * 
     * @param coordinates The coordinates.
     */
    public void setCursor(CellCoordinates coordinates) {
        if (coordinatesLock)
            return;

        coordinatesLock = true;
        if (coordinates == null)
            coordinatesInput.setText("");
        else
            coordinatesInput.setText(coordinates.toString());
        coordinatesLock = false;

        passiveSetCursor(coordinates);
    }

    /**
     * Sets the cursor without affecting the coordinates input.
     * 
     * @param coordinates The coordinates.
     */
    private void passiveSetCursor(CellCoordinates coordinates) {
        this.coordinates = coordinates;

        initialExpression = "";

        Sheet selected = storage.getSelectedSheet();
        if (selected != null && coordinates != null) {
            CellContent content = selected.getContent(coordinates);
            initialExpression = content == null ? "" : content.getExpression();
            if (initialExpression == null)
                initialExpression = "";
        }

        expressionInput.setText(initialExpression);

        expressionInput.setCaretPosition(0);
        expressionInput.moveCaretPosition(initialExpression.length());
    }

    /**
     * Updates the expression.
     */
    public void updateExpression() {
        String newExpression = expressionInput.getText();
        if (!newExpression.equals(initialExpression))
            return;

        initialExpression = "";
        Sheet selected = storage.getSelectedSheet();
        if (selected != null && coordinates != null) {
            CellContent content = selected.getContent(coordinates);
            initialExpression = content == null ? "" : content.getExpression();
            if (initialExpression == null)
                initialExpression = "";
        }

        expressionInput.setText(initialExpression);
    }

    /**
     * Cancels the input, and resets the expression to the initial expression.
     */
    public void cancel() {
        initialExpression = "";
        Sheet selected = storage.getSelectedSheet();
        if (selected != null && coordinates != null) {
            CellContent content = selected.getContent(coordinates);
            initialExpression = content == null ? "" : content.getExpression();
            if (initialExpression == null)
                initialExpression = "";
        }

        expressionInput.setText(initialExpression);
    }

    /**
     * Confirms the input, and updates the sheet.
     */
    public void confirm() {
        if (coordinates == null)
            return;

        initialExpression = expressionInput.getText();

        sheetRequestListener.setExpression(coordinates, initialExpression);

        cancelButton.setEnabled(false);
        confirmButton.setEnabled(false);
    }

    /**
     * Shows the warning popup.
     */
    public void showWarning() {
        if (warningMessage == null)
            return;

        popupRequestListener.pushPopup(new WarningPopup(settings, warningMessage));
    }

    /**
     * Checks the state of the input bar.
     */
    private void checkState() {
        String newExpression = expressionInput.getText();
        boolean changed = !newExpression.equals(initialExpression);

        cancelButton.setEnabled(changed);
        confirmButton.setEnabled(changed && coordinates != null);

        Sheet selectedSheet = storage.getSelectedSheet();
        if (selectedSheet == null)
            return;
        try {
            warningMessage = null;
            warningContainer.setVisible(false);
            if (!newExpression.startsWith("="))
                return;
            selectedSheet.interactive(newExpression.substring(1));
        } catch (Exception e) {
            warningContainer.setVisible(true);
            warningMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
        }
    }

    /**
     * Emits a key event to the expression input.
     * 
     * @param e The key event.
     */
    public void emitKey(KeyEvent e) {
        expressionInput.requestFocusInWindow();
        expressionInput.dispatchEvent(e);
    }

    /**
     * Handles an action event.
     * 
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case CANCEL_ACTION:
                cancel();
                break;
            case CONFIRM_ACTION:
                confirm();
                break;
            case SHOW_WARNING_ACTION:
                showWarning();
                break;
        }
    }

    /**
     * Handles a keybind event.
     * 
     * @param keybind   The keybind.
     * @param modifiers The modifiers.
     */
    @Override
    public void keybindPressed(String keybind, int modifiers) {
        switch (keybind) {
            case CANCEL_ACTION:
                cancel();
                viewRequestListener.acquireFocus();
                break;

            case KEY_CYCLE_DOWN_ACTION:
                confirm();
                viewRequestListener.moveCursor(SelectionDirection.DOWN, true);
                viewRequestListener.acquireFocus();
                break;

            case KEY_CYCLE_UP_ACTION:
                confirm();
                viewRequestListener.moveCursor(SelectionDirection.UP, true);
                viewRequestListener.acquireFocus();
                break;

            case KEY_CYCLE_LEFT_ACTION:
                confirm();
                viewRequestListener.moveCursor(SelectionDirection.LEFT, true);
                viewRequestListener.acquireFocus();
                break;

            case KEY_CYCLE_RIGHT_ACTION:
                confirm();
                viewRequestListener.moveCursor(SelectionDirection.RIGHT, true);
                viewRequestListener.acquireFocus();
                break;

            case KEY_UNDO_ACTION:
                sheetRequestListener.undo();
                cancel();
                break;

            case KEY_REDO_ACTION:
                sheetRequestListener.redo();
                cancel();
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
        if (e.getSource() == expressionInput) {
            checkState();
        } else if (e.getSource() == coordinatesInput && !coordinatesLock) {
            CellCoordinates coordinates = null;

            try {
                coordinates = CellCoordinates.parse(coordinatesInput.getText());
            } catch (Exception ex) {
            }

            coordinatesLock = true;
            passiveSetCursor(coordinates);
            viewRequestListener.moveCursor(coordinates);
            coordinatesLock = false;
        }
    }
}
