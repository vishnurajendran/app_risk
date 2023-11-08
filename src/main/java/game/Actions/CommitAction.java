package game.Actions;

import common.Command;
import entity.PlayerHandler;

/**
 * this class aims at implementing the commit action.
 * this action basically marks a player as done issue-ing orders.
 * @author vishnurajendran
 */
public class CommitAction extends GameAction {
    @Override
    public void execute(Command p_cmd) {
        if(!d_context.getCurrentPlayer().hasOrders()){
            System.out.println("Cannot commit on a player that has no orders issued this turn");
            d_execStatus = ActionExecStatus.Fail;
            return;
        }
        PlayerHandler.markComitted(d_context.getCurrentPlayer());
    }

    @Override
    public void postExecute() {

    }
}
