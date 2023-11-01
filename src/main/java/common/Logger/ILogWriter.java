package common.Logger;

/**
 * This interface implements log writing capability,
 * Various writers can implement this functionality to write
 * logs to various streams.
 * @author vishnurajendran
 */
public interface ILogWriter {

    /**
     * Invoked when a log is needs to be written by the buffer.
     * @param p_data logging data to write.
     */
    public void onLogEntered(LogData p_data);
}
