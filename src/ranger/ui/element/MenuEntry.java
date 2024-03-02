package ranger.ui.element;

import java.awt.Color;
import java.awt.Font;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.ui.component.Button;
import ranger.ui.menu.Menu;

/**
 * Class representing a menu entry.
 */
public class MenuEntry extends Button {
    /**
     * The menu this entry represents.
     */
    private Menu menu;

    /**
     * The selected background.
     */
    private Color selectedBackground;

    /**
     * The selected foreground.
     */
    private Color selectedForeground;

    /**
     * Constructs a new menu entry.
     * 
     * @param settings The settings.
     * @param menu     The menu.
     */
    public MenuEntry(SettingsRegistrar settings, Menu menu) {
        super(menu.getName());

        this.menu = menu;

        selectedBackground = settings.get(Setting.MENU_BACKGROUND, Color.class);
        selectedForeground = settings.get(Setting.HEADER_BACKGROUND, Color.class);

        Color foreground = settings.get(Setting.HEADER_FOREGROUND, Color.class);
        Font font = settings.get(Setting.FONT, Font.class);

        /* ---- ---- */

        setPreferredSize(getPreferredWidth() + 40, 32);

        setBackground(selectedBackground);
        setForeground(foreground);
        setFont(font);
    }

    /**
     * Returns the menu this entry represents.
     * 
     * @return The menu this entry represents.
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Returns whether this entry is opaque.
     * 
     * @return Whether this entry is opaque.
     */
    @Override
    public boolean isOpaque() {
        return isSelected() || super.isOpaque();
    }

    /**
     * Returns the background.
     * 
     * @return The background.
     */
    @Override
    public Color getBackground() {
        return isSelected() ? selectedBackground : super.getBackground();
    }

    /**
     * Returns the foreground.
     * 
     * @return The foreground.
     */
    @Override
    public Color getForeground() {
        return isSelected() ? selectedForeground : super.getForeground();
    }
}
