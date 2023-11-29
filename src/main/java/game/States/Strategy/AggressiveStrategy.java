package game.States.Strategy;

import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.Orders.AdvanceOrder;
import game.Orders.DeployOrder;
import game.Orders.EmptyOrder;
import game.Orders.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.util.Objects.isNull;

/**
 * Represents an Aggressive strategy in the APP Risk game.
 * This strategy focuses on centralization of forces and attacking strategically.
 */
public class AggressiveStrategy extends Strategy {

    Stack<Order> d_unsubmittedOrders;

    /**
     * Default constructor that initializes orders to null;
     */
    public AggressiveStrategy() {
        d_unsubmittedOrders = null;
    }

    /**
     * @return an order based on the strategy rules
     */
    @Override
    public Order decide() {
        // Ensure StrategyData is available
        if (d_strategyData == null || d_strategyData.getCurrentPlayer() == null) {
            // Handle the case where the current player or StrategyData is null.
            return new EmptyOrder();
        }
        Player l_player = d_strategyData.getCurrentPlayer();

        // check to see if a stack of orders is created
        if (isNull(d_unsubmittedOrders)) {
            d_unsubmittedOrders = new Stack<>();

            // Find the strongest country that the player owns
            Country strongestCountry = findStrongestCountry(l_player.getCountriesOwned());

            // Check if the player has armies available for deployment
            int armiesToDeploy = l_player.getAvailableReinforcements();
            if (armiesToDeploy > 0) {
                // Deploy all armies to the strongest country
                DeployOrder deployOrder = new DeployOrder(l_player, armiesToDeploy, strongestCountry.getDId(), d_strategyData.getEngine().getMap());

                // Move all armies from other owned countries to the strongest country
                for (Country ownedCountry : l_player.getCountriesOwned()) {
                    if (!ownedCountry.equals(strongestCountry)) {
                        AdvanceOrder moveOrder = new AdvanceOrder(l_player, ownedCountry.getDId(), strongestCountry.getDId(), ownedCountry.getArmy(), d_strategyData.getEngine().getMap());
                        d_unsubmittedOrders.push(moveOrder);
                    }
                }

                // Attack a neighboring country that the player does not own
                AdvanceOrder attackOrder = attackWithStrongestCountry(strongestCountry);

                d_unsubmittedOrders.push(deployOrder);
                d_unsubmittedOrders.push(attackOrder);
            } else {
                // Mark the player as committed if there are no armies to deploy
                PlayerHandler.markComitted(l_player);
            }
        }

        // Execute orders one by one
        if (!d_unsubmittedOrders.isEmpty()) {
            return d_unsubmittedOrders.pop();
        } else {
            // Mark player as committed when all orders are executed
            PlayerHandler.markComitted(l_player);
            d_unsubmittedOrders = null;
            return new EmptyOrder();
        }
    }

    /**
     * Finds the strongest country among the given list of countries based on the number of armies.
     *
     * @param countries The list of countries to search.
     * @return The strongest country, or null if the list is empty.
     */
    private Country findStrongestCountry(List<Country> countries) {
        Country strongestCountry = null;

        for (Country country : countries) {
            if (strongestCountry == null || country.getArmy() > strongestCountry.getArmy()) {
                strongestCountry = country;
            }
        }

        return strongestCountry;
    }

    /**
     * Creates an AdvanceOrder to attack with the strongest country.
     *
     * @param p_strongestCountry The strongest country to launch an attack from.
     * @return The AdvanceOrder representing the attack.
     */
    private AdvanceOrder attackWithStrongestCountry(Country p_strongestCountry) {
        ArrayList<Country> l_neighboringCountries = new ArrayList<>(p_strongestCountry.getBorders().values());

        int l_amountAvailable = p_strongestCountry.getArmy() + d_strategyData.getCurrentPlayer().getAvailableReinforcements();

        // Advance all armies from other owned countries to the strongest country until other owned countries have 0 armies
        for (Country l_country : l_neighboringCountries) {
            if (!d_strategyData.getCurrentPlayer().isCountryOwned(l_country)) {
                return new AdvanceOrder(d_strategyData.getCurrentPlayer(), p_strongestCountry.getDId(), l_country.getDId(), l_amountAvailable, d_strategyData.getEngine().getMap());
            }
        }

        // If all neighbors are owned, attack the next non-owned country
        return new AdvanceOrder(d_strategyData.getCurrentPlayer(), p_strongestCountry.getDId(), l_neighboringCountries.get(0).getDId(), l_amountAvailable, d_strategyData.getEngine().getMap());
    }
}
