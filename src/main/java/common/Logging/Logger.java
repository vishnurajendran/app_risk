package common.Logging;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Simple logging class
 *
 * @author vishnurajendran
 */
public class Logger {

    private static PrintStream d_stdOut = new PrintStream(System.out);
    private static SimpleDateFormat d_formatter;
    private static LogBuffer d_logBuffer;
    private static boolean d_isInitialised = false;

    public static PrintStream getPrintStream() {
        return d_printStream;
    }

    private static PrintStream d_printStream;

    /**
     * private constructor to make class purely static.
     */
    private Logger(){

    }

    /**
     * de-initialises the logger,
     * post this call, logger will stop working,
     * and needs to be initialised again before the logging continues.
     */
    public static void cleanUp(){
        d_logBuffer = null;
        d_printStream = null;
        System.setOut(d_stdOut);
        d_isInitialised = false;
    }

    /**
     * Initialises the logger, and prepares for logging.
     * @param p_hasConsoleWriter flag to enable console printing.
     */
    public static void initialise(List<ILogWriter> p_logWriters, boolean p_hasConsoleWriter){
        if(d_isInitialised)
            return;

        d_logBuffer = new LogBuffer();

        //register the writers.
        for(ILogWriter l_writer : p_logWriters ){
           d_logBuffer.registerWriter(l_writer);
        }

        //create a print stream instance to override default
        //std out PrintStream.
        d_printStream = new PrintStream(System.out) {
            public void println(String s) {
                d_logBuffer.log(LogType.STDOUT, "[" + getTime() + "] SOUT: " + s, true);

                //only print this if not in console printing mode.
                if (!p_hasConsoleWriter)
                    super.println(s);
            }
        };

        //modify the print stream to log all System.Out writes.
        System.setOut(d_printStream);
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
