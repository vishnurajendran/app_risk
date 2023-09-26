package common;
/**
 * Simple logging class
 * @author vishnurajendran
 * Dated 26-09-2023
 */
public class Logger {

    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_YELLOW = "\u001B[33m";

    private static boolean d_printToConsole = true;

    public static void SetConsolePrinting(boolean p_printToConsole){
        d_printToConsole = p_printToConsole;
    }

    /**
     * Logs a message to the console
     * @param msg message to print
     */
    public static void log(String msg){
        if(d_printToConsole)
            System.out.println("[LOG] " + msg);
    }

    /**
     * Logs a warning to the console in yellow color
     * @param warning warning message to print
     */
    public static void logWarning(String warning){
        if(d_printToConsole)
            System.out.println(COLOR_YELLOW + "[WARN] " + warning + " " + COLOR_RESET);
    }

    /**
     * Logs a warning to the console in red color
     * @param error error message to print
     */
    public static void logError(String error){
        if(d_printToConsole)
            System.out.println(COLOR_RED + "[ERROR] " + error + " " + COLOR_RESET);
    }
}
