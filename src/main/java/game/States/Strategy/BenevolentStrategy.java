package game.States.Strategy;

import entity.Country;
import game.Data.StrategyData;
import game.Orders.DeployOrder;
import game.Orders.Order;

import java.util.List;

/**
 * Benevolent strategy that focuses on protecting weak countries.
 * Deploys armies on the weakest country and reinforces it.
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
     * @return a deploy order based on the benevolent strategy rules
     */
    @Override
    public Order decide() {

        List<Country> playerCountries = d_strategyData.getCurrentPlayer().getCountriesOwned();

        Country weakestCountry = findWeakestCountry(playerCountries);

        if (weakestCountry != null) {
            int armiesToDeploy = calculateArmiesToDeploy(weakestCountry);
            return new DeployOrder(d_strategyData.getCurrentPlayer(), weakestCountry, armiesToDeploy);
        } else {
            return new DeployOrder(d_strategyData.getCurrentPlayer(), null, 0);
        }
    }

    /**
     * Find the weakest country among the given list of countries.
     *
     * @param countries List of countries to check
     * @return The weakest country
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


    private int calculateArmiesToDeploy(Country weakestCountry) {
        // Implement your logic to calculate the number of armies to deploy
        // This can depend on various factors such as the number of territories owned, etc.
        // For simplicity, let's deploy a fixed number of armies (e.g., 5)
        return 3;
    }
}
