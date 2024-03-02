package ranger.ui.menu;

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
import ranger.sheet.cell.CellHorizontalAlignment;
import ranger.sheet.cell.CellVerticalAlignment;
import ranger.ui.component.Block;
import ranger.ui.component.Button;
import ranger.ui.component.ColoredIcon;
import ranger.ui.component.Container;
import ranger.ui.component.Input;
import ranger.ui.component.KeybindListener;
import ranger.ui.component.Spacer;
import ranger.ui.component.layout.Orientation;
import ranger.ui.component.layout.Padding;
import ranger.ui.view.Selection;
import ranger.ui.view.View;
import ranger.ui.view.ViewStorage;

/**
 * Class representing the home menu.
 */
public class HomeMenu extends Menu implements ActionListener, KeybindListener, ChangeListener {
    /**
     * The copy action.
     */
    private static final String COPY_ACTION = "COPY_ACTION";

    /**
     * The cut action.
     */
    private static final String CUT_ACTION = "CUT_ACTION";

    /**
     * The paste action.
     */
    private static final String PASTE_ACTION = "PASTE_ACTION";

    /**
     * The undo action.
     */
    private static final String UNDO_ACTION = "UNDO_ACTION";

    /**
     * The redo action.
     */
    private static final String REDO_ACTION = "REDO_ACTION";

    /**
     * The horizontal left alignment action.
     */
    private static final String HORIZONTAL_LEFT_ACTION = "HORIZONTAL_LEFT_ACTION";

    /**
     * The horizontal center alignment action.
     */
    private static final String HORIZONTAL_CENTER_ACTION = "HORIZONTAL_CENTER_ACTION";

    /**
     * The horizontal right alignment action.
     */
    private static final String HORIZONTAL_RIGHT_ACTION = "HORIZONTAL_RIGHT_ACTION";

    /**
     * The horizontal alignment reset action.
     */
    private static final String HORIZONTAL_NONE_ACTION = "HORIZONTAL_NONE_ACTION";

    /**
     * The vertical top alignment action.
     */
    private static final String VERTICAL_TOP_ACTION = "VERTICAL_TOP_ACTION";

    /**
     * The vertical bottom alignment action.
     */
    private static final String VERTICAL_BOTTOM_ACTION = "VERTICAL_BOTTOM_ACTION";

    /**
     * The vertical alignment reset action.
     */
    private static final String VERTICAL_NONE_ACTION = "VERTICAL_NONE_ACTION";

    /**
     * The storage containing the sheets on which to perform actions.
     */
    private Storage storage;

    /**
     * The view storage containing the views on which to perform actions.
     */
    private ViewStorage viewStorage;

    /**
     * The paste button.
     */
    private Button pasteButton;

    /**
     * The cut button.
     */
    private Button cutButton;

    /**
     * The copy button.
     */
    private Button copyButton;

    /**
     * The format input.
     */
    private Input formatInput;

    /**
     * Whether the format input is locked.
     */
    private boolean formatLock;

    /**
     * The left alignment button.
     */
    private Button leftAlignButton;

    /**
     * The center alignment button.
     */
    private Button centerAlignButton;

    /**
     * The right alignment button.
     */
    private Button rightAlignButton;

    /**
     * The top alignment button.
     */
    private Button topAlignButton;

    /**
     * The bottom alignment button.
     */
    private Button bottomAlignButton;

    /**
     * The sheet request listener.
     */
    private SheetRequestListener sheetRequestListener;

