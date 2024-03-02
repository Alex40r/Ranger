package ranger.ui.popup;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.ui.component.Container;
import ranger.ui.component.layout.Padding;

/**
 * Class representing a popup controller.
 */
public class PopupController implements PopupListener {
    /**
     * The settings registrar containing the settings to use.
     */
    private SettingsRegistrar settings;

    /**
     * The popups that are currently registered.
     */
    private Map<Popup, Container> popups;

    /**
     * The tasks that are currently running.
     */
    private Map<Popup, PopupTimeoutTask> tasks;

    /**
     * The timer used to schedule the tasks.
     */
    private Timer timer;

    /**
     * Constructs a new popup controller.
     * 
     * @param settings The settings registrar to use.
     */
    public PopupController(SettingsRegistrar settings) {
        this.settings = settings;

        this.popups = new HashMap<Popup, Container>();
        this.tasks = new HashMap<Popup, PopupTimeoutTask>();

        this.timer = new Timer();
    }

    /**
     * Registers a popup.
     * 
     * @param popup          The popup to register.
     * @param popupContainer The container to add the popup to.
     */
    public void registerPopup(Popup popup, Container popupContainer) {
        popup.setPopupListener(this);

        Container container = new Container(new Padding(4, 4, 4, 4));
        container.add(popup);
        popupContainer.add(container);

        popups.put(popup, container);

        startTimer(popup);
    }

    /**
     * Unregisters a popup.
     * 
     * @param popup The popup to unregister.
     */
    public void unregisterPopup(Popup popup) {
        popup.setPopupListener(null);

        Container container = popups.get(popup);
        if (container == null)
            return;

        popups.remove(popup);

        java.awt.Container parent = container.getParent();
        if (parent == null)
            return;

        parent.remove(container);
        parent.validate();
        parent.repaint();
    }

    /**
     * Starts the timer for the given popup.
     * 
     * @param popup The popup to start the timer for.
     */
    private void startTimer(Popup popup) {
        if (tasks.containsKey(popup))
            return;

        PopupTimeoutTask task = new PopupTimeoutTask(popup);
        task.setPopupListener(this);

        Integer timeout = popup.getTimeout();
        if (timeout == null)
            timeout = settings.get(Setting.POPUP_TIMEOUT, Integer.class);

        if (timeout > 0) {
            timer.schedule(task, timeout);
            tasks.put(popup, task);
        }
    }

    /**
     * Stops the timer for the given popup.
     * 
     * @param popup The popup to stop the timer for.
     */
    private void stopTimer(Popup popup) {
        PopupTimeoutTask task = tasks.get(popup);
        if (task == null)
            return;

        task.cancel();
        tasks.remove(popup);
    }

    /**
     * Disposes of this popup controller.
     */
    public void dispose() {
        timer.cancel();
    }

    /**
     * Handles a popup click event.
     * 
     * @param popup The popup that was clicked.
     */
    @Override
    public void popupClicked(Popup popup) {
        unregisterPopup(popup);
    }

    /**
     * Handles a popup timed out event.
     * 
     * @param popup The popup that timed out.
     */
    @Override
    public void popupTimedOut(Popup popup) {
        unregisterPopup(popup);
    }

    /**
     * Handles a popup entered event.
     * 
     * @param popup The popup that was entered.
     */
    @Override
    public void popupEntered(Popup popup) {
        stopTimer(popup);
    }

    /**
     * Handles a popup exited event.
     * 
     * @param popup The popup that was exited.
     */
    @Override
    public void popupExited(Popup popup) {
        startTimer(popup);
    }

    /**
     * Handles a popup closed event.
     * 
     * @param popup The popup that was closed.
     */
    @Override
    public void popupClosed(Popup popup) {
        unregisterPopup(popup);
    }
}
