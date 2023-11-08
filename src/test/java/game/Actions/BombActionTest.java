package game.Actions;

import common.Command;
import entity.*;
import game.Data.Context;
import game.*;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BombActionTest {

    GameEngine d_gameEngineTest;

    ArrayList<Player> d_gamePlayersTest;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    Continent d_continent;
    GameAction d_bombActionTest;


    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        d_bombActionTest = new BombAction();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3")), null);
        d_continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        System.setOut(new PrintStream(outputStreamCaptor));
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(1), 5);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(23), 5);
        d_gamePlayersTest.get(2).assignCountry(d_gameEngineTest.getMap().getCountryById(2), 5);

        d_bombActionTest = GameActionFactory.getBombAction();
        d_bombActionTest.setContext(new Context(d_gamePlayersTest.get(0), d_gameEngineTest));
    }

    @AfterEach
    void cleanup(){
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    @Test
    void TestCommandValidity(){

        Command l_cmd = Command.parseString("bomb");
        d_bombActionTest.execute(l_cmd);
        // To check if bomb command is valid, should create an error
        Assertions.assertEquals(GameCommands.BOMB_ERROR_MESSAGES.get(0), outputStreamCaptor.toString().trim());

    }

    @Test
    void TestBombCardOwnership(){
        Command l_cmd = Command.parseString("bomb 2");
        d_bombActionTest.execute(l_cmd);
        // To check if bomb command is valid, should create an error
        Assertions.assertEquals(GameCommands.BOMB_ERROR_MESSAGES.get(1), outputStreamCaptor.toString().trim());
    }

    @Test
    void TestCountryOwnership(){
        Command l_cmd = Command.parseString("bomb 1");
        d_bombActionTest.d_context.getCurrentPlayer().addCard(CardType.Bomb);
        d_bombActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.BOMB_ERROR_MESSAGES.get(2), outputStreamCaptor.toString().trim());
    }

    @Test
    void TestCountryNegotiated(){
        Command l_cmd = Command.parseString("bomb 23");

        d_bombActionTest.d_context.getCurrentPlayer().addNegotiatedPlayer((d_gamePlayersTest.get(1).getPlayerId()));
        d_bombActionTest.d_context.getCurrentPlayer().addCard(CardType.Bomb);
        d_bombActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.BOMB_ERROR_MESSAGES.get(4), outputStreamCaptor.toString().trim());
    }
    /**
     * checks if countries are adjacent
     */
    @Test
    void TestCountryAdjacency(){
        Command l_cmd = Command.parseString("bomb 2");
        d_bombActionTest.d_context.getCurrentPlayer().addCard(CardType.Bomb);
        d_bombActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.BOMB_ERROR_MESSAGES.get(3), outputStreamCaptor.toString().trim());
    }

    /**
     * checks if a valid command ran
     */
    @Test
    void TestCommandSuccess(){
        Command l_cmd = Command.parseString("bomb 23");
        d_bombActionTest.d_context.getCurrentPlayer().addCard(CardType.Bomb);
        d_bombActionTest.execute(l_cmd);
        assertNotNull(d_bombActionTest.d_context.getCurrentPlayer().nextOrder());
    }

}