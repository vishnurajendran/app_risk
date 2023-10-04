package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    GameEngine d_gameEngineTest;

    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();

    }

    /**
     * Tests whether the function processes the commands correctly.
     */
    @Test
    void testCanProcess(){
        assertTrue(d_gameEngineTest.canProcess("assigncountries"));
        assertTrue(d_gameEngineTest.canProcess("loadmap"));
        assertFalse(d_gameEngineTest.canProcess("editmap"));
    }
}