    /**
     * Constructs a new home menu.
     * 
     * @param settings    The settings to use.
     * @param storage     The storage containing the sheets on which to perform
     *                    actions.
     * @param viewStorage The view storage containing the views on which to perform
     *                    actions.
     */
    public HomeMenu(SettingsRegistrar settings, Storage storage, ViewStorage viewStorage) {
        super(settings, "Home");

        this.storage = storage;
        this.viewStorage = viewStorage;

        Color foreground = settings.get(Setting.MENU_FOREGROUND, Color.class);
        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        Color inputBackground = settings.get(Setting.INPUT_BACKGROUND, Color.class);
        Color inputForeground = settings.get(Setting.INPUT_FOREGROUND, Color.class);
        Font font = settings.get(Setting.FONT, Font.class);

        /* ---- ---- */

        add(new Spacer(Orientation.HORIZONTAL, 4));

        pasteButton = new Button(new ColoredIcon("res/img/clipboard/paste.png", foreground, 19, 19));
        pasteButton.setPreferredSize(24, 24);
        pasteButton.setActionCommand(PASTE_ACTION);
        pasteButton.addActionListener(this);
        add(pasteButton);

        add(new Spacer(Orientation.HORIZONTAL, 4));

        cutButton = new Button(new ColoredIcon("res/img/clipboard/cut.png", foreground, 19, 19));
        cutButton.setPreferredSize(24, 24);
        cutButton.setActionCommand(CUT_ACTION);
        cutButton.addActionListener(this);
        add(cutButton);

        add(new Spacer(Orientation.HORIZONTAL, 4));

        copyButton = new Button(new ColoredIcon("res/img/clipboard/copy.png", foreground, 19, 19));
        copyButton.setPreferredSize(24, 24);
        copyButton.setActionCommand(COPY_ACTION);
        copyButton.addActionListener(this);
        add(copyButton);

        add(new Spacer(Orientation.HORIZONTAL, 4));
        add(new Block(border, 1, 20));
        add(new Spacer(Orientation.HORIZONTAL, 4));

        Container formatContainer = new Container(new Padding(1, 1, 5, 5));
        formatContainer.setOpaque(true);
        formatContainer.setBackground(inputBackground);
        formatContainer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));

        formatInput = new Input();
        formatInput.setPreferredSize(192, 22);
        formatInput.setForeground(inputForeground);
        formatInput.addChangeListener(this);

        formatInput.setKeybind(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK, UNDO_ACTION, this);
        formatInput.setKeybind(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK, REDO_ACTION, this);

        formatInput.setFont(font);

        formatContainer.add(formatInput);

        add(formatContainer);

        add(new Spacer(Orientation.HORIZONTAL, 4));
        add(new Block(border, 1, 20));
        add(new Spacer(Orientation.HORIZONTAL, 4));

        leftAlignButton = new Button(new ColoredIcon("res/img/format/align/left.png", foreground, 19, 19));
        leftAlignButton.setPreferredSize(24, 24);
        leftAlignButton.setActionCommand(HORIZONTAL_LEFT_ACTION);
        leftAlignButton.addActionListener(this);
        add(leftAlignButton);

        add(new Spacer(Orientation.HORIZONTAL, 4));

        centerAlignButton = new Button(new ColoredIcon("res/img/format/align/center.png", foreground, 19, 19));
        centerAlignButton.setPreferredSize(24, 24);
        centerAlignButton.setActionCommand(HORIZONTAL_CENTER_ACTION);
        centerAlignButton.addActionListener(this);
        add(centerAlignButton);

        add(new Spacer(Orientation.HORIZONTAL, 4));

        rightAlignButton = new Button(new ColoredIcon("res/img/format/align/right.png", foreground, 19, 19));
        rightAlignButton.setPreferredSize(24, 24);
        rightAlignButton.setActionCommand(HORIZONTAL_RIGHT_ACTION);
        rightAlignButton.addActionListener(this);
        add(rightAlignButton);

        add(new Spacer(Orientation.HORIZONTAL, 4));
        add(new Block(border, 1, 20));
        add(new Spacer(Orientation.HORIZONTAL, 4));

        topAlignButton = new Button(new ColoredIcon("res/img/format/align/top.png", foreground, 19, 19));
        topAlignButton.setPreferredSize(24, 24);
        topAlignButton.setActionCommand(VERTICAL_TOP_ACTION);
        topAlignButton.addActionListener(this);
        add(topAlignButton);

        add(new Spacer(Orientation.HORIZONTAL, 4));

        bottomAlignButton = new Button(new ColoredIcon("res/img/format/align/bottom.png", foreground, 19, 19));
        bottomAlignButton.setPreferredSize(24, 24);
        bottomAlignButton.setActionCommand(VERTICAL_BOTTOM_ACTION);
        bottomAlignButton.addActionListener(this);
        add(bottomAlignButton);

        add(new Spacer(Orientation.HORIZONTAL, 4));
        add(new Block(border, 1, 20));
        add(new Spacer(Orientation.HORIZONTAL, 4));
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
     * Returns the current selection of the selected view.
     * 
     * @return The current selection of the selected view.
     */
    private Selection getSelection() {
        View view = viewStorage.getSelectedView();
        if (view == null)
            return null;

        return view.getSelection();
    }

    /**
     * Updates the menu.
     */
    public void update() {
        updateFormat();
        updateAlignment();
    }

    /**
     * Updates the format input.
     */
    private void updateFormat() {
        if (formatLock)
            return;

        String format = null;
        Sheet sheet = storage.getSelectedSheet();

        Selection selection = getSelection();
        if (sheet != null && selection != null) {
            CellContent content = sheet.getContent(selection.getCursor());
            if (content != null)
                format = content.getFormat();
        }

        formatLock = true;
        formatInput.setText(format);
        formatLock = false;
    }

    /**
     * Updates the alignment buttons.
     */
    private void updateAlignment() {
        Selection selection = getSelection();
        if (selection == null)
            return;

        CellHorizontalAlignment horizontalAlignment = null;
        CellVerticalAlignment verticalAlignment = null;

        Sheet sheet = storage.getSelectedSheet();
        if (sheet != null) {
            CellContent content = sheet.getContent(selection.getCursor());
            if (content != null) {
                horizontalAlignment = content.getHorizontalAlignment();
                verticalAlignment = content.getVerticalAlignment();
            }
        }

        leftAlignButton.setSelected(horizontalAlignment == CellHorizontalAlignment.LEFT);
        leftAlignButton.setActionCommand(horizontalAlignment == CellHorizontalAlignment.LEFT ? HORIZONTAL_NONE_ACTION
                : HORIZONTAL_LEFT_ACTION);

        centerAlignButton.setSelected(horizontalAlignment == CellHorizontalAlignment.CENTER);
        centerAlignButton
                .setActionCommand(horizontalAlignment == CellHorizontalAlignment.CENTER ? HORIZONTAL_NONE_ACTION
                        : HORIZONTAL_CENTER_ACTION);

        rightAlignButton.setSelected(horizontalAlignment == CellHorizontalAlignment.RIGHT);
        rightAlignButton.setActionCommand(horizontalAlignment == CellHorizontalAlignment.RIGHT ? HORIZONTAL_NONE_ACTION
                : HORIZONTAL_RIGHT_ACTION);

        topAlignButton.setSelected(verticalAlignment == CellVerticalAlignment.TOP);
        topAlignButton.setActionCommand(verticalAlignment == CellVerticalAlignment.TOP ? VERTICAL_NONE_ACTION
                : VERTICAL_TOP_ACTION);

        bottomAlignButton.setSelected(verticalAlignment == CellVerticalAlignment.BOTTOM);
        bottomAlignButton.setActionCommand(verticalAlignment == CellVerticalAlignment.BOTTOM ? VERTICAL_NONE_ACTION
                : VERTICAL_BOTTOM_ACTION);
    }

    /**
     * Handles an action event.
     * 
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Selection selection = getSelection();
        if (selection == null)
            return;

        switch (e.getActionCommand()) {
            case COPY_ACTION:
                sheetRequestListener.copy(selection.getTopLeft(), selection.getWidth(), selection.getHeight());
                break;
            case CUT_ACTION:
                sheetRequestListener.cut(selection.getTopLeft(), selection.getWidth(), selection.getHeight());
                break;
            case PASTE_ACTION:
                sheetRequestListener.paste(selection.getTopLeft(), selection.getWidth(), selection.getHeight());
                break;

            case HORIZONTAL_LEFT_ACTION:
                sheetRequestListener.setHorizontalAlignments(selection.getTopLeft(), selection.getBottomRight(),
                        CellHorizontalAlignment.LEFT);
                break;
            case HORIZONTAL_CENTER_ACTION:
                sheetRequestListener.setHorizontalAlignments(selection.getTopLeft(), selection.getBottomRight(),
                        CellHorizontalAlignment.CENTER);
                break;
            case HORIZONTAL_RIGHT_ACTION:
                sheetRequestListener.setHorizontalAlignments(selection.getTopLeft(), selection.getBottomRight(),
                        CellHorizontalAlignment.RIGHT);
                break;
            case HORIZONTAL_NONE_ACTION:
                sheetRequestListener.setHorizontalAlignments(selection.getTopLeft(), selection.getBottomRight(), null);
                break;

            case VERTICAL_TOP_ACTION:
                sheetRequestListener.setVerticalAlignments(selection.getTopLeft(), selection.getBottomRight(),
                        CellVerticalAlignment.TOP);
                break;
            case VERTICAL_BOTTOM_ACTION:
                sheetRequestListener.setVerticalAlignments(selection.getTopLeft(), selection.getBottomRight(),
                        CellVerticalAlignment.BOTTOM);
                break;
            case VERTICAL_NONE_ACTION:
                sheetRequestListener.setVerticalAlignments(selection.getTopLeft(), selection.getBottomRight(), null);
                break;
        }
    }

    /**
     * Handles a keybind press.
     * 
     * @param keybind   The keybind.
     * @param modifiers The modifiers.
     */
    @Override
    public void keybindPressed(String keybind, int modifiers) {
        switch (keybind) {
            case UNDO_ACTION:
                sheetRequestListener.undo();
                break;
            case REDO_ACTION:
                sheetRequestListener.redo();
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
        Selection selection = getSelection();

        if (selection == null || formatLock)
            return;

        formatLock = true;
        sheetRequestListener.setFormat(selection.getCursor(), formatInput.getText());
        formatLock = false;
    }

}
