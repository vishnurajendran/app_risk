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
    private static boolean isInitialised = false;

    /**
     * Initialises the logger, and prepares for logging.
     * @param p_enableConsolePrinting flag to enable console printing.
     */
    public static void Initialise(boolean p_enableConsolePrinting){
        d_logBuffer = new LogBuffer();
        d_logBuffer.registerWriter(new FileLogWriter());
        if(p_enableConsolePrinting){
            d_logBuffer.registerWriter(new ConsoleLogWriter());
        }
        isInitialised = true;
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
     * @param msg message to print
     */
    public static void log(String msg) {
        if(!isInitialised)
            return;

        d_logBuffer.log(LogLevel.Log, "[" + getTime() + "] LOG: " + msg, true);
    }

    /**
     * Logs a warning to the console in yellow color
     * @param warning warning message to print
     */
    public static void logWarning(String warning) {
        if(!isInitialised)
            return;

        d_logBuffer.log(LogLevel.Warn,"[" + getTime() + "] WARN: " + warning, true);
    }

    /**
     * Logs a warning to the console in red color
     * @param error error message to print
     */
    public static void logError(String error) {
        if(!isInitialised)
            return;

        d_logBuffer.log(LogLevel.Error,"[" + getTime() + "] ERROR: " + error, true);
    }
}
