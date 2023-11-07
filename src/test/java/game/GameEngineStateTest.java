package game;

import common.Command;
import entity.Player;
import entity.PlayerHandler;
import game.Orders.DeployOrder;
import game.States.GameStates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this class aims at testing the state management of the
 * game engine class. these tests ensure the state transitions
 * are working as intended.
 * @author vishnurajendran
 */
class GameEngineStateTest {

    GameEngine d_gameEngineTest;

    /**
     * initialise the engine before each test.
     */
    @BeforeEach
    public void setUp() {
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
    }

    /**
     * Tests default game state.
     */
    @Test
    public void testDefaultStartState() {
       assertEquals(d_gameEngineTest.getGameState(), GameStates.GameStart);
    }

    /**
     * Test game state after loding map.
     */
    @Test
    public void testStateAfterMapLoad() {
        // we loadmap, add players and assigncountries,
        // the last command invoke should start the game.
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testresources/wow.map"));
        d_gameEngineTest.submitCommand(Command.parseString("gameplayer -add pla1 pla2"));
        d_gameEngineTest.submitCommand(Command.parseString("assigncountries"));
        assertTrue(d_gameEngineTest.gameStarted());
    }

    /**
     * Test order execution and return to issue order state.
     */
    @Test
    public void testExecuteState() {
        // start a game
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testresources/wow.map"));
        d_gameEngineTest.submitCommand(Command.parseString("gameplayer -add pla1 pla2"));
        d_gameEngineTest.submitCommand(Command.parseString("assigncountries"));

        // check if you have two players exact
        assertEquals(PlayerHandler.getGamePlayers().size(), 2);

        // we bypass the deploy command to add deploy orders to the player
        // this is done, since the deploy command validates ownership which can
        // cause failures when done via sending command to the game-engine, so we do this from outside
        // the game-engine.

        // do for player 1
        Player l_playerRef = PlayerHandler.getGamePlayers().get(0);
        l_playerRef.issueOrder(new DeployOrder(l_playerRef,5,1));
        // do for player 2 now.
        l_playerRef = PlayerHandler.getGamePlayers().get(1);
        l_playerRef.issueOrder(new DeployOrder(l_playerRef,5,2));

        //we just need to send commit to the engine to change state now.
        d_gameEngineTest.submitCommand(Command.parseString("commit"));
        d_gameEngineTest.submitCommand(Command.parseString("commit"));

        // the three asserts basically means our executions were done, we shifted to issue order
        // and the players orders are cleared.
        assertEquals(d_gameEngineTest.getGameState(), GameStates.IssueOrder);
        assertFalse(PlayerHandler.getGamePlayers().get(0).hasOrders());
        assertFalse(PlayerHandler.getGamePlayers().get(0).hasOrders());
    }

    /**
     * Test order execution and move to game over.
     */
    @Test
    public void testGameOverState() {
        // start a game
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testresources/wow.map"));
        d_gameEngineTest.submitCommand(Command.parseString("gameplayer -add pla1 pla2"));
        d_gameEngineTest.submitCommand(Command.parseString("assigncountries"));

        // check if you have two players exact
        assertEquals(PlayerHandler.getGamePlayers().size(), 2);

        // remove 1 player to make it just one player left.
        // usually this isn't how players are removed from game, but assigncountires
        // introduces randomness that can't be predicted. so we remove one player
        // to make one player remaining to trigger the state change after execution to
        // move to game over state.
        PlayerHandler.getGamePlayers().remove(0);

        // we bypass the deploy command to add deploy orders to the player
        // this is done, since the deploy command validates ownership which can
        // cause failures when done via sending command to the game-engine due to randomness
        // introduced by the assigncountries, so we do this from outside
        // the game-engine.

        Player l_playerRef = PlayerHandler.getGamePlayers().get(0);
        l_playerRef.issueOrder(new DeployOrder(l_playerRef,5,1));


        //we just need to send commit to the engine to change state now.
        d_gameEngineTest.submitCommand(Command.parseString("commit"));

        // the three asserts basically means our executions were done, we shifted to issue order
        // and the players orders are cleared.
        assertEquals(d_gameEngineTest.getGameState(), GameStates.GameOver);
    }
}