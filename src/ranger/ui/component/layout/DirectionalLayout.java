package ranger.ui.component.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class representing a directional layout.
 * In a directional layout, components are laid out in a single direction,
 * similar to CSS's flex.
 */
public class DirectionalLayout implements LayoutManager {
    /**
     * Enum representing the behavior of the children of a directional layout.
     */
    public enum Behavior {
        /**
         * The children take up as much space as possible in the other axis.
         */
        FILL,

        /**
         * The children are aligned to the left of the other axis.
         */
        LEFT,

        /**
         * The children are aligned to the center of the other axis.
         */
        CENTER,

        /**
         * The children are aligned to the right of the other axis.
         */
        RIGHT
    }

    /**
     * The direction in which the layout is oriented.
     */
    private Direction direction;

    /**
     * The behavior of the children of the layout.
     */
    private Behavior behavior;

    /**
     * The padding of the layout.
     */
    private Padding padding;

    /**
     * Constructs a new directional layout with the specified direction, behavior,
     * and padding.
     * 
     * @param direction The direction in which the layout is oriented.
     * @param behavior  The behavior of the children of the layout.
     * @param padding   The padding of the layout.
     */
    public DirectionalLayout(Direction direction, Behavior behavior, Padding padding) {
        this.direction = direction;
        this.behavior = behavior;
        this.padding = padding;
    }

    /**
     * Constructs a new directional layout with the specified direction and
     * behavior.
     * 
     * @param direction The direction in which the layout is oriented.
     * @param behavior  The behavior of the children of the layout.
     */
    public DirectionalLayout(Direction direction, Behavior behavior) {
        this(direction, behavior, new Padding(0, 0, 0, 0));
    }

    /**
     * Constructs a new directional layout with the specified direction and padding.
     * 
     * @param direction The direction in which the layout is oriented.
     * @param padding   The padding of the layout.
     */
    public DirectionalLayout(Direction direction, Padding padding) {
        this(direction, Behavior.CENTER, padding);
    }

    /**
     * Constructs a new directional layout with the specified direction.
     * 
     * @param direction The direction in which the layout is oriented.
     */
    public DirectionalLayout(Direction direction) {
        this(direction, Behavior.CENTER);
    }

    /**
     * This method does nothing for this layout manager.
     * 
     * @param name The name of the component.
     * @param comp The component.
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * This method does nothing for this layout manager.
     * 
     * @param comp The component.
     */
    @Override
    public void removeLayoutComponent(Component comp) {
    }

    /**
     * Returns the preferred size of the specified container.
     * 
     * @param parent The parent container.
     * @return The preferred size of the specified container.
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int preferredWidth = 0;
        int preferredHeight = 0;

        for (Component component : parent.getComponents()) {
            Dimension preferredSize = component.getPreferredSize();

            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                preferredHeight += preferredSize.height;
                preferredWidth = Math.max(preferredWidth, preferredSize.width);
            } else if (direction == Direction.EAST || direction == Direction.WEST) {
                preferredWidth += preferredSize.width;
                preferredHeight = Math.max(preferredHeight, preferredSize.height);
            }

            if (preferredHeight < 0)
                preferredHeight = Integer.MAX_VALUE;
            if (preferredWidth < 0)
                preferredWidth = Integer.MAX_VALUE;
        }

        preferredWidth += padding.left + padding.right;
        preferredHeight += padding.top + padding.bottom;

        if (preferredHeight < 0)
            preferredHeight = Integer.MAX_VALUE;
        if (preferredWidth < 0)
            preferredWidth = Integer.MAX_VALUE;

        return new Dimension(preferredWidth, preferredHeight);
    }

    /**
     * Returns the minimum size of the specified container.
     * 
     * @param parent The parent container.
     * @return The minimum size of the specified container.
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        int minimumWidth = 0;
        int minimumHeight = 0;

        for (Component component : parent.getComponents()) {
            Dimension minimumSize = component.getMinimumSize();

            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                minimumHeight += minimumSize.height;
                minimumWidth = Math.max(minimumWidth, minimumSize.width);
            } else if (direction == Direction.EAST || direction == Direction.WEST) {
                minimumWidth += minimumSize.width;
                minimumHeight = Math.max(minimumHeight, minimumSize.height);
            }
        }

        return new Dimension(minimumWidth, minimumHeight);
    }

    /**
     * Lays out the specified container.
     * 
     * @param parent The parent container.
     */
    @Override
    public void layoutContainer(Container parent) {
        List<Component> components = Arrays.asList(parent.getComponents());
        int[] sizes = new int[components.size()];

        Map<Integer, Set<Component>> groups = new HashMap<Integer, Set<Component>>();

        int available = getSize(parent);

        if (direction == Direction.NORTH || direction == Direction.SOUTH)
            available -= padding.top + padding.bottom;
        else if (direction == Direction.EAST || direction == Direction.WEST)
            available -= padding.left + padding.right;

        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);

            int minimum = getMinimumSize(component);

            sizes[i] = minimum;
            available -= minimum;

            int weight = getWeight(component);
            if (!groups.containsKey(weight))
                groups.put(weight, new HashSet<Component>());

