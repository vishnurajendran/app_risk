package game.Actions;

import common.Command;
import game.Orders.SkipOrder;

/**
 * @author Soham
 */
public class SkipAction extends GameAction{
    @Override
    public void execute(Command p_cmd) {
        d_context.getCurrentPlayer().issueOrder(new SkipOrder());
    }

    @Override
    public void postExecute() {

    }
}
