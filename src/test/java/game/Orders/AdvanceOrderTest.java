package game.Orders;

import common.Command;
import entity.Continent;
import entity.Player;
import entity.PlayerHandler;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdvanceOrderTest {
    GameEngine d_gameEngineTest;
    ArrayList<Player> d_gamePlayersTest;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    Continent d_continent;
    GameAction d_gameActionTest;

    Order d_gameOrderTest;
    PlayerHandler d_playerHandlerTest;


    /**
     * Setup method for testing Advance commands validity
     * creates 4 players and assigns them country 1, 16, 2, 12
     * Country 1 and 16 are neighbours and country 2 and 12 are neighbours
     * Then it passes the context of player to check for conditions
     */
    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
        d_continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testresources/wow.map"));
        System.setOut(new PrintStream(outputStreamCaptor));
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(1), 4);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(16), 0);
        d_gamePlayersTest.get(2).assignCountry(d_gameEngineTest.getMap().getCountryById(2), 4);
        d_gamePlayersTest.get(3).assignCountry(d_gameEngineTest.getMap().getCountryById(12), 4);
        d_gameActionTest = GameActionFactory.getAdvanceAction();
        d_gameActionTest.SetContext(new Context(d_gamePlayersTest.get(0), d_gameEngineTest));
    }

    /**
     * Cleanup after tests
     */
    @AfterEach
    void cleanup(){
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    @Test
    void TestAttack(){
        d_gameOrderTest = new AdvanceOrder(d_gamePlayersTest.get(0), 1, 16, 4, d_gameEngineTest.getMap());
        d_gameOrderTest.executeOrder();
        assertEquals(4, d_gameEngineTest.getMap().getCountryById(16).getArmy());
        assertEquals(0, d_gameEngineTest.getMap().getCountryById(1).getArmy());
        assertTrue(d_gamePlayersTest.get(0).isCountryOwned(d_gameEngineTest.getMap().getCountryById(16)));
    }
}