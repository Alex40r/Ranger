package ranger.ui.context;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.ui.component.layout.Direction;
import ranger.ui.component.layout.DirectionalLayout;
import ranger.ui.component.layout.Padding;
import ranger.ui.component.layout.DirectionalLayout.Behavior;

/**
 * Class representing a context menu.
 */
public class ContextMenu extends JPopupMenu {
    /**
     * Constructs a new context menu.
     * 
     * @param settings The settings.
     */
    public ContextMenu(SettingsRegistrar settings) {
        Color background = settings.get(Setting.CONTEXT_BACKGROUND, Color.class);
        Color border = settings.get(Setting.BORDER_COLOR, Color.class);

        /* ---- ---- */

        setLayout(new DirectionalLayout(Direction.SOUTH, Behavior.FILL, new Padding(1, 1, 1, 1)));

        setBackground(background);
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));
    }
}
