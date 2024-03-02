package ranger.ui.menu;

import java.awt.Color;

import javax.swing.BorderFactory;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.ui.component.Container;
import ranger.ui.component.layout.Padding;

/**
 * Class representing a menu.
 */
public abstract class Menu extends Container {
    /**
     * The name of this menu.
     */
    private String name;

    /**
     * Constructs a new menu.
     * 
     * @param settings The settings registrar to use.
     * @param name     The name of this menu.
     */
    public Menu(SettingsRegistrar settings, String name) {
        super(new Padding(0, 1, 0, 0));

        this.name = name;

        Color background = settings.get(Setting.MENU_BACKGROUND, Color.class);
        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        /* ---- ---- */

        setPreferredSize(Integer.MAX_VALUE, 33);

        setOpaque(true);
        setBackground(background);

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, border));
    }

    /**
     * Returns the name of this menu.
     * 
     * @return The name of this menu.
     */
    public String getName() {
        return name;
    }
}
