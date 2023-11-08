package game.Orders;

import entity.CardType;
import entity.Country;
import entity.Player;
import entity.RiskMap;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;



import static org.junit.jupiter.api.Assertions.*;

public class AirliftOrderTest {
    private Player player;
    private RiskMap riskMap;

    @Before
    public void setUp() {
        // Create a player and a risk map for testing
        player = new Player();
        riskMap = new RiskMap();
        // Initialize the risk map with countries and armies
        // Add some countries and armies to the riskMap for testing purposes
    }

    @Test
    public void testAirliftOrderExecution() {
        // Create source and target countries
        Country sourceCountry = new Country(1,"testCountry",1);
        sourceCountry.setArmy(10); // Set 10 armies in the source country
        Country targetCountry = new Country(2,"testCountry",2);

        // Add the countries to the risk map
        riskMap.addCountry(sourceCountry);
        riskMap.addCountry(targetCountry);

        // Create an airlift order with 5 armies
        AirliftOrder airliftOrder = new AirliftOrder(player, 1, 2, 5, riskMap);

        // Execute the airlift order
        airliftOrder.executeOrder();

        // Check if the order was executed correctly
        assertEquals(5, targetCountry.getArmy()); // Target country should have received 5 armies
        assertEquals(5, sourceCountry.getArmy()); // Source country should have 5 armies left
    }

    @Test
    public void testAirliftOrderInvalidSourceCountry() {
        // Create an airlift order with an invalid source country
        AirliftOrder airliftOrder = new AirliftOrder(player, 1, 2, 5, riskMap);

        // Execute the airlift order
        airliftOrder.executeOrder();

        // Check if the order failed due to an invalid source country
        // You can modify the actual failure message based on how your AirliftOrder class handles this case.
        assertEquals("Airlift order failed: Invalid source .", getConsoleOutput());
    }

    @Test
    public void testAirliftOrderNotEnoughArmies() {
        // Create source and target countries
        Country sourceCountry = new Country(1,"testCountry",1);
        sourceCountry.setArmy(3); // Set 3 armies in the source country
        Country targetCountry = new Country(2,"testCountry",2);

        // Add the countries to the risk map
        riskMap.addCountry(sourceCountry);
        riskMap.addCountry(targetCountry);

        // Create an airlift order with 5 armies
        AirliftOrder airliftOrder = new AirliftOrder(player, 1, 2, 5, riskMap);

        // Execute the airlift order
        airliftOrder.executeOrder();

        // Check if the order failed due to not enough armies in the source country
        // You can modify the actual failure message based on how your AirliftOrder class handles this case.
        assertEquals("Airlift order failed: Not enough armies in the source country.", getConsoleOutput());
    }

}

