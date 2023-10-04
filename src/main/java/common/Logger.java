package common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple logging class
 *
 * @author vishnurajendran
 */
public class Logger {

    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_YELLOW = "\u001B[33m";

    private static boolean d_printToConsole = false;
    private static SimpleDateFormat d_formatter;

    /**
     * Enables or disables the flag that prints logs to console.
     *
     * @param p_printToConsole flag to set or unset.
     */
    public static void SetConsolePrinting(boolean p_printToConsole) {
        d_printToConsole = p_printToConsole;
    }

    /**
     * @return current system time.
     */
    private static String getTime() {
        if (d_formatter == null)
            d_formatter = new SimpleDateFormat("HH:mm:ss");
        Date l_date = new Date();
        return d_formatter.format(l_date).toString();
    }


    /**
     * Logs a message to the console
     *
     * @param msg message to print
     */
    public static void log(String msg) {
        if (d_printToConsole)
            System.out.println("[" + getTime() + "] LOG: " + msg);
    }

    /**
     * Logs a warning to the console in yellow color
     *
     * @param warning warning message to print
     */
    public static void logWarning(String warning) {
        if (d_printToConsole)
            System.out.println(COLOR_YELLOW + "[" + getTime() + "] WARN: " + warning + " " + COLOR_RESET);
    }

    /**
     * Logs a warning to the console in red color
     *
     * @param error error message to print
     */
    public static void logError(String error) {
        if (d_printToConsole)
            System.out.println(COLOR_RED + "[" + getTime() + "] ERROR: " + error + " " + COLOR_RESET);
    }
}
