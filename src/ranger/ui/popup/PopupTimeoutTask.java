package ranger.ui.popup;

/**
 * Class representing a popup timeout task.
 */
public class PopupTimeoutTask extends java.util.TimerTask {
    /**
     * The popup that this task is for.
     */
    private Popup popup;

    /**
     * The popup listener.
     */
    private PopupListener popupListener;

    /**
     * Constructs a new popup timeout task.
     * 
     * @param popup The popup.
     */
    public PopupTimeoutTask(Popup popup) {
        this.popup = popup;
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
     * Called when the task is run.
     */
    @Override
    public void run() {
        if (popupListener != null)
            popupListener.popupTimedOut(popup);
    }
}
