package ranger.ui.menu;

import ranger.setting.SettingsRegistrar;

/**
 * Class representing a file menu.
 */
public class FileMenu extends Menu {
    /**
     * Constructs a new file menu.
     * 
     * @param settings The settings registrar to use.
     */
    public FileMenu(SettingsRegistrar settings) {
        super(settings, "File");
    }
}
