package game.States.Strategy;

import game.Orders.AdvanceOrder;
import game.Orders.DeployOrder;
import game.Orders.Order;
import entity.Country;
import entity.PlayerHandler;
import game.Data.StrategyData;

import java.util.Collections;
import java.util.List;

public class AggressiveStrategy extends Strategy {

    private StrategyData d_strategyData;

    public AggressiveStrategy(StrategyData strategyData) {
        this.d_strategyData = strategyData;
    }

    @Override
    public Order decide() {
        List<Country> playerCountries = d_strategyData.getCurrentPlayer().getCountriesOwned();

        // Check if the current player has any armies
        if (d_strategyData.getCurrentPlayer().getAvailableReinforcements() == 0) {
            // If no armies, mark the player as committed and return null
            PlayerHandler.markCommitted(d_strategyData.getCurrentPlayer());
            return null;
        }

        // Deploy on the strongest country
        Country strongestCountry = findStrongestCountry(playerCountries);
        DeployOrder deployOrder = calculateArmiesToDeploy(strongestCountry);

        // Advance to attack a neighboring country not owned by the player
        AdvanceOrder advanceOrder = calculateAdvanceOrder(strongestCountry);

        // Return the sequence of orders
        return new OrderSequence(deployOrder, advanceOrder, new MarkCommittedOrder(d_strategyData.getCurrentPlayer())) {
        };
    }

    private Country findStrongestCountry(List<Country> countries) {
        if (countries.isEmpty()) {
            return null;
        }

        // Sort countries by armies in descending order
        Collections.sort(countries, Collections.reverseOrder());

        // Return the strongest country
        return countries.get(0);
    }

    private DeployOrder calculateArmiesToDeploy(Country strongestCountry) {
        int armiesToDeploy = d_strategyData.getCurrentPlayer().getAvailableReinforcements();
        return new DeployOrder(d_strategyData.getCurrentPlayer(), armiesToDeploy, strongestCountry, d_strategyData.getEngine().getMap());
    }

    private AdvanceOrder calculateAdvanceOrder(Country strongestCountry) {
        // Find a neighboring country not owned by the player
        Country targetCountry = findTargetCountry(strongestCountry);

        if (targetCountry != null) {
            // Advance from the strongest country to the target country
            return new AdvanceOrder(d_strategyData.getCurrentPlayer(), strongestCountry, targetCountry, d_strategyData.getEngine().getMap());
        } else {
            // If no valid target, return null
            return null;
        }
    }

    private Country findTargetCountry(Country sourceCountry) {
        // Find a neighboring country not owned by the player
        List<Country> neighbors = d_strategyData.getEngine().getMap().ge(sourceCountry);

        for (Country neighbor : neighbors) {
            if (!neighbor.isOwnedBy(d_strategyData.getCurrentPlayer())) {
                return neighbor;
            }
        }

        return null;
    }
}
