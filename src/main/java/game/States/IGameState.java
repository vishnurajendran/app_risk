package game.States;

import common.Command;
import game.Context;

/**
 * @author Soham
 */
public interface IGameState {

    public void performAction(Context context,  Command command);

    public boolean canProcessCommand(String p_cmdName);

    public void setContext(Context p_ctx);
}
