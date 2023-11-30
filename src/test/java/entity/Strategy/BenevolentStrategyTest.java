/**
 * A unit test for the BenevolentStrategy class.
 *
 * The BenevolentStrategyTest class contains test cases for the methods
 * implemented in the BenevolentStrategy class, which is a part of the
 * game's strategy package. The test cases focus on ensuring the correct
 * functionality of the BenevolentStrategy methods.
 *
 * @author Shravani
 */
package entity.Strategy;

import common.Command;
import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import entity.Strategy.BenevolentStrategy;
import game.GameEngine;
import game.Orders.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BenevolentStrategyTest {
    GameEngine d_gameEngineTest;

    ArrayList<Player> d_gamePlayersTest;

    /**
     * Sets up the initial conditions for each test case.
     *
     * This method is annotated with @BeforeEach to ensure that it runs
     * before each test method. It initializes a GameEngine instance,
     * loads a test map, adds a player with BenevolentStrategy, assigns
     * countries, and performs other setup operations.
     */
    @BeforeEach
    void setUp() {
        d_gameEngineTest = new GameEngine();
        d_gameEngineTest.initialise();
        d_gameEngineTest.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        d_gameEngineTest.submitCommand(Command.parseString("gameplayer -add p1 -strategy b"));
        d_gameEngineTest.submitCommand(Command.parseString("assigncountries"));
        d_gamePlayersTest = PlayerHandler.getGamePlayers();
        for(Player player: d_gamePlayersTest){
            player.setStrategyContext(d_gameEngineTest);
        }
        d_gamePlayersTest.get(0).assignReinforcementsToPlayer();
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(28), 0);
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(20), 1);
        d_gamePlayersTest.get(0).assignCountry(d_gameEngineTest.getMap().getCountryById(15), 1);
    }

    /**
     * Cleans up resources after each test case is executed.
     *
     */
    @AfterEach
    void tearDown() {
        d_gameEngineTest.shutdown();
        PlayerHandler.cleanup();
    }

    /**
     * Tests the findWeakestCountry method of the BenevolentStrategy class.
     *
     * This test case ensures that the findWeakestCountry method correctly
     * identifies the weakest country owned by a player.
     */
    @Test
    void testFindWeakestCountry() {
        BenevolentStrategy benevolentStrategy = new BenevolentStrategy();

        Country weakestCountry = benevolentStrategy.findWeakestCountry(d_gamePlayersTest.get(0).getCountriesOwned());
        assertEquals(d_gameEngineTest.getMap().getCountryById(28), weakestCountry);
    }

    /**
     * Tests the deployOrder method of the BenevolentStrategy class.
     *
     * This test case ensures that the deployOrder method correctly deploys
     * reinforcements to a weakest country owned by a player.
     */
    @Test
    void testDeployOrder() {
        d_gamePlayersTest.get(0).issueOrder();
        Order order = d_gamePlayersTest.get(0).nextOrder();
        order.executeOrder();
        assertEquals(1, d_gameEngineTest.getMap().getCountryById(28).getArmy());
    }
}
