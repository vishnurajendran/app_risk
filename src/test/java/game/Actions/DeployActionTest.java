package game.Actions;

import common.Command;
import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.Data.Context;
import game.GameEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DeployActionTest {
    DeployAction d_action;
    GameEngine d_gameEngine;
    /**
     * Setup player handler
     */
    @BeforeEach
    void setUp() {
        d_gameEngine = new GameEngine();
        d_gameEngine.initialise();
        d_gameEngine.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        PlayerHandler.addGamePlayers(new ArrayList<>(Arrays.asList("player1", "player2", "player3")), d_gameEngine.getMap());
        for(Player player: PlayerHandler.getGamePlayers()){
            player.assignReinforcementsToPlayer();
        }
        ArrayList<Player> allPlayers = PlayerHandler.getGamePlayers();
        for (int i = 0; i < allPlayers.size(); i++) {
            allPlayers.get(i).assignCountry(d_gameEngine.getMap().getCountryById(i+1), 1);
        }
        d_action = new DeployAction();
        d_action.setContext(new Context(PlayerHandler.getGamePlayers().get(0), d_gameEngine));
    }

    /**
     * cleans player handler
     */
    @AfterEach
    void tearDown() {
        PlayerHandler.cleanup();
    }

    /**
     * this test checks if deploy action
     * is correctly behaving when the deploy command is
     * valid
     */
    @Test
    void testValidOrder() {
        d_action.execute(Command.parseString("deploy 1 1"));
        assertEquals(d_action.getExecutionStatus(),ActionExecStatus.Success);
    }

    /**
     * this test checks if deploy action
     * is correctly behaving when the deploy command is
     * provided with too many units.
     */
    @Test
    void testInvalidOrderTooManyReinforcements() {
        d_action.execute(Command.parseString("deploy 1 7"));
        assertEquals(d_action.getExecutionStatus(),ActionExecStatus.Fail);
    }

    /**
     * this test checks if deploy action
     * is correctly behaving when the deploy command is
     * provided with a country, the player doesn't own.
     */
    @Test
    void testInvalidOrderInvalidCountry() {
        d_action.execute(Command.parseString("deploy 2 2"));
        assertEquals(d_action.getExecutionStatus(),ActionExecStatus.Fail);
    }
}