package ranger.ui.popup;

import java.awt.Component;

import ranger.ui.component.Container;
import ranger.ui.component.layout.Direction;
import ranger.ui.component.layout.DirectionalLayout.Behavior;

/**
 * Class representing a popup layer.
 */
public class PopupLayer extends Container {
    /**
     * Constructs a new popup layer.
     */
    public PopupLayer() {
        super(Direction.SOUTH, Behavior.RIGHT);
    }

    /**
     * Returns whether or not the popup layer contains the specified point.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return Whether or not the popup layer contains the specified point.
     */
    @Override
    public boolean contains(int x, int y) {
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);

            if (component.contains(x - component.getX(), y - component.getY()))
                return true;
        }

        return false;
    }
}
