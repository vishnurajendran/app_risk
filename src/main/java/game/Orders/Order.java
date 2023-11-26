package game.Orders;

import entity.Player;
import entity.RiskMap;

/**
 * This Class is used to initialize orders that the player sets
 * executeOrder() Method is implemented by the child class DeployOrder
 *
 * @author Soham
 */
abstract public class Order {
    protected Player d_ctxPlayer;
    protected int d_targetCountry;
    protected int d_sourceCountry;
    protected RiskMap d_riskMap;

    /**
     * this method executes the specific behaviour of an order
     * when invoked.
     */
    public abstract void executeOrder();

    /**
     * @return OrderType enum representing the type of order.
     */
    public abstract OrderType getOrderType();

    /**
     * method override to print the name of the order
     * @return a string with order details
     */
    @Override
    public String toString() {
        return "< " + this.getClass().getSimpleName() + " >";
    }

    /**
     * @return reference of context player
     */
    public Player getCtxPlayer() {
        return d_ctxPlayer;
    }

    /**
     * @return target country id for this order.
     */
    public int getTargetCountry() {
        return d_targetCountry;
    }

    /**
     * @return source country id for this order.
     */
    public int getSourceCountry() {
        return d_sourceCountry;
    }
}
