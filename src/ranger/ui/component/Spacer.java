package ranger.ui.component;

import java.awt.Dimension;

import javax.swing.JComponent;

import ranger.ui.component.layout.Orientation;
import ranger.ui.component.layout.WeightedComponent;

/**
 * Class representing a spacer.
 */
public class Spacer extends JComponent implements WeightedComponent {
    /**
     * The orientation.
     */
    private Orientation orientation;

    /**
     * The size.
     */
    private int size;

    /**
     * The weight.
     */
    private int weight;

    /**
     * Constructs a new spacer.
     * 
     * @param weight      The weight.
     * @param orientation The orientation.
     * @param size        The size.
     */
    public Spacer(int weight, Orientation orientation, int size) {
        this.weight = weight;
        this.orientation = orientation;
        this.size = size;
    }

    /**
     * Constructs a new spacer.
     * 
     * @param weight      The weight.
     * @param orientation The orientation.
     */
    public Spacer(int weight, Orientation orientation) {
        this(weight, orientation, Integer.MAX_VALUE);
    }

    /**
     * Constructs a new spacer.
     * 
     * @param weight The weight.
     */
    public Spacer(int weight) {
        this(weight, Orientation.HORIZONTAL);
    }

    /**
     * Constructs a new spacer.
     * 
     * @param orientation The orientation.
     * @param size        The size.
     */
    public Spacer(Orientation orientation, int size) {
        this(0, orientation, size);
    }

    /**
     * Constructs a new spacer.
     * 
     * @param orientation The orientation.
     */
    public Spacer(Orientation orientation) {
        this(0, orientation);
    }

    /**
     * Returns the orientation of this spacer.
     * 
     * @return The orientation of this spacer.
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Sets the orientation of this spacer.
     * 
     * @param orientation The orientation.
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns the size of this spacer.
     * 
     * @return The size of this spacer.
     */
    public int getSpacerSize() {
        return size;
    }

    /**
     * Sets the size of this spacer.
     * 
     * @param size The size.
     */
    public void setSpacerSize(int size) {
        this.size = size;
    }

    /**
     * Returns the weight of this spacer.
     * 
     * @return The weight of this spacer.
     */
    @Override
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight of this spacer.
     * 
     * @param weight The weight.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Returns the minimum size of this spacer.
     * 
     * @return The minimum size of this spacer.
     */
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(0, 0);
    }

    /**
     * Returns the preferred size of this spacer.
     * 
     * @return The preferred size of this spacer.
     */
    @Override
    public Dimension getPreferredSize() {
        if (orientation == Orientation.HORIZONTAL)
            return new Dimension(size, 0);
        else if (orientation == Orientation.VERTICAL)
            return new Dimension(0, size);
        else if (orientation == Orientation.BOTH)
            return new Dimension(size, size);
        else
            return new Dimension(0, 0);
    }
}
