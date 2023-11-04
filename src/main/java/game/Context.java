package game;

import entity.Player;
import game.States.IGameState;

/**
 * @author Soham
 */
public class Context {
    protected Player d_currentPlayer;

    protected IGameState d_activeState;

    public Context(){
        d_currentPlayer = null;
        d_activeState = null;
    }
    public Context(Player p_currentPlayer, IGameState p_activeState){
        d_currentPlayer = p_currentPlayer;
        d_activeState = p_activeState;
    }

    public Player getCurrentPlayer(){
        return d_currentPlayer;
    }

    public IGameState getCurrentState(){
        return d_activeState;
    }
}
