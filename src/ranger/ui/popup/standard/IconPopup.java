package ranger.ui.popup.standard;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.ui.component.Label;
import ranger.ui.component.Spacer;
import ranger.ui.component.layout.Orientation;
import ranger.ui.popup.Popup;

/**
 * Class representing a popup with an icon and text.
 */
public class IconPopup extends Popup {
    /**
     * Constructs a new icon popup.
     * 
     * @param settings The settings registrar to use.
     * @param icon     The icon.
     * @param text     The text.
     */
    public IconPopup(SettingsRegistrar settings, Icon icon, String text) {
        super(settings);

        Color foreground = settings.get(Setting.POPUP_FOREGROUND, Color.class);
        Font font = settings.get(Setting.FONT, Font.class);

        /* ---- ---- */

        Label label = new Label(icon);
        add(label);

        add(new Spacer(Orientation.HORIZONTAL, 4));

        label = new Label(text);
        label.setForeground(foreground);
        label.setFont(font);
        add(label);
    }
}
