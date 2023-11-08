package application;

import java.text.MessageFormat;

/**
 * Application command constants, used for command registrations.
 *
 * @author vishnurajendran
 */
public final class ApplicationConstants {

    /**
     * Sub-Application exit command.
     */
    public static final String CMD_EXIT_SUB_APPLICATION = "exit";

    /**
     * Application exit command.
     */
    public static final String CMD_EXIT_APP = "exitapp";

    /**
     * Game start command.
     */
    public static final String CMD_START_GAME = "loadmap";

    /**
     * help command.
     */
    public static final String CMD_HELP = "help";

    /**
     * Map editor command.
     */
    public static final String CMD_START_MAPEDITOR = "editmap";

    /**
     * Message for invalid commands.
     */
    public static final String ERR_MSG_INVALID_CMD = "The command `{0}` is invalid and cannot be processed. please try again";

    /**
     * Message for invalid command usage.
     */
    public static final String ERR_INVALID_CMD_USAGE = "Command cannot be used at this time, please try another command.";

    /**
     * Message for invalid start game command usage.
     */
    public static final String ERR_MSG_INVALID_START_GAME_CMD_USAGE = "to use loadmap again. please exit and then try again";

    /**
     * Message for invalid start map-editor command usage.
     */
    public static final String ERR_MSG_INVALID_MAP_EDITOR_GAME_CMD_USAGE = "to use editmap again. please exit and then try again";

    /**
     * Message for Invalid exit command calls.
     */
    public static final String MSG_INVALID_EXIT_CMD =
            MessageFormat.format("INFO: Nothing to exit from, to exit the app use `{0}`", CMD_EXIT_APP);
}
