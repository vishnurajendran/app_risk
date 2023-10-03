package application;

import common.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    private final PrintStream d_stdOut = System.out;
    private ByteArrayOutputStream d_outStream;

    private Application d_app;

    /**
     * This method initialises each test by
     * creating a new application instance and
     * redirecting the standard output stream.
     */
    @BeforeEach
    void setUp() {
        d_outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(d_outStream));
        d_app = new Application(new GameInstantiatorMock(), new MapEditorInstantiatorMock());
        d_app.startup();
    }

    /**
     * After each test we reset the standard out stream
     * and also cleanup the application
     */
    @AfterEach
    void tearDown() {
        System.setOut(d_stdOut);
        d_outStream = null;

        d_app.cleanup();
        d_app = null;
    }

    /**
     * Tests if the application has initialised correctly
     * isInitialised() should be true.
     */
    @Test
    void testInitialisation() {
        assertTrue(d_app.isInitialised());
    }

    /**
     * Test application exit flow, using the appropriate command.
     * once the application has quit, hasQuit() should return true.
     */
    @Test
    void testApplicationExit() {
        d_app.processCommand(Command.parseString(ApplicationConstants.CMD_EXIT_APP));
        assertTrue(d_app.hasQuit());
    }

    /**
     * Test gameLoad flow using the appropriate command.
     * once the command is processed, application state should switch to Game.
     */
    @Test
    void testGameLoad() {
        d_app.processCommand(Command.parseString(ApplicationConstants.CMD_START_GAME + " testmap"));
        assertEquals(d_app.getAppState(), AppState.Game);
    }

    /**
     * Test handling of game commands
     * no errors should be printed to the standard output
     */
    @Test
    void testGameCmd() {
        d_app.processCommand(Command.parseString(ApplicationConstants.CMD_START_GAME + " testmap"));
        assertEquals(d_app.getAppState(), AppState.Game);
        d_app.processCommand(Command.parseString(ApplicationTestConstants.GAME_TEST_CMD));
        String l_actual = d_outStream.toString();
        String l_errMsg = MessageFormat.format(ApplicationConstants.MSG_INVALID_CMD, ApplicationTestConstants.GAME_TEST_CMD);
        assertFalse(l_actual.contains(l_errMsg));
    }

    /**
     * Test game exit flow using the appropriate command.
     * once the command is processed, application state should return to Standard.
     */
    @Test
    void testGameExit() {
        d_app.processCommand(Command.parseString(ApplicationConstants.CMD_START_GAME + " testmap")); // boot up game
        assertEquals(d_app.getAppState(), AppState.Game);
        d_app.processCommand(Command.parseString(ApplicationConstants.CMD_EXIT_SUB_APPLICATION));
        assertEquals(d_app.getAppState(), AppState.Standard);
    }

    /**
     * Test map-editor load flow using the appropriate command.
     * once the command is processed, application state should switch to MapEditor.
     */
    @Test
    void testMapEditorLoad() {
        d_app.processCommand(Command.parseString(ApplicationConstants.CMD_START_MAPEDITOR));
        assertEquals(d_app.getAppState(), AppState.MapEditor);
    }

    /**
     * Test handling of map editor commands
     * no errors should be printed to the standard output
     */
    @Test
    void testMapEditorCmd() {
        d_app.processCommand(Command.parseString(ApplicationConstants.CMD_START_MAPEDITOR));
        assertEquals(d_app.getAppState(), AppState.MapEditor);
        d_app.processCommand(Command.parseString(ApplicationTestConstants.MAPEDITOR_TEST_CMD));
        String l_actual = d_outStream.toString();
        String l_errMsg = MessageFormat.format(ApplicationConstants.MSG_INVALID_CMD, ApplicationTestConstants.MAPEDITOR_TEST_CMD);
        assertFalse(l_actual.contains(l_errMsg));
    }

    /**
     * Test map-editor exit flow using the appropriate command.
     * once the command is processed, application state should return to Standard.
     */
    @Test
    void testMapEditorExit() {
        d_app.processCommand(Command.parseString(ApplicationConstants.CMD_START_MAPEDITOR)); // boot up map editor
        assertEquals(d_app.getAppState(), AppState.MapEditor);
        d_app.processCommand(Command.parseString(ApplicationConstants.CMD_EXIT_SUB_APPLICATION));
        assertEquals(d_app.getAppState(), AppState.Standard);
    }

    /**
     * Test invalid command handling by the application.
     * the standard output should display and error
     */
    @Test
    void testInvalidCmd() {
        d_app.processCommand(Command.parseString(ApplicationTestConstants.INVALID_CMD));
        String l_actual = d_outStream.toString();
        String l_errMsg = MessageFormat.format(ApplicationConstants.MSG_INVALID_CMD, ApplicationTestConstants.INVALID_CMD);
        assertTrue(l_actual.contains(l_errMsg));
    }
}