package game.States;

import common.*;
import game.Context;
import game.GameEngine;

/**
 * @author Soham
 */
public interface IGameState {

    public void performAction(GameEngine gameEngine, Command command);

    public boolean canProcessCommand(String p_cmdName);

    public void setContext(Context p_ctx);
}
