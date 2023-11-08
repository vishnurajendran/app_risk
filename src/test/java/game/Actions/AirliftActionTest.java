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


    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        System.setOut(new PrintStream(outputStreamCaptor));
        d_airliftActionTest = new AirliftAction();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
        d_continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testresources/wow.map"));
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(1), 5);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(2), 5);
        d_gamePlayersTest.get(2).assignCountry(d_gameEngineTest.getMap().getCountryById(3), 5);
        d_gamePlayersTest.get(3).assignCountry(d_gameEngineTest.getMap().getCountryById(4), 5);

        d_gameActionTest = GameActionFactory.getAirliftAction();
        d_gameActionTest.SetContext(new Context(d_gamePlayersTest.get(1), d_gameEngineTest));
    }

    @AfterEach
    void cleanup(){
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    @Test
    void TestCommandValidity(){

        Command l_cmd = Command.parseString("airlift 2 3");
        d_gameActionTest.execute(l_cmd);
        // To check if airlift command is valid, should create an error
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(0), outputStreamCaptor.toString().trim());

    }

    @Test
    void TestAirliftCardOwnership(){
        Command l_cmd = Command.parseString("airlift 2 3 3");
        d_gameActionTest.execute(l_cmd);
        // To check if airlift command is valid, should create an error
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(1), outputStreamCaptor.toString().trim());
    }

    @Test
    void TestSourceCountryOwnership(){
        Command l_cmd = Command.parseString("advance 1 3 3");
        d_gameActionTest.d_context.getCurrentPlayer().addCard(CardType.Airlift);
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(2), outputStreamCaptor.toString().trim());
    }

    @Test
    void TestCountryNumberOfArmies(){
        Command l_cmd = Command.parseString("advance 1  11");
        d_gameActionTest.d_context.getCurrentPlayer().addCard(CardType.Airlift);
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(3), outputStreamCaptor.toString().trim());
    }

    @Test
    void TestTargetCountryOwnership(){
        Command l_cmd = Command.parseString("advance 2 3 3");
        d_gameActionTest.d_context.getCurrentPlayer().addCard(CardType.Airlift);
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.AIRLIFT_ERROR_MESSAGES.get(4), outputStreamCaptor.toString().trim());
    }



}