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
     * Default constructor that initialize orders to null;
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

            Country strongestCountry = findStrongestCountry(l_player.getCountriesOwned());

            // Deploy armies in the strongest country
            int armiesToDeploy = l_player.getAvailableReinforcements();
            DeployOrder deployOrder = new DeployOrder(l_player, armiesToDeploy, strongestCountry.getDId(), d_strategyData.getEngine().getMap());

            // Attack with the strongest country
            AdvanceOrder advanceOrder = attackWithStrongestCountry(strongestCountry);

            d_unsubmittedOrders.push(deployOrder);
            d_unsubmittedOrders.push(advanceOrder);


        }
        // Since the order stack is created, execute rest one by one
        if (d_unsubmittedOrders.isEmpty()) {
            PlayerHandler.markComitted(l_player);
            d_unsubmittedOrders = null;
            return new EmptyOrder();
        }

        return d_unsubmittedOrders.pop();
    }

    /**
     * Finds the strongest country among the given list of countries based on the number of armies.
     *
     * @param countries The list of countries to search.
     * @return The strongest country, or null if the list is empty.
     */
    private Country findStrongestCountry(List<Country> countries) {


        Country strongestCountry = countries.get(0);

        for (Country country : countries) {
            if (country.getArmy() > strongestCountry.getArmy()) {
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
        // You need to implement logic here to determine the target country for the attack.
        // For simplicity, let's assume you are attacking the first neighboring country for now.
        ArrayList<Country> l_neighboringCountries = new ArrayList<>(p_strongestCountry.getBorders().values());

        int l_amountAvailable=p_strongestCountry.getArmy()+d_strategyData.getCurrentPlayer().getAvailableReinforcements();
        for (Country l_country : l_neighboringCountries) {
            if (!d_strategyData.getCurrentPlayer().isCountryOwned(l_country)) {
                return new AdvanceOrder(d_strategyData.getCurrentPlayer(), p_strongestCountry.getDId(), l_country.getDId(),l_amountAvailable , d_strategyData.getEngine().getMap());
            }
        }


        // Move to next place since all neighbour is not owned by current player
        return new AdvanceOrder(d_strategyData.getCurrentPlayer(), p_strongestCountry.getDId(), l_neighboringCountries.get(0).getDId(), l_amountAvailable, d_strategyData.getEngine().getMap());
    }


}
