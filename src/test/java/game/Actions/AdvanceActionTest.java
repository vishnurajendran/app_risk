package game.Actions;

import common.Command;
import entity.*;
import game.Data.Context;
import game.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

class AdvanceActionTest {

    GameEngine d_gameEngineTest;
    AdvanceAction d_advanceActionTest;

    ArrayList<Player> d_gamePlayersTest;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        System.setOut(new PrintStream(outputStreamCaptor));
//        System.setOut(new PrintStream(outputStreamCaptor));
//        d_advanceActionTest = new AdvanceAction();
//        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
//        Continent continent = new Continent(1, "test-continent", 3);
//        d_gamePlayersTest = PlayerHandler.getGamePlayers();
//        for (int i = 0; i < d_gamePlayersTest.size(); i++) {
//            d_gamePlayersTest.get(i).assignCountry(new Country(i, "test-country" + i, 1), 0);
//        }
    }

    @AfterEach
    void cleanup(){
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    @Test
    void TestCommandValidity(){
        d_advanceActionTest = new AdvanceAction();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
        Continent continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        for (int i = 1; i < d_gamePlayersTest.size(); i++) {
            d_gameEngineTest.submitCommand(Command.parseString("loadmap testresources/wow.map"));
            d_gamePlayersTest.get(i-1).assignCountry(d_gameEngineTest.getMap().getCountryById(i), 0);
        }
        GameAction l_gameActionTest = GameActionFactory.getAdvanceAction();
        l_gameActionTest.SetContext(new Context(d_gamePlayersTest.get(1), d_gameEngineTest));
        Command l_cmd = Command.parseString("advance 1 2");
        l_gameActionTest.execute(l_cmd);
        // To check if advance command is valid, should create an error
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(0), outputStreamCaptor.toString().trim());

        l_cmd = Command.parseString("advance 1 2 3");
        l_gameActionTest.execute(l_cmd);
        // To check if player2 owns the country 1, which should return false;
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(1), outputStreamCaptor.toString().trim());
    }

    void TestCountryOwnership(){

    }


}