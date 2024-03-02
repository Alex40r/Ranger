package ranger.setting;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a settings registrar.
 */
public class SettingsRegistrar {
    /**
     * The map of settings, indexed by setting.
     */
    private Map<Setting, Object> settings;

    /**
     * Constructs a new settings registrar, and registers the default settings.
     */
    public SettingsRegistrar() {
        settings = new HashMap<Setting, Object>();

        for (Setting setting : Setting.values())
            set(setting, setting.getDefaultValue());
    }

    /**
     * Returns whether a setting is registered.
     * 
     * @param key The key of the setting.
     * @return Whether the setting is registered.
     */
    public boolean has(Setting key) {
        return settings.containsKey(key);
    }

    /**
     * Sets a setting to an arbitrary value.
     * 
     * @param key   The key of the setting.
     * @param value The value of the setting.
     */
    public void set(Setting key, Object value) {
        if (value == null)
            settings.remove(key);
        else
            settings.put(key, value);
    }

    /**
     * Returns a setting by key, or null if no setting with the specified key is
     * registered. The setting is cast to the specified type.
     * 
     * @param <T>  The type of the setting.
     * @param key  The key of the setting.
     * @param type The type of the setting.
     * @return The setting, or null if no setting with the specified key is
     *         registered.
     */
    public <T> T get(Setting key, Class<T> type) {
        if (!settings.containsKey(key))
            return null;

        return type.cast(settings.get(key));
    }
}
