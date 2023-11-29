package game.States.Strategy;

import entity.Country;
import game.Data.StrategyData;
import game.Orders.DeployOrder;
import game.Orders.Order;
import entity.Player;
import entity.PlayerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BenevolentStrategyTest {

    private BenevolentStrategy benevolentStrategy;
    private StrategyData strategyData;

    @BeforeEach
    void setUp() {
        // Set up a mock StrategyData instance for testing
        Player player = new Player();  // You may need to adjust the Player class based on your actual implementation
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(/* parameters for Country */));  // Add country instances with appropriate parameters
        // Add more countries as needed
        player.setCountriesOwned(countries);


        benevolentStrategy = new BenevolentStrategy(strategyData);
    }rddeeefzxx

    @Test
    void decide_withNoAvailableReinforcements_shouldMarkPlayerCommittedAndReturnNull() {
        // Test when the player has no reinforcements
        strategyData.getCurrentPlayer().setAvailableReinforcements(0);
        Order order = benevolentStrategy.decide();

        // Verify that the player is marked as committed
        assertTrue(PlayerHandler.isPlayerCommitted(strategyData.getCurrentPlayer()));
        // Verify that the result of the strategy decision is null
        assertNull(order);
    }

    @Test
    void decide_withAvailableReinforcementsAndWeakCountry_shouldReturnDeployOrder() {
        // Test when the player has reinforcements and a weak country
        // Example: Set up a country with a small army to be the weakest
        Country weakestCountry = new Country(/* parameters for Country */);
        weakestCountry.setArmy(1);
        strategyData.getCurrentPlayer().getCountriesOwned().add(weakestCountry);

        // Set up reinforcements for the player
        strategyData.getCurrentPlayer().setAvailableReinforcements(3);

        // Perform the decide action
        Order order = benevolentStrategy.decide();

        // Verify that the order is not null and is of the expected type
        assertNotNull(order);
        assertTrue(order instanceof DeployOrder);

        // Verify that no more interactions should happen with the PlayerHandler (assuming it's a mock)
        assertFalse(PlayerHandler.isPlayerCommitted(strategyData.getCurrentPlayer()));
    }

    @Test
    void decide_withAvailableReinforcementsAndNoWeakCountry_shouldReturnNull() {
        // Test when the player has reinforcements but no weak country
        // Set up reinforcements for the player
        strategyData.getCurrentPlayer().setAvailableReinforcements(3);

        // Perform the decide action
        Order order = benevolentStrategy.decide();

        // Verify that the result of the strategy decision is null
        assertNull(order);

        // Verify that no more interactions should happen with the PlayerHandler (assuming it's a mock)
        assertFalse(PlayerHandler.isPlayerCommitted(strategyData.getCurrentPlayer()));
    }

    @Test
    void findWeakestCountry_withEmptyCountryList_shouldReturnNull() {
        // Test when the list of countries is empty
        List<Country> emptyCountryList = new ArrayList<>();
        Country weakestCountry = benevolentStrategy.findWeakestCountry(emptyCountryList);

        // Verify that the result is null
        assertNull(weakestCountry);
    }

    @Test
    void findWeakestCountry_withNonEmptyCountryList_shouldReturnWeakestCountry() {
        // Test when the list of countries is non-empty
        // Example: Set up countries with varying numbers of armies
        Country country1 = new Country(/* parameters for Country */);
        country1.setArmy(3);
        Country country2 = new Country(/* parameters for Country */);
        country2.setArmy(1);
        Country country3 = new Country(/* parameters for Country */);
        country3.setArmy(5);

        List<Country> countryList = List.of(country1, country2, country3);

        // Perform the findWeakestCountry action
        Country weakestCountry = benevolentStrategy.findWeakestCountry(countryList);

        // Verify that the weakest country is correctly identified
        assertEquals(country2, weakestCountry);
    }

    @Test
    void calculateArmiesToDeploy_shouldReturnDeployOrderWithCorrectValues() {
        // Test the calculateArmiesToDeploy method
        // Example: Set up a weakest country with an ID
        Country weakestCountry = new Country(/* parameters for Country */);
        weakestCountry.setDId(42);

        // Perform the calculateArmiesToDeploy action
        DeployOrder deployOrder = benevolentStrategy.calculateArmiesToDeploy(weakestCountry);

        // Verify that the DeployOrder is correctly generated with the expected values
        assertEquals(strategyData.getCurrentPlayer(), deployOrder.getPlayer());
        assertEquals(1, deployOrder.getArmiesToDeploy());
        assertEquals(42, deployOrder.getCountryId());
        assertEquals(strategyData.getEngine().getMap(), deployOrder.getMap());
    }
}
