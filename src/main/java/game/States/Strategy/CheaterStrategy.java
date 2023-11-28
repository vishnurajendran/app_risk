package game.States.Strategy;

import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.Orders.AdvanceOrder;
import game.Orders.DeployOrder;
import game.Orders.Order;
import game.Orders.SkipOrder;

import java.util.*;

/**
 * @author Soham
 */
public class CheaterStrategy extends Strategy{

    public ArrayList<Country> d_playerCountries;


    public CheaterStrategy(){
        d_playerCountries = new ArrayList<>();
    }
    /**
     * @return an order based on the strategy rules
     */
    @Override
    public Order decide() {
        Map<Integer, Country> neighboringCountries = new HashMap<>();

        Map<Player, Country> l_countriesToAdd = new HashMap<>();
        System.out.println("Doing cheating strategy");
        d_playerCountries = d_strategyData.getCurrentPlayer().getCountriesOwned();

        for(Country country : d_playerCountries){
            neighboringCountries = country.getBorders();
            for(Map.Entry<Integer, Country> borders : neighboringCountries.entrySet()){
                if(!d_strategyData.getCurrentPlayer().isCountryOwned(borders.getValue())){
                    for(Player player :PlayerHandler.getGamePlayers()){
                        if(player.isCountryOwned(borders.getValue())){
                            l_countriesToAdd.put(player, borders.getValue());
                        }
                    }
                }
            }
        }
        System.out.println(neighboringCountries + " for: ->" + d_strategyData.getCurrentPlayer().getPlayerName());
        for(Map.Entry<Player, Country> playerCountryEntry : l_countriesToAdd.entrySet()){

            playerCountryEntry.getKey().removeCountry(playerCountryEntry.getValue());
            d_strategyData.getCurrentPlayer().assignCountry(playerCountryEntry .getValue(), playerCountryEntry .getValue().getArmy()*2);
        }

        PlayerHandler.markComitted(d_strategyData.getCurrentPlayer());
        // make an empty order class to return here.
        return new SkipOrder();
    }
}
