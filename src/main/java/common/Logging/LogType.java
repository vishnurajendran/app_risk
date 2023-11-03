package common.Logging;

/**
 * Enum values to handle log types.
 * @author vishnurajendran
 */
public enum LogType {
    /**
     * Standard logs
     */
    Log,
    /**
     * Warning logs
     */
    Warn,
    /**
     * Error logs
     */
    Error,

    /**
     * Special case for printing STD_OUTS.
     */
    STDOUT
}
