package game;
import java.io.*;
import java.util.*;
import common.Logger;
import entity.Country;
import mapEditer.MapLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * This class handles the actions that happen to the player
 * @author Soham
 */
public class PlayerHandler {
    private static ArrayList<Player> d_gamePlayers = new ArrayList<>();


    /**
     * This method checks if there are any duplicates in the list
     * if there are, it removes them from adding
     * then runs a loop to add all the players
     * @param p_playerNamesToAdd
     */
    public static void addGamePlayers(ArrayList<String> p_playerNamesToAdd){
        // Create a LinkedHashSet to remove duplicates while preserving order
        LinkedHashSet<String> setWithoutDuplicates = new LinkedHashSet<>(p_playerNamesToAdd);

        // Create a new ArrayList to store the elements without duplicates
        ArrayList<String> p_playerNames = new ArrayList<>(setWithoutDuplicates);

        ArrayList<String> d_duplicates = new ArrayList<>();
        for(String name: p_playerNames){
            for(Player player : d_gamePlayers){
                if(player.getPlayerName().equals(name)){
                    System.out.println(name + " already exists in the game");
                    d_duplicates.add(name);
                }
            }
        }

        for(String name: d_duplicates){
            p_playerNames.remove(name);
        }

        for(String name: p_playerNames){
            d_gamePlayers.add(new Player(name));
        }
        System.out.println("Added " + p_playerNames.size() + " players to the game:");
    }

    /**
     * @param p_playerNamesToRemove contains all the player names in an array format
     * The method creates an iterator to iterate through d_gameplayers
     * It compares based on names to remove the one that matches
     */
    public static void removeGamePlayers(ArrayList<String> p_playerNamesToRemove){
        Iterator<Player> l_iterator = d_gamePlayers.iterator();
        for (String name : p_playerNamesToRemove) {
            l_iterator = d_gamePlayers.iterator();
            while (l_iterator.hasNext()) {
                Player player = l_iterator.next();
                if (player.getPlayerName().equals(name)) {
                    l_iterator.remove();
                }
            }
        }
        System.out.println("The list of Game Players is: ");
    }

    public static void assignCountriesToPlayer(MapLoader p_loadedMap){
        ArrayList<Integer> randomCountryIDs = new ArrayList<>();
        for(int i = 1; i<p_loadedMap.getMap().getCountries().size(); i++){
            randomCountryIDs.add(i);
        }
        if(p_loadedMap.getMap().getCountryIds().size()< d_gamePlayers.size()){
            System.out.println("ERROR: Number of players are greater than the number of countries in the map");
            return;
        }
        Logger.log("game player size: " + d_gamePlayers.size());
        Collections.shuffle(randomCountryIDs);
        Logger.log("Countries were assigned in the following order: " + String.valueOf(randomCountryIDs));
        for(int i = 0; i < d_gamePlayers.size(); i++){
            d_gamePlayers.get(i).assignCountry(p_loadedMap.getMap().getCountryById(randomCountryIDs.get(i)), p_loadedMap.getMap().getCountryArmyById(randomCountryIDs.get(i)));
        }
        displayGamePlayersWithCountries(p_loadedMap);
    }

    public static void displayGamePlayersWithCountries(MapLoader p_loadedMap){
        Logger.log("Displaying countries assigned to players");
        for(Player name: d_gamePlayers){
            System.out.println(name.getPlayerName() + " Owns ");
            name.getCountriesOwned().forEach((key, value) -> System.out.println(p_loadedMap.getMap().getCountryIds()));
        }
    }

    public static void displayGamePlayers(){

        for(Player name: d_gamePlayers){
            System.out.println(name.getPlayerName());
        }
    }
    public ArrayList<Player> getGamePlayers() {
        return d_gamePlayers;
    }
}
