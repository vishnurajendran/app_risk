package common.Logging;

/**
 * Implementation for ILogWriter that writes log to console.
 * @author vishnurajendran
 */
public class ConsoleLogWriter implements ILogWriter {

    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_YELLOW = "\u001B[33m";

    /**
     * Constructor for ConsoleLogWriter.
     */
    public ConsoleLogWriter(){

    }

    /**
     * Implements the ILogWriter function to write to console.
     * @param p_data logging data to write.
     */
    @Override
    public void onLogEntered(LogData p_data) {
        System.out.printf(colorFormat(p_data.getLevel(), p_data.getMessage())+"\n");
    }

    /**
     * Formats the message to add color according to logLevel.
     * @param level
     * @param message
     * @return
     */
    private String colorFormat(LogType level, String message){
        if(level == LogType.Log)
            return message;

        String newMsg = "";
        if(level == LogType.Warn)
            newMsg += COLOR_YELLOW;
        else if(level == LogType.Error)
            newMsg += COLOR_RED;

        newMsg += message + " " + COLOR_RESET;
        return newMsg;
    }
}
