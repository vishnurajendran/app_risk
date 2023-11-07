package game.Actions;
import common.Command;
import entity.Player;
import game.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdvanceActionTest {

    GameEngine d_gameEngineTest;

    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
    }

    @Test
    void TestCommands(){
        Player testPlayer = new Player("test-player", null);

        d_gameEngineTest.submitCommand(Command.parseString("advance 1 1 "));
    }


}