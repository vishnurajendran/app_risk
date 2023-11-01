package common.Logger;

/**
 * Implementation for ILogWriter that writes log to console.
 * @author vishnurajendran
 */
public class ConsoleLogWriter implements ILogWriter {

    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_YELLOW = "\u001B[33m";

    /**
     * Implements the ILogWriter function to write to console.
     * @param p_data logging data to write.
     */
    @Override
    public void onLogEntered(LogData p_data) {
        System.out.println(colorFormat(p_data.getLevel(), p_data.getMessage()));
    }

    /**
     * Formats the message to add color according to logLevel.
     * @param level
     * @param message
     * @return
     */
    private String colorFormat(LogLevel level, String message){
        if(level == LogLevel.Log)
            return message;

        String newMsg = "";
        if(level == LogLevel.Warn)
            newMsg += COLOR_YELLOW;
        else if(level == LogLevel.Error)
            newMsg += COLOR_RED;

        newMsg += message + " " + COLOR_RESET;
        return newMsg;
    }
}
