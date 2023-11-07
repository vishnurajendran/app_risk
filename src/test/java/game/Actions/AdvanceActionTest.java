package game.Actions;

import game.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdvanceActionTest {

    GameEngine d_gameEngineTest;

    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
    }

    @Test
    void TestCommands(){
    }


}