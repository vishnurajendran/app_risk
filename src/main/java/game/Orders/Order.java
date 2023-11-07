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
    protected int d_armiesToAdvance;
    protected int d_armiesToAirlift;
    protected int d_sourceCountry;

    protected RiskMap d_riskMap;

    /**
     * this method executes the specific behaviour of an order
     * when invoked.
     */
    public abstract void executeOrder();

    /**
     * method override to print the name of the order
     * @return a string with order details
     */
    @Override
    public String toString() {
        return "< " + this.getClass().getSimpleName() + " >";
    }
}
