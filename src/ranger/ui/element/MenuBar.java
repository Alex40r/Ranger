package ranger.ui.element;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.ui.component.Container;
import ranger.ui.component.layout.Direction;
import ranger.ui.menu.Menu;

/**
 * Class representing the menu bar.
 */
public class MenuBar extends Container implements ActionListener {
    /**
     * The settings registrar that contains the settings to use.
     */
    private SettingsRegistrar settings;

    /**
     * The entry container.
     */
    private Container entryContainer;

    /**
     * The menu container.
     */
    private Container menuContainer;

    /**
     * The menus.
     */
    private Menu[] menus;

    /**
     * The selected menu.
     */
    private Menu selectedMenu;

    /**
     * Constructs a new menu bar.
     * 
     * @param settings The settings.
     */
    public MenuBar(SettingsRegistrar settings) {
        super(Direction.SOUTH);

        this.settings = settings;

        Color background = settings.get(Setting.HEADER_BACKGROUND, Color.class);

        /* ---- ---- */

        entryContainer = new Container();
        entryContainer.setPreferredSize(Integer.MAX_VALUE, 32);
        entryContainer.setOpaque(true);
        entryContainer.setBackground(background);

        populateEntryContainer();

        add(entryContainer);

        /* ---- ---- */

        menuContainer = new Container();
        add(menuContainer);
    }

    /**
     * Returns the menus in this menu bar.
     * 
     * @return The menus.
     */
    public Menu[] getMenus() {
        return menus.clone();
    }

    /**
     * Sets the menus in this menu bar.
     * 
     * @param menus The menus.
     */
    public void setMenus(Menu[] menus) {
        this.menus = menus == null ? null : menus.clone();

        populateEntryContainer();
    }

    /**
     * Returns whether this menu bar has the specified menu.
     * 
     * @param menu The menu.
     * @return Whether this menu bar has the specified menu.
     */
    public boolean hasMenu(Menu menu) {
        if (menus == null)
            return false;

        for (Menu m : menus)
            if (m == menu)
                return true;

        return false;
    }

    /**
     * Returns the selected menu.
     * 
     * @return The selected menu.
     */
    public Menu getSelectedMenu() {
        return selectedMenu;
    }

    /**
     * Sets the selected menu.
     * 
     * @param menu The selected menu.
     */
    public void setSelectedMenu(Menu menu) {
        if (menu != null && !hasMenu(menu))
            throw new IllegalArgumentException("Cannot select a menu that is not in the menu bar.");

        selectedMenu = menu;

        updateSelection();
    }

    /**
     * Populates the entry container with the menu entries.
     */
    private void populateEntryContainer() {
        entryContainer.removeAll();

        if (menus == null)
            return;

        for (Menu menu : menus) {
            MenuEntry entry = new MenuEntry(settings, menu);
            entry.addActionListener(this);
            entryContainer.add(entry);
        }

        updateSelection();
    }

    /**
     * Updates the selection.
     */
    private void updateSelection() {
        for (Component component : entryContainer.getComponents())
            if (component instanceof MenuEntry) {
                MenuEntry entry = (MenuEntry) component;

                entry.setSelected(entry.getMenu() == selectedMenu);
            }

        entryContainer.revalidate();
        entryContainer.repaint();

        menuContainer.removeAll();
        if (selectedMenu != null)
            menuContainer.add(selectedMenu);

        menuContainer.revalidate();
        menuContainer.repaint();
    }

    /**
     * Handles the action event.
     * 
     * @param e The event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof MenuEntry) {
            MenuEntry entry = (MenuEntry) e.getSource();
            Menu menu = entry.getMenu();

            if (menu == selectedMenu)
                selectedMenu = null;
            else
                selectedMenu = menu;

            updateSelection();
        }
    }
}
