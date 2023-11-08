package game.Actions;

import entity.Player;
import game.GameEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class AdvanceActionTest {

    GameEngine d_gameEngineTest;
    AdvanceAction d_advanceActionTest;

    ArrayList<Player> d_gamePlayersTest;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp(){
//        System.setOut(new PrintStream(outputStreamCaptor));
//        d_gameEngineTest = new GameEngine();
//        d_gameEngineTest.initialise();
//        d_advanceActionTest = new AdvanceAction();
//        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3", "player4")), null);
//        Continent continent = new Continent(1, "test-continent", 3);
//        d_gamePlayersTest = PlayerHandler.getGamePlayers();
//        for (int i = 0; i < d_gamePlayersTest.size(); i++) {
//            d_gamePlayersTest.get(i).assignCountry(new Country(i, "test-country" + i, 1), 0);
//        }
    }

    @Test
    void TestCommandValidity(){
//        GameAction l_gameActionTest = GameActionFactory.getAdvanceAction();
//        l_gameActionTest.SetContext(new Context(d_gamePlayersTest.get(1), d_gameEngineTest));
//        Command l_cmd = Command.parseString("advance 1 2");
//        l_gameActionTest.execute(l_cmd);
//        Assertions.assertEquals(GameCommands.ADVANCE_ERROR_MESSAGES.get(0), outputStreamCaptor.toString().trim());
    }


}