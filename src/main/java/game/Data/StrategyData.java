package game.Data;
import entity.Player;
import game.GameEngine;

/**
 * @author Soham
 */
public class StrategyData {

    /**
     * stores the current active player
     */
    private Player d_currentPlayer;

    /**
     * stores an instance of the game
     */
    private GameEngine d_gameEngine;


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
    public StrategyData(Player p_currentPlayer, GameEngine p_engine){
        d_currentPlayer = p_currentPlayer;
        d_gameEngine = p_engine;
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
        return d_gameEngine;
    }

}
