/**
 * Unit test for the AggressiveStrategy class.
 * It includes test methods to validate the behavior of the AggressiveStrategy class.
 *
 * @author Rachit
 */
package game.States.Strategy;

import common.Command;
import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.GameEngine;
import game.Orders.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AggressiveStrategyTest {

    GameEngine d_gameEngineTest;
    ArrayList<Player> d_gamePlayersTest;

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a GameEngine, loads a map, and adds players with specific strategies.
     * Assigns countries to players for testing.
     */
    @BeforeEach
    void setup() {
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        ArrayList<String> p_playersToAdd = new ArrayList<>(Arrays.asList("v1", "v2"));
        ArrayList<String> p_strategiesToAdd = new ArrayList<>(Arrays.asList("a", "h"));
        PlayerHandler.addGamePlayers(p_playersToAdd, p_strategiesToAdd, d_gameEngineTest.getMap());
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        for (Player player : d_gamePlayersTest) {
            player.setStrategyContext(d_gameEngineTest);
        }
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(27), 4);
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(29), 2);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(30), 2);
        d_gamePlayersTest.get(1).assignCountry(d_gameEngineTest.getMap().getCountryById(38), 2);
    }

    /**
     * Cleans up the test environment after each test method is executed.
     * Quits the game, shuts down the GameEngine, and sets GameEngine instance to null.
     */
    @AfterEach
    void cleanup() {
        d_gameEngineTest.quitGame();
        d_gameEngineTest.shutdown();
        d_gameEngineTest = null;
    }

    /**
     * Tests the findStrongestCountry method of the AggressiveStrategy class.
     * Verifies that the method correctly identifies the strongest country owned by a player.
     */
    @Test
    void FindStrongestCountryTest() {
        AggressiveStrategy l_aggressiveStrat = new AggressiveStrategy();
        Country findStrongest = l_aggressiveStrat.findStrongestCountry(d_gamePlayersTest.get(0).getCountriesOwned());
        assertEquals(findStrongest, d_gamePlayersTest.get(0).getCountriesOwned().get(0));
    }

    /**
     * Tests the deploy phase of the AggressiveStrategy class.
     * Verifies that the strategy deploys reinforcements to the strongest country owned by the player.
     */
    @Test
    void AggressiveStrategyDeployTest() {
        Player l_currentPlayer = d_gamePlayersTest.get(0);
        l_currentPlayer.assignReinforcementsToPlayer();
        AggressiveStrategy l_aggressiveStrat = new AggressiveStrategy();
        Country findStrongest = l_aggressiveStrat.findStrongestCountry(l_currentPlayer.getCountriesOwned());
        assertEquals(findStrongest, l_currentPlayer.getCountriesOwned().get(0));
        l_currentPlayer.issueOrder();
        if (l_currentPlayer.hasOrders()) {
            Order order = l_currentPlayer.nextOrder();
            System.out.println("Running: " + order);
            order.executeOrder();
        }
        assertEquals(l_currentPlayer.getCountriesOwned().get(0).getArmy(), 7);
    }

    /**
     * Tests the advance phase of the AggressiveStrategy class.
     * Verifies that the strategy issues an order to advance
     * to a neighboring country after deploying reinforcements.
     */
    @Test
    void AggressiveStrategyAdvanceTest() {
        Player l_currentPlayer = d_gamePlayersTest.get(0);
        AggressiveStrategy l_aggressiveStrat = new AggressiveStrategy();
        Country findStrongest = l_aggressiveStrat.findStrongestCountry(l_currentPlayer.getCountriesOwned());
        assertEquals(findStrongest, l_currentPlayer.getCountriesOwned().get(0));
        l_currentPlayer.issueOrder();
        if (l_currentPlayer.hasOrders()) {
            Order order = l_currentPlayer.nextOrder();
            System.out.println("Running: " + order);
            order.executeOrder();
        }
        assertTrue(l_currentPlayer.isCountryOwned(d_gameEngineTest.getLoadedMap().getCountryById(30)));
    }

}