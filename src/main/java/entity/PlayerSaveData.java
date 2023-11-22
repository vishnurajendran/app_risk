package entity;
import entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * this class holds all the data, that is needed to re-store
 * PlayerHandler, for continuing gameplay.
 * @author vishnurajendran
 */
public class PlayerSaveData {
    private List<Player> d_playerList;
    private List<Integer> d_commitedPlayers;
    private int d_playerTurn = 0;

    /**
     * default constructor,
     * sets the players, commited players to empty lists and turn to 0.
     */
    public PlayerSaveData(){
        this(new ArrayList<>(), new ArrayList<>(), 0);
    }

    /**
     * this constructor initialises the object with the required properties.
     * @param p_players players to store
     * @param p_committedPlayer committed player data to store
     * @param p_playerTurn player turn to store.
     */
    public PlayerSaveData(List<Player> p_players, List<Player> p_committedPlayer, int p_playerTurn){
        if(p_players != null)
            this.d_playerList = p_players;

        if(p_committedPlayer != null) {
            d_commitedPlayers = new ArrayList<>();
            for (Player l_player : p_committedPlayer) {
                d_commitedPlayers.add(l_player.getPlayerId());
            }
        }

        d_playerTurn = p_playerTurn;
    }

    /**
     * @return returns a list of players
     */
    public List<Player> getPlayerList() {
        return d_playerList;
    }

    /**
     * @return a list of Ids of all committed players.
     */
    public List<Integer> getCommitedPlayers() {
        return d_commitedPlayers;
    }

    public int getPlayerTurn() {
        return d_playerTurn;
    }



}
