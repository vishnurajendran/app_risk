package game.States.Strategy;

import entity.Country;
import entity.PlayerHandler;
import game.Data.StrategyData;
import game.Orders.AdvanceOrder;
import game.Orders.DeployOrder;
import game.Orders.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Aggressive strategy in the APP Risk game.
 * This strategy focuses on centralization of forces and attacking strategically.
 */
public class AggressiveStrategy extends Strategy {

    private StrategyData d_strategyData;

    /**
     * Constructor for AggressiveStrategy.
     *
     * @param strategyData The StrategyData instance to be used by this strategy.
     */
    public AggressiveStrategy(StrategyData strategyData) {
        this.d_strategyData = strategyData;
    }

    /**
     * @return an order based on the strategy rules
     */
    @Override
    public Order decide() {
        // Ensure StrategyData is available
        if (d_strategyData == null || d_strategyData.getCurrentPlayer() == null) {
            // Handle the case where the current player or StrategyData is null.
            return null;
        }

        PlayerHandler.markComitted(d_strategyData.getCurrentPlayer());
        System.out.println("Committing orders for: " + d_strategyData.getCurrentPlayer().getPlayerName());

        Country strongestCountry = findStrongestCountry(d_strategyData.getCurrentPlayer().getCountriesOwned());

        if (strongestCountry != null) {
            // Deploy armies in the strongest country
            int armiesToDeploy = calculateArmiesToDeploy(strongestCountry);
            DeployOrder deployOrder = new DeployOrder(d_strategyData.getCurrentPlayer(), armiesToDeploy, strongestCountry.getDId(), d_strategyData.getEngine().getMap());

            // Attack with the strongest country
            AdvanceOrder advanceOrder = attackWithStrongestCountry(strongestCountry);

            // Move armies to maximize aggregation of forces in one country
            DeployOrder moveOrder = moveArmies(strongestCountry);

            // Return a compound order containing deploy, advance, and move orders
            List<Order> orders = new ArrayList<>();
            orders.add(deployOrder);
            orders.add(advanceOrder);
            orders.add(moveOrder);

            return (Order) orders;
        } else {
            // If no strong country found, do nothing
            return null;
        }
    }

    /**
     * Finds the strongest country among the given list of countries based on the number of armies.
     *
     * @param countries The list of countries to search.
     * @return The strongest country, or null if the list is empty.
     */
    private Country findStrongestCountry(List<Country> countries) {
        if (countries.isEmpty()) {
            return null;
        }

        Country strongestCountry = countries.get(0);

        for (Country country : countries) {
            if (country.getArmy() > strongestCountry.getArmy()) {
                strongestCountry = country;
            }
        }

        return strongestCountry;
    }

    /**
     * Calculates the number of armies to deploy in the strongest country.
     *
     * @param strongestCountry The strongest country to reinforce.
     * @return The number of armies to deploy.
     */
    private int calculateArmiesToDeploy(Country strongestCountry) {
        // You can implement your own logic to determine the number of armies to deploy.
        // For simplicity, let's deploy a fixed number of armies, e.g., 3.
        return 3;
    }

    /**
     * Creates an AdvanceOrder to attack with the strongest country.
     *
     * @param strongestCountry The strongest country to launch an attack from.
     * @return The AdvanceOrder representing the attack.
     */
    private AdvanceOrder attackWithStrongestCountry(Country strongestCountry) {
        // You need to implement logic here to determine the target country for the attack.
        // For simplicity, let's assume you are attacking the first neighboring country for now.
        List<Country> neighboringCountries = strongestCountry.isNeighbour();
        if (!neighboringCountries.isEmpty()) {
            Country targetCountry = neighboringCountries.get(0);
            return new AdvanceOrder(d_strategyData.getCurrentPlayer(), strongestCountry.getDId(), targetCountry.getDId(), 1, d_strategyData.getEngine().getMap());
        } else {
            // If no neighboring countries, do nothing
            return null;
        }
    }

    /**
     * Deploys armies from conquered territories to maximize aggregation of forces in one country.
     *
     * @param destinationCountry The country where armies are moved to.
     * @return The DeployOrder representing the movement of armies.
     */
    private DeployOrder moveArmies(Country destinationCountry) {
        // You need to implement logic here to determine the source country and the number of armies to move.
        // For simplicity, let's assume you are moving armies from the first neighboring country.
        List<Country> neighboringCountries = destinationCountry.isNeighbour();
        if (!neighboringCountries.isEmpty()) {
            Country sourceCountry = neighboringCountries.get(0);
            int armiesToMove = sourceCountry.getArmy() - 1; // Move all armies except one for defense
            return new DeployOrder(d_strategyData.getCurrentPlayer(), armiesToMove, destinationCountry.getDId(), d_strategyData.getEngine().getMap());
        } else {
            // If no neighboring countries, do nothing
            return null;
        }
    }
}
