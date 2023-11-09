/**
 * unit test for AirliftAction class.
 * It ensures that the AirliftAction behaves correctly in different scenarios.
 *
 * @author Shravani

 */

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

class AirliftActionTest {

    GameEngine d_gameEngineTest;
    AirliftAction d_airliftActionTest;

    ArrayList<Player> d_gamePlayersTest;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    Continent d_continent;
    GameAction d_gameActionTest;


    /**
     * Setup method that initializes the test environment before each test case.
     * It creates a GameEngine, adds players, sets up a continent, and assigns countries to players.
     */
    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();

        d_airliftActionTest = new AirliftAction();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
        d_continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        System.setOut(new PrintStream(outputStreamCaptor));

        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(2), 5);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(5), 5);


        d_gameActionTest = GameActionFactory.getAirliftAction();
        d_gameActionTest.setContext(new Context(d_gamePlayersTest.get(1), d_gameEngineTest));
    }

    /**
     * Cleanup method that runs after each test case to clean up and reset the environment.
     */
    @AfterEach
    void cleanup(){
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    /**
     * Tests the validity of the Airlift command by checking if it produces the expected error message.
     */
    @Test
    void TestCommandValidity(){

        Command l_cmd = Command.parseString("airlift 2 3");
        d_gameActionTest.execute(l_cmd);
        // To check if airlift command is valid, should create an error
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(0), outputStreamCaptor.toString().trim());

    }

    /**
     * Tests whether the player has the ownership of the Airlift card and checks for the expected error message.
     */
    @Test
    void TestAirliftCardOwnership(){
        Command l_cmd = Command.parseString("airlift 2 3 3");
        d_gameActionTest.execute(l_cmd);
        // To check if airlift command is valid, should create an error
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(1), outputStreamCaptor.toString().trim());
    }

    /**
     * Tests whether the player owns the Source country and checks for the expected error message.
     */
    @Test
    void TestSourceCountryOwnership(){
        Command l_cmd = Command.parseString("airlift 1 3 3");
        d_gameActionTest.d_context.getCurrentPlayer().addCard(CardType.Airlift);
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(2), outputStreamCaptor.toString().trim());
    }

    /**
     * Tests whether the player has enough number of armies in that country and checks for the expected error message.
     */
    @Test
    void TestCountryNumberOfArmies(){
        Command l_cmd = Command.parseString("airlift 2 5 11");
        d_gameActionTest.d_context.getCurrentPlayer().addCard(CardType.Airlift);
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(3), outputStreamCaptor.toString().trim());
    }

    /**
     * Tests whether the player owns the target country and checks for the expected error message.
     */
    @Test
    void TestTargetCountryOwnership(){
        Command l_cmd = Command.parseString("airlift 2 3 3");
        d_gameActionTest.d_context.getCurrentPlayer().addCard(CardType.Airlift);
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(4), outputStreamCaptor.toString().trim());
    }



}