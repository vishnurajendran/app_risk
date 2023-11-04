package game.Data;

import entity.Player;
import game.GameEngine;
import game.States.IGameState;

/**
 * @author Soham
 */
public class Context {
    private Player d_currentPlayer;

    private GameEngine d_engine;

    public Context(){
        d_currentPlayer = null;
    }
    public Context(Player p_currentPlayer, GameEngine p_engine){
        d_currentPlayer = p_currentPlayer;
        d_engine = p_engine;
    }

    public Player getCurrentPlayer(){
        return d_currentPlayer;
    }

    public GameEngine getEngine() {
        return d_engine;
    }
}
