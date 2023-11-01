package common.Logger;

/**
 * This class holds the log information that is sent from
 * the buffer to the writer at the time of buffer flushing.
 * @author vishnurajendran
 */
public class LogData {
    private final String d_message;
    private final LogType d_level;

    /**
     * constructor for LogData.
     * @param p_level level of log.
     * @param p_message message to log.
     */
    public LogData(LogType p_level, String p_message){
        d_message = p_message;
        d_level = p_level;
    }

    /**
     * @return message to log.
     */
    public String getMessage() {
        return d_message;
    }

    /**
     * @return level of logging.
     */
    public LogType getLevel() {
        return d_level;
    }

}
