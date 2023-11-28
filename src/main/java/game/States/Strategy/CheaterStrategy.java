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
//            System.out.print("Checking for country id: " + country.getDId() + " has neighbors: ");
//            for(Map.Entry<Integer, Country> country1: neighboringCountries.entrySet()){
//                System.out.print(" : " + country1.getValue().getDId() + " ");
//            }
            System.out.println();
            for(Map.Entry<Integer, Country> borders : neighboringCountries.entrySet()){
                if(!d_strategyData.getCurrentPlayer().isCountryOwned(borders.getValue())){
                    //System.out.print("Added: ");
                    ArrayList<Country> l_countries = new ArrayList<>();
                    for(Player player :PlayerHandler.getGamePlayers()){
                        if(player.isCountryOwned(borders.getValue())){
                            //System.out.print(" " + borders.getValue().getDId() + ", ");
                            player.removeCountry(borders.getValue());
                            d_strategyData.getCurrentPlayer().assignCountry(borders.getValue(), borders.getValue().getArmy()*2);
                        }
                    }
                }
                System.out.println();
            }
        }
//        System.out.println("Final list of countries to add for : " + d_strategyData.getCurrentPlayer().getPlayerName());
//        for(Map.Entry<Player, Country> check: d_countriesToAdd.entrySet()){
//            System.out.println(check.getKey().getPlayerName() + " : " + check.getValue().getDId());
//        }
//        for(Map.Entry<Player, Country> playerCountryEntry : d_countriesToAdd.entrySet()){
//            //System.out.println("For " + d_strategyData.getCurrentPlayer().getPlayerName() + " Conquering: " + playerCountryEntry.getValue().getName() + " previously owned by: " + playerCountryEntry.getKey().getPlayerName());
//            playerCountryEntry.getKey().removeCountry(playerCountryEntry.getValue());
//            d_strategyData.getCurrentPlayer().assignCountry(playerCountryEntry .getValue(), playerCountryEntry .getValue().getArmy()*2);
//        }

        PlayerHandler.markComitted(d_strategyData.getCurrentPlayer());
        // make an empty order class to return here.
        return new SkipOrder();
    }
}