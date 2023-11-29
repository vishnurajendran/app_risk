package entity;

import common.Logging.Logger;
import game.GameCommands;
import game.States.Strategy.*;

import java.util.*;

/**
 * This class handles the actions that happen to the player
 *
 * @author Soham
 */
public class PlayerHandler {

    /**
     * list of players that has committed.
     */
    private static ArrayList<Player> d_commitedPlayers = new ArrayList<>();


    private static ArrayList<Player> d_gamePlayers = new ArrayList<>();

    private static int d_whichPlayersTurn;

    private static RiskMap d_loadedMap;

    private static boolean d_countriesAssigned = false;

    /**
     * default constructor
     */
    public PlayerHandler() {

    }

    public static void countriesAssigned(boolean p_countriesAssigned){
        d_countriesAssigned = p_countriesAssigned;
    }

    public static boolean areCountriesAssigned(){
        return d_countriesAssigned;
    }

    /**
     * cleans up and resets all internal vars to
     * default values
     */
    public static void cleanup() {
        d_gamePlayers.clear();
        d_commitedPlayers.clear();
        d_countriesAssigned = false;
        d_loadedMap = null;
        d_whichPlayersTurn = 0;
    }

    /**
     * This method checks if there are any duplicates in the list
     * if there are, it removes them from adding
     * then runs a loop to add all the players
     *
     * @param p_playerNamesToAdd is the list of players to add to the d_gameplayers object
     */
    public static void addGamePlayers(ArrayList<String> p_playerNamesToAdd, RiskMap p_map) {
        // Create a LinkedHashSet to remove duplicates while preserving order
        LinkedHashSet<String> setWithoutDuplicates = new LinkedHashSet<>(p_playerNamesToAdd);

// Create a new ArrayList to store the elements without duplicates
        ArrayList<String> p_playerNames = new ArrayList<>(setWithoutDuplicates);

        ArrayList<String> d_duplicates = new ArrayList<>();
        for (String name : p_playerNames) {
            for (Player player : d_gamePlayers) {
                if (player.getPlayerName().equals(name)) {
                    System.out.println(name + " already exists in the game");
                    d_duplicates.add(name);
                }
            }
        }

        for (String name : d_duplicates) {
            p_playerNames.remove(name);
        }

        for (String name : p_playerNames) {
            d_gamePlayers.add(new Player(generatePlayerId(),name, p_map));
        }
    }

    /**
     * This method checks if there are any duplicates in the list
     * if there are, it removes them from adding
     * then runs a loop to add all the players and their respective strategies
     *
     * @param p_playerNamesToAdd is the list of players to add to the d_gameplayers object
     * @param p_strategiesToAdd is the list of strategies to add to game players
     */
    public static void addGamePlayers(ArrayList<String> p_playerNamesToAdd, ArrayList<String> p_strategiesToAdd, RiskMap p_map) {
        // Create a LinkedHashSet to remove duplicates while preserving order
        LinkedHashSet<String> setWithoutDuplicates = new LinkedHashSet<>(p_playerNamesToAdd);

        // Create a new ArrayList to store the elements without duplicates
        ArrayList<String> p_playerNames = new ArrayList<>(setWithoutDuplicates);

        ArrayList<String> d_duplicates = new ArrayList<>();
        for (String name : p_playerNames) {
            for (Player player : d_gamePlayers) {
                if (player.getPlayerName().equals(name)) {
                    System.out.println(name + " already exists in the game");
                    d_duplicates.add(name);
                }
            }
        }

        for (String name : d_duplicates) {
            p_strategiesToAdd.remove(p_playerNames.indexOf(name));
            p_playerNames.remove(name);
        }

        for (int i = 0; i < p_playerNames.size(); i++) {
            Strategies l_playerStrategy = switch (p_strategiesToAdd.get(i)){
                case GameCommands.STRAT_RANDOM -> Strategies.Random;
                case GameCommands.STRAT_AGGRESSIVE -> Strategies.Aggressive;
                case GameCommands.STRAT_BENEVOLENT -> Strategies.Benevolent;
                case GameCommands.STRAT_CHEATER -> Strategies.Cheater;
                default -> Strategies.Human;
            };
            d_gamePlayers.add(new Player(generatePlayerId(),p_playerNames.get(i), p_map, l_playerStrategy));
        }
    }


