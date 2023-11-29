package game.Data;
import Tournament.Tournament;
import entity.Player;
import game.GameEngine;
import game.IEngine;

/**
 * @author Soham
 */
public class StrategyData {

    /**
     * stores the current active player
     */
    private Player d_currentPlayer;

    /**
     * stores an instance of the game/tournament engine.
     */
    private IEngine d_engine;


    /**
     * default constructor
     */
    public StrategyData(){
        d_currentPlayer = null;
    }

    /**
     * sets the data to the variables
     * @param p_currentPlayer current active player
     * @param p_engine instance of the game engine
     */
    public StrategyData(Player p_currentPlayer, IEngine p_engine){
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
     * @return the instance of game engine or tournament engine whichever is active.
     */
    public IEngine getEngine() {
        return d_engine;
    }

}
