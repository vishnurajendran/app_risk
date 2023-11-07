package game;

import common.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this class tests the basics of the game engine
 * class.
 * @author soham
 */
class GameEngineTest {

    GameEngine d_gameEngineTest;

    @BeforeEach
    public void setUp() {
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
    }

    /**
     * Tests game engine auto-quit on incorrect command
     * specifically when no map is given.
     */
    @Test
    public void testAutoQuitWhenNoMap() {
        d_gameEngineTest.submitCommand(Command.parseString("loadmap"));
        assertTrue(d_gameEngineTest.hasQuit());
    }

    /**
     * Tests game engine auto-quit on incorrect command
     * specifically when wrong map path is given.
     */
    @Test
    public void testAutoQuitWhenIncorrectMapPath() {
        d_gameEngineTest.submitCommand(Command.parseString("loadmap test.map"));
        assertTrue(d_gameEngineTest.hasQuit());
    }
}