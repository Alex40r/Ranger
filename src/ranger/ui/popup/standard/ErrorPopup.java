package ranger.ui.popup.standard;

import java.awt.Color;

import ranger.setting.SettingsRegistrar;
import ranger.ui.component.ColoredIcon;

/**
 * Class representing an error popup.
 */
public class ErrorPopup extends IconPopup {
    /**
     * Constructs a new error popup.
     * 
     * @param settings The settings registrar to use.
     * @param text     The text.
     */
    public ErrorPopup(SettingsRegistrar settings, String text) {
        super(settings, new ColoredIcon("res/img/popup/error.png", Color.RED, 24, 24), text);
    }
}
