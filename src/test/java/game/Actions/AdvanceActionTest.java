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
    Continent d_continent;
    GameAction d_gameActionTest;
    PlayerHandler d_playerHandlerTest;

    @BeforeEach
    void setUp(){
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        System.setOut(new PrintStream(outputStreamCaptor));
        d_advanceActionTest = new AdvanceAction();
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
        d_continent = new Continent(1, "test-continent", 3);
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        for (int i = 1; i <= d_gamePlayersTest.size(); i++) {
            d_gameEngineTest.submitCommand(Command.parseString("loadmap testresources/wow.map"));
            d_gamePlayersTest.get(i-1).assignCountry(d_gameEngineTest.getMap().getCountryById(i), 5);
        }
        d_gameActionTest = GameActionFactory.getAdvanceAction();
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

        Command l_cmd = Command.parseString("advance 2 3");
        d_gameActionTest.execute(l_cmd);
        // To check if advance command is valid, should create an error
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(0), outputStreamCaptor.toString().trim());

    }

    @Test
    void TestCountryAdjacency(){
        Command l_cmd = Command.parseString("advance 2 3 3");
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(3), outputStreamCaptor.toString().trim());
    }

    @Test
    void TestCountryOwnership(){
        Command l_cmd = Command.parseString("advance 1 3 3");
        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(1), outputStreamCaptor.toString().trim());
    }

    @Test
    void TestCountryNegotiated(){
        Command l_cmd = Command.parseString("advance 1 3 3");

        d_gameActionTest.execute(l_cmd);
        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(1), outputStreamCaptor.toString().trim());
    }


}