package game;

import java.util.HashMap;

/**
 * Class PlayerDetails contains details of the player
 * @author Soham
 */
public class Player {

    private int noOfArmies;
    private String playerName;
    // @param listOfCountriesOwned contains the name of the country and the number of armies present.
    private HashMap<String, Integer> listOfCountriesOwned;


    /**
     * This constructor is used to add new players
     * The number of armies are automatically assigned at 5
     */
    Player(String playerName){
        this.noOfArmies = 5;
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }
}
