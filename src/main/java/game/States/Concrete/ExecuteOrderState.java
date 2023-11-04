package game.States.Concrete;

import common.Command;
import common.Logging.Logger;
import entity.PlayerHandler;
import game.Data.Context;
import game.Orders.Order;
import game.States.GameState;
import game.States.GameStates;

/**
 * @author Soham
 */
public class ExecuteOrderState extends GameState {

    public ExecuteOrderState(){

    }

    @Override
    public void setContext(Context p_ctx) {
        super.setContext(p_ctx);

        executeOrders();
        //run some game logic
        //by default we shift back to game Issue Order state.
        d_context.getEngine().changeState(GameStates.IssueOrder);
    }

    public void executeOrders() {
        int l_index = 0;
        Order orderToExecute = PlayerHandler.getGamePlayers().get(0).nextOrder();
        do {
            orderToExecute.executeOrder();
            Logger.log("Executing order for: " + PlayerHandler.getGamePlayers().get(l_index % PlayerHandler.getGamePlayers().size()).getPlayerName() + ", Orders remaining: " + PlayerHandler.getGamePlayers().get(l_index % PlayerHandler.getGamePlayers().size()).getOrderSize());
            l_index = (l_index + 1) % PlayerHandler.getGamePlayers().size();
            for (int i = 0; i < PlayerHandler.getGamePlayers().size(); i++) {
                orderToExecute = PlayerHandler.getGamePlayers().get(l_index % PlayerHandler.getGamePlayers().size()).nextOrder();
                if (orderToExecute == null) {
                    l_index = (l_index + 1) % PlayerHandler.getGamePlayers().size();
                } else {
                    break;
                }
            }

        } while (orderToExecute != null);
    }

    @Override
    public void performAction(Command p_command) {
        //nothing here.
    }

    @Override
    public boolean canProcessCommand(String p_cmdName) {
        return false;
    }
}
