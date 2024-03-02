package ranger.ui.popup;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.ui.component.Container;
import ranger.ui.component.layout.Padding;

/**
 * Class representing a popup.
 */
public class Popup extends Container implements MouseListener {
    /**
     * The settings registrar to use.
     */
    private SettingsRegistrar settings;

    /**
     * The background color.
     */
    private Color background;

    /**
     * The timeout after which this popup should be closed.
     * If null, the default timeout is used.
     */
    private Integer timeout;

    /**
     * Whether this popup is clickable.
     */
    private boolean clickable;

    /**
     * The popup listener.
     */
    private PopupListener popupListener;

    /**
     * Constructs a new popup.
     * 
     * @param settings   The settings registrar to use.
     * @param background The background color.
     * @param border     The border color.
     */
    public Popup(SettingsRegistrar settings, Color background, Color border) {
        super(new Padding(4, 4, 8, 8));

        this.settings = settings;
        this.background = background;
        this.clickable = true;

        if (this.background == null)
            this.background = settings.get(Setting.POPUP_BACKGROUND, Color.class);
        if (border == null)
            border = settings.get(Setting.BORDER_COLOR, Color.class);

        /* ---- ---- */

        setOpaque(true);
        setBackground(this.background);

        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, border));

        addMouseListener(this);
    }

    /**
     * Constructs a new popup with the default background and border colors.
     * 
     * @param settings The settings registrar to use.
     */
    public Popup(SettingsRegistrar settings) {
        this(settings, null, null);
    }

    /**
     * Returns the timeout after which this popup should be closed.
     * 
     * @return The timeout after which this popup should be closed.
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * Sets the timeout after which this popup should be closed.
     * 
     * @param timeout The timeout after which this popup should be closed.
     */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    /**
     * Returns whether this popup is clickable.
     * 
     * @return Whether this popup is clickable.
     */
    public boolean isClickable() {
        return clickable;
    }

    /**
     * Sets whether this popup is clickable.
     * 
     * @param clickable Whether this popup is clickable.
     */
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    /**
     * Sets the popup listener.
     * 
     * @param popupListener The popup listener.
     */
    public void setPopupListener(PopupListener popupListener) {
        this.popupListener = popupListener;
    }

    /**
     * Closes this popup.
     */
    public void closePopup() {
        if (popupListener != null)
            popupListener.popupClosed(this);
    }

    /**
     * Handles a mouse click event.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Handles a mouse press event.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Handles a mouse release event.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (popupListener != null && clickable)
            popupListener.popupClicked(this);
    }

    /**
     * Handles a mouse enter event.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (!clickable)
            return;

        setBackground(settings.get(Setting.POPUP_HOVERED_BACKGROUND, Color.class));

        if (popupListener != null)
            popupListener.popupEntered(this);
    }

    /**
     * Handles a mouse exit event.
     * 
     * @param e The mouse event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (!clickable)
            return;

        setBackground(background);

        if (popupListener != null)
            popupListener.popupExited(this);
    }
}
