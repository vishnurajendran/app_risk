package application;

import java.text.MessageFormat;

/**
 * Application command constants, used for command registrations.
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
     * Map editor command.
     */
    public static final String CMD_START_MAPEDITOR = "editmap";

    /**
     * Message for invalid commands.
     */
    public static final String MSG_INVALID_CMD = "ERROR: The command `{0}` is invalid and cannot be processed";

    /**
     * Message for Invalid exit command calls.
     */
    public static final String MSG_INVALID_EXIT_CMD =
                MessageFormat.format("INFO: Nothing to exit from, to exit the app use `{0}`", CMD_EXIT_APP);
}
