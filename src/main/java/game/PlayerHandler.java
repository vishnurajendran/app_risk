package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * This class handles the actions that happen to the player
 * @author Soham
 */
public class PlayerHandler {
    private static final ArrayList<Player> GamePlayers = new ArrayList<>();


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
            for(Player player : GamePlayers){
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
            GamePlayers.add(new Player(name));
        }
        System.out.println("Added " + p_playerNames.size() + " players to the game:");
    }

    /**
     * @param p_playerNamesToRemove contains all the player names in an array format
     * The method creates an iterator to iterate through d_gameplayers
     * It compares based on names to remove the one that matches
     */
    public static void removeGamePlayers(ArrayList<String> p_playerNamesToRemove){
        Iterator<Player> l_iterator = GamePlayers.iterator();
        for (String name : p_playerNamesToRemove) {
            while (l_iterator.hasNext()) {
                Player player = l_iterator.next();
                if (player.getPlayerName().equals(name)) {
                    l_iterator.remove();
                }
            }
        }
        System.out.println("The list of Game Players is: ");
    }

    public void assignCountriesToPlayer(){

    }

    public static void displayGamePlayers(){

        for(Player name: GamePlayers){
            System.out.println(name.getPlayerName());
        }
    }
    public ArrayList<Player> getGamePlayers() {
        return GamePlayers;
    }
}
