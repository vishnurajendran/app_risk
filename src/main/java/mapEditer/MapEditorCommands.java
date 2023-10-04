package mapEditer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Map Editor commands, used for command registrations.
 * and some other constants used in map editor class.
 * @author TaranjeetKaur
 */
public class MapEditorCommands {
    public static final String CMD_SHOW_MAP = "showmap";
    public static final String CMD_SAVE_MAP = "savemap";
    public static final String CMD_EDIT_MAP = "editmap";
    public static final String CMD_VALIDATE_MAP = "validatemap";
    public static final String CMD_EDIT_CONTINENT = "editcontinent";
    public static final String CMD_EDIT_COUNTRY = "editcountry";
    public static final String CMD_EDIT_NEIGHBOR = "editneighbor";
    public static final String CMD_OPTION_ADD = "add";
    public static final String CMD_OPTION_REMOVE = "remove";
    public static final Set<String> VALIDCOMMANDS = new HashSet<>(Arrays.asList(CMD_EDIT_CONTINENT, CMD_EDIT_COUNTRY, CMD_EDIT_NEIGHBOR,
                                                                                CMD_SHOW_MAP,CMD_SAVE_MAP,CMD_EDIT_MAP, CMD_VALIDATE_MAP));
    public static final Set<String> VALIDOPTIONS = new HashSet<>(Arrays.asList(CMD_OPTION_ADD, CMD_OPTION_REMOVE));
    public static final String DEFAULT_MAP = "resources/defaultMap.map";
    public static final String NEW_MAP_FILE_NAME = "newMap.map";
    public static final String DEFAULT_CONTINENT_COLOR = "white";

}
