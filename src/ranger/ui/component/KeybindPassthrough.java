package ranger.ui.component;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

/**
 * Class representing a keybind passthrough.
 * This class receives keybind events and passes them to a list of keybind
 * listeners.
 */
public class KeybindPassthrough extends AbstractAction {
    /**
     * The keybind.
     */
    private String keybind;

    /**
     * The keybind listeners.
     */
    private List<KeybindListener> keybindListeners;

    /**
     * Constructs a new keybind passthrough.
     * 
     * @param keybind   The keybind.
     * @param listeners The keybind listeners.
     */
    public KeybindPassthrough(String keybind, KeybindListener... listeners) {
        this.keybind = keybind;

        keybindListeners = new ArrayList<KeybindListener>();

        for (KeybindListener listener : listeners)
            addKeybindListener(listener);
    }

    /**
     * Adds a keybind listener.
     * 
     * @param listener The keybind listener.
     */
    public void addKeybindListener(KeybindListener listener) {
        keybindListeners.add(listener);
    }

    /**
     * Removes a keybind listener.
     * 
     * @param listener The keybind listener.
     */
    public void removeKeybindListener(KeybindListener listener) {
        keybindListeners.remove(listener);
    }

    /**
     * Called when the keybind is pressed.
     * 
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (KeybindListener listener : keybindListeners)
            listener.keybindPressed(keybind, e.getModifiers());
    }
}
