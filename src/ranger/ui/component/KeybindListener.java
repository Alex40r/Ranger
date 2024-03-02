package ranger.ui.component;

/**
 * Interface for keybind listeners.
 */
public interface KeybindListener {
    /**
     * Called when a keybind is pressed.
     * 
     * @param keybind   The keybind.
     * @param modifiers The modifiers.
     */
    public void keybindPressed(String keybind, int modifiers);
}
