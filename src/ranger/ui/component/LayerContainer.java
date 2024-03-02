package ranger.ui.component;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLayeredPane;

/**
 * Class representing a layer container.
 * This class is a JLayeredPane that automatically resizes its components when
 * it is resized, to ensure that the components fill the entire container.
 */
public class LayerContainer extends JLayeredPane implements ComponentListener {
    /**
     * Constructs a new layer container.
     */
    public LayerContainer() {
        addComponentListener(this);
    }

    /**
     * Called when the layer container is resized.
     * 
     * @param e The component event.
     */
    @Override
    public void componentResized(ComponentEvent e) {
        for (int i = 0; i < getComponentCount(); i++)
            getComponent(i).setBounds(0, 0, getWidth(), getHeight());

        revalidate();
        repaint();
    }

    /**
     * Called when the layer container is moved.
     * 
     * @param e The component event.
     */
    @Override
    public void componentMoved(ComponentEvent e) {
    }

    /**
     * Called when the layer container is shown.
     * 
     * @param e The component event.
     */
    @Override
    public void componentShown(ComponentEvent e) {
    }

    /**
     * Called when the layer container is hidden.
     * 
     * @param e The component event.
     */
    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
