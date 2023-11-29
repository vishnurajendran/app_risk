package game.States.Strategy;

import entity.Country;
import entity.Player;
import entity.PlayerHandler;
import game.Orders.Order;
import game.Orders.SkipOrder;

import java.util.*;

/**
 * @author Soham
 */
public class CheaterStrategy extends Strategy{

    public ArrayList<Country> d_playerCountries;

    public ArrayList<ArrayList<Integer>> d_countriesToAdd;

    public CheaterStrategy(){
        d_playerCountries = new ArrayList<>();
        d_countriesToAdd = new ArrayList<>();
    }
    /**
     * @return an order based on the strategy rules
     */
    @Override
    public Order decide() {
        Map<Integer, Country> neighboringCountries = new HashMap<>();

        System.out.println("Doing cheating strategy");
        d_playerCountries = d_strategyData.getCurrentPlayer().getCountriesOwned();

        for(Country country : d_playerCountries){
            neighboringCountries = country.getBorders();
            for(Map.Entry<Integer, Country> borders : neighboringCountries.entrySet()){
                if(!d_strategyData.getCurrentPlayer().isCountryOwned(borders.getValue())){
                    for(Player player :PlayerHandler.getGamePlayers()){
                        if(player.isCountryOwned(borders.getValue())){

                            player.removeCountry(borders.getValue());
                            d_strategyData.getCurrentPlayer().assignCountry(borders.getValue(), borders.getValue().getArmy()*2);
                        }
                    }
                }
            }
        }


        PlayerHandler.markComitted(d_strategyData.getCurrentPlayer());
        // make an empty order class to return here.
        return new SkipOrder();
    }
}
