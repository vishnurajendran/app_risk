package common.Logging;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FileLoggingTest {

    private FileLogWriter d_fileLogWriter;

    /**
     * initialises logger with file writer
     * for testing.
     */
    @BeforeEach
    public void beforeTest() {
        d_fileLogWriter = new FileLogWriter();
        Logger.initialise(new ArrayList<>(Arrays.asList(d_fileLogWriter)), true);
    }

    /**
     * reads file and returns a string with its data
     * @param path path of file to read.
     * @return contents of the file as a string
     */
    private String ReadFile(String path){
        try {
            String data = "";
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
            return data;
        } catch (FileNotFoundException e) {
            return "";
        }
    }

    /**
     * Test regular logs.
     */
    @Test
    public void testConsoleLogging() {
        String l_msg = "test logging!";
        Logger.log(l_msg);
        String l_output = ReadFile(d_fileLogWriter.getFilePath());
        assertTrue(l_output.contains(l_msg));
        assertTrue(l_output.contains("LOG"));
    }

    /**
     * Test warning logs.
     */
    @Test
    public void testConsoleLoggingWarn() {
        String l_msg = "test logging!";
        Logger.logWarning(l_msg);
        String l_output = ReadFile(d_fileLogWriter.getFilePath());
        assertTrue(l_output.contains(l_msg));
        assertTrue(l_output.contains("WARN"));
    }

    /**
     * test error logs.
     */
    @Test
    public void testConsoleLoggingError() {
        String l_msg = "test logging!";
        Logger.logError(l_msg);
        String l_output = ReadFile(d_fileLogWriter.getFilePath());
        assertTrue(l_output.contains(l_msg));
        assertTrue(l_output.contains("ERROR"));
    }

    /**
     * cleaning up logger.
     */
    @AfterEach
    public void afterTest() {
        File logFile = new File(d_fileLogWriter.getFilePath());
        Logger.cleanUp();
        logFile.delete();
    }

}