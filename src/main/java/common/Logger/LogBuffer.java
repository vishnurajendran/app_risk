package common.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * implementation of ILogBuffer, holds a buffer
 * where logs are written and flushed.
 * @author vishnurajendran
 */
public class LogBuffer implements ILogBuffer {

    private final int MAXENTRIES_BEFORE_FORCEFLUSH = 5;
    private final List<ILogWriter> d_writers;
    private final List<LogData> d_buffer;

    /**
     * Constructor for LogBuffer
     */
    public LogBuffer(){
        d_buffer = new ArrayList<>();
        d_writers = new ArrayList<>();
    }

    /**
     * Implementation of the interface method.
     * @param msg message to log.
     * @param flush true if flush the buffer immediately.
     */
    @Override
    public void log(LogLevel level, String msg, boolean flush) {
        d_buffer.add(new LogData(level, msg));
        if(flush)
        {
            flush();
        }
        else{
            if(d_buffer.size() >= MAXENTRIES_BEFORE_FORCEFLUSH)
                flush();
        }
    }

    /**
     * Flush the buffer to the writers.
     */
    private void flush(){
        for (LogData l_data:d_buffer) {
            for (ILogWriter writer : d_writers){
                writer.onLogEntered(l_data);
            }
        }
        d_buffer.clear();
    }

    /**
     * Register writers to this LogBuffer instance. the writers
     * are notified when buffer is written to.
     * @param p_writer writer to register.
     */
    @Override
    public void registerWriter(ILogWriter p_writer) {
        d_writers.add(p_writer);
    }

    /**
     * Remove the writer from LogBuffer registered buffers.
     * @param p_writer writer to remove.
     */
    @Override
    public void unRegisterWriter(ILogWriter p_writer) {
        d_writers.remove(p_writer);
    }
}
