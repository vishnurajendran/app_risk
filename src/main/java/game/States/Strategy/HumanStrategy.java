package game.States.Strategy;

import game.Orders.Order;

/**
 * @author Soham
 */
public class HumanStrategy extends Strategy{

    /**
     * @return an order based on the strategy rules
     * in this case, it will be redirected to issue order where all the human commands are present
     */
    @Override
    public Order decide(){
        return null;
    }
}
