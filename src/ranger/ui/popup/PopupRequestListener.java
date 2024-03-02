package ranger.ui.popup;

/**
 * Interface representing a popup request listener.
 */
public interface PopupRequestListener {
    /**
     * Called when a popup is requested.
     * 
     * @param popup The popup.
     */
    public void pushPopup(Popup popup);

    /**
     * Called when a popup is requested to be removed.
     * 
     * @param popup The popup.
     */
    public void popPopup(Popup popup);
}
