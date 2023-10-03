package game;

import entity.Country;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class PlayerDetails contains details of the player
 * @author Soham
 */
public class Player {

    private int d_availableReinforcements;
    private String playerName;
    // @param listOfCountriesOwned contains the name of the country and the number of armies present.
    private HashMap<Country, Integer> d_listOfCountriesOwned;

    private ArrayList<Order> d_orders;

    //

    Player(){
        playerName = "";
        d_availableReinforcements = 0;
        d_listOfCountriesOwned = new HashMap<>();
    }

    public void issueOrder(Order p_newOrder){
        
    }

    public Order nextOrder(){
        if(d_orders.isEmpty()){
           return null;
        }
        Order l_order = d_orders.get(0);
        d_orders.remove(0);
        return l_order;
    }

    /**
     * This constructor is used to add new players
     * The number of armies are automatically assigned at 5
     */
    Player(String playerName){
        this.d_availableReinforcements = 5;
        this.playerName = playerName;
        d_listOfCountriesOwned = new HashMap<>();
    }

    public void assignCountry(Country p_country, int p_noOfArmies){
        d_listOfCountriesOwned.put(p_country, p_noOfArmies);
    }

    public String getPlayerName(){
        return playerName;
    }

    public int getAvailableReinforcements(){
        return d_availableReinforcements;
    }
    public HashMap<Country, Integer> getCountriesOwned(){
        return d_listOfCountriesOwned;
    }
}
