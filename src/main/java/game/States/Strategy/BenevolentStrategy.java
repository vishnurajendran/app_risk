package game.States.Strategy;

import entity.Country;
import game.Data.StrategyData;
import game.Orders.DeployOrder;
import game.Orders.Order;
import entity.PlayerHandler;

import java.util.List;

/**
 * Represents a Benevolent strategy in a Risk-style game.
 * This strategy focuses on protecting weak countries by deploying armies on the weakest country and reinforcing it.
 */
public class BenevolentStrategy extends Strategy {

    private StrategyData d_strategyData;

    /**
     * Constructor for BenevolentStrategy.
     *
     * @param strategyData The StrategyData instance to be used by this strategy.
     */
    public BenevolentStrategy(StrategyData strategyData) {
        this.d_strategyData = strategyData;
    }

    /**
     * Determines the next move for the Benevolent strategy.
     *
     * @return The Order representing the strategy's decision, or null if no action is taken.
     */
    @Override
    public Order decide() {
        List<Country> playerCountries = d_strategyData.getCurrentPlayer().getCountriesOwned();
        Country weakestCountry = findWeakestCountry(playerCountries);

        // Check if the current player has any armies
        if (d_strategyData.getCurrentPlayer().getAvailableReinforcements() == 0) {
            // If no armies, mark the player as committed and return null
            PlayerHandler.markComitted(d_strategyData.getCurrentPlayer());
            return null;
        }

        if (weakestCountry != null) {
            return calculateArmiesToDeploy(weakestCountry);
        } else {
            return null;
        }
    }

    /**
     * Finds the weakest country among the given list of countries based on the number of armies.
     *
     * @param countries The list of countries to search.
     * @return The weakest country, or null if the list is empty.
     */
    private Country findWeakestCountry(List<Country> countries) {
        if (countries.isEmpty()) {
            return null;
        }

        Country weakestCountry = countries.get(0);

        for (Country country : countries) {
            if (country.getArmy() < weakestCountry.getArmy()) {
                weakestCountry = country;
            }
        }

        return weakestCountry;
    }

    /**
     * Calculates the number of armies to deploy on the weakest country.
     *
     * @param weakestCountry The weakest country to reinforce.
     * @return The DeployOrder representing the deployment of armies.
     */
    private DeployOrder calculateArmiesToDeploy(Country weakestCountry) {
        int armiesToDeploy = 1;
        int countryId = weakestCountry.getDId();

        return new DeployOrder(d_strategyData.getCurrentPlayer(), armiesToDeploy, countryId, d_strategyData.getEngine().getMap());
    }
}
