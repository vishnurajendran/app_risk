package game.States.Concrete;

import common.Command;
import game.Data.Context;
import game.States.GameState;
import game.States.IGameState;

/**
 * @author Soham
 */
public class GameOver extends GameState {
    @Override
    public void performAction(Command p_command) {

    }

    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return false;
    }

}