    /**
     * @return a numerical number that can be used as an Id for players
     */
    public static int generatePlayerId(){
        if(d_gamePlayers == null)
            d_gamePlayers = new ArrayList<>();

        return d_gamePlayers.size() + 1;
    }

    /**
     * @param p_playerNamesToRemove contains all the player names in an array format
     *                              The method creates an iterator to iterate through d_gameplayers
     *                              It compares based on names to remove the one that matches
     */
    public static void removeGamePlayers(ArrayList<String> p_playerNamesToRemove) {
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

    /**
     * It generates a random array of integers withing the size of number of countries
     * Then assigns countries in a round-robin fashion until every country is assigned
     *
     * @param p_loadedMap is a reference to the map to help assign countries
     */

    public static void assignCountriesToPlayer(RiskMap p_loadedMap) {
        ArrayList<Integer> randomCountryIDs = new ArrayList<>();
        for (int i = 0; i < p_loadedMap.getCountries().size(); i++) {
            randomCountryIDs.add(p_loadedMap.getCountries().get(i).getDId());
        }
        if (p_loadedMap.getCountryIds().size() < d_gamePlayers.size()) {
            System.out.println("ERROR: Number of players are greater than the number of countries in the map");
            return;
        }
        Logger.log("game player size: " + d_gamePlayers.size());
        Collections.shuffle(randomCountryIDs);
        Logger.log("Countries were assigned in the following order: " + String.valueOf(randomCountryIDs));
        int l_index = 0;
        while (randomCountryIDs.size() > 0) {
            d_gamePlayers.get(l_index).assignCountry(p_loadedMap.getCountryById(randomCountryIDs.get(0)), p_loadedMap.getCountryArmyById(randomCountryIDs.get(0)));
            l_index = (l_index + 1) % d_gamePlayers.size();
            randomCountryIDs.remove(0);
        }
        for (Player player : d_gamePlayers) {
            player.assignReinforcementsToPlayer();
        }
        displayGamePlayersWithCountries(p_loadedMap);
    }

    /**
     * A method to display all the players and their countries owned respectively
     *
     * @param p_loadedMap is a reference to the map to access every country's armies
     */
    public static void displayGamePlayersWithCountries(RiskMap p_loadedMap) {
        Player l_currentPlayer = d_gamePlayers.get(d_whichPlayersTurn % d_gamePlayers.size());
        Logger.log("Displaying countries assigned to players");
        d_loadedMap = p_loadedMap;
        for (Player l_player : d_gamePlayers) {
            System.out.println(l_player.getPlayerName() + " Owns: ");
            for (Country l_country : l_player.getCountriesOwned()) {
                if(l_country == null)
                    continue;

                System.out.println(" " + d_loadedMap.getCountryById(l_country.getDId()).getName() + ", ID: "
                            + l_country.getDId() + ", Armies: " + d_loadedMap.getCountryById(l_country.getDId()).getArmy());
            }
            System.out.println();
        }
        l_currentPlayer.assignReinforcementsToPlayer();
        System.out.println("Now every player needs to deploy their reinforcements into their respective countries.");
        System.out.println(l_currentPlayer.getPlayerName() + "'s turn, Reinforcements left: " + l_currentPlayer.getAvailableReinforcements());
        displayGamePlayersCountries(l_currentPlayer);
    }

    /**
     * Displays the player's country stats
     *
     * @param p_player player to print stats for
     */
    public static void displayGamePlayersCountries(Player p_player) {
        ArrayList<Country> l_playerCountries = p_player.getCountriesOwned();
        System.out.println("The player owns following countries: ");
        for (Country value : l_playerCountries) {
            if(value==null)
                continue;
            System.out.println("ID: " + value.getDId() + "; Name: " + value.getName() + "; Armies: " + value.getArmy());
        }
    }

    /**
     * Displays the player names
     */
    public static void displayGamePlayers() {

        for (Player name : d_gamePlayers) {
            System.out.println(name.getPlayerName());
        }
    }

    /**
     * @return list of players
     */
    public static ArrayList<Player> getGamePlayers() {
        return d_gamePlayers;
    }

    /**
     * @return current player turn
     */
    public static int getPlayerTurn() {
        return d_whichPlayersTurn;
    }

    /**
     * updates player turn
     *
     * @param p_incrementValue value to update the player turn by
     */
    public static void increasePlayerTurn(int p_incrementValue) {
        d_whichPlayersTurn += p_incrementValue;
    }

    /**
     * re-assign the reinforcement values for next turn
     */
    public static void reassignValuesForNextTurn() {
        d_whichPlayersTurn = 0;
        d_commitedPlayers.clear();
        for (Player player : d_gamePlayers) {
            player.assignReinforcementsToPlayer();
            player.clearNegotiations();
        }
    }

    public static Player getCurrentPlayer(){
        Player currentPlayer = null;
        if(!PlayerHandler.getGamePlayers().isEmpty()){
            int playerIndex = d_whichPlayersTurn % PlayerHandler.getGamePlayers().size();
            currentPlayer = PlayerHandler.getGamePlayers().get(playerIndex);
        }
        return currentPlayer;
    }

    /**
     * This method returns the player object for given playerId
     *
     * @param p_playerId    playerId as an integer.
     * @return      player object
     */
    public static Player getPlayerById(int p_playerId){
        for(Player l_player : d_gamePlayers){
            if(l_player.getPlayerId() == p_playerId){
                return l_player;
            }
        }
        return null;
    }

    /**
     * @return true if this player has committed.
     */
    public static boolean isCommittedPlayer(Player p_player){
        return d_commitedPlayers.contains(p_player);
    }

    /**
     * @return no. of committed players
     */
    public static int getCommittedPlayerCount(){
        return d_commitedPlayers.size();
    }

    /**
     * marks this player as committed and done with order issue-ing.
     */
    public static void markComitted(Player p_player){
        if(isCommittedPlayer(p_player))
            return;

        d_commitedPlayers.add(p_player);
    }

    /**
     * Returns the unique uuid of the player with given name
     * used for testing.
     * @param p_name    name of the player.
     * @return  return the uuid of the player.
     */
    public static int getPlayerIDByName(String p_name){
        for (Player player : d_gamePlayers) {
            if (player.getPlayerName().equals(p_name)) {
                return player.getPlayerId();
            }
        }
        return -1;
    }

    /**
     * creates an instance of PlayerSave data with state info of PlayerHandler.
     * @return an PlayerSaveData instance.
     */
    public static PlayerSaveData getPlayerSaveData(){
        return new PlayerSaveData(d_gamePlayers, d_commitedPlayers, d_whichPlayersTurn);
    }

    /**
     * load player handler state from PlayerSaveData
     * @param p_data data to load from
     * @return
     */
    public static boolean loadFromPlayerSaveData(PlayerSaveData p_data){
        List<Player> l_playerList = new ArrayList<>(p_data.getPlayerList());
        for (Integer l_id:p_data.getCommitedPlayers()) {
            Optional<Player> l_player = l_playerList.stream().filter((l_playerRef)->l_playerRef.getPlayerId() == l_id).findFirst();
            if(!l_player.isPresent()){
                return false;
            }
            d_commitedPlayers.add(l_player.get());
        }
        d_gamePlayers = new ArrayList<>(l_playerList);
        d_whichPlayersTurn = p_data.getPlayerTurn();
        d_countriesAssigned = true;
        return true;
    }
}