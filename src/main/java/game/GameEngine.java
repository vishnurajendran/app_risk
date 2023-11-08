package game;

import common.Command;
import common.ISubApplication;
import common.Logging.Logger;
import entity.PlayerHandler;
import entity.RiskMap;
import game.Data.Context;
import game.States.GameStateFactory;
import game.States.GameStates;
import game.States.IGameState;
import mapShow.MapViewer;


/**
 * This is the game class that handles the main logic of the game
 * executes registering of commands and methods based on the commands.
 * @author Soham
 */

public class GameEngine implements ISubApplication {
    private RiskMap d_map;
    private boolean d_HasQuit;

    private GameStates d_gameState = GameStates.GameStart;

    private IGameState d_currentState;

    private boolean d_gameStarted = false;

    /**
     * default constructor
     */
    public GameEngine() {
        d_HasQuit = false;
    }

    /**
     * sets current instance of map
     */
    public void setMap(RiskMap p_map) {
        d_map = p_map;
        if(p_map == null)
            quitGame();
    }

    /**
     * @return current instance of map
     */
    public RiskMap getLoadedMap() {
        return d_map;
    }

    /**
     * quits the game.
     */
    public void quitGame() {
        d_HasQuit = true;
    }

    public GameStates getGameState() {
        return d_gameState;
    }

    /**
     * Change the state of the game
     * @param p_newState is the new state
     */
    public void changeState(GameStates p_newState) {
        Logger.log("Changing State From " + d_gameState + " >>> " + p_newState);
        d_gameState = p_newState;
        d_currentState = GameStateFactory.get(p_newState);
        d_currentState.setContext(new Context(PlayerHandler.getCurrentPlayer(), this));
    }


    @Override
    public void initialise() {
        changeState(GameStates.GameStart);
    }

    /**
     * This method checks if the application has quit due to an unexpected error
     *
     * @return a boolean which says whether the application has quit.
     */
    @Override
    public boolean hasQuit() {
        return d_HasQuit;
    }

    /**
     * Checks if the command can be processed based on the current game state.
     *
     * @param p_cmdName name of the command for validation.
     * @return returns true/false based on if the command is present in GameCommands.java
     */
    @Override
    public boolean canProcess(String p_cmdName) {

        if (p_cmdName.equals(GameCommands.CMD_SHOWMAP))
            return true;
        return d_currentState.canProcessCommand(p_cmdName);
    }

    /**
     * This method firstly checks the gamestate of game.
     * If it is deploy mode, then it runs a game loop in round-robin fashion
     * parsing through all players
     * else it checks through other commands and runs their function
     *
     * @param p_command
     */
    @Override
    public void submitCommand(Command p_command) {

        if(p_command.getCmdName().equals(GameCommands.CMD_SHOWMAP)){
            if(d_map == null)
                return;

            MapViewer.showMap(d_map);
            return;
        }

        if (d_currentState.canProcessCommand(p_command.getCmdName())) {
            d_currentState.performAction(p_command);
        }
    }

    /**
     * shuts down the game and clears all data
     * held for gameplay.
     */
    @Override
    public void shutdown() {
        PlayerHandler.cleanup();
    }

    /**
     * @return returns the instance of the map loaded.
     */
    public RiskMap getMap() {
        return d_map;
    }

    /**
     * @return true if game has started
     */
    public boolean gameStarted(){
        return d_gameStarted;
    }

    /**
     * sets the game engine to mark the start of the game.
     * @return
     */
    public void setGameStared(){
        d_gameStarted = true;
    }
}
