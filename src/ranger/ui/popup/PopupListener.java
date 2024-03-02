package ranger.ui.popup;

/**
 * Interface representing a popup listener.
 */
public interface PopupListener {
    /**
     * Called when a popup is clicked.
     * 
     * @param popup The popup.
     */
    public void popupClicked(Popup popup);

    /**
     * Called when a popup times out.
     * 
     * @param popup The popup.
     */
    public void popupTimedOut(Popup popup);

    /**
     * Called when a popup is entered.
     * 
     * @param popup The popup.
     */
    public void popupEntered(Popup popup);

    /**
     * Called when a popup is exited.
     * 
     * @param popup The popup.
     */
    public void popupExited(Popup popup);

    /**
     * Called when a popup is closed.
     * 
     * @param popup The popup.
     */
    public void popupClosed(Popup popup);
}
