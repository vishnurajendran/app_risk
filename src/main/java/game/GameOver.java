package game;

import common.Command;
import game.States.IGameState;

/**
 * @author Soham
 */
public class GameOver implements IGameState {
    @Override
    public void performAction(Context p_ctx, Command p_command) {

    }

    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return false;
    }

    @Override
    public void setContext(Context p_ctx) {

    }
}
