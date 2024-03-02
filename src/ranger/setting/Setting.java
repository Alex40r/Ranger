package ranger.setting;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;

import ranger.syntax.parser.ParserType;

/**
 * Enum representing all settings that can be changed by the user.
 */
public enum Setting {
    /**
     * The initial parser type for new sheets.
     */
    INITIAL_PARSER_TYPE(ParserType.PREFIX),

    /**
     * The maximum number of sheets that can be opened at once.
     */
    MAXIMUM_SHEETS(5),

    /**
     * The font to use for all text.
     */
    FONT(loadFont()),

    /**
     * The accent color.
     */
    ACCENT_COLOR(new Color(0x208040)),

    /**
     * The background color for the header.
     */
    HEADER_BACKGROUND(new Color(0x208040)),

    /**
     * The foreground color for the header.
     */
    HEADER_FOREGROUND(Color.WHITE),

    /**
     * The background color for the menus.
     */
    MENU_BACKGROUND(new Color(0xE0E0E0)),

    /**
     * The foreground color for the menus.
     */
    MENU_FOREGROUND(Color.BLACK),

    /**
     * The background color for the input bar.
     */
    INPUT_BAR_BACKGROUND(new Color(0xD0D0D0)),

    /**
     * The background color for the inputs
     */
    INPUT_BACKGROUND(Color.WHITE),

    /**
     * The foreground color for the inputs.
     */
    INPUT_FOREGROUND(Color.BLACK),

    /**
     * The background color for the status bar.
     */
    STATUS_BAR_BACKGROUND(new Color(0xD0D0D0)),

    /**
     * The foreground color for the status bar.
     */
    STATUS_BAR_FOREGROUND(Color.BLACK),

    /**
     * The background color for the popups.
     */
    POPUP_BACKGROUND(new Color(0xE0E0E0)),

    /**
     * The background color for the hovered popups.
     */
    POPUP_HOVERED_BACKGROUND(new Color(0xF0F0F0)),

    /**
     * The foreground color for the popups.
     */
    POPUP_FOREGROUND(Color.BLACK),

    /**
     * The default timeout after which a popup is closed.
     */
    POPUP_TIMEOUT(5000),

    /**
     * The color of the borders.
     */
    BORDER_COLOR(new Color(0xA0A0A0)),

    /**
     * The background color for the sheets.
     */
    SHEET_BACKGROUND(Color.WHITE),

    /**
     * The foreground color for the sheets.
     */
    SHEET_FOREGROUND(Color.BLACK),

    /**
     * The background color for the sheet labels.
     */
    SHEET_LABEL_BACKGROUND(new Color(0xE8E8E8)),

    /**
     * The foreground color for the sheet labels.
     */
    SHEET_LABEL_FOREGROUND(Color.BLACK),

    /**
     * The foreground color for the selected sheet labels.
     */
    SHEET_SELECTED_LABEL_FOREGROUND(new Color(0x208040)),

    /**
     * The background color for the selected sheet labels.
     */
    SHEET_SELECTED_LABEL_BACKGROUND(new Color(0xB0B0B0)),

    /**
     * The background color for the sheet cells.
     */
    SHEET_SELECTED_CELL_BACKGROUND(new Color(0, 0, 0, 32)),

    /**
     * The border color for the selected cells.
     */
    SHEET_SELECTED_BORDER_COLOR(new Color(0x208040)),

    /**
     * The minimum zoom level.
     */
    MINIMUM_ZOOM(0.05),

    /**
     * The maximum zoom level.
     */
    MAXIMUM_ZOOM(5.00),

    /**
     * The bakcground color for context menus.
     */
    CONTEXT_BACKGROUND(new Color(0xE0E0E0)),

    ; /* ---- ---- */

    /**
     * The default value for this setting.
     */
    private Object defaultValue;

    /**
     * Constructs a new setting with the given default value.
     * 
     * @param defaultValue The default value for this setting.
     */
    private Setting(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Returns the default value for this setting.
     * 
     * @return The default value for this setting.
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    private static Font loadFont() {
        try {
            ClassLoader loader = Setting.class.getClassLoader();
            URL url = loader.getResource("res/font/chivo-mono.ttf");

            Font font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());

            font = font.deriveFont(13f);

            return font;
        } catch (Exception e) {
            return new Font(Font.MONOSPACED, Font.PLAIN, 13);
        }
    }
}
