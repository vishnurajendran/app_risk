package game.States.Strategy;

import entity.Country;
import game.Data.StrategyData;
import game.Orders.DeployOrder;
import game.Orders.Order;
import entity.PlayerHandler;

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


    private DeployOrder calculateArmiesToDeploy(Country weakestCountry) {
        int armiesToDeploy = 1;


        return new DeployOrder(d_strategyData.getCurrentPlayer(), armiesToDeploy, weakestCountry, d_strategyData.getEngine().getMap());
    }
}
