package game.Orders;

import common.Command;
import entity.Continent;
import entity.Player;
import entity.PlayerHandler;
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

class AirliftOrderTest {
    GameEngine d_gameEngineTest;
    ArrayList<Player> d_gamePlayersTest;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    Continent d_continent;

    Order d_AirliftOrderTest;



    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
        d_continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        System.setOut(new PrintStream(outputStreamCaptor));
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(16), 0);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(5), 5);

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
    void TestAirliftOrder(){
        d_AirliftOrderTest = new AdvanceOrder(d_gamePlayersTest.get(0), 5, 16, 4, d_gameEngineTest.getMap());
        d_AirliftOrderTest.executeOrder();
        assertEquals(1, d_gameEngineTest.getMap().getCountryById(5).getArmy());
        assertEquals(4, d_gameEngineTest.getMap().getCountryById(16).getArmy());
        assertTrue(d_gamePlayersTest.get(0).isCountryOwned(d_gameEngineTest.getMap().getCountryById(16)));
    }
}