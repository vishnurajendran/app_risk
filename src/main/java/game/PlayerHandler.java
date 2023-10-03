package game;

import common.Command;
import common.Logger;
import entity.Country;
import mapEditer.MapLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * This class handles the actions that happen to the player
 * @author Soham
 */
public class PlayerHandler {
    private static ArrayList<Player> d_gamePlayers = new ArrayList<>();

    private static int d_whichPlayersTurn;

    private static MapLoader d_loadedMap;


    PlayerHandler(){
        d_whichPlayersTurn = 0;
    }
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
        int l_index = 0;
        while(randomCountryIDs.size()>0){
            d_gamePlayers.get(l_index).assignCountry(p_loadedMap.getMap().getCountryById(randomCountryIDs.get(0)), p_loadedMap.getMap().getCountryArmyById(randomCountryIDs.get(0)));
            l_index = (l_index+1)%d_gamePlayers.size();
            randomCountryIDs.remove(0);
        }
        for(Player player: d_gamePlayers){
            player.assignReinforcementsToPlayer();
        }
        displayGamePlayersWithCountries(p_loadedMap);
    }

    public static void displayGamePlayersWithCountries(MapLoader p_loadedMap){
        Player l_currentPlayer = d_gamePlayers.get(d_whichPlayersTurn % d_gamePlayers.size());
        Logger.log("Displaying countries assigned to players");
        d_loadedMap = p_loadedMap;
        for(Player name: d_gamePlayers){
            System.out.println(name.getPlayerName() + " Owns: ");
            name.getCountriesOwned().forEach((key) -> System.out.println(" " + d_loadedMap.getMap().getCountryById(key.getDId()).getName() + ", ID: "
                    + key.getDId() + ", Armies: " + d_loadedMap.getMap().getCountryById(key.getDId()).getArmy()));
            System.out.println();
        }
        l_currentPlayer.assignReinforcementsToPlayer();
        System.out.println("Now every player needs to deploy their reinforcements into their respective countries.");
        System.out.println(l_currentPlayer.getPlayerName() + "'s turn, Reinforcements left: " + l_currentPlayer.getAvailableReinforcements());
        displayGamePlayersCountries(l_currentPlayer);
    }

    public static void displayGamePlayersCountries(Player p_player){
        ArrayList<Country> l_playerCountries = p_player.getCountriesOwned();
        System.out.println("The player owns following countries: ");
        for(Country value: l_playerCountries){
            System.out.println("ID: " + value.getDId() + " Name: " + value.getName() + " Armies: " + value.getArmy());
        }
    }

    /**
     * This function returns integer based on the ability to process the deploy order
     * @return 1 means the command given isn't valid,
     * 2 means the player doesn't possess the country,
     * 3 means the player deployed more armies than they had.
     * 4 means the command is valid and can proceed to deploy order
     * @param p_cmd includes the countryID and the numberOfReinforcements
     */

    public static int issueOrder(Command p_cmd){
        int l_indexOfPlayer = d_whichPlayersTurn % d_gamePlayers.size();
        Player l_currentPlayer = d_gamePlayers.get(l_indexOfPlayer);
        if(!p_cmd.getCmdAttributes().isEmpty()) {
            int l_countryId;

            int l_deployReinforcements;
            try {
                l_countryId = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(0));
                l_deployReinforcements = Integer.parseInt(p_cmd.getCmdAttributes().get(0).getArguments().get(1));
            } catch (Exception e) {
                Logger.log("Number exception");
                return 1;
            }
            if(!l_currentPlayer.getCountriesOwned().contains(d_loadedMap.getMap().getCountryById(l_countryId))){
                Logger.log("The player doesn't have this country");
                return 2;
            } else if(l_currentPlayer.getAvailableReinforcements()<l_deployReinforcements){
                Logger.log("The armies requested to display are more than what the player has");
                return 3;
            } else {

                l_currentPlayer.setAvailableReinforcements(l_currentPlayer.getAvailableReinforcements() - l_deployReinforcements);
                l_currentPlayer.setTempOrder(
                        new DeployOrder(l_currentPlayer, l_deployReinforcements, l_countryId));
                l_currentPlayer.issueOrder();
                Logger.log("Orders for player " + l_currentPlayer + " = " + l_currentPlayer.getOrderSize());
                d_whichPlayersTurn += 1;
            }
        }
        else{
            Logger.log(String.valueOf(p_cmd.getCmdAttributes().size()+ " Isn't valid"));
            return 1;
        }
        return 4;
    }

    public static void displayGamePlayers(){

        for(Player name: d_gamePlayers){
            System.out.println(name.getPlayerName());
        }
    }
    public static ArrayList<Player> getGamePlayers() {
        return d_gamePlayers;
    }

    public static int getPlayerTurn(){
        return d_whichPlayersTurn;
    }
    public static void increasePlayerTurn(int p_incrementValue){
        d_whichPlayersTurn+=p_incrementValue;
    }

    public static void reassignValuesForNextTurn(){
        d_whichPlayersTurn = 0;
        displayGamePlayersWithCountries(d_loadedMap);
        for(Player player: d_gamePlayers){
            player.assignReinforcementsToPlayer();
        }
    }
}
