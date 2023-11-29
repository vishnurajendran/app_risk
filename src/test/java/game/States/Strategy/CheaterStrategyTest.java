package game.States.Strategy;

import common.Command;
import entity.Continent;
import entity.Player;
import entity.PlayerHandler;
import game.Actions.AdvanceAction;
import game.Actions.GameAction;
import game.Actions.GameActionFactory;
import game.Data.Context;
import game.GameEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CheaterStrategyTest {

    GameEngine d_gameEngineTest;
    ArrayList<Player> d_gamePlayersTest;
    Continent d_continent;


    /**
     * Setup method for testing cheater strategy execution
     * Here v1 is being assigned country 28, which is neighbor to 33, 38, and 32 but not 55
     */
    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        d_gameEngineTest.submitCommand(Command.parseString("gameplayer -add v1 v2 v3 -strategy c h h"));
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        for(Player player: d_gamePlayersTest){
            player.setStrategyContext(d_gameEngineTest);
        }
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(28), 4);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(33), 2);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(38), 2);
        d_gamePlayersTest.get(2).assignCountry(d_gameEngineTest.getMap().getCountryById(32), 3);
        d_gamePlayersTest.get(2).assignCountry(d_gameEngineTest.getMap().getCountryById(55), 3);
    }

    @AfterEach
    void cleanup(){
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    /**
     * Tests if cheater conquers neighboring countries
     * should conquer 33, 38 and 32 since they are neighbors but not 55
     */
    @Test
    void testCheaterOwnership(){
        Player l_currentPlayer = d_gamePlayersTest.get(0);
        l_currentPlayer.issueOrder();
        assertTrue(l_currentPlayer.isCountryOwned(d_gameEngineTest.getMap().getCountryById(33)));
        assertTrue(l_currentPlayer.isCountryOwned(d_gameEngineTest.getMap().getCountryById(38)));
        assertTrue(l_currentPlayer.isCountryOwned(d_gameEngineTest.getMap().getCountryById(32)));
        assertFalse(l_currentPlayer.isCountryOwned(d_gameEngineTest.getMap().getCountryById(55)));
    }

    /**
     * Tests whether the countries that cheater conquers are being removed from their original owners
     *
     */
    @Test
    void testPlayerCountryRemoval(){
        d_gamePlayersTest.get(0).issueOrder();
        assertFalse(d_gamePlayersTest.get(1).isCountryOwned(d_gameEngineTest.getMap().getCountryById(33)));
        assertFalse(d_gamePlayersTest.get(1).isCountryOwned(d_gameEngineTest.getMap().getCountryById(38)));
        assertFalse(d_gamePlayersTest.get(2).isCountryOwned(d_gameEngineTest.getMap().getCountryById(32)));
        assertTrue(d_gamePlayersTest.get(2).isCountryOwned(d_gameEngineTest.getMap().getCountryById(55)));
    }



}