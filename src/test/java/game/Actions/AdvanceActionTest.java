package game.Actions;

import common.Command;
import entity.Continent;
import entity.Player;
import entity.PlayerHandler;
import game.Data.Context;
import game.GameCommands;
import game.GameEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

class AdvanceActionTest {

    GameEngine d_gameEngineTest;
    AdvanceAction d_advanceActionTest;

    ArrayList<Player> d_gamePlayersTest;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    Continent d_continent;
    GameAction d_gameActionTest;


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
        d_advanceActionTest = new AdvanceAction();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
        d_continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        System.setOut(new PrintStream(outputStreamCaptor));
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(1), 4);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(16), 4);
        d_gamePlayersTest.get(2).assignCountry(d_gameEngineTest.getMap().getCountryById(2), 4);
        d_gamePlayersTest.get(3).assignCountry(d_gameEngineTest.getMap().getCountryById(12), 4);
        d_gameActionTest = GameActionFactory.getAdvanceAction();
        d_gameActionTest.setContext(new Context(d_gamePlayersTest.get(1), d_gameEngineTest));
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

    /**
     * Checks the validity of the advance command
     */
    @Test
    void TestCommandValidity(){
        Command l_cmd = Command.parseString("advance 2 3");
        d_gameActionTest.execute(l_cmd);
        // To check if advance command is valid, should create an error
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(0), outputStreamCaptor.toString().trim());

    }

    /**
     * checks if countries are adjacent
     */
    @Test
    void TestCountryAdjacency(){
        Command l_cmd = Command.parseString("advance 16 3 3");
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(3), outputStreamCaptor.toString().trim());
    }

    /**
     * checks if player has enough armies
     */
    @Test
    void TestCountryArmies(){
        Command l_cmd = Command.parseString("advance 16 1 5");
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(2), outputStreamCaptor.toString().trim());
    }

    /**
     * checks if country is owned by the player
     */
    @Test
    void TestCountryOwnership(){
        Command l_cmd = Command.parseString("advance 12 16 1");
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(1), outputStreamCaptor.toString().trim());
    }

    /**
     * checks if player trying to attack a negotiated player
     */
    @Test
    void TestCountryNegotiated(){
        Command l_cmd = Command.parseString("advance 16 1 3");
        d_gamePlayersTest.get(1).addNegotiatedPlayer(d_gamePlayersTest.get(0).getPlayerId());
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(4), outputStreamCaptor.toString().trim());
    }

    /**
     * Gives a valid command that should pass
     */
    @Test
    void TestCommandSuccess(){
        Command l_cmd = Command.parseString("advance 16 1 3");
        d_gameActionTest.execute(l_cmd);
        Assertions.assertNotNull(d_gameActionTest.d_context.getCurrentPlayer().nextOrder());
    }


}