package game;

import common.*;

/**
 * @author Soham
 */
public interface IGameState {

    public void performAction(GameEngine gameEngine,Command command);

    public boolean canProcessCommand(String p_cmdName);
}
