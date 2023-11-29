package game.States.Strategy;

import entity.Country;
import entity.PlayerHandler;
import game.Orders.AdvanceOrder;
import game.Orders.DeployOrder;
import game.Orders.Order;

import java.util.List;

/**
 * Represents an Aggressive strategy in the APP Risk game.
 * This strategy focuses on centralization of forces and attacking strategically.
 */
public class AggressiveStrategy extends Strategy {

    /**
     * @return an order based on the strategy rules
     */
    @Override
    public Order decide() {
        if (d_strategyData.getCurrentPlayer() == null) {
            // Handle the case where the current player is null.
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

            // Return a compound order containing both deploy and advance orders
            return new CompoundOrder(deployOrder, advanceOrder);
        } else {
            // If no strong country found, do nothing
            return null;
        }
    }

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


    private int calculateArmiesToDeploy(Country strongestCountry) {
        // You can implement your own logic to determine the number of armies to deploy.
        // For simplicity, let's deploy a fixed number of armies, e.g., 3.
        return 3;
    }


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
}