            groups.get(weight).add(component);
        }

        int[] weights = new int[groups.size()];
        int index = 0;
        for (int weight : groups.keySet())
            weights[index++] = weight;

        Arrays.sort(weights);

        for (int i = weights.length - 1; i >= 0; i--) {
            int weight = weights[i];

            Set<Component> group = groups.get(weight);

            int totalAdditionalSpace = 0;
            for (Component component : group) {
                totalAdditionalSpace += getAdditionalSize(component);

                if (totalAdditionalSpace < 0)
                    totalAdditionalSpace = Integer.MAX_VALUE;
            }

            if (totalAdditionalSpace <= available) {
                for (Component component : group) {
                    int additionalSpace = getAdditionalSize(component);

                    sizes[components.indexOf(component)] += additionalSpace;
                    available -= additionalSpace;
                }
            } else {
                boolean found = true;

                while (found) {
                    found = false;

                    int additionalPerComponent = available / group.size();

                    for (Component component : group) {
                        int additionalSpace = getAdditionalSize(component);

                        if (additionalSpace < additionalPerComponent) {
                            sizes[components.indexOf(component)] += additionalSpace;
                            available -= additionalSpace;

                            group.remove(component);

                            found = true;
                            break;
                        }
                    }
                }

                int additionalPerComponent = available / group.size();

                for (Component component : group) {
                    sizes[components.indexOf(component)] += additionalPerComponent;
                    available -= additionalPerComponent;
                }

                if (available > 0) {
                    Component component = group.iterator().next();

                    sizes[components.indexOf(component)] += available;
                    available = 0;
                }
            }
        }

        int x = padding.left;
        int y = padding.top;

        int maxWidth = parent.getWidth() - padding.left - padding.right;
        int maxHeight = parent.getHeight() - padding.top - padding.bottom;

        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);

            int targetWidth = 0;
            int targetHeight = 0;
            int offsetX = 0;
            int offsetY = 0;

            if (behavior == Behavior.FILL) {
                targetWidth = maxWidth;
                targetHeight = maxHeight;
            } else if (behavior == Behavior.LEFT || behavior == Behavior.CENTER || behavior == Behavior.RIGHT) {
                targetWidth = Math.min(maxWidth,
                        Math.max(component.getPreferredSize().width, component.getMinimumSize().width));
                targetHeight = Math.min(maxHeight,
                        Math.max(component.getPreferredSize().height, component.getMinimumSize().height));

                if (behavior == Behavior.CENTER) {
                    if (direction == Direction.NORTH || direction == Direction.SOUTH)
                        offsetX = (maxWidth - targetWidth) / 2;
                    else if (direction == Direction.EAST || direction == Direction.WEST)
                        offsetY = (maxHeight - targetHeight) / 2;
                } else if (behavior == Behavior.RIGHT) {
                    if (direction == Direction.NORTH || direction == Direction.SOUTH)
                        offsetX = maxWidth - targetWidth;
                    else if (direction == Direction.EAST || direction == Direction.WEST)
                        offsetY = maxHeight - targetHeight;
                }
            }

            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                component.setBounds(
                        x + offsetX,
                        (direction == Direction.SOUTH ? y : parent.getHeight() - y - sizes[i]),
                        targetWidth,
                        sizes[i]);
                y += sizes[i];
            } else if (direction == Direction.EAST || direction == Direction.WEST) {
                component.setBounds(
                        (direction == Direction.EAST ? x : parent.getWidth() - x - sizes[i]),
                        y + offsetY,
                        sizes[i],
                        targetHeight);
                x += sizes[i];
            }
        }
    }

    /**
     * Returns the weight of the specified component.
     * 
     * @param component The component.
     * @return The weight of the specified component.
     */
    private int getWeight(Component component) {
        if (component instanceof WeightedComponent)
            return ((WeightedComponent) component).getWeight();
        else
            return 0;
    }

    /**
     * Returns the size of the specified component in the direction of the layout.
     * 
     * @param component The component.
     * @return The size of the specified component in the direction of the layout.
     */
    private int getSize(Component component) {
        if (direction == Direction.NORTH || direction == Direction.SOUTH)
            return component.getHeight();
        else if (direction == Direction.EAST || direction == Direction.WEST)
            return component.getWidth();
        else
            return 0;
    }

    /**
     * Returns the minimum size of the specified component in the direction of the
     * layout.
     * 
     * @param component The component.
     * @return The minimum size of the specified component in the direction of the
     *         layout.
     */
    private int getMinimumSize(Component component) {
        if (!component.isVisible())
            return 0;

        if (direction == Direction.NORTH || direction == Direction.SOUTH)
            return component.getMinimumSize().height;
        else if (direction == Direction.EAST || direction == Direction.WEST)
            return component.getMinimumSize().width;
        else
            return 0;
    }

    /**
     * Returns the preferred size of the specified component in the direction of the
     * layout.
     * 
     * @param component The component.
     * @return The preferred size of the specified component in the direction of the
     *         layout.
     */
    private int getPreferredSize(Component component) {
        if (!component.isVisible())
            return 0;

        if (direction == Direction.NORTH || direction == Direction.SOUTH)
            return Math.max(component.getPreferredSize().height, component.getMinimumSize().height);
        else if (direction == Direction.EAST || direction == Direction.WEST)
            return Math.max(component.getPreferredSize().width, component.getMinimumSize().width);
        else
            return 0;
    }

    /**
     * Returns the additional size wanted by the specified component in the
     * direction of the layout.
     * 
     * @param component The component.
     * @return The additional size wanted by the specified component in the
     *         direction of the layout.
     */
    private int getAdditionalSize(Component component) {
        if (!component.isVisible())
            return 0;

        int additionalSpace = getPreferredSize(component) - getMinimumSize(component);

        if (additionalSpace < 0)
            additionalSpace = Integer.MAX_VALUE;

        return additionalSpace;
    }
}