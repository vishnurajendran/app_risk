package game.States.Strategy;

import common.Command;
import entity.PlayerHandler;
import game.GameEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

    GameEngine d_gameEngineTest;

    @BeforeEach
    void setup(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
    }
    @AfterEach
    void cleanup(){
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    /**
     * Checks if the strategies are being assigned correctly.
     */
    @Test
    void TestStrategyAssign(){
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        d_gameEngineTest.submitCommand(Command.parseString("gameplayer -add v1 v2 v3 -strategy h c a"));
        assertEquals("Human", PlayerHandler.getGamePlayers().get(0).getPlayerStrategy());
        assertEquals("Cheater", PlayerHandler.getGamePlayers().get(1).getPlayerStrategy());
        assertEquals("Aggressive", PlayerHandler.getGamePlayers().get(2).getPlayerStrategy());
    }



}