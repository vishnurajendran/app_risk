package common.Logger;
/**
 * This interface acts the observable for all the log writers
 * @author vishnurajendran
 */
public interface ILogBuffer {
    /**
     * Log to buffer.
     * @param p_level level of logging.
     * @param p_msg message to log.
     * @param p_flush flush immediately if true.
     */
    public void log(LogType p_level, String p_msg, boolean p_flush);

    /**
     * Register writers
     * @param p_writer writer to register.
     */
    public void registerWriter(ILogWriter p_writer);

    /**
     * Remove writer
     * @param p_writer writer to remove.
     */
    public void unRegisterWriter(ILogWriter p_writer);
}
