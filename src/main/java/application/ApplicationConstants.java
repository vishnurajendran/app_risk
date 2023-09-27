package application;

import java.text.MessageFormat;

/**
 * Application command constants, used for command registrations.
 * @author vishnurajendran
 */
public final class ApplicationConstants {
    public static final String EXIT_SUB_APPLICATION = "exit";
    public static final String EXIT_APP = "exitapp";
    public static final String START_GAME = "loadmap";
    public static final String START_MAPEDITOR = "editmap";

    public static final String MSG_INVALID_CMD = "ERROR: The command `{0}` is invalid and cannot be processed";
    public static final String MSG_INVALID_EXIT_CMD =
                MessageFormat.format("INFO: Nothing to exit from, to exit the app use `{0}`", EXIT_APP);
}
