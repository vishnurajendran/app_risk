package common.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple logging class
 *
 * @author vishnurajendran
 */
public class Logger {

    private static SimpleDateFormat d_formatter;
    private static LogBuffer d_logBuffer;
    private static boolean d_isInitialised = false;

    /**
     * private constructor to make class purely static.
     */
    private Logger(){

    }

    /**
     * Initialises the logger, and prepares for logging.
     * @param p_enableConsolePrinting flag to enable console printing.
     */
    public static void Initialise(boolean p_enableConsolePrinting){
        if(d_isInitialised)
            return;

        d_logBuffer = new LogBuffer();
        d_logBuffer.registerWriter(new FileLogWriter());
        if(p_enableConsolePrinting){
            d_logBuffer.registerWriter(new ConsoleLogWriter());
        }
        d_isInitialised = true;
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
     * Logs a message
     * @param p_msg message to print
     */
    public static void log(String p_msg) {
        if(!d_isInitialised)
            return;

        d_logBuffer.log(LogType.Log, "[" + getTime() + "] LOG: " + p_msg, true);
    }

    /**
     * Logs a warning
     * @param p_warning warning message to print
     */
    public static void logWarning(String p_warning) {
        if(!d_isInitialised)
            return;

        d_logBuffer.log(LogType.Warn,"[" + getTime() + "] WARN: " + p_warning, true);
    }

    /**
     * Logs an error
     * @param p_error error message to print
     */
    public static void logError(String p_error) {
        if(!d_isInitialised)
            return;

        d_logBuffer.log(LogType.Error,"[" + getTime() + "] ERROR: " + p_error, true);
    }
}
