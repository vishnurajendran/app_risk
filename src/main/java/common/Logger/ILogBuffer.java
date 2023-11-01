package common.Logger;
/**
 * This interface acts the observable for all the log writers
 * @author vishnurajendran
 */
public interface ILogBuffer {
    /**
     * Log to buffer.
     * @param level level of logging.
     * @param msg message to log.
     * @param flush flush immediately if true.
     */
    public void log(LogLevel level, String msg, boolean flush);

    /**
     * Register writers
     * @param writer writer to register.
     */
    public void registerWriter(ILogWriter writer);

    /**
     * Remove writer
     * @param writer writer to remove.
     */
    public void unRegisterWriter(ILogWriter writer);
}
