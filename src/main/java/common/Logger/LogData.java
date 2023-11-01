package common.Logger;

/**
 * @author vishnurajendran
 */
public class LogData {
    private final String d_message;
    private final LogLevel d_level;

    /**
     * constructor for LogData.
     * @param p_level level of log.
     * @param p_message message to log.
     */
    public LogData(LogLevel p_level, String p_message){
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
    public LogLevel getLevel() {
        return d_level;
    }

}
