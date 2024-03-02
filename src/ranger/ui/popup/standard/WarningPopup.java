package ranger.ui.popup.standard;

import java.awt.Color;

import ranger.setting.SettingsRegistrar;
import ranger.ui.component.ColoredIcon;

/**
 * Class representing a warning popup.
 */
public class WarningPopup extends IconPopup {
    /**
     * Constructs a new warning popup.
     * 
     * @param settings The settings registrar to use.
     * @param text     The text.
     */
    public WarningPopup(SettingsRegistrar settings, String text) {
        super(settings, new ColoredIcon("res/img/popup/warning.png", new Color(0xFF4000), 24, 24), text);
    }
}
