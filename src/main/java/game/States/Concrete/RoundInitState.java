package game.States.Concrete;

import common.Command;
import entity.PlayerHandler;
import game.Data.Context;
import game.States.GameState;
import game.States.GameStates;

/**
 * This state acts as the initialisation state for a new round
 * @author vishnurajendran
 */
public class RoundInitState extends GameState {

    @Override
    public void setContext(Context p_ctx) {
        super.setContext(p_ctx);
        PlayerHandler.reassignValuesForNextTurn();
        d_context.getEngine().changeState(GameStates.IssueOrder);
        issueOrderForAI();
    }

    /**
     * Handles issuing orders for AI players with strategies.
     */
    public void issueOrderForAI(){
        while(!PlayerHandler.getCurrentPlayer().isPlayerHuman() && !PlayerHandler.isCommittedPlayer(PlayerHandler.getCurrentPlayer())){
            PlayerHandler.getCurrentPlayer().issueOrder();
            PlayerHandler.increasePlayerTurn(1);
        }
        super.setContext(new Context(PlayerHandler.getCurrentPlayer(), d_context.getEngine()));
        d_context.getCurrentPlayer().setStrategyContext(d_context.getEngine());
    }

    /**
     * this state does not handle any commands, this method does nothing
     * here
     * @param command command to perform
     */
    @Override
    public void performAction(Command command) {
        //do nothing
    }

    /**
     * this method will always return false
     * as this state is not supposed to take any commands for execution
     * @param p_cmdName command to check
     * @return
     */
    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return false;
    }
}
