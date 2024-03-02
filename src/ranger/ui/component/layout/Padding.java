package ranger.ui.component.layout;

/**
 * Class representing padding.
 */
public class Padding {
    /**
     * The top padding.
     */
    public final int top;

    /**
     * The bottom padding.
     */
    public final int bottom;

    /**
     * The left padding.
     */
    public final int left;

    /**
     * The right padding.
     */
    public final int right;

    /**
     * Constructs a new empty padding.
     */
    public Padding() {
        this(0, 0, 0, 0);
    }

    /**
     * Constructs a new padding.
     * 
     * @param top    The top padding.
     * @param bottom The bottom padding.
     * @param left   The left padding.
     * @param right  The right padding.
     */
    public Padding(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }
}