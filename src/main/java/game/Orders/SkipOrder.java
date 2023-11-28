package game.Orders;

/**
 * @author Soham
 */
public class SkipOrder extends Order{
    @Override
    public void executeOrder() {
        // do nothing here, since it is meant to skip the order
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.Skip;
    }
}
