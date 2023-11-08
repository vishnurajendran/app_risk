package game.Orders;

import common.Command;
import entity.CardType;
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

import static org.junit.jupiter.api.Assertions.*;

class BombOrderTest {
    GameEngine d_gameEngineTest;

    ArrayList<Player> d_gamePlayersTest;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    Continent d_continent;

    BombOrder d_bombOrder;


    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2")), null);
        d_continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        System.setOut(new PrintStream(outputStreamCaptor));
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(1), 5);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(23), 6);
        d_gamePlayersTest.get(0).addCard(CardType.Bomb);
        d_bombOrder = new BombOrder(d_gamePlayersTest.get(0), 23, d_gameEngineTest.getMap());
    }

    @AfterEach
    void cleanup(){
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    @Test
    void TestBombOrderExecution(){
        assertTrue(d_bombOrder.d_ctxPlayer.isCardAvailable(CardType.Bomb));
        d_bombOrder.executeOrder();

        assertEquals(3, d_bombOrder.d_riskMap.getCountryById(d_bombOrder.d_targetCountry).getArmy());
    }

}