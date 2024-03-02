package ranger.ui.element;

import javax.swing.Icon;

import ranger.sheet.Sheet;
import ranger.ui.context.ContextEntry;
import ranger.ui.context.ContextMenu;

/**
 * Class representing a sheet context entry, in a context menu.
 */
public class SheetContextEntry extends ContextEntry {
    /**
     * The sheet this context entry represents.
     */
    private Sheet sheet;

    /**
     * Constructs a new sheet context entry.
     * 
     * @param menu  The context menu.
     * @param sheet The sheet.
     */
    public SheetContextEntry(ContextMenu menu, Sheet sheet) {
        this(menu, sheet, null, null);
    }

    /**
     * Constructs a new sheet context entry.
     * 
     * @param menu  The context menu.
     * @param sheet The sheet.
     * @param text  The text.
     */
    public SheetContextEntry(ContextMenu menu, Sheet sheet, String text) {
        this(menu, sheet, text, null);
    }

    /**
     * Constructs a new sheet context entry.
     * 
     * @param menu  The context menu.
     * @param sheet The sheet.
     * @param icon  The icon.
     */
    public SheetContextEntry(ContextMenu menu, Sheet sheet, Icon icon) {
        this(menu, sheet, null, icon);
    }

    /**
     * Constructs a new sheet context entry.
     * 
     * @param menu  The context menu.
     * @param sheet The sheet.
     * @param text  The text.
     * @param icon  The icon.
     */
    public SheetContextEntry(ContextMenu menu, Sheet sheet, String text, Icon icon) {
        super(menu, text, icon);

        this.sheet = sheet;
    }

    /**
     * Returns the sheet this context entry represents.
     * 
     * @return The sheet this context entry represents.
     */
    public Sheet getSheet() {
        return sheet;
    }
}
