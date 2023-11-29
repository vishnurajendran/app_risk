package game.Orders;

/**
 * @author Soham
 */
public class EmptyOrder extends Order{
    @Override
    public void executeOrder() {
        // do nothing here, since it is meant to skip the order
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.Empty;
    }
}
