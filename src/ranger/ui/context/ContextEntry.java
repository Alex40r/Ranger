package ranger.ui.context;

import javax.swing.BorderFactory;
import javax.swing.Icon;

import ranger.ui.component.Button;

/**
 * Class representing a context entry, in a context menu.
 */
public class ContextEntry extends Button {
    /**
     * The context menu.
     */
    private ContextMenu menu;

    /**
     * Constructs a new context entry.
     * 
     * @param menu The context menu.
     */
    public ContextEntry(ContextMenu menu) {
        this(menu, null, null);
    }

    /**
     * Constructs a new context entry.
     * 
     * @param menu The context menu.
     * @param text The text.
     */
    public ContextEntry(ContextMenu menu, String text) {
        this(menu, text, null);
    }

    /**
     * Constructs a new context entry.
     * 
     * @param menu The context menu.
     * @param icon The icon.
     */
    public ContextEntry(ContextMenu menu, Icon icon) {
        this(menu, null, icon);
    }

    /**
     * Constructs a new context entry.
     * 
     * @param menu The context menu.
     * @param text The text.
     * @param icon The icon.
     */
    public ContextEntry(ContextMenu menu, String text, Icon icon) {
        super(text, icon);

        this.menu = menu;

        setHorizontalAlignment(LEFT);

        setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        setPreferredSize(getPreferredWidth(), 22);
    }

    /**
     * Returns the context menu.
     * 
     * @return The context menu.
     */
    public ContextMenu getMenu() {
        return menu;
    }
}
