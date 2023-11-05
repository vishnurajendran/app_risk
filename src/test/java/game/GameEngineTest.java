package game;

import common.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    GameEngine d_gameEngineTest;

    @BeforeEach
    void setUp() {
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
    }

    /**
     * Tests game engine auto-quit on incorrect command.
     */
    @Test
    void testAutoQuit() {
        d_gameEngineTest.submitCommand(Command.parseString("loadmap"));
        assertTrue(d_gameEngineTest.hasQuit());
    }
}