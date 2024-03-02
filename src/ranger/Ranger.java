package ranger;

import ranger.function.DefaultFunctionRegistrar;
import ranger.function.FunctionRegistrar;
import ranger.setting.Setting;
import ranger.setting.SettingsRegistrar;
import ranger.sheet.Sheet;
import ranger.sheet.Storage;
import ranger.sheet.StorageRequestListener;
import ranger.syntax.parser.ParserType;
import ranger.ui.UserInterfaceController;
import ranger.ui.popup.standard.ErrorPopup;

/**
 * Class representing the main Ranger class.
 */
public class Ranger implements StorageRequestListener {
    /**
     * The settings registrar that contains all the settings used in the program.
     */
    private SettingsRegistrar settings;

    /**
     * The function registrar that contains all the functions that can be used in
     * the spreadsheets.
     */
    private FunctionRegistrar functionRegistrar;

    /**
     * The storage that contains all the sheets.
     */
    private Storage storage;

    /**
     * The user interface controller.
     */
    private UserInterfaceController userInterface;

    /**
     * Constructs a new instance of the program.
     */
    public Ranger() {
        settings = new SettingsRegistrar();

        storage = new Storage();
        functionRegistrar = new DefaultFunctionRegistrar();

        userInterface = new UserInterfaceController(settings, storage);
        userInterface.setStorageRequestListener(this);

        Sheet infix = new Sheet("Infixed", functionRegistrar, ParserType.INFIX.getParser());
        storage.addSheet(infix);

        Sheet prefixed = new Sheet("Prefixed", functionRegistrar, ParserType.PREFIX.getParser());
        storage.addSheet(prefixed);
    }

    /**
     * The main method.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new Ranger();
    }

    /**
     * Saves the storage.
     */
    @Override
    public void save() {
        // TODO
    }

    /**
     * Adds a sheet to the storage.
     */
    @Override
    public void addSheet() {
        if (storage.getSheetCount() >= settings.get(Setting.MAXIMUM_SHEETS, Integer.class)) {
            userInterface.pushPopup(new ErrorPopup(settings, "Maximum number of sheets reached."));
            return;
        }

        ParserType parserType = settings.get(Setting.INITIAL_PARSER_TYPE, ParserType.class);
        Sheet sheet = new Sheet("Sheet " + (storage.getSheetCount() + 1), functionRegistrar, parserType.getParser());
        storage.addSheet(sheet);
    }

    /**
     * Selects the specified sheet.
     * 
     * @param sheet The sheet.
     */
    @Override
    public void selectSheet(Sheet sheet) {
        storage.selectSheet(sheet);
    }

    /**
     * Removes the specified sheet.
     * 
     * @param sheet The sheet.
     */
    @Override
    public void removeSheet(Sheet sheet) {
        storage.removeSheet(sheet);
    }

    /**
     * Renames the specified sheet.
     * 
     * @param sheet The sheet.
     * @param name  The new name.
     */
    @Override
    public void renameSheet(Sheet sheet, String name) {
        sheet.setName(name);
    }
}