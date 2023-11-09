package game.States;

import common.Command;
import game.Data.Context;

/**
 * this interface provides the methods a GameState should
 * have to be successfully used with game engine
 * @author Soham
 */
public interface IGameState {

    /**
     * this method performs the action for a command in the
     * implemented state
     * @param command command to perform
     */
    public void performAction(Command command);

    /**
     * this methods return true if p_cmdName is a valid command for
     * this state
     * @param p_cmdName command to check
     * @return true if a valid command
     */
    public boolean canProcessCommand(String p_cmdName);

    /**
     * sets the context for the state.
     * @param p_ctx context to set
     */
    public void setContext(Context p_ctx);

    /**
     * @return a help string for this state.
     */
    public String getHelp();
}
