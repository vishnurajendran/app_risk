package game;

import entity.Country;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class PlayerDetails contains details of the player
 * @author Soham
 */
public class Player {

    private int noOfArmies;
    private String playerName;
    // @param listOfCountriesOwned contains the name of the country and the number of armies present.
    private HashMap<Country, Integer> listOfCountriesOwned;


    Player(){
        playerName = "";
        noOfArmies = 0;
        listOfCountriesOwned = new HashMap<>();
    }
    /**
     * This constructor is used to add new players
     * The number of armies are automatically assigned at 5
     */
    Player(String playerName){
        this.noOfArmies = 5;
        this.playerName = playerName;
        listOfCountriesOwned = new HashMap<>();
    }

    public void assignCountry(Country p_country, int p_noOfArmies){
        listOfCountriesOwned.put(p_country, p_noOfArmies);
    }

    public String getPlayerName(){
        return playerName;
    }

    public HashMap<Country, Integer> getCountriesOwned(){
        return listOfCountriesOwned;
    }
}
