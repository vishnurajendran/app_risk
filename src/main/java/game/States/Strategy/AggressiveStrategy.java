package game.States.Strategy;

import entity.PlayerHandler;
import game.Orders.AdvanceOrder;
import game.Orders.Order;

/**
 * @author Soham
 */
public class AggressiveStrategy extends Strategy{

    /**
     * @return an order based on the strategy rules
     */
    @Override
    public Order decide() {
        PlayerHandler.markComitted(d_context.getCurrentPlayer());
        System.out.println("Committing advance order for: " + d_context.getCurrentPlayer().getPlayerName());
        return new AdvanceOrder(d_context.getCurrentPlayer(), 0, 1, 4, d_context.getEngine().getMap());
    }
}
