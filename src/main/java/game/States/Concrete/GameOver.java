package game.States.Concrete;

import common.Command;
import entity.Player;
import entity.PlayerHandler;
import game.Data.Context;
import game.States.GameState;
import game.States.IGameState;

/**
 * This state is the final state of the game
 * gameover prints the winner if only one player left,
 * the player is print to console and quits the game.
 * if no player is available, state just simply quits game.
 * @author Soham
 */
public class GameOver extends GameState {

    /**
     * this method overrides the setContext method
     * to print the winner or just simply quits the game.
     * @param p_ctx context to set
     */
    @Override
    public void setContext(Context p_ctx) {
        super.setContext(p_ctx);
        if(PlayerHandler.getGamePlayers().isEmpty()){
            d_context.getEngine().quitGame();
            return;
        }
        Player l_player = PlayerHandler.getGamePlayers().get(0);
        System.out.println(l_player.getPlayerName() + " has won the game!!!");
        d_context.getEngine().quitGame();
    }

    /**
     * no command can be performed in this state.
     * @param p_command command to perform
     */
    @Override
    public void performAction(Command p_command) {
        // nothing here
    }

    /**
     * returns false, as this state is command less.
     * @param p_cmdName command to check (irrelevant to this state.)
     * @return false always.
     */
    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return false;
    }

}
