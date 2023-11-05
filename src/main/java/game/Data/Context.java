package game.Data;

import entity.Player;
import game.GameEngine;

/**
 * @author Soham
 */
public class Context {

    /**
     * stores the current active player
     */
    private Player d_currentPlayer;

    /**
     * Instance of game engine
     */
    private GameEngine d_engine;

    public Context(){
        d_currentPlayer = null;
    }
    public Context(Player p_currentPlayer, GameEngine p_engine){
        d_currentPlayer = p_currentPlayer;
        d_engine = p_engine;
    }

    /**
     * @return current player in the game
     */
    public Player getCurrentPlayer(){
        return d_currentPlayer;
    }

    /**
     * @return the instance of game engine
     */
    public GameEngine getEngine() {
        return d_engine;
    }
}
