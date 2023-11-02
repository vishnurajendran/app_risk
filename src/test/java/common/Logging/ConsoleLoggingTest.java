package common.Logging;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * this class tests if the console logging feature of logger
 * works as intended.
 */
class ConsoleLoggingTest {

    private final PrintStream d_stdOut = System.out;
    private PrintStream d_printStream;
    private ByteArrayOutputStream d_outStream;

    /**
     * initialises logger and d_printStream so that the
     * tests can be performed
     */
    @BeforeEach
    public void beforeTest() {
        d_outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(d_outStream));
        Logger.initialise(new ArrayList<>(Arrays.asList(new ConsoleLogWriter())), true);
        d_printStream = Logger.getPrintStream();
    }

    /**
     * Test regular logs.
     */
    @Test
    public void testConsoleLogging() {
        String l_msg = "test logging!";
        Logger.log(l_msg);
        assertTrue(d_outStream.toString().contains(l_msg));
        assertTrue(d_outStream.toString().contains("LOG"));
    }

    /**
     * Test warning logs.
     */
    @Test
    public void testConsoleLoggingWarn() {
        String l_msg = "test logging!";
        Logger.logWarning(l_msg);
        assertTrue(d_outStream.toString().contains(l_msg));
        assertTrue(d_outStream.toString().contains("WARN"));
    }

    /**
     * Test error logs.
     */
    @Test
    public void testConsoleLoggingError() {
        String l_msg = "test logging!";
        Logger.logError(l_msg);
        assertTrue(d_outStream.toString().contains(l_msg));
        assertTrue(d_outStream.toString().contains("ERROR"));
    }

    /**
     * cleaning up logger.
     */
    @AfterEach
    public void afterTest() {
        System.setOut(d_stdOut);
        Logger.cleanUp();
    }
}