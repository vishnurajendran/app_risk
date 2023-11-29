package game.States.Strategy;

import common.Command;
import entity.Country;
import entity.PlayerHandler;
import game.GameEngine;
import game.Orders.DeployOrder;
import game.Orders.Order;
import game.States.Strategy.BenevolentStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BenevolentStrategyTest {
    GameEngine d_engine;

    @BeforeEach
    void setUp() {
        d_engine = new GameEngine();
        d_engine.initialise();
        d_engine.submitCommand(Command.parseString("loadmap testResources/WoW.map"));
        d_engine.submitCommand(Command.parseString("gameplayer -add p1 -strategy b"));
        d_engine.submitCommand(Command.parseString("assigncountries"));
    }

    @AfterEach
    void tearDown() {
        d_engine.shutdown();
        PlayerHandler.cleanup();
    }

    @Test
    void testFindWeakestCountry() {
        // Assuming that the BenevolentStrategy is the current player
        BenevolentStrategy benevolentStrategy = (BenevolentStrategy) PlayerHandler.getCurrentPlayer().getPlayerStrategy();

        // Manually set the armies of countries for testing
        Country country1 = d_engine.getMap().getCountryById(1);
        Country country2 = d_engine.getMap().getCountryById(2);
        Country country3 = d_engine.getMap().getCountryById(3);

        country1.setArmy(5);
        country2.setArmy(3);
        country3.setArmy(7);

        List<Country> countries = Arrays.asList(country1, country2, country3);

        // Test if it correctly finds the country with the least armies
        Country weakestCountry = benevolentStrategy.findWeakestCountry(countries);
        assertEquals(country2, weakestCountry);
    }

    @Test
    void testDeployOrder() {
        // Assuming that the BenevolentStrategy is the current player
        BenevolentStrategy benevolentStrategy = (BenevolentStrategy) PlayerHandler.getCurrentPlayer().getPlayerStrategy();

        // Manually set the armies of countries for testing
        Country weakestCountry = d_engine.getMap().getCountryById(2);
        weakestCountry.setArmy(3);

        int initialReinforcements = PlayerHandler.getCurrentPlayer().getAvailableReinforcements();

        // Ensure that the decide method returns a DeployOrder
        Order order = benevolentStrategy.decide();
        assertTrue(order instanceof DeployOrder);

        // Ensure that the correct number of armies is deployed
        DeployOrder deployOrder = (DeployOrder) order;
        assertEquals(initialReinforcements, deployOrder.getArmiesToDeploy());
    }
}
