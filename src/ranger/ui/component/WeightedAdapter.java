package ranger.ui.component;

import javax.swing.JComponent;

import ranger.ui.component.layout.WeightedComponent;

/**
 * Class representing a weighted adapter.
 */
public class WeightedAdapter extends Container implements WeightedComponent {
    /**
     * The weight.
     */
    private int weight;

    /**
     * Constructs a new weighted adapter, with the specified weight and components.
     * 
     * @param weight     The weight.
     * @param components The components.
     */
    public WeightedAdapter(int weight, JComponent... components) {
        this.weight = weight;

        for (JComponent component : components)
            add(component);
    }

    /**
     * Returns the weight of the component.
     * 
     * @return The weight of the component.
     */
    @Override
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the component.
     * 
     * @param weight The weight of the component.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

}
