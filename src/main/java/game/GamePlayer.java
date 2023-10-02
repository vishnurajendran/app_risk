package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Soham
 */
public class GamePlayer {
    private static final ArrayList<Player> GamePlayers = new ArrayList<>();


    public static void addGamePlayers(ArrayList<String> p_playerNamesToAdd){
        ArrayList<String> d_duplicates = new ArrayList<>();
        for(String name: p_playerNamesToAdd){
            for(Player player : GamePlayers){
                if(player.getPlayerName().equals(name)){
                    System.out.println(name + " already exists in the game");
                    d_duplicates.add(name);
                }
            }
        }

        for(String name: d_duplicates){
            p_playerNamesToAdd.remove(name);
        }

        for(String name: p_playerNamesToAdd){
            GamePlayers.add(new Player(name));
        }
        System.out.println("Added " + p_playerNamesToAdd.size() + " players to the game:");
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

    public static void displayGamePlayers(){

        for(Player name: GamePlayers){
            System.out.println(name.getPlayerName());
        }
    }
    public ArrayList<Player> getGamePlayers() {
        return GamePlayers;
    }
}